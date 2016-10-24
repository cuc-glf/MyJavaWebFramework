package tech.gaolinfeng.chat.controller.ws;

/**
 * Created by gaolf on 16/10/22.
 * Server给客户端发送的消息
 */
public abstract class TypedMessageResponse {
    public String messageType;

    public TypedMessageResponse(String messageType) {
        this.messageType = messageType;
    }
}
