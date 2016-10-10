package tech.gaolinfeng.controller;

import org.springframework.security.core.Authentication;
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

    /**
     * 已经登录的用户不再提示请登录
     */
    @RequestMapping("/notLogin")
    public CommonResponse handleNotLogin(Authentication authentication) {
        if (authentication != null) {
            return new CommonResponse(CommonResponse.RESOURCE_NOT_FOUND, "");
        } else {
            return new CommonResponse(CommonResponse.TOKEN_ERROR, "请登录");
        }
    }

    @RequestMapping("/loginSuccess")
    public CommonResponse handleLoginSuccess() {
        return new CommonResponse(CommonResponse.CODE_OK, "登录成功");
    }

    /**
     * 已经登录的用户不要提示登录失败
     */
    @RequestMapping("/loginFailure")
    public CommonResponse handleLoginFailure(Authentication authentication) {
        if (authentication != null) {
            return new CommonResponse(CommonResponse.REQUEST_NOT_ALLOWED, "");
        } else {
            return new CommonResponse(CommonResponse.CODE_OK, "登录失败, 用户名或密码错误");
        }
    }

}
