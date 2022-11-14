package plus.jdk.zeus.message;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class ZeusMessageApplication {

    public static void main(String[] args) {
        new SpringApplicationBuilder(ZeusMessageApplication.class)
                .web(WebApplicationType.NONE)
                .run(args);
    }
}

