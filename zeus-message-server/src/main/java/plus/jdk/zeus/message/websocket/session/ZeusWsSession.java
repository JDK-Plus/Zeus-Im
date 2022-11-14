package plus.jdk.zeus.message.websocket.session;

import io.netty.channel.Channel;
import lombok.Data;
import plus.jdk.websocket.model.IWsSession;

@Data
public class ZeusWsSession implements IWsSession<String> {

    /**
     * 用户id
     */
    private String userId;

    /**
     *
     */
    private Channel channel;
}
