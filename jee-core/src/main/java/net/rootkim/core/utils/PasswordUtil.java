package net.rootkim.core.utils;

import net.rootkim.core.constant.EncryptSecretConstant;
import net.rootkim.core.exception.ParamException;
import org.springframework.util.StringUtils;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

/**
 * 密码相关工具类
 *
 * @author RootKim[rootkim.net]
 * @since 2024/4/29
 */
public class PasswordUtil {

    public static String encrypt(String password) throws UnsupportedEncodingException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        return AesUtil.encrypt(password, EncryptSecretConstant.AES_SECRET);
    }

    public static String decrypt(String password) throws UnsupportedEncodingException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        return AesUtil.decrypt(password, EncryptSecretConstant.AES_SECRET);
    }

    public static void check(String password) {
        if (!StringUtils.hasText(password)) {
            throw new ParamException("密码不可为空");
        } else {
            if (password.length() < 6 || password.length() > 20) {
                throw new ParamException("密码长度必须在6-20位之间");
            }
        }
    }
}
