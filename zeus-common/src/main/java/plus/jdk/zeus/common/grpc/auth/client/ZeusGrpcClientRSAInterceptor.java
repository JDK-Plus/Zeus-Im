package plus.jdk.zeus.common.grpc.auth.client;

import io.grpc.*;
import lombok.SneakyThrows;
import org.apache.commons.codec.binary.Base64;
import plus.jdk.grpc.client.interceptor.marshaller.DefaultAsciiMarshaller;
import plus.jdk.zeus.common.RSACipherService;
import plus.jdk.zeus.common.crypt.RSA;
import plus.jdk.zeus.common.grpc.protoc.GrpcAuthToken;
import plus.jdk.zeus.common.skeleton.IdGenerator;

import java.util.HashMap;

public class ZeusGrpcClientRSAInterceptor implements ClientInterceptor {

    private final String appKey;

    private final RSACipherService rsaCipherService;

    public ZeusGrpcClientRSAInterceptor(String appKey, RSACipherService rsaCipherService) {
        this.appKey = appKey;
        this.rsaCipherService = rsaCipherService;
    }

    @Override
    public <ReqT, RespT> ClientCall<ReqT, RespT> interceptCall(MethodDescriptor<ReqT, RespT> method, CallOptions callOptions, Channel next) {
        return new ForwardingClientCall.SimpleForwardingClientCall<ReqT, RespT>(next.newCall(method, callOptions)) {
            @SneakyThrows
            @Override
            public void start(Listener<RespT> responseListener, Metadata headers) {
                DefaultAsciiMarshaller marshaller = new DefaultAsciiMarshaller();
                long timestamp = System.currentTimeMillis();
                HashMap<String, Object> parameters = new HashMap<>();
                headers.put(Metadata.Key.of("app_key", marshaller), appKey.getBytes());
                RSA rsa = rsaCipherService.getRSAHandler(appKey);
                GrpcAuthToken.Builder builder = GrpcAuthToken.newBuilder();
                builder.setAppKey(appKey);
                builder.setTimestamp(timestamp);
                builder.setTraceId(String.valueOf(IdGenerator.getId()));
                GrpcAuthToken grpcAuthToken = builder.build();
                String plainText = Base64.encodeBase64String(grpcAuthToken.toByteArray());
                String cipherText = rsa.publicEncrypt(plainText);
                headers.put(Metadata.Key.of("auth_token", marshaller), cipherText.getBytes());
                super.start(responseListener, headers);
            }
        };
    }
}
