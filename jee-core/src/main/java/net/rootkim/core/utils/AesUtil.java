package net.rootkim.core.utils;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.springframework.util.StringUtils;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.Security;
import java.security.spec.AlgorithmParameterSpec;
import java.util.Base64;


/**
 * Aes加解密工具类
 *
 * @author RootKim[net.rootkim]
 * @since 2024-2-16
 */
public class AesUtil {

    /**
     *
     * 解决java不支持AES/CBC/PKCS7Padding模式解密
     *
     */
    static {
        Security.addProvider(new BouncyCastleProvider());
    }

    private static final String ENCODING = "UTF-8";

    private static final String AES_ALGORITHM = "AES";

    private static final String CIPHER_PADDING = "AES/ECB/PKCS5Padding";

    /**
     * AES加密（加密模式：ECB，填充：PKCS5Padding，数据块：128位，输出：base64，字符集：UTF8）
     *
     * @param content   待加密内容
     * @param aesSecret 密码（必须是16位）
     * @return 加密后的数据
     */
    public static String encrypt(String content, String aesSecret) throws UnsupportedEncodingException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        if (StringUtils.hasText(aesSecret) && aesSecret.length() == 16) {
            byte[] bytes = aesSecret.getBytes(ENCODING);
            SecretKeySpec secretKeySpec = new SecretKeySpec(bytes, AES_ALGORITHM);
            Cipher cipher = Cipher.getInstance(CIPHER_PADDING);
            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);
            byte[] encrypted = cipher.doFinal(content.getBytes(ENCODING));
            return Base64.getEncoder().encodeToString(encrypted);
        } else {
            return null;
        }
    }

    /**
     * 解密（加密模式：ECB，填充：PKCS5Padding，数据块：128位，输出：base64，字符集：UTF8）
     *
     * @param content   待解密内容
     * @param aesSecret 密码（必须是16位）
     * @return 解密后的数据
     */
    public static String decrypt(String content, String aesSecret) throws UnsupportedEncodingException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        if (StringUtils.hasText(aesSecret) && aesSecret.length() == 16) {
            byte[] bytes = aesSecret.getBytes(ENCODING);
            SecretKeySpec skeySpec = new SecretKeySpec(bytes, AES_ALGORITHM);
            Cipher cipher = Cipher.getInstance(CIPHER_PADDING);
            cipher.init(Cipher.DECRYPT_MODE, skeySpec);

            byte[] decodeBase64 = Base64.getDecoder().decode(content);

            byte[] decrypted = cipher.doFinal(decodeBase64);
            return new String(decrypted, ENCODING);
        } else {
            return null;
        }
    }


    /**
     * 解密ts（加密模式：CBC，填充：PKCS7Padding，数据块：128位，输出：byte[]，字符集：UTF8）
     * @param src
     * @param aesSecret
     * @return
     * @throws UnsupportedEncodingException
     * @throws NoSuchPaddingException
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeyException
     * @throws IllegalBlockSizeException
     * @throws BadPaddingException
     */
    public static byte[] decryptTs(byte[] src, String aesSecret) throws UnsupportedEncodingException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException {
        if (StringUtils.hasText(aesSecret) && aesSecret.length() == 16) {
            SecretKeySpec skeySpec = new SecretKeySpec(aesSecret.getBytes(ENCODING), AES_ALGORITHM);
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding");
            AlgorithmParameterSpec paramSpec = new IvParameterSpec(new byte[16]);
            cipher.init(Cipher.DECRYPT_MODE, skeySpec,paramSpec);

            return cipher.doFinal(src);
        } else {
            return null;
        }
    }
}
