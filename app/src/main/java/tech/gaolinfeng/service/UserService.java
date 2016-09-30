package tech.gaolinfeng.service;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.gaolinfeng.entity.User;
import tech.gaolinfeng.util.TextUtils;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

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

    @Transactional
    public boolean createUser(User user) {
        return sqlSession.insert("entity.User.insert", user) == 1;
    }

    @Transactional
    public boolean updateUserInfoByIdOrName(int id, String name, Date birth, int gender) {
        Map<String, Object> objectMap = new HashMap<>();
        if (id > 0) {
            objectMap.put("id", id);
        } else if (!TextUtils.isEmpty(name)) {
            objectMap.put("name", name);
        } else {
            return false;
        }

        if (birth != null) {
            objectMap.put("birth", birth);
        }
        if (gender == User.MALE || gender == User.FEMALE) {
            objectMap.put("gender", gender);
        }
        // 注意这里根据提供的参数不同, 需要产生的sql也不同
        // 这时候需要用到mybatis的动态sql的特性了
        return sqlSession.update("entity.User.updateUserInfoByIdOrName", objectMap) == 1;
    }

    @Transactional
    public User getUserByName(String userName) {
        Map<String, Object> objectMap = new HashMap<>();
        objectMap.put("name", userName);
        return sqlSession.selectOne("entity.User.selectOneByName", objectMap);
    }
}
