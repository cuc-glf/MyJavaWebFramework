package tech.gaolinfeng.chat.controller.ws.handler;

import com.fasterxml.jackson.databind.JsonNode;
import org.apache.shiro.subject.Subject;
import tech.gaolinfeng.base.entity.User;
import tech.gaolinfeng.chat.annotation.MessageHandler;
import tech.gaolinfeng.chat.controller.ws.ClientMessageType;
import tech.gaolinfeng.chat.controller.ws.TypedMessageHandler;
import tech.gaolinfeng.chat.controller.ws.TypedMessageResponse;
import tech.gaolinfeng.chat.service.IClientMessageIdService;

import javax.annotation.Resource;
import javax.websocket.Session;

/**
 * Created by gaolf on 16/10/24.
 * 获取当前消息id, 为了支持重传和避免重复处理消息, 客户端在发送需要重传且非冥等的请求时需要确保每个消息的id的唯一性.
 *
 * message: {
 *     messageType
 * }
 */
@MessageHandler("getCurrentMessageId")
public class GetCurrentMessageId extends TypedMessageHandler {

    @Resource
    private IClientMessageIdService clientMessageIdService;

    private static class GetCurrentMessageIdResponse extends TypedMessageResponse {
        public int currentMessageId;

        public GetCurrentMessageIdResponse(int currentMessageId) {
            super(ClientMessageType.CurrentMessageId);
            this.currentMessageId = currentMessageId;
        }
    }

    @Override
    public TypedMessageResponse handleMessage(Session session, JsonNode root, Subject subject) {
        User user = (User) subject.getPrincipal();
        int messageId = clientMessageIdService.getCurrentMessageId(user.getId());
        return new GetCurrentMessageIdResponse(messageId);
    }
}
