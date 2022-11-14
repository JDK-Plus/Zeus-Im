package plus.jdk.zeus.common.selector;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import plus.jdk.zeus.common.global.SpringContext;

@Configuration
public class ZeusCommonSelector {

    @Bean
    public SpringContext getSpringContext(){
        return new SpringContext();
    }
}
