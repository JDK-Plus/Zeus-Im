package plus.jdk.zeus.common.selector;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.support.WebApplicationObjectSupport;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import plus.jdk.zeus.common.RSACipherService;
import plus.jdk.zeus.common.grpc.auth.server.GrpcServiceGlobalInterceptorConfigurer;
import plus.jdk.zeus.common.properties.ZeusGrpcAuthProperties;

@Configuration
public class ZeusGrpcAuthenticateSelector  extends WebApplicationObjectSupport implements WebMvcConfigurer {

    @Bean
    GrpcServiceGlobalInterceptorConfigurer getGrpcServiceGlobalInterceptorConfigurer(ZeusGrpcAuthProperties zeusGrpcAuthProperties, RSACipherService rsaCipherService) {
        return new GrpcServiceGlobalInterceptorConfigurer(zeusGrpcAuthProperties, rsaCipherService);
    }

    @Bean
    RSACipherService getRSACipherService(ZeusGrpcAuthProperties properties) {
        return new RSACipherService(properties.getSecrets());
    }
}