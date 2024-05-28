package net.rootkim.baseservice.service.impl;

import cn.hutool.core.lang.Validator;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import net.rootkim.baseservice.service.SmsService;
import net.rootkim.core.constant.SmsRedisKeyConstant;
import net.rootkim.core.domain.bo.SmsCodeType;
import net.rootkim.core.exception.SmsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;


import java.util.concurrent.TimeUnit;

/**
 * @author RootKim[rootkim.net]
 * @since 2024/4/30
 */
@Service
@Slf4j
public class SmsServiceImpl implements SmsService {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public void sendSmsCode(String phone, String ip, SmsCodeType smsCodeType, String userId) throws Exception {
        if (StrUtil.isBlank(phone)) {
            throw new SmsException("手机号不可为空");
        }
        if (!Validator.isMobile(phone)) {
            throw new SmsException("手机号格式不正确");
        }
        if (StrUtil.isBlank(ip)) {
            throw new SmsException("ip不可为空");
        }
        if (ObjectUtils.isEmpty(smsCodeType)) {
            throw new SmsException("短信验证码类型不可为空");
        }
        if (StrUtil.isBlank(userId) && (!smsCodeType.equals(SmsCodeType.LOGIN) || !smsCodeType.equals(SmsCodeType.REGISTER))) {
            throw new SmsException("发送非登录或注册验证码，需要先登录");
        }
        String smsCodeRecordKey = SmsRedisKeyConstant.SMS_CODE_RECORD + phone;
        //检查手机号短信验证码发送记录[所有业务，用于限制每个手机号 1分钟内发送间隔]
        String value = stringRedisTemplate.opsForValue().get(smsCodeRecordKey);
        if (StrUtil.isNotBlank(value)) {
            throw new SmsException("1分钟内只能发送一次短信验证码");
        }
        //检查手机号短信验证码发送总数[所有业务，用于限制每个手机号 24小时内发送总数]
        String smsCodeTotalPhoneKey = SmsRedisKeyConstant.SMS_CODE_TOTAL_PHONE + phone;
        value = stringRedisTemplate.opsForValue().get(smsCodeTotalPhoneKey);
        if (StrUtil.isNotBlank(value) && Integer.parseInt(value) > 60) {
            throw new SmsException("短信发送频繁，24小时后解封");
        }
        //检查IP短信验证码发送总数[所有业务，用于限制每个IP 24小时内发送总数]
        String smsCodeTotalIpKey = SmsRedisKeyConstant.SMS_CODE_TOTAL_IP + ip;
        value = stringRedisTemplate.opsForValue().get(smsCodeTotalIpKey);
        if (StrUtil.isNotBlank(value) && Integer.parseInt(value) > 600) {
            throw new SmsException("短信发送频繁，24小时后解封");
        }
        //发送短信验证码
        String smsCode = RandomUtil.randomNumbers(6);
        log.info("发送短信验证码：{}", smsCode);
//        AliSmsUtil.sendSms(phone,"","",null);
        //存储手机号短信验证码发送记录[所有业务，用于限制每个手机号 1分钟内发送间隔]
        stringRedisTemplate.opsForValue().set(smsCodeRecordKey, "1", SmsRedisKeyConstant.SMS_CODE_RECORD_EXPIRES_MINUTE, TimeUnit.MINUTES);
        //存储手机号短信验证码发送总数[所有业务，用于限制每个手机号 24小时内发送总数]
        value = stringRedisTemplate.opsForValue().get(smsCodeTotalPhoneKey);
        Long expire = stringRedisTemplate.getExpire(smsCodeTotalPhoneKey);
        if (StrUtil.isNotBlank(value)) {
            stringRedisTemplate.opsForValue().set(smsCodeTotalPhoneKey, String.valueOf(Integer.parseInt(value) + 1), expire, TimeUnit.SECONDS);
        } else {
            stringRedisTemplate.opsForValue().set(smsCodeTotalPhoneKey, "1", SmsRedisKeyConstant.SMS_CODE_TOTAL_PHONE_EXPIRES_HOUR, TimeUnit.HOURS);
        }
        //存储IP短信验证码发送总数[所有业务，用于限制每个IP 24小时内发送总数]
        value = stringRedisTemplate.opsForValue().get(smsCodeTotalIpKey);
        expire = stringRedisTemplate.getExpire(smsCodeTotalIpKey);
        if (StrUtil.isNotBlank(value)) {
            stringRedisTemplate.opsForValue().set(smsCodeTotalIpKey, String.valueOf(Integer.parseInt(value) + 1), expire, TimeUnit.SECONDS);
        } else {
            stringRedisTemplate.opsForValue().set(smsCodeTotalIpKey, "1", SmsRedisKeyConstant.SMS_CODE_TOTAL_IP_EXPIRES_HOUR, TimeUnit.HOURS);
        }
        //根据具体业务存储短信验证码
        stringRedisTemplate.opsForValue().set(getSmsCodeKey(phone, smsCodeType), smsCode, SmsRedisKeyConstant.SMS_CODE_EXPIRES_MINUTE, TimeUnit.MINUTES);
    }

    @Override
    public boolean checkSmsCode(String phone, SmsCodeType smsCodeType, String smsCode) {
        if (StrUtil.isBlank(phone)) {
            throw new SmsException("手机号不可为空");
        }
        if (ObjectUtils.isEmpty(smsCodeType)) {
            throw new SmsException("短信验证码类型不可为空");
        }
        if (StrUtil.isBlank(smsCode)) {
            throw new SmsException("短信验证码不可为空");
        }
        String smsCodeKey = getSmsCodeKey(phone, smsCodeType);
        String smsCodeValue = stringRedisTemplate.opsForValue().get(smsCodeKey);
        if (StrUtil.isBlank(smsCodeValue)) {
            return false;
        }
        if (!smsCode.equals(smsCodeValue)) {
            return false;
        }
        stringRedisTemplate.delete(smsCodeKey);
        return true;
    }

    private String getSmsCodeKey(String phone, SmsCodeType smsCodeType) {
        StringBuilder smsCodeKey = new StringBuilder();
        if (smsCodeType.equals(SmsCodeType.LOGIN)) {
            smsCodeKey.append(SmsRedisKeyConstant.SMS_CODE_LOGIN);
        } else if (smsCodeType.equals(SmsCodeType.REGISTER)) {
            smsCodeKey.append(SmsRedisKeyConstant.SMS_CODE_REGISTER);
        } else if (smsCodeType.equals(SmsCodeType.UPDATE_LOGIN_PASSWORD)) {
            smsCodeKey.append(SmsRedisKeyConstant.SMS_CODE_UPDATE_LOGIN_PASSWORD);
        } else if (smsCodeType.equals(SmsCodeType.UPDATE_PAY_PASSWORD)) {
            smsCodeKey.append(SmsRedisKeyConstant.SMS_CODE_UPDATE_PAY_PASSWORD);
        } else {
            throw new SmsException("不支持的短信业务");
        }
        smsCodeKey.append(phone);
        return smsCodeKey.toString();
    }
}
