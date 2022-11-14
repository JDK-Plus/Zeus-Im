package plus.jdk.zeus.cli;

import plus.jdk.cli.JCommandLinePlus;
import plus.jdk.cli.annotation.CommandLinePlus;
import plus.jdk.cli.annotation.CommandParameter;
import plus.jdk.cli.annotation.SubInstruction;
import plus.jdk.zeus.cli.crypt.RsaKeyCreateInstruction;
import plus.jdk.zeus.cli.message.ZeusMessageInstruction;
import plus.jdk.zeus.cli.passport.ZeusPassportInstruction;

@CommandLinePlus(description = "这是一个用于Zeus-Im功能测试的指令集")
public class ZeusCliApplication extends JCommandLinePlus {

    @CommandParameter(name = "h", longName = "help", needArgs = false, description = "展示帮助信息")
    private Boolean help;

    @SubInstruction
    @CommandParameter(name = "passport", longName = "passport", needArgs = false, description = "用户账号管理")
    private ZeusPassportInstruction zeusPassportInstruction;

    @SubInstruction
    @CommandParameter(name = "message", longName = "message", needArgs = false, description = "消息发送 & 接收")
    private ZeusMessageInstruction zeusMessageInstruction;

    @SubInstruction
    @CommandParameter(name = "rsaKeyCreate", longName = "rsaKeyCreate", needArgs = false, description = "生成rsa的公钥和私钥")
    private RsaKeyCreateInstruction rsaKeyCreateInstruction;

    @Override
    protected void doInCommand() throws Exception {
        showUsage();
    }

    public static void main(String[] args) throws Exception {
        new ZeusCliApplication().run(args);
    }
}
