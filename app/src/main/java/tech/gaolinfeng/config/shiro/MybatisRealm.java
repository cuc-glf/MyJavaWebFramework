package tech.gaolinfeng.config.shiro;

import org.apache.ibatis.datasource.DataSourceException;
import org.apache.shiro.authc.*;
import org.apache.shiro.authc.pam.UnsupportedTokenException;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import tech.gaolinfeng.entity.User;
import tech.gaolinfeng.service.basic.IUserRoleService;
import tech.gaolinfeng.service.basic.IUserService;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

/**
 * Created by gaolf on 16/10/10.
 */
public class MybatisRealm extends AuthorizingRealm {

    @Resource
    private IUserRoleService userRoleService;

    @Resource
    private IUserService userService;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {

        //null usernames are invalid
        if (principals == null) {
            throw new AuthorizationException("PrincipalCollection method argument cannot be null.");
        }

        User user = (User) getAvailablePrincipal(principals);
        String username = user.getName();
        List<String> userRoleList;
        try {
            userRoleList = userRoleService.getUserRolesByName(username);
        } catch (DataSourceException e) {
            e.printStackTrace();
            return new SimpleAuthorizationInfo(Collections.emptySet());
        }
        return new SimpleAuthorizationInfo(new HashSet<>(userRoleList));
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        if (!(token instanceof UsernamePasswordToken)) {
            throw new UnsupportedTokenException();
        } else {
            UsernamePasswordToken usernamePasswordToken = (UsernamePasswordToken) token;
            String username = usernamePasswordToken.getUsername();

            // Null username is invalid
            if (username == null) {
                throw new AccountException("Null usernames are not allowed by this realm.");
            }

            User user = userService.getUserByName(username);
            if (user == null) {
                throw new UnknownAccountException("No account found for user [" + username + "]");
            }

            return new SimpleAuthenticationInfo(user, user.getPasswd(), ByteSource.Util.bytes(user.getSalt()), getName());
        }

    }
}
