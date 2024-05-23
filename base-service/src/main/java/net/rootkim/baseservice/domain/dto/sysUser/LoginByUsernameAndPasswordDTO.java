package net.rootkim.baseservice.domain.dto.sysUser;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

/**
 * @author RootKim[rootkim.net]
 * @since 2024/3/11
 */
@Getter
@Setter
@ApiModel(value = "用户名密码登录[B端]入参")
public class LoginByUsernameAndPasswordDTO {

    @NotBlank(message = "用户名不能为空")
    @ApiModelProperty(value = "用户名",required = true)
    private String username;

    @NotBlank(message = "密码不能为空")
    @ApiModelProperty(value = "密码（加密后的密码）",required = true)
    private String password;
}
