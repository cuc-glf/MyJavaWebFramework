package tech.gaolinfeng.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import tech.gaolinfeng.entity.User;
import tech.gaolinfeng.service.UserService;

import javax.annotation.Resource;

/**
 * Created by gaolf on 16/9/21.
 */
@RestController
@RequestMapping("/helloWorld")
public class HelloWorldController {

    @Resource
    private UserService userService;

    @RequestMapping(method = RequestMethod.GET)
    public User helloWorld() {
        return userService.getUserById(1);
    }

}
