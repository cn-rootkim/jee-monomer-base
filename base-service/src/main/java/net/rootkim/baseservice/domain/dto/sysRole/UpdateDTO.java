package net.rootkim.baseservice.domain.dto.sysRole;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import net.rootkim.core.domain.dto.BaseUpdateDTO;

import javax.validation.constraints.NotBlank;


/**
 * @author RootKim[rootkim.net]
 * @since 2024/5/20
 */
@Getter
@Setter
@ApiModel(value = "修改角色入参")
public class UpdateDTO extends BaseUpdateDTO {

    @NotBlank(message = "角色名称不能为空")
    @ApiModelProperty(value = "角色名称",required = true)
    private String name;

    @NotBlank(message = "角色key不能为空")
    @ApiModelProperty(value = "角色key",required = true)
    private String roleKey;

    @ApiModelProperty("角色描述")
    private String description;
}
