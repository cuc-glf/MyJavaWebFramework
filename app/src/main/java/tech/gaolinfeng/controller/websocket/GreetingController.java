package tech.gaolinfeng.controller.websocket;

import org.apache.shiro.subject.Subject;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.socket.config.WebSocketMessageBrokerStats;
import tech.gaolinfeng.controller.CommonResponse;
import tech.gaolinfeng.entity.User;
import tech.gaolinfeng.service.basic.IUserService;

import javax.annotation.Resource;

/**
 * Created by gaolf on 16/9/21.
 */
@RestController
public class GreetingController {

    @Resource
    private IUserService userService;

    @Resource
    private WebSocketMessageBrokerStats stats;

    private static class GreetingResponse extends CommonResponse {
        public User user;

        public GreetingResponse(String info, User user) {
            super(info);
            this.user = user;
        }
    }

    @MessageMapping("/greeting")
    @SendTo("/topic/greeting")
    public CommonResponse greeting(StompHeaderAccessor headerAccessor, String idStr) {
        Subject subject = (Subject) headerAccessor.getSessionAttributes().get("subject");
        User user = (User) subject.getPrincipal();

        System.out.println("handle greeting: " + idStr);

        return new GreetingResponse("", user);
    }


}
