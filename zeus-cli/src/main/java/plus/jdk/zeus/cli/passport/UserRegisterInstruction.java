package plus.jdk.zeus.cli.passport;

import plus.jdk.cli.JCommandLinePlus;
import plus.jdk.cli.annotation.CommandLinePlus;
import plus.jdk.cli.annotation.CommandParameter;


@CommandLinePlus(description = "注册一个用户信息")
public class UserRegisterInstruction extends JCommandLinePlus {

    @CommandParameter(name = "h", longName = "help", needArgs = false, description = "展示帮助信息")
    private Boolean help;

    /**
     * 用户名
     */
    @CommandParameter(name = "userName", longName = "userName", needArgs = false, description = "用户名", required = true)
    private String userName;

    /**
     * 手机号
     */
    @CommandParameter(name = "password", longName = "password", needArgs = false, description = "用户密码", required = false)
    private String password;


    @Override
    protected void doInCommand() throws Exception {
        if(help) {
            showUsage();
            return;
        }
        this.validate();
    }
}
