package tech.gaolinfeng.base.service.basic;

import java.util.List;

/**
 * Created by gaolf on 16/10/11.
 */
public interface IUserRoleService {

    List<String> getUserRoles(int userId);

    List<String> getUserRolesByName(String username);

    void createUserRole(int userId, String role);
}
