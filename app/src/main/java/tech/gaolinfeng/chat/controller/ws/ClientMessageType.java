package tech.gaolinfeng.chat.controller.ws;

/**
 * Created by gaolf on 16/10/22.
 * websocket由Server发送给Client的消息类型
 */
public class ClientMessageType {

    /**
     * 通用的消息接收确认
     */
    public static final String MessageReceived = "MessageReceived";

    /**
     * 公共聊天室有新消息
     */
    public static final String PublicMessage = "PublicMessage";
    /**
     * 公共聊天室历史消息
     */
    public static final String PublicMessageHistory = "PublicMessageHistory";

    /**
     * 获取当前消息id
     */
    public static final String CurrentMessageId = "CurrentMessageId";
}
