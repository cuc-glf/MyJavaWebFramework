package tech.gaolinfeng.controller.websocket;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.RestController;
import tech.gaolinfeng.controller.CommonResponse;
import tech.gaolinfeng.entity.User;

/**
 * Created by gaolf on 16/9/28.
 * 提供聊天功能 - 只有登录后的用户可以参与聊天
 */
@RestController
public class ChatController {

    private static class NewMessageResponse extends CommonResponse {
        public NewMessageResponse(String info, String sender, String content) {
            super(info);
            this.sender = sender;
            this.content = content;
        }
        public String sender;
        public String content;
    }

    @MessageMapping("/sendToPublic")
    @SendTo("/public")
    public NewMessageResponse handleNewMessage(String content) {
        Subject subject = SecurityUtils.getSubject();
        String username = ((User)subject.getPrincipal()).getName();
        return new NewMessageResponse("", username, content);
    }

}
