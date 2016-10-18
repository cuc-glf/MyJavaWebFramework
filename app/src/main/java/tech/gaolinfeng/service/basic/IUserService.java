package tech.gaolinfeng.service.basic;

import tech.gaolinfeng.entity.User;

/**
 * Created by gaolf on 16/10/11.
 */
public interface IUserService {

    /**
     * 根据id拿到用户信息
     */
    User getUserById(int id);

    /**
     * 根据用户姓名获取用户信息
     */
    User getUserByName(String userName);

    /**
     * 根据用户邮箱获取用户信息
     */
    User getUserByEmail(String email);

    /**
     * 根据用户手机号获取用户信息
     */
    Object getuserByPhone(String phone);

    /**
     * 创建新用户
     */
    void createUser(User user);

}
