package tech.gaolinfeng.controller.websocket;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

/**
 * Created by gaolf on 16/9/21.
 */
@Controller
public class GreetingController {

    @MessageMapping("/greeting")
    @SendTo("/topic/greeting")
    public String handle(String greeting) {
        System.out.println("handle greeting: " + greeting);
        return "received greeting: " + greeting;
    }

}
