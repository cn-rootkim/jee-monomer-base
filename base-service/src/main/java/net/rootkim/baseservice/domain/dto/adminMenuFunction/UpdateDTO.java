package net.rootkim.baseservice.domain.dto.adminMenuFunction;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import net.rootkim.core.domain.dto.BaseUpdateDTO;

/**
 * @author RootKim[rootkim.net]
 * @since 2024/5/17
 */
@Getter
@Setter
@ApiModel(value = "修改菜单功能入参")
public class UpdateDTO extends BaseUpdateDTO {

    @ApiModelProperty("功能名称")
    private String name;

    @ApiModelProperty("菜单id")
    private String adminMenuId;
}
