package net.rootkim.baseservice.domain.dto.sysRole;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * @author RootKim[rootkim.net]
 * @since 2024/5/20
 */
@Getter
@Setter
@ApiModel(value = "授权菜单功能入参")
public class AuthMenuFunctionDTO {

    @NotBlank(message = "角色id不能为空")
    @ApiModelProperty(value = "角色id",required = true)
    private String roleId;

    @NotBlank(message = "菜单id不能为空")
    @ApiModelProperty(value = "菜单id",required = true)
    private String menuId;

    @ApiModelProperty("菜单功能id列表")
    private List<String> menuFunctionIdList;
}
