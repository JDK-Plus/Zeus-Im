package plus.jdk.zeus.common.grpc.auth.server;

import io.grpc.*;
import lombok.AllArgsConstructor;
import org.apache.commons.codec.binary.Base64;
import org.springframework.stereotype.Component;
import plus.jdk.grpc.client.interceptor.marshaller.DefaultAsciiMarshaller;
import plus.jdk.zeus.common.RSACipherService;
import plus.jdk.zeus.common.crypt.RSA;
import plus.jdk.zeus.common.grpc.protoc.GrpcAuthToken;
import plus.jdk.zeus.common.model.ZeusRsaAuthSecret;
import plus.jdk.zeus.common.properties.ZeusGrpcAuthProperties;
import plus.jdk.zeus.common.skeleton.ZeusCommonException;

@Component
@AllArgsConstructor
public class GrpcAuthServerInterceptor implements ServerInterceptor {

    private final ZeusGrpcAuthProperties zeusGrpcAuthProperties;

    private final RSACipherService rsaCipherService;

    @Override
    public <ReqT, RespT> ServerCall.Listener<ReqT> interceptCall(ServerCall<ReqT, RespT> call, Metadata headers, ServerCallHandler<ReqT, RespT> next) {
        DefaultAsciiMarshaller marshaller = new DefaultAsciiMarshaller();
        byte[] appKeyBytes = headers.get(Metadata.Key.of("app_key", marshaller));
        byte[] tokenBytes = headers.get(Metadata.Key.of("auth_token", marshaller));
        if(appKeyBytes == null || tokenBytes == null) {
            call.close(Status.PERMISSION_DENIED, headers);
            return next.startCall(call, headers);
        }
        String appKey = new String(appKeyBytes);
        ZeusRsaAuthSecret rsaAuthSecret = rsaCipherService.getZeusRsaAuthSecret(appKey);
        if(rsaAuthSecret == null) {
            call.close(Status.PERMISSION_DENIED, headers);
            return next.startCall(call, headers);
        }
        try{
            RSA rsa = rsaCipherService.getRSAHandler(appKey);
            byte[] plainBase64Bytes = rsa.privateDecryptBytes(new String(tokenBytes));
            byte[] plainBytes = Base64.decodeBase64(plainBase64Bytes);
            GrpcAuthToken grpcAuthToken = GrpcAuthToken.parseFrom(plainBytes);
            if(!appKey.equals(grpcAuthToken.getAppKey())) {
                throw new ZeusCommonException("no permission");
            }
            long timeout = Math.abs(System.currentTimeMillis() - grpcAuthToken.getTimestamp());
            if(timeout > 5 * 60 * 1000) {
                throw new ZeusCommonException("token timeout");
            }
        }catch (Exception | Error e) {
            call.close(Status.PERMISSION_DENIED, headers);
            return next.startCall(call, headers);
        }
        return next.startCall(call, headers);
    }
}
