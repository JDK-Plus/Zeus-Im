package plus.jdk.zeus.common.grpc.auth.client;

import io.grpc.ClientInterceptor;
import plus.jdk.grpc.global.GrpcClientInterceptorConfigurer;
import plus.jdk.zeus.common.RSACipherService;
import plus.jdk.zeus.common.properties.ZeusGrpcProperties;

import java.util.List;

public class ClientInterceptorGlobalConfigurer  implements GrpcClientInterceptorConfigurer {

    private final ZeusGrpcProperties properties;

    private final RSACipherService rsaCipherService;

    public ClientInterceptorGlobalConfigurer(ZeusGrpcProperties properties, RSACipherService rsaCipherService) {
        this.properties = properties;
        this.rsaCipherService = rsaCipherService;
    }


    @Override
    public void configureClientInterceptors(List<ClientInterceptor> interceptors) {
        interceptors.add(new ZeusGrpcClientRSAInterceptor(properties.getAppKey(), rsaCipherService));
    }
}
