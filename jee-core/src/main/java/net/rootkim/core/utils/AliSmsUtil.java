package net.rootkim.core.utils;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONObject;
import com.aliyun.dysmsapi20170525.Client;
import com.aliyun.dysmsapi20170525.models.SendSmsRequest;
import com.aliyun.dysmsapi20170525.models.SendSmsResponse;
import com.aliyun.teaopenapi.models.Config;
import com.aliyun.teautil.models.RuntimeOptions;
import net.rootkim.core.exception.SmsException;
import org.springframework.util.ObjectUtils;


/**
 * 阿里云短信工具类
 *
 * @author RootKim[rootkim.net]
 * @since 2024/5/1
 */
public class AliSmsUtil {
    private static final String ACCESS_KEY_ID = "";
    private static final String ACCESS_KEY_SECRET = "";

    public static void sendSms(String phone, String signName, String templateCode, JSONObject templateParam) throws Exception {
        if (StrUtil.isBlank(ACCESS_KEY_ID) || StrUtil.isBlank(ACCESS_KEY_SECRET)) {
            throw new SmsException("请初始化ACCESS_KEY_ID和ACCESS_KEY_SECRET");
        } else if(StrUtil.isBlank(phone) || StrUtil.isBlank(signName) || StrUtil.isBlank(templateCode) || ObjectUtils.isEmpty(templateParam)) {
            throw new SmsException("AliSmsUtil.sendSms缺少必要参数");
        }
        Config config = new Config().setAccessKeyId(ACCESS_KEY_ID).setAccessKeySecret(ACCESS_KEY_SECRET).setEndpoint("dysmsapi.aliyuncs.com");
        Client client = new Client(config);
        SendSmsResponse sendSmsResponse = client.sendSmsWithOptions(new SendSmsRequest()
                .setPhoneNumbers(phone).setSignName(signName).setTemplateCode(templateCode).setTemplateParam(templateParam.toJSONString()), new RuntimeOptions());
        if (ObjectUtils.isEmpty(sendSmsResponse) || sendSmsResponse.getStatusCode() != 200
                || ObjectUtils.isEmpty(sendSmsResponse.getBody()) || !"OK".equals(sendSmsResponse.getBody().getCode())) {
            throw new SmsException("短信发送失败");
        }
    }
}
