package tech.gaolinfeng.config.shiro;

/**
 * Created by gaolf on 16/10/11.
 */
public interface ISimplePasswordService {

    String generateSalt();

    String encryptPassword(String password, String salt);

}
