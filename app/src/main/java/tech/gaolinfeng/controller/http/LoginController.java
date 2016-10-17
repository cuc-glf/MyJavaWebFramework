package tech.gaolinfeng.controller.http;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import tech.gaolinfeng.controller.CommonResponse;
import tech.gaolinfeng.entity.User;
import tech.gaolinfeng.service.IUserService;

import javax.annotation.Resource;

/**
 * Created by gaolf on 16/10/10.
 * 使用shiro处理用户的登录注册
 * 不得不说shiro比spring security要小巧多了, 没有做一些不必要的事情, 更容易理解
 */
@RestController
public class LoginController {

    @Resource
    private IUserService userService;

    // 登录处理
    @RequestMapping(value = "/unauthorized", method = RequestMethod.GET)
    public CommonResponse unauthorizedGet() {
        return new CommonResponse(CommonResponse.TOKEN_ERROR, "无访问权限");
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public CommonResponse loginGet() {
        return new CommonResponse(CommonResponse.TOKEN_ERROR, "请登录");
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public CommonResponse loginPost(@RequestParam String username,
                                    @RequestParam String password) {
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        try {
            subject.login(token);
        } catch (UnknownAccountException | IncorrectCredentialsException e) {
            e.printStackTrace();
            return new CommonResponse(CommonResponse.TOKEN_ERROR, "用户名或密码错误");
        } catch (AuthenticationException e) {
            e.printStackTrace();
            return new CommonResponse(CommonResponse.TOKEN_ERROR, "其他错误: " + e.getMessage());
        }
        return new CommonResponse("登录成功");
    }

    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    public CommonResponse logout() {
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        return new CommonResponse("已退出登录");
    }

    private static final class WhoAmIResponse extends CommonResponse {
        public User user;

        public WhoAmIResponse(String info, User user) {
            super(info);
            this.user = user;
        }
    }

    @RequestMapping(value = "whoami", method = RequestMethod.GET)
    public CommonResponse whoAmI() {
        Subject subject = SecurityUtils.getSubject();
        if (subject.isAuthenticated()) {
            User user = (User) subject.getPrincipal();
            return new WhoAmIResponse(user.getName(), user);
        } else {
            return new WhoAmIResponse("anonymous", null);
        }
    }

    private static final class CheckUserResponse extends CommonResponse {
        public boolean usernameAllowed;

        public CheckUserResponse(String info, boolean usernameAllowed) {
            super(info);
            this.usernameAllowed = usernameAllowed;
        }
    }

    @RequestMapping(value = "checkUsername", method = RequestMethod.POST)
    public CommonResponse checkUsername(@RequestParam String username) {
        User user = userService.getUserByName(username);
        if (user != null) {
            return new CheckUserResponse("用户名已存在", false);
        } else {
            return new CheckUserResponse("", true);
        }
    }



}
