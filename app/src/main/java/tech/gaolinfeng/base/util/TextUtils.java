package tech.gaolinfeng.base.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by gaolf on 16/9/22.
 */
public class TextUtils {

    public static final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
    public static final Pattern VALID_PHONE_NUMBER_REGEX =
            Pattern.compile("\\d{11}");

    private TextUtils() {
        // 避免有人创建这个类的对象
    }

    /**
     * 通用的判断字符串是否存在(非空指针且不为空串)
     */
    public static boolean isEmpty(String string) {
        if (string == null || string.isEmpty()) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 通用的判断两个字符串是否相等, 如果任意一个字符串是空串或null, 都认为不相等
     */
    public static boolean equals(String str1, String str2) {
        return !(isEmpty(str1) || isEmpty(str2)) && str1.equals(str2);
    }

    public static boolean validateEmail(String emailStr) {
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX .matcher(emailStr);
        return matcher.find();
    }

    public static boolean validatePhoneNumber(String phoneNumberStr) {
        Matcher matcher = VALID_PHONE_NUMBER_REGEX.matcher(phoneNumberStr);
        return matcher.find();
    }

    public static boolean validateUserName(String name) {
        return !isEmpty(name) && name.length() < 20;
    }

    public static boolean validateUserPasswd(String passwd) {
        return !isEmpty(passwd) && passwd.length() >= 6;
    }
}
