package plus.jdk.zeus.cli.message;

import plus.jdk.cli.JCommandLinePlus;
import plus.jdk.cli.annotation.CommandLinePlus;
import plus.jdk.cli.annotation.CommandParameter;

@CommandLinePlus(description = "消息发送 & 接受")
public class ZeusMessageInstruction extends JCommandLinePlus {

    @CommandParameter(name = "h", longName = "help", needArgs = false, description = "展示帮助信息")
    private Boolean help;

    @Override
    protected void doInCommand() throws Exception {
        showUsage();
    }
}