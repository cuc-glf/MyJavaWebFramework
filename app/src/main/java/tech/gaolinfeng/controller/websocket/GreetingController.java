package tech.gaolinfeng.controller.websocket;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.socket.config.WebSocketMessageBrokerStats;
import tech.gaolinfeng.entity.User;
import tech.gaolinfeng.service.IUserService;

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

    @MessageMapping("/greeting")
    @SendTo("/topic/greeting")
    public User handle(String idStr) {

        System.out.println("handle greeting: " + idStr);
        String out = null;
        User user = null;
        try {
            int id = Integer.parseInt(idStr);
            user = userService.getUserById(id);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return user;

    }

}
