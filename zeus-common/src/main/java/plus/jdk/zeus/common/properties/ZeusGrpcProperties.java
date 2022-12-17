package plus.jdk.zeus.common.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import plus.jdk.zeus.common.model.ZeusRsaAuthSecret;

import java.util.List;

@Data
@ConfigurationProperties(prefix = "plus.jdk.zeus.grpc.auth")
public class ZeusGrpcProperties {

    /**
     * 是否启用
     */
    private boolean enabled = false;

    /**
     * 要使用哪一个appKey做验证
     */
    private String appKey;

    /**
     * 授权秘钥列表
     */
    public List<ZeusRsaAuthSecret> secrets;
}
