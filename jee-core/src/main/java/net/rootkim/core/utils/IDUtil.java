package net.rootkim.core.utils;

import java.util.UUID;

/**
 * ID工具类
 * @author RootKim[net.rootkim]
 * @since 2024-2-16
 */
public class IDUtil {

    /**
     * @return 去除“-”的32位UUID
     */
    public static String getUUID32(){
        return UUID.randomUUID().toString().replaceAll("-","");
    }
}
