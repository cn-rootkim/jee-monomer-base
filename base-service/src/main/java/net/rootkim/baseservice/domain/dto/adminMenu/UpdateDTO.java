package net.rootkim.baseservice.domain.dto.adminMenu;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.Getter;
import lombok.Setter;
import net.rootkim.core.domain.dto.BaseUpdateDTO;

/**
 * @author RootKim[rootkim.net]
 * @since 2024/5/14
 */
@Getter
@Setter
@ApiModel(value = "修改菜单入参")
public class UpdateDTO extends BaseUpdateDTO {

    @ApiModelProperty("菜单名称")
    private String name;

    @ApiModelProperty("菜单路径(前端用)")
    private String path;

    @ApiModelProperty("父级菜单id")
    private String parentId;
}
