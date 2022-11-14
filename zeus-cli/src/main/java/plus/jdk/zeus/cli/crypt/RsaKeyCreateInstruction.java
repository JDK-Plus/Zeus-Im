package plus.jdk.zeus.cli.crypt;

import lombok.extern.slf4j.Slf4j;
import plus.jdk.cli.JCommandLinePlus;
import plus.jdk.cli.annotation.CommandLinePlus;
import plus.jdk.cli.annotation.CommandParameter;
import plus.jdk.zeus.common.crypt.RSA;
import plus.jdk.zeus.common.crypt.model.RsaStringKeyPair;

@Slf4j
@CommandLinePlus(description = "生成rsa秘钥对", usage="usage:zeus-cli -rsa -h")
public class RsaKeyCreateInstruction extends JCommandLinePlus {

    @CommandParameter(name = "keySize", longName = "keySize", needArgs = true, description = "秘钥长度", required = true)
    private Integer keySize;

    @CommandParameter(name = "h", longName = "help", needArgs = false, description = "展示帮助信息")
    private Boolean help;

    @Override
    protected void doInCommand() throws Exception {
        if(help) {
            showUsage();
            return;
        }
        this.validate();
        if(keySize < 512) {
            System.out.println("秘钥长度必须超过512");
            return;
        }
        RsaStringKeyPair rsaStringKeyPair = RSA.createKeys(keySize);
        System.out.printf("PUBLIC_KEY=%s \n", rsaStringKeyPair.getPublicKey());
        System.out.printf("PRIVATE_KEY=%s \n", rsaStringKeyPair.getPrivateKey());
    }
}
