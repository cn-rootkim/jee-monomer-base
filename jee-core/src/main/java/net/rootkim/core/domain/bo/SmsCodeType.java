package net.rootkim.core.domain.bo;

/**
 * 短信验证码类型枚举
 *
 * @author RootKim[rootkim.net]
 * @since 2024/4/30
 */
public enum SmsCodeType {
    /**
     * 登录
     */
    LOGIN,
    /**
     * 注册
     */
    REGISTER,
    /**
     * 修改登录密码
     */
    UPDATE_LOGIN_PASSWORD,
    /**
     * 修改支付密码
     */
    UPDATE_PAY_PASSWORD
}
