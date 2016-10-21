package tech.gaolinfeng.base.service.advanced;

import org.springframework.transaction.annotation.Transactional;

/**
 * Created by gaolf on 16/10/11.
 * 用户注册Service
 */
public interface IAuthService {

    /**
     * 注册用户基本信息, 注册时需要生成并纪录散列盐
     */
    @Transactional
    void basicRegister(String username, String rawPasswd, String phone);

}
