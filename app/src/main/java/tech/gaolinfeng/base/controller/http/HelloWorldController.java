package tech.gaolinfeng.base.controller.http;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import tech.gaolinfeng.base.entity.User;
import tech.gaolinfeng.base.service.basic.IUserService;

import javax.annotation.Resource;

/**
 * Created by gaolf on 16/9/21.
 */
@RestController
public class HelloWorldController {

    @Resource
    private IUserService userService;

    @RequestMapping(value = "/helloWorld", method = RequestMethod.GET)
    public User helloWorld(@RequestParam int id) {
        return userService.getUserById(id);
    }

}
