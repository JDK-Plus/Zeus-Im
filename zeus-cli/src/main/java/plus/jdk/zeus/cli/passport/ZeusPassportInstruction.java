package plus.jdk.zeus.cli.passport;

import plus.jdk.cli.JCommandLinePlus;
import plus.jdk.cli.annotation.CommandLinePlus;
import plus.jdk.cli.annotation.CommandParameter;
import plus.jdk.cli.annotation.SubInstruction;

@CommandLinePlus(description = "注册一个用户信息")
public class ZeusPassportInstruction extends JCommandLinePlus {

    @CommandParameter(name = "h", longName = "help", needArgs = false, description = "展示帮助信息")
    private Boolean help;

    @SubInstruction
    @CommandParameter(name = "register", longName = "register", needArgs = false, description = "注册一个用户")
    private UserRegisterInstruction userRegisterInstruction;

    @Override
    protected void doInCommand() throws Exception {
        showUsage();
    }
}
