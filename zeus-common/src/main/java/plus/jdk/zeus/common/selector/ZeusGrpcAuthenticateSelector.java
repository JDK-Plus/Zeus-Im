package plus.jdk.zeus.common.selector;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.support.WebApplicationObjectSupport;
import plus.jdk.zeus.common.RSACipherService;
import plus.jdk.zeus.common.grpc.auth.client.ClientInterceptorGlobalConfigurer;
import plus.jdk.zeus.common.grpc.auth.client.GlobalNameResolverConfigurer;
import plus.jdk.zeus.common.grpc.auth.server.GrpcServiceGlobalInterceptorConfigurer;
import plus.jdk.zeus.common.properties.ZeusGrpcProperties;

@Configuration
public class ZeusGrpcAuthenticateSelector  extends WebApplicationObjectSupport {

    @Bean
    GrpcServiceGlobalInterceptorConfigurer getGrpcServiceGlobalInterceptorConfigurer(ZeusGrpcProperties zeusGrpcProperties, RSACipherService rsaCipherService) {
        return new GrpcServiceGlobalInterceptorConfigurer(zeusGrpcProperties, rsaCipherService);
    }

    @Bean
    RSACipherService getRSACipherService(ZeusGrpcProperties properties) {
        return new RSACipherService(properties.getSecrets());
    }

    @Bean
    ClientInterceptorGlobalConfigurer getClientInterceptorGlobalConfigurer(ZeusGrpcProperties properties, RSACipherService rsaCipherService) {
        return new ClientInterceptorGlobalConfigurer(properties, rsaCipherService);
    }

    @Bean
    GlobalNameResolverConfigurer getGlobalNameResolverConfigurer(ZeusGrpcProperties properties) {
        return new GlobalNameResolverConfigurer(properties);
    }
}