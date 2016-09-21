package tech.gaolinfeng.service;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.gaolinfeng.entity.User;

import javax.annotation.Resource;

/**
 * Created by gaolf on 16/9/21.
 */
@Service
public class UserService {

    @Resource(name = "sqlSession")
    private SqlSession sqlSession;

    @Transactional
    public User getUserById(int id) {
        User user = sqlSession.selectOne("entity.User.selectOne", id);
        return user;
    }

}
