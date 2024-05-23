package net.rootkim.baseservice.domain.dto.adminMenuFunction;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

/**
 * @author RootKim[rootkim.net]
 * @since 2024/5/17
 */
@Getter
@Setter
@ApiModel(value = "菜单功能列表入参")
public class ListDTO {

    @NotNull
    @ApiModelProperty(value = "模式：0.查询全部 1.根据角色id标记授权状态 2.根据当前登录用户标记授权状态",required = true)
    private Integer mode;

    @ApiModelProperty("功能名称（支持模糊查询）")
    private String name;

    @ApiModelProperty("菜单id")
    private String adminMenuId;

    @ApiModelProperty(value = "角色id")
    private String roleId;
}
