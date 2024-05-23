package net.rootkim.core.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;

import java.util.HashMap;

/**
 * JWT工具类
 * @author RootKim[net.rootkim]
 * @since 2024/3/10
 */
public class JWTUtil {

    /**
     * 生成JWTToken
     * @param secret 密钥
     * @param userId 用户id
     * @param userType 用户类型
     * @return JWTToken
     */
    public static String create(String secret, String userId, Byte userType){
        HashMap<String, Object> headers = new HashMap<>();
        return JWT.create()
                // 第一部分Header
                .withHeader(headers)
                .withJWTId(IDUtil.getUUID32())
                // 第二部分Payload
                .withClaim("userId", userId)
                .withClaim("userType", userType.toString())
                // 第三部分Signature
                .sign(Algorithm.HMAC256(secret));
    }

    /**
     * 获取用户id
     * @param secret 密钥
     * @param JWTToken
     * @return
     */
    public static String getUserId(String secret, String JWTToken){
        JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(secret)).build();
        DecodedJWT verify = jwtVerifier.verify(JWTToken);
        return verify.getClaim("userId").asString();
    }

    /**
     * 获取用户类型
     * @param secret 密钥
     * @param JWTToken
     * @return
     */
    public static String getUserType(String secret, String JWTToken){
        JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(secret)).build();
        DecodedJWT verify = jwtVerifier.verify(JWTToken);
        return verify.getClaim("userType").asString();
    }
}
