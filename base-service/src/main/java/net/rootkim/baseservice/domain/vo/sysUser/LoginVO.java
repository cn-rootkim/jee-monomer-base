package net.rootkim.baseservice.domain.vo.sysUser;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * @author RootKim[rootkim.net]
 * @since 2024/3/11
 */
@Getter
@Setter
@AllArgsConstructor
@ApiModel(value = "用户名密码登录[B端]反参")
public class LoginVO {

    @ApiModelProperty("令牌")
    private String token;
}
