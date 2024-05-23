package net.rootkim.baseservice.domain.dto.sysApi;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import net.rootkim.core.domain.dto.BaseUpdateDTO;

/**
 * @author RootKim[rootkim.net]
 * @since 2024/5/20
 */
@Getter
@Setter
@ApiModel(value = "修改接口入参")
public class UpdateDTO extends BaseUpdateDTO {

    @ApiModelProperty("接口")
    private String api;

    @ApiModelProperty("api描述")
    private String description;

    @ApiModelProperty("系统接口父路径id")
    private String sysApiBasePathId;
}
