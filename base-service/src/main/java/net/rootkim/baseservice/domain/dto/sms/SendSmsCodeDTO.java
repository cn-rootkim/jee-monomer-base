package net.rootkim.baseservice.domain.dto.sms;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import net.rootkim.core.domain.bo.SmsCodeType;

import javax.validation.constraints.NotBlank;

/**
 * @author RootKim[rootkim.net]
 * @since 2024/5/2
 */
@Getter
@Setter
@ApiModel(value = "发送短信验证码入参")
public class SendSmsCodeDTO {

    @NotBlank(message = "手机号不能为空")
    @ApiModelProperty(value = "手机号",required = true)
    private String phone;

    @NotBlank(message = "短信验证码类型不能为空")
    @ApiModelProperty(value = "短信验证码类型",required = true)
    private SmsCodeType smsCodeType;
}
