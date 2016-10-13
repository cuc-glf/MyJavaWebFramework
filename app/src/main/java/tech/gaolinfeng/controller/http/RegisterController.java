package tech.gaolinfeng.controller.http;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import tech.gaolinfeng.controller.CommonResponse;
import tech.gaolinfeng.service.IUserService;
import tech.gaolinfeng.service.advanced.IAuthService;
import tech.gaolinfeng.util.TextUtils;

import javax.annotation.Resource;

/**
 * Created by gaolf on 16/10/11.
 */
@RestController
public class RegisterController {

    @Resource
    private IAuthService authService;

    @Resource
    private IUserService userService;

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public CommonResponse register(@RequestParam String username,
                                   @RequestParam String password,
                                   @RequestParam String phone) {

        if (!TextUtils.validatePhoneNumber(phone)) {
            return new CommonResponse(CommonResponse.PARAM_NOT_ALLOWED, "手机号码错误");
        }
        if (!TextUtils.validateUserName(username)) {
            return new CommonResponse(CommonResponse.PARAM_NOT_ALLOWED, "用户名非法");
        }
        if (!TextUtils.validateUserPasswd(password)) {
            return new CommonResponse(CommonResponse.PARAM_NOT_ALLOWED, "密码过于简单");
        }

        if (userService.getUserByName(username) != null) {
            return new CommonResponse(CommonResponse.PARAM_NOT_ALLOWED, "用户名已被注册");
        }
        if (userService.getuserByPhone(phone) != null) {
            return new CommonResponse(CommonResponse.PARAM_NOT_ALLOWED, "手机号已被注册");
        }

        authService.basicRegister(username, password, phone);
        return new CommonResponse("注册成功");
    }



}
