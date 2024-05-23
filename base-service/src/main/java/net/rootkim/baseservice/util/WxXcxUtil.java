package net.rootkim.baseservice.util;

import net.rootkim.core.utils.OkHttpUtil;

/**
 * 微信小程序工具类
 *
 * @author RootKim[rootkim.net]
 * @since 2024/5/5
 */
public class WxXcxUtil {

    public final static String APPID = "";
    public final static String SECRET = "";

    public static String login(String code) {
        StringBuilder url = new StringBuilder("https://api.weixin.qq.com/sns/jscode2session");
        url.append("?appid=");
        url.append(APPID);
        url.append("&secret=");
        url.append(SECRET);
        url.append("&js_code=");
        url.append(code);
        url.append("&grant_type=authorization_code");
        String resp = OkHttpUtil.sendGet(url.toString());
        return null;
    }
}
