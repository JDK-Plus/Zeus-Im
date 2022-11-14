package plus.jdk.zeus.common;

import plus.jdk.zeus.common.crypt.RSA;
import plus.jdk.zeus.common.model.ZeusRsaAuthSecret;
import plus.jdk.zeus.common.skeleton.CollectionUtil;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.HashMap;
import java.util.List;

public class RSACipherService {

    private HashMap<String, ZeusRsaAuthSecret> secretHashMap = new HashMap<>();

    public RSACipherService(List<ZeusRsaAuthSecret> secrets) {
        if (CollectionUtil.isEmpty(secrets)) {
            return;
        }
        secretHashMap = CollectionUtil.toHashMap(secrets, ZeusRsaAuthSecret::getAppKey);
    }

    public ZeusRsaAuthSecret getZeusRsaAuthSecret(String appKey) {
        return secretHashMap.get(appKey);
    }

    public RSA getRSAHandler(String appKey) throws NoSuchAlgorithmException, InvalidKeySpecException {
        ZeusRsaAuthSecret rsaSecret = secretHashMap.get(appKey);
        if(rsaSecret == null) {
            return null;
        }
        return new RSA(rsaSecret.getPublicKey(), rsaSecret.getPrivateKey());
    }
}
