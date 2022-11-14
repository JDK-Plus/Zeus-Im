package plus.jdk.zeus.common.grpc.auth.server;

import io.grpc.ServerInterceptor;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import plus.jdk.grpc.global.GrpcServiceInterceptorConfigurer;
import plus.jdk.zeus.common.RSACipherService;
import plus.jdk.zeus.common.properties.ZeusGrpcAuthProperties;

import java.util.List;

@AllArgsConstructor
public class GrpcServiceGlobalInterceptorConfigurer implements GrpcServiceInterceptorConfigurer {

    private final ZeusGrpcAuthProperties zeusGrpcAuthProperties;

    private final RSACipherService rsaCipherService;

    @Override
    public void configureServerInterceptors(List<ServerInterceptor> interceptors) {
        GrpcAuthServerInterceptor grpcAuthServerInterceptor =
                new GrpcAuthServerInterceptor(zeusGrpcAuthProperties, rsaCipherService);
        interceptors.add(grpcAuthServerInterceptor);
    }
}
