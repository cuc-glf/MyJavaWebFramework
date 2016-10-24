package tech.gaolinfeng.chat.ws;

import tech.gaolinfeng.chat.controller.ws.TypedMessageResponse;

import javax.websocket.PongMessage;
import javax.websocket.Session;

/**
 * Created by gaolf on 16/10/22.
 * 纪录所有已经链接的WebSocket Session, 可以向指定的Session发送消息
 */
public interface ISessionManager {
    int PING_INTERVAL = 1000 * 20;
    void onSessionOpened(Session session);
    void onSessionClosed(Session session);
    void send(TypedMessageResponse response, ISessionFilter filter);
    void onPongMessage(Session session, PongMessage pongMessage);
}
