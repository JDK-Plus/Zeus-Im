package plus.jdk.zeus.common.model;

import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
public class ZeusRsaAuthSecret {

    /**
     * 被授权的服务的业务号
     */
    private String appKey;

    /**
     * RSA 私钥（用于服务方解密）
     */
    private String privateKey;

    /**
     * RSA 公钥（用于调用方数据加密）
     */
    private String publicKey;

    /**
     * 白名单，当前业务有调用哪些服务接口的调用权限
     */
    private Set<String> whiteList = new HashSet<>();
}
