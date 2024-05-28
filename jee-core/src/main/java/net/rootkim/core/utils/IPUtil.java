package net.rootkim.core.utils;

import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;


import javax.servlet.http.HttpServletRequest;

/**
 * IP相关工具类
 *
 * @author RootKim[rootkim.net]
 * @since 2024/5/1
 */
@Slf4j
public class IPUtil {

    public static String getIpAddr(HttpServletRequest request) {
        String ipAddr = request.getHeader("X-Forwarded-For");
        if (StrUtil.isNotBlank(ipAddr)) {
            if (ipAddr.contains(",")) {
                ipAddr = ipAddr.split(",")[0];
            }
        } else {
            ipAddr = request.getHeader("Proxy-Client-IP");
            if (StrUtil.isNotBlank(ipAddr)) {
                if (ipAddr.contains(",")) {
                    ipAddr = ipAddr.split(",")[0];
                }
            } else {
                ipAddr = request.getHeader("WL-Proxy-Client-IP");
                if (StrUtil.isNotBlank(ipAddr)) {
                    if (ipAddr.contains(",")) {
                        ipAddr = ipAddr.split(",")[0];
                    }
                } else {
                    ipAddr = request.getHeader("X-Real-IP");
                    if (StrUtil.isNotBlank(ipAddr)) {
                        if (ipAddr.contains(",")) {
                            ipAddr = ipAddr.split(",")[0];
                        }
                    } else {
                        ipAddr = request.getRemoteAddr();
                    }
                }
            }
        }
        if ("127.0.0.1".equals(ipAddr)) {
            log.error("获取到的ip地址为127.0.0.1，可能存在反向代理，需要检查配置");
        }
        return ipAddr;
    }
}
