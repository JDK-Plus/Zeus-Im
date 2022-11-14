package plus.jdk.zeus.message.websocket.handler;

import io.netty.handler.codec.http.FullHttpRequest;
import plus.jdk.websocket.annotations.OnWsHandshake;
import plus.jdk.websocket.annotations.OnWsOpen;
import plus.jdk.websocket.annotations.WebsocketHandler;
import io.netty.channel.Channel;
import io.netty.handler.codec.http.HttpHeaders;
import org.springframework.web.bind.annotation.RequestParam;
import plus.jdk.websocket.annotations.*;
import plus.jdk.zeus.message.websocket.session.ZeusWsSession;


@WebsocketHandler(values = {"/ws/message"})
public class UserMessageHandler {

    @OnWsHandshake
    public void doHandshake(FullHttpRequest req, ZeusWsSession session) {
    }

    @OnWsOpen
    public void onOpen(Channel channel, FullHttpRequest req, ZeusWsSession session, HttpHeaders headers, @RequestParam String uid) {
        session.sendText("onOpen" + System.currentTimeMillis());
    }

    @OnWsMessage
    public void onWsMessage(ZeusWsSession session, String data) {
        session.sendText("onWsMessage" + System.currentTimeMillis());
        session.sendText("receive data" + data);
        session.sendText("onWsMessage, id:" + session.getChannel().id().asShortText());
    }

    @OnWsEvent
    public void onWsEvent(Object data, ZeusWsSession session) {
        session.sendText("onWsEvent" + System.currentTimeMillis());
    }

    @OnWsBinary
    public void OnWsBinary(ZeusWsSession session, byte[] data) {
        session.sendText("OnWsBinary" + System.currentTimeMillis());
    }

    @OnWsError
    public void onWsError(ZeusWsSession session, Throwable throwable) {
        session.sendText("onWsError" + throwable.getMessage());
    }

    @OnWsClose
    public void onWsClose(ZeusWsSession session){
        session.sendText("onWsClose" + System.currentTimeMillis());
    }
}