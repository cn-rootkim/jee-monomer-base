package net.rootkim.baseservice.domain.dto.sysRole;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * @author RootKim[rootkim.net]
 * @since 2024/5/20
 */
@Getter
@Setter
@ApiModel(value = "授权接口入参")
public class AuthApiDTO {

    @NotBlank(message = "角色id不能为空")
    @ApiModelProperty(value = "角色id",required = true)
    private String roleId;

    @ApiModelProperty("接口id集合")
    private List<String> apiIdList;
}
