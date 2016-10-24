package tech.gaolinfeng.chat.controller.handler;

import com.fasterxml.jackson.databind.JsonNode;
import org.apache.shiro.subject.Subject;
import tech.gaolinfeng.base.entity.User;
import tech.gaolinfeng.base.util.TextUtils;
import tech.gaolinfeng.chat.annotation.MessageHandler;
import tech.gaolinfeng.chat.controller.ClientMessageType;
import tech.gaolinfeng.chat.controller.TypedMessageHandler;
import tech.gaolinfeng.chat.controller.TypedMessageResponse;
import tech.gaolinfeng.chat.entity.PublicChatMessage;
import tech.gaolinfeng.chat.service.IPublicChatMessageService;
import tech.gaolinfeng.chat.util.WebSocketUtil;
import tech.gaolinfeng.chat.ws.ISessionManager;
import tech.gaolinfeng.chat.ws.MatchAllSessionFilter;

import javax.annotation.Resource;
import javax.websocket.EncodeException;
import javax.websocket.Session;
import java.io.IOException;
import java.util.Date;

/**
 * Created by gaolf on 16/10/22.
 * 公共聊天室发言
 */
@MessageHandler("sendToPublic")
public class SendToPublic extends TypedMessageHandler {

    @Resource
    private ISessionManager sessionManager;

    @Resource
    private IPublicChatMessageService publicChatMessageService;

    private static class SendToPublicResponse extends TypedMessageResponse {
        public String content;      // 发言内容
        public Date time;           // 发言时间
        public String senderName;   // 发言用户名

        public SendToPublicResponse(String content, Date time, String senderName) {
            super(ClientMessageType.PublicMessage);

            this.content = content;
            this.time = time;
            this.senderName = senderName;
        }
    }

    @Override
    public TypedMessageResponse handleMessage(Session session, JsonNode root) throws IOException, EncodeException {
        Subject subject = WebSocketUtil.getSubjectAndTouch(session);
        User user = (User) subject.getPrincipal();
        String message = root.get("content").asText();
        if (TextUtils.isEmpty(message)) {
            return null;
        }
        publicChatMessageService.addMessage(new PublicChatMessage(user.getId(), new Date(), message));
        sessionManager.send(new SendToPublicResponse(message, new Date(), user.getName()),
                new MatchAllSessionFilter());
        return null;
    }
}
