package tech.gaolinfeng.service;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.gaolinfeng.exceptions.ServiceRuntimeException;

import javax.annotation.Resource;
import java.util.List;

import static tech.gaolinfeng.util.CollectionUtil.asMap;

/**
 * Created by gaolf on 16/10/11.
 */
@Service
public class UserRoleService implements IUserRoleService{
    @Resource
    private SqlSession session;

    @Override
    @Transactional
    public List<String> getUserRoles(int userId) {
        return session.selectList("entity.UserRole.selectByUserId",
                asMap("userId", userId));
    }

    @Override
    @Transactional
    public List<String> getUserRolesByName(String username) {
        return session.selectList("entity.UserRole.selectByUserName",
                asMap("username", username));
    }

    @Override
    @Transactional
    public void createUserRole(int userId, String role) {
        int affectedRows = session.insert("entity.UserRole.insert",
                asMap("userId", userId, "role", role));
        if (affectedRows == 0) {
            throw new ServiceRuntimeException("创建用户角色错误, 用户角色已存在");
        }
    }
}
