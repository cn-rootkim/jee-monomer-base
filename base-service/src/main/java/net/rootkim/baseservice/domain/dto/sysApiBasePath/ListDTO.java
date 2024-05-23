package net.rootkim.baseservice.domain.dto.sysApiBasePath;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * @author RootKim[rootkim.net]
 * @since 2024/5/17
 */
@Getter
@Setter
@ApiModel(value = "接口父路径列表入参")
public class ListDTO {

    @ApiModelProperty(value = "是否需要接口列表",required = true)
    private Boolean isNeedApiList;

    @ApiModelProperty("角色id")
    private String roleId;
}
