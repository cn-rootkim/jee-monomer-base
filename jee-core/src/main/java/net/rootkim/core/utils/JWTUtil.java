package net.rootkim.core.utils;

import cn.hutool.core.util.IdUtil;
import cn.hutool.jwt.JWT;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/**
 * JWT工具类
 *
 * @author RootKim[net.rootkim]
 * @since 2024/3/10
 */
public class JWTUtil {

    /**
     * 生成JWTToken
     *
     * @param secret   密钥
     * @param userId   用户id
     * @param userType 用户类型
     * @return JWTToken
     */
    public static String create(String secret, String userId, Byte userType) {
        Map<String, Object> payload = new HashMap<>();
        payload.put("id", IdUtil.fastSimpleUUID());
        payload.put("userId", userId);
        payload.put("userType", userType);
        return cn.hutool.jwt.JWTUtil.createToken(payload, secret.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * 获取用户id
     *
     * @param JWTToken
     * @return
     */
    public static String getUserId(String JWTToken) {
        JWT jwt = cn.hutool.jwt.JWTUtil.parseToken(JWTToken);
        return jwt.getPayload("userId").toString();
    }

    /**
     * 获取用户类型
     *
     * @param JWTToken
     * @return
     */
    public static String getUserType(String JWTToken) {
        JWT jwt = cn.hutool.jwt.JWTUtil.parseToken(JWTToken);
        return jwt.getPayload("userType").toString();
    }
}
