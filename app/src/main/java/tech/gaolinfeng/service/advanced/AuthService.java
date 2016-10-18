package tech.gaolinfeng.service.advanced;

import org.apache.shiro.util.ByteSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.gaolinfeng.config.shiro.ISimplePasswordService;
import tech.gaolinfeng.entity.User;
import tech.gaolinfeng.service.basic.IUserService;

import javax.annotation.Resource;

/**
 * Created by gaolf on 16/10/11.
 */
@Service
public class AuthService implements IAuthService {

    @Resource
    private IUserService userService;

    @Resource
    private ISimplePasswordService passwordService;

    @Transactional
    public void basicRegister(String username, String rawPasswd, String phone) {
        // 生成随机盐
        String salt = passwordService.generateSalt();
        String passwd = passwordService.encryptPassword(rawPasswd, ByteSource.Util.bytes(salt).toString());

        User user = new User();
        user.setPasswd(passwd);
        user.setSalt(salt);
        user.setName(username);
        user.setPhone(phone);

        userService.createUser(user);
    }

}
