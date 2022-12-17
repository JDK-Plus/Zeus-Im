package plus.jdk.zeus.common.grpc.auth.server;

import io.grpc.ServerInterceptor;
import lombok.AllArgsConstructor;
import plus.jdk.grpc.global.GrpcServiceInterceptorConfigurer;
import plus.jdk.zeus.common.RSACipherService;
import plus.jdk.zeus.common.properties.ZeusGrpcProperties;

import java.util.List;

@AllArgsConstructor
public class GrpcServiceGlobalInterceptorConfigurer implements GrpcServiceInterceptorConfigurer {

    private final ZeusGrpcProperties zeusGrpcProperties;

    private final RSACipherService rsaCipherService;

    @Override
    public void configureServerInterceptors(List<ServerInterceptor> interceptors) {
        GrpcAuthServerInterceptor grpcAuthServerInterceptor =
                new GrpcAuthServerInterceptor(zeusGrpcProperties, rsaCipherService);
        interceptors.add(grpcAuthServerInterceptor);
    }
}
