package tech.gaolinfeng.base.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by gaolf on 16/9/28.
 * 和业务无关的通用的Controller
 */
@RestController
public class CommonController {

    @RequestMapping("/notFound")
    public CommonResponse handleResourceNotFound() {
        return CommonResponse.createResourceNotFoundResponse();
    }


    @RequestMapping("/loginSuccess")
    public CommonResponse handleLoginSuccess() {
        return new CommonResponse(CommonResponse.CODE_OK, "登录成功");
    }


}
