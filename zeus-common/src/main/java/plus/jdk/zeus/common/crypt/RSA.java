package plus.jdk.zeus.common.crypt;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.IOUtils;
import plus.jdk.zeus.common.crypt.model.RsaStringKeyPair;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

@Slf4j
public class RSA {

    public static final String CHARSET = "UTF-8";
    public static final String RSA_ALGORITHM = "RSA";

    private RSAPublicKey publicKey;

    private RSAPrivateKey privateKey;

    /**
     * @param publicKey RSA公钥
     */
    public RSA(RSAPublicKey publicKey) {
        this.publicKey = publicKey;
    }

    /**
     * @param privateKey RSA私钥
     */
    public RSA(RSAPrivateKey privateKey) {
        this.privateKey = privateKey;
    }

    /**
     *
     * @param publicKey RSA公钥
     * @param privateKey RSA私钥
     */
    public RSA(String publicKey, String privateKey) throws NoSuchAlgorithmException, InvalidKeySpecException {
        if (publicKey != null) {
            this.publicKey = getPublicKey(publicKey);
        }
        if (privateKey != null) {
            this.privateKey = getPrivateKey(privateKey);
        }
    }

    /**
     *
     * @param keyPair RSA公钥、私钥内容
     */
    public RSA(RsaStringKeyPair keyPair) throws NoSuchAlgorithmException, InvalidKeySpecException {
        this(keyPair.getPublicKey(), keyPair.getPrivateKey());
    }

    /**
     * 生成一对RSA的公钥和私钥
     * @param keySize key长度
     * @return RSA的公钥和私钥
     */
    public static RsaStringKeyPair createKeys(int keySize) {
        KeyPairGenerator kpg;
        try {
            kpg = KeyPairGenerator.getInstance(RSA_ALGORITHM);
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalArgumentException("No such algorithm-->[" + RSA_ALGORITHM + "]");
        }

        //初始化KeyPairGenerator对象,密钥长度
        kpg.initialize(keySize);
        //生成密匙对
        KeyPair keyPair = kpg.generateKeyPair();
        //得到公钥
        Key publicKey = keyPair.getPublic();
        String publicKeyStr = Base64.encodeBase64URLSafeString(publicKey.getEncoded()); //返回一个 publicKey 经过base64encode的字符串
        //得到私钥
        Key privateKey = keyPair.getPrivate();
        String privateKeyStr = Base64.encodeBase64URLSafeString(privateKey.getEncoded());// 返回一个 privateKey 经过base64encode的字符串
        return new RsaStringKeyPair(publicKeyStr, privateKeyStr);
    }

    /**
     * 得到公钥
     * @return 获取RSA公钥对象
     */
    public static RSAPublicKey getPublicKey(String publicKey) throws NoSuchAlgorithmException, InvalidKeySpecException {
        //通过X509编码的Key指令获得公钥对象
        KeyFactory keyFactory = KeyFactory.getInstance(RSA_ALGORITHM);
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(Base64.decodeBase64(publicKey));
        return (RSAPublicKey) keyFactory.generatePublic(x509KeySpec);
    }

    /**
     * 获取私钥
     * @return 获取RSA私钥钥对象
     */
    public static RSAPrivateKey getPrivateKey(String privateKey) throws NoSuchAlgorithmException, InvalidKeySpecException {
        //通过PKCS#8编码的Key指令获得私钥对象
        KeyFactory keyFactory = KeyFactory.getInstance(RSA_ALGORITHM);
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(Base64.decodeBase64(privateKey));
        return (RSAPrivateKey) keyFactory.generatePrivate(pkcs8KeySpec);
    }

    /**
     * RSA公钥加密
     * @return 公钥加密密文
     */
    public String publicEncrypt(String data) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, UnsupportedEncodingException {
        Cipher cipher = Cipher.getInstance(RSA_ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        return Base64.encodeBase64URLSafeString(rsaSplitCodec(cipher, Cipher.ENCRYPT_MODE, data.getBytes(CHARSET), publicKey.getModulus().bitLength()));
    }

    /**
     * RSA私钥解密
     * @return 私钥解密后的内容
     */
    public String privateDecrypt(String data) throws NoSuchPaddingException, NoSuchAlgorithmException, UnsupportedEncodingException, InvalidKeyException {
        Cipher cipher = Cipher.getInstance(RSA_ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        return new String(rsaSplitCodec(cipher, Cipher.DECRYPT_MODE, Base64.decodeBase64(data), privateKey.getModulus().bitLength()), CHARSET);
    }

    /**
     * 私钥加密
     * @return 私钥加密密文
     */
    public String privateEncrypt(String data) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, UnsupportedEncodingException {
        Cipher cipher = Cipher.getInstance(RSA_ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, privateKey);
        return Base64.encodeBase64URLSafeString(rsaSplitCodec(cipher, Cipher.ENCRYPT_MODE, data.getBytes(CHARSET), privateKey.getModulus().bitLength()));
    }

    /**
     * @param data 输入内容
     * @return 公钥解密后的内容
     */
    public String publicDecrypt(String data) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, UnsupportedEncodingException {
        Cipher cipher = Cipher.getInstance(RSA_ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, publicKey);
        return new String(rsaSplitCodec(cipher, Cipher.DECRYPT_MODE, Base64.decodeBase64(data), publicKey.getModulus().bitLength()), CHARSET);
    }

    /**
     * 公钥解密
     */
    public byte[] publicDecryptBytes(String data) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException {
        Cipher cipher = Cipher.getInstance(RSA_ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, publicKey);
        return rsaSplitCodec(cipher, Cipher.DECRYPT_MODE, Base64.decodeBase64(data), publicKey.getModulus().bitLength());
    }


    /**
     * 私钥解密
     */
    public byte[] privateDecryptBytes(String data) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException {
        Cipher cipher = Cipher.getInstance(RSA_ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        return rsaSplitCodec(cipher, Cipher.DECRYPT_MODE, Base64.decodeBase64(data), privateKey.getModulus().bitLength());
    }

    /**
     *
     * @param cipher a cryptographic cipher for encryption and decryption.
     * @param opMode 选择加密还是解密
     * @param dataBytes 输入数据
     * @param keySize key的长度
     * @return 处理后的内容
     */
    private byte[] rsaSplitCodec(Cipher cipher, int opMode, byte[] dataBytes, int keySize) {
        int maxBlock = 0;
        if (opMode == Cipher.DECRYPT_MODE) {
            maxBlock = keySize / 8;
        } else {
            maxBlock = keySize / 8 - 11;
        }
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;
        byte[] buff;
        int i = 0;
        try {
            while (dataBytes.length > offSet) {
                if (dataBytes.length - offSet > maxBlock) {
                    buff = cipher.doFinal(dataBytes, offSet, maxBlock);
                } else {
                    buff = cipher.doFinal(dataBytes, offSet, dataBytes.length - offSet);
                }
                out.write(buff, 0, buff.length);
                i++;
                offSet = i * maxBlock;
            }
        } catch (Exception e) {
            throw new RuntimeException("crypt data quota [" + maxBlock + "] error", e);
        }
        byte[] resultDataBytes = out.toByteArray();
        IOUtils.closeQuietly(out);
        return resultDataBytes;
    }

    public static void main(String[] args) throws NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException, UnsupportedEncodingException, InvalidKeyException {
        RsaStringKeyPair keyPair = RSA.createKeys(512);
        String plainText = "哈哈哈哈哈收拾收拾";
        RSA rsa = new RSA(keyPair);
        String privateEncryptText = rsa.privateEncrypt(plainText);
        String publicDecryptText = rsa.publicDecrypt(privateEncryptText);
        String publicEncryptText = rsa.publicEncrypt(plainText);
        String privateDecryptText = rsa.privateDecrypt(publicEncryptText);
        log.info("{}", keyPair);
    }
}
