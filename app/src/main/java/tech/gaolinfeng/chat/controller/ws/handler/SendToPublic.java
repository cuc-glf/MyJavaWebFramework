package tech.gaolinfeng.chat.controller.ws.handler;

import com.fasterxml.jackson.databind.JsonNode;
import org.apache.shiro.subject.Subject;
import tech.gaolinfeng.base.entity.User;
import tech.gaolinfeng.base.util.TextUtils;
import tech.gaolinfeng.chat.annotation.MessageHandler;
import tech.gaolinfeng.chat.controller.ws.TypedMessageHandler;
import tech.gaolinfeng.chat.controller.ws.TypedMessageResponse;
import tech.gaolinfeng.chat.controller.ws.handler.response.SendToPublicResponse;
import tech.gaolinfeng.chat.entity.PublicChatMessage;
import tech.gaolinfeng.chat.service.IPublicChatMessageService;
import tech.gaolinfeng.chat.ws.ISessionManager;
import tech.gaolinfeng.chat.ws.MatchAllSessionFilter;

import javax.annotation.Resource;
import javax.websocket.Session;
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

    @Override
    public TypedMessageResponse handleMessage(Session session, JsonNode root, Subject subject) {
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
