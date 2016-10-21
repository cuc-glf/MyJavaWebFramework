package tech.gaolinfeng.base.service.basic;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.gaolinfeng.base.entity.User;
import tech.gaolinfeng.base.exceptions.ServiceRuntimeException;
import tech.gaolinfeng.base.util.CollectionUtil;

import javax.annotation.Resource;

/**
 * Created by gaolf on 16/9/21.
 * 用户信息管理
 */
@Service
public class UserService implements IUserService {

    @Resource(name = "sqlSession")
    private SqlSession sqlSession;

    @Override
    @Transactional
    public User getUserById(int id) {
        User user = sqlSession.selectOne("entity.User.selectOne", id);
        return user;
    }

    @Override
    @Transactional
    public User getUserByName(String userName) {
        return sqlSession.selectOne("entity.User.selectOneByName", CollectionUtil.asMap("name", userName));
    }

    @Override
    @Transactional
    public User getUserByEmail(String email) {
        return sqlSession.selectOne("entity.User.selectOneByEmail", CollectionUtil.asMap("email", email));
    }

    @Override
    @Transactional
    public Object getuserByPhone(String phone) {
        return sqlSession.selectOne("entity.User.selectOneByPhone", CollectionUtil.asMap("phone", phone));
    }

    @Override
    @Transactional
    public void createUser(User user) {
        int affectedRows = sqlSession.insert("entity.User.insert", user);
        if (affectedRows == 0) {
            throw new ServiceRuntimeException("创建用户错误, 用户名, 手机或邮箱已存在");
        }
    }

}
