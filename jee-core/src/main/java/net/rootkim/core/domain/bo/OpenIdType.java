package net.rootkim.core.domain.bo;

/**
 * OpenId类型枚举
 *
 * @author RootKim[rootkim.net]
 * @since 2024/5/2
 */
public enum OpenIdType {
    /**
     * 微信小程序openid
     */
    WX_XCX,
    /**
     * 微信公众号openid
     */
    WX_GZH,
    /**
     * 微信移动端openid
     */
    WX_APP,
    /**
     * 微信Web端openid
     */
    WX_WEB,
    /**
     * 微信跨平台id
     */
    WX_UNIONID
}
