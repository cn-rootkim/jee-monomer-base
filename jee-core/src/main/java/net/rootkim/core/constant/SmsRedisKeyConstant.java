package net.rootkim.core.constant;

/**
 * 短信RedisKey常量类
 *
 * @author RootKim[rootkim.net]
 * @since 2024/4/30
 */
public class SmsRedisKeyConstant {

    /**
     * 存储手机号短信验证码发送记录的KEY[所有业务，用于限制每个手机号 1分钟内发送间隔]
     */
    public static final String SMS_CODE_RECORD = "sms:code:record:";//手机号
    public static final int SMS_CODE_RECORD_EXPIRES_MINUTE = 1;//手机号短信验证码发送记录有效时间（分钟）

    /**
     * 存储手机号短信验证码发送总数的KEY[所有业务，用于限制每个手机号 24小时内发送总数]
     */
    public static final String SMS_CODE_TOTAL_PHONE = "sms:code:total:phone:";//IP
    public static final int SMS_CODE_TOTAL_PHONE_EXPIRES_HOUR = 24;//手机号短信验证码发送总数有效时间（小时）

    /**
     * 存储IP短信验证码发送总数的KEY[所有业务，用于限制每个IP 24小时内发送总数]
     */
    public static final String SMS_CODE_TOTAL_IP = "sms:code:total:ip:";//IP
    public static final int SMS_CODE_TOTAL_IP_EXPIRES_HOUR = 24;//IP短信验证码发送总数有效时间（小时）

    /**
     * 所有业务短信验证码有效时间（分钟）
     */
    public static final int SMS_CODE_EXPIRES_MINUTE = 5;

    /**
     * 存储短信验证码的KEY[登录业务]
     */
    public static final String SMS_CODE_LOGIN = "sms:code:login:";//手机号

    /**
     * 存储短信验证码的KEY[注册业务]
     */
    public static final String SMS_CODE_REGISTER = "sms:code:register:";//手机号

    /**
     * 存储短信验证码的KEY[修改登录密码业务]
     */
    public static final String SMS_CODE_UPDATE_LOGIN_PASSWORD = "sms:code:update:login:password:";//手机号

    /**
     * 存储短信验证码的KEY[修改支付密码业务]
     */
    public static final String SMS_CODE_UPDATE_PAY_PASSWORD = "sms:code:update:pay:password:";//手机号

}
