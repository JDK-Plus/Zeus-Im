package plus.jdk.zeus.common.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import plus.jdk.zeus.common.model.ZeusRsaAuthSecret;

import java.util.List;

@Data
@ConfigurationProperties(prefix = "plus.jdk.zeus.grpc.auth")
public class ZeusGrpcAuthProperties {

    /**
     * 是否启用
     */
    private boolean enabled = false;

    /**
     * 授权秘钥列表
     */
    public List<ZeusRsaAuthSecret> secrets;
}
