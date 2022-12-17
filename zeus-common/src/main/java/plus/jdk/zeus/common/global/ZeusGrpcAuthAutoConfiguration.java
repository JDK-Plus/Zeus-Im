package plus.jdk.zeus.common.global;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import plus.jdk.zeus.common.annotation.EnableZeusGrpcAuthenticate;
import plus.jdk.zeus.common.properties.ZeusGrpcProperties;

@Slf4j
@Configuration
@EnableZeusGrpcAuthenticate
@ConditionalOnProperty(prefix = "plus.jdk.zeus.grpc.auth", name = "enabled", havingValue = "true")
@EnableConfigurationProperties(ZeusGrpcProperties.class)
public class ZeusGrpcAuthAutoConfiguration {

}
