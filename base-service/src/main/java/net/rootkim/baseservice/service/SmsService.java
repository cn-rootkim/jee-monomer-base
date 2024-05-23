package net.rootkim.baseservice.service;

import net.rootkim.core.domain.bo.SmsCodeType;

/**
 * 短信验证码服务
 *
 * @author RootKim[rootkim.net]
 * @since 2024/4/30
 */
public interface SmsService {

    void sendSmsCode(String phone, String ip, SmsCodeType smsCodeType, String userId) throws Exception;

    boolean checkSmsCode(String phone, SmsCodeType smsCodeType, String smsCode);
}
