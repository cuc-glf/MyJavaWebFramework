package tech.gaolinfeng.controller.websocket;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

/**
 * Created by gaolf on 16/9/28.
 * 提供聊天功能 - 只有登录后的用户可以参与聊天
 */
@Controller
public class ChatController {

    @MessageMapping
    public void receiveMessage() {

    }

}
