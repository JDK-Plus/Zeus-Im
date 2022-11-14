package plus.jdk.zeus.passport;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class ZeusPassportApplication {

    public static void main(String[] args) {
        new SpringApplicationBuilder(ZeusPassportApplication.class)
//                .web(WebApplicationType.NONE)
                .build()
                .run(args);
    }
}
