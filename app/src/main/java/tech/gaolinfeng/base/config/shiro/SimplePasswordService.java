package tech.gaolinfeng.base.config.shiro;

import org.apache.shiro.crypto.RandomNumberGenerator;
import org.apache.shiro.crypto.hash.Sha512Hash;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * Created by gaolf on 16/10/11.
 */
@Component("passwordService")
public class SimplePasswordService implements ISimplePasswordService {

    @Resource
    private RandomNumberGenerator secureNumberGenerator;

    public String generateSalt() {
        return secureNumberGenerator.nextBytes().toString();
    }

    public String encryptPassword(String password, String salt) {
        return new Sha512Hash(password, salt).toString();
    }

}
