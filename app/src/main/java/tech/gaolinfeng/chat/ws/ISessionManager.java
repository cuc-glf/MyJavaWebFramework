package tech.gaolinfeng.chat.ws;

import tech.gaolinfeng.chat.controller.TypedMessageResponse;

import javax.websocket.EncodeException;
import javax.websocket.Session;
import java.io.IOException;

/**
 * Created by gaolf on 16/10/22.
 * 纪录所有已经链接的WebSocket Session, 可以向指定的Session发送消息
 */
public interface ISessionManager {
    void onSessionOpened(Session session);
    void onSessionClosed(Session session);
    void send(TypedMessageResponse response, ISessionFilter filter) throws IOException, EncodeException;
}
