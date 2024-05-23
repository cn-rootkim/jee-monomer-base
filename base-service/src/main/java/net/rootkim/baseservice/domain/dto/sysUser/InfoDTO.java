package net.rootkim.baseservice.domain.dto.sysUser;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

/**
 * @author RootKim[rootkim.net]
 * @since 2024/3/26
 */
@Getter
@Setter
@ApiModel(value = "获取用户信息入参")
public class InfoDTO {

    @NotBlank(message = "id不能为空")
    @ApiModelProperty(value = "id",required = true)
    private String id;
}
