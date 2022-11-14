package plus.jdk.zeus.message.websocket.session;


import io.netty.channel.Channel;
import io.netty.handler.codec.http.FullHttpRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import plus.jdk.broadcast.model.Monitor;
import plus.jdk.websocket.global.IWSSessionAuthenticatorManager;
import plus.jdk.websocket.model.IWsSession;
import plus.jdk.websocket.properties.WebsocketProperties;

@Slf4j
@Component
public class ZeusWSSessionAuthenticator implements IWSSessionAuthenticatorManager<String, ZeusWsSession> {

    @Override
    public ZeusWsSession authenticate(Channel channel, FullHttpRequest req, String path, WebsocketProperties properties) throws Exception {
        ZeusWsSession wsSession = new ZeusWsSession();
        wsSession.setChannel(channel);
        wsSession.setUserId("");
        return wsSession;
    }

    @Override
    public void onSessionDestroy(IWsSession<?> session, String path, WebsocketProperties properties) {
        IWSSessionAuthenticatorManager.super.onSessionDestroy(session, path, properties);
    }

    @Override
    public Monitor[] getUserConnectedMachine(String userId, String path, WebsocketProperties properties) {
        return IWSSessionAuthenticatorManager.super.getUserConnectedMachine(userId, path, properties);
    }

    @Override
    public Monitor[] getAllUdpMonitors(WebsocketProperties properties) {
        return IWSSessionAuthenticatorManager.super.getAllUdpMonitors(properties);
    }
}
