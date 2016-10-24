package tech.gaolinfeng.chat.ws;

/**
 * Created by gaolf on 16/10/22.
 */
import javax.websocket.Session;

/**
 * 使用该接口来指定向哪些Session发送消息
 */
public interface ISessionFilter {
    boolean filter(Session session);
}