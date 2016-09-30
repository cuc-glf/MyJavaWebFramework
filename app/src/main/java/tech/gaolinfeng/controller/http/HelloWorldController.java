package tech.gaolinfeng.controller.http;

import org.springframework.web.bind.annotation.*;
import tech.gaolinfeng.entity.User;
import tech.gaolinfeng.service.UserService;

import javax.annotation.Resource;

/**
 * Created by gaolf on 16/9/21.
 */
@RestController
public class HelloWorldController {

    @Resource
    private UserService userService;

    @RequestMapping(value = "/helloWorld", method = RequestMethod.GET)
    public User helloWorld(@RequestParam int id) {
        return userService.getUserById(id);
    }

}
