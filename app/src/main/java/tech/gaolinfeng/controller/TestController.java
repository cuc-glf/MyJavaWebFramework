package tech.gaolinfeng.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by gaolf on 16/9/21.
 */
@Controller
public class TestController {

    @RequestMapping("/test")
    public void test() {
        System.out.println("test ok");
    }

}
