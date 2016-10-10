package tech.gaolinfeng.controller.http;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import tech.gaolinfeng.controller.CommonResponse;
import tech.gaolinfeng.entity.User;
import tech.gaolinfeng.service.TokenService;
import tech.gaolinfeng.service.UserService;

import javax.annotation.Resource;

/**
 * Created by gaolf on 16/9/28.
 * 管理登录验证和账号注册
 */
@RestController
public class UserController {

    @Resource
    private UserService userService;

    @Resource
    private TokenService tokenService;

    private static class QueryResult extends CommonResponse {
        public User user;

        public QueryResult(int code, String info, User user) {
            super(code, info);
            this.user = user;
        }
    }

    private static class LoginResult extends CommonResponse {
        public String token;

        public LoginResult(int code, String info, String token) {
            super(code, info);
            this.token = token;
        }
    }

    @RequestMapping(value = "/user/{id}", method = RequestMethod.GET)
    public CommonResponse handleQuery(@PathVariable int id) {
        User user = userService.getUserById(id);
        return new QueryResult(CommonResponse.CODE_OK, "", user);
    }

//    @RequestMapping(value = "/user/update", method = {RequestMethod.PUT, RequestMethod.POST})
//    public CommonResponse handleUpdate(@RequestParam(required = false) Date birth,
//                                       @RequestParam(defaultValue = "0") int gender) {
//        if (birth == null && gender != User.MALE && gender != User.FEMALE) {
//            return new CommonResponse(CommonResponse.PARAM_NOT_ALLOWED, "没有可更新的信息");
//        }
//
//        boolean success;
//        success = userService.updateUserInfoByIdOrName(id, null, birth, gender);
//        if (success) {
//            return new CommonResponse(CommonResponse.CODE_OK, "用户信息更新成功");
//        } else {
//            return new CommonResponse(CommonResponse.SERVER_INTERNAL_ERROR, "用户信息更新失败");
//        }
//    }
//
//    @RequestMapping(value = "/user/register", method = RequestMethod.POST)
//    public CommonResponse handleRegister(@RequestParam String userName,
//                                         @RequestParam String email,
//                                         @RequestParam String phone,
//                                         @RequestParam String passwd,
//                                         @RequestParam(required = false) Date birth,
//                                         @RequestParam(defaultValue = "0") int gender) {
//
//        if (!TextUtils.validateEmail(email)) {
//            return new CommonResponse(CommonResponse.PARAM_NOT_ALLOWED, "电子邮箱格式错误");
//        }
//        if (!TextUtils.validatePhoneNumber(phone)) {
//            return new CommonResponse(CommonResponse.PARAM_NOT_ALLOWED, "手机号码错误");
//        }
//        if (!TextUtils.validateUserName(userName)) {
//            return new CommonResponse(CommonResponse.PARAM_NOT_ALLOWED, "用户名非法");
//        }
//        if (!TextUtils.validateUserPasswd(passwd)) {
//            return new CommonResponse(CommonResponse.PARAM_NOT_ALLOWED, "密码签名错误");
//        }
//
//        User user = new User(userName, phone, email, birth, gender, passwd);
//
//        boolean success = false;
//        try {
//            success = userService.createUser(user);
//        } catch (DuplicateKeyException e) {
//            return new CommonResponse(CommonResponse.PARAM_NOT_ALLOWED, "无法创建用户, 邮箱, 用户名或手机号已被占用");
//        }
//        if (success) {
//            return new CommonResponse(CommonResponse.CODE_OK, "注册新用户成功");
//        } else {
//            return new CommonResponse(CommonResponse.SERVER_INTERNAL_ERROR, "注册失败, 请稍后重试");
//        }
//    }

//    @RequestMapping(value = "/user/login", method = RequestMethod.POST)
//    public CommonResponse handleLogin(@RequestParam String name, @RequestParam String passwd) {
//        if (!TextUtils.validateUserName(name)) {
//            return new CommonResponse(CommonResponse.PARAM_NOT_ALLOWED, "用户名不存在");
//        }
//        if (!TextUtils.validateUserPasswd(passwd)) {
//            return new CommonResponse(CommonResponse.PARAM_NOT_ALLOWED, "密码错误");
//        }
//
//        User user = userService.getUserByName(name);
//        if (user == null) {
//            return new CommonResponse(CommonResponse.PARAM_NOT_ALLOWED, "用户不存在");
//        }
//        if (!TextUtils.equals(user.getPasswd(), passwd)) {
//            return new CommonResponse(CommonResponse.PARAM_NOT_ALLOWED, "密码错误");
//        }
//
//        String token = SessionIdentifierGenerator.randomString(32);
//        tokenService.updateToken(user.getId(), token);
//        return new LoginResult(CommonResponse.CODE_OK, "", token);
//    }
//
//    @RequestMapping(value = "/user/logout", method = RequestMethod.POST)
//    public CommonResponse handleLogout(@RequestParam String token) {
//        tokenService.clearToken(token);
//        return new CommonResponse(CommonResponse.CODE_OK, "");
//    }

}
