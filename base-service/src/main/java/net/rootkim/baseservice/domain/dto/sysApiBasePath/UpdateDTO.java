package net.rootkim.baseservice.domain.dto.sysApiBasePath;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import net.rootkim.core.domain.dto.BaseUpdateDTO;

/**
 * @author RootKim[rootkim.net]
 * @since 2024/5/14
 */
@Getter
@Setter
@ApiModel(value = "修改接口父路径入参")
public class UpdateDTO extends BaseUpdateDTO {

    @ApiModelProperty("接口父路径")
    private String basePath;

    @ApiModelProperty("描述")
    private String description;
}
