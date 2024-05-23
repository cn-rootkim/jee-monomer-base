package net.rootkim.core.utils;

/**
 * 校验工具类
 *
 * @author RootKim[rootkim.net]
 * @since 2024/5/2
 */
public class ValidUtil {

    public static boolean isValidPhoneNumber(String phoneNumber) {
        String regex = "^1[3-9]\\d{9}$";
        return phoneNumber.matches(regex);
    }
}
