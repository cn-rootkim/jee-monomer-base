package net.rootkim.baseservice.domain.dto.adminMenuFunction;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

/**
 * @author RootKim[rootkim.net]
 * @since 2024/5/17
 */
@Getter
@Setter
@ApiModel(value = "新增菜单功能入参")
public class AddDTO {

    @NotBlank(message = "功能名称不能为空")
    @ApiModelProperty(value = "功能名称",required = true)
    private String name;

    @NotBlank(message = "菜单id不能为空")
    @ApiModelProperty(value = "菜单id",required = true)
    private String adminMenuId;
}
