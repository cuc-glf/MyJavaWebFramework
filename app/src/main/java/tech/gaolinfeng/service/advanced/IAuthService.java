package tech.gaolinfeng.service.advanced;

import org.springframework.transaction.annotation.Transactional;

/**
 * Created by gaolf on 16/10/11.
 */
public interface IAuthService {

    /**
     * 注册用户基本信息
     */
    @Transactional
    void basicRegister(String username, String rawPasswd, String phone);

}
