package net.rootkim.baseservice.domain.dto.adminMenu;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

/**
 * @author RootKim[rootkim.net]
 * @since 2024/5/14
 */
@Getter
@Setter
@ApiModel(value = "新增菜单入参")
public class AddDTO {

    @NotBlank(message = "菜单名称不能为空")
    @ApiModelProperty(value = "菜单名称", required = true)
    private String name;

    @ApiModelProperty("菜单路径(前端用)")
    private String path;

    @ApiModelProperty("父级菜单id")
    private String parentId;
}
