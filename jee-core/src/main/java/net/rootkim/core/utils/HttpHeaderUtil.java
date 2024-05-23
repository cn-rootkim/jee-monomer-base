package net.rootkim.core.utils;

import net.rootkim.core.domain.bo.Platform;
import org.springframework.http.server.reactive.ServerHttpRequest;

import javax.servlet.http.HttpServletRequest;

/**
 * HttpHeader工具类
 * @author RootKim[net.rootkim]
 * @since 2024/3/11
 */
public class HttpHeaderUtil {
    /**
     * Http Header Token Key
     */
    public static final String TOKEN_KEY = "token";

    /**
     * Http Header User-Agent
     */
    public static final String USER_AGENT_KEY = "User-Agent";

    /**
     * Http Header USER-ID
     */
    public static final String USER_ID_KEY = "USER-ID";

    /**
     * Http Header USER_TYPE
     */
    public static final String USER_TYPE_KEY = "USER_TYPE";

    /**
     * 获取客户端平台
     * @param userAgent
     * @return 平台枚举
     */
    public static Platform getClientPlatform(String userAgent){
        String lowerCaseUserAgent = userAgent.toLowerCase();
        if (lowerCaseUserAgent.contains("windows phone")){
            return Platform.WINDOWS_PHONE;
        }else if (lowerCaseUserAgent.contains("android")) {
            return Platform.ANDROID;
        }else if(lowerCaseUserAgent.contains("iphone") || lowerCaseUserAgent.contains("ipad")){
            return Platform.IOS;
        }else {
            return Platform.PC;
        }
    }

    /**
     * 获取客户端平台
     * @param request
     * @return 平台枚举
     */
    public static Platform getClientPlatform(HttpServletRequest request){
        String userAgent = request.getHeader(USER_AGENT_KEY).toLowerCase();
        return getClientPlatform(userAgent);
    }

    /**
     * 获取客户端平台
     * @param request
     * @return 平台枚举
     */
    public static Platform getClientPlatformGateWay(ServerHttpRequest request){
        String userAgent = request.getHeaders().getFirst(USER_AGENT_KEY).toLowerCase();
        return getClientPlatform(userAgent);
    }
}
