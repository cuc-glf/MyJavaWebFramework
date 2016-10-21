package tech.gaolinfeng.base.config.shiro;

import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.springframework.stereotype.Component;
import tech.gaolinfeng.base.util.TextUtils;

import javax.annotation.Resource;

/**
 * Created by gaolf on 16/10/10.
 */
@Component("credentialsMatcher")
public class Md5SaltCredentialsMatcher implements CredentialsMatcher {

    @Resource
    private ISimplePasswordService passwordService;

    @Override
    public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {
        SimpleAuthenticationInfo simpleAuthenticationInfo = (SimpleAuthenticationInfo) info;

        String rawPasswd = String.valueOf((char[]) token.getCredentials());
        String salt = simpleAuthenticationInfo.getCredentialsSalt().toString();
        String passwd = passwordService.encryptPassword(rawPasswd, salt);

        String savedPasswd = (String) simpleAuthenticationInfo.getCredentials();
        return TextUtils.equals(savedPasswd, passwd);
    }
}
