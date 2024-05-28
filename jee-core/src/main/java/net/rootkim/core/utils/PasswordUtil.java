package net.rootkim.core.utils;

import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import net.rootkim.core.constant.EncryptSecretConstant;
import net.rootkim.core.exception.ParamException;
import java.nio.charset.StandardCharsets;

/**
 * 密码相关工具类
 *
 * @author RootKim[rootkim.net]
 * @since 2024/4/29
 */
public class PasswordUtil {

    public static String encrypt(String password) {
        return StrUtil.str(SecureUtil.aes(EncryptSecretConstant.AES_SECRET.getBytes(StandardCharsets.UTF_8)).encrypt(password), StandardCharsets.UTF_8);
    }

    public static String decrypt(String password) {
        return SecureUtil.aes(EncryptSecretConstant.AES_SECRET.getBytes(StandardCharsets.UTF_8)).decryptStr(password);
    }

    public static void check(String password) {
        if (StrUtil.isBlank(password)) {
            throw new ParamException("密码不可为空");
        } else {
            if (password.length() < 6 || password.length() > 20) {
                throw new ParamException("密码长度必须在6-20位之间");
            }
        }
    }
}
