package net.rootkim.baseservice.domain.dto.sysApiBasePath;

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
@ApiModel(value = "新增接口父路径入参")
public class AddDTO {

    @NotBlank(message = "接口父路径不能为空")
    @ApiModelProperty(value = "接口父路径",required = true)
    private String basePath;

    @ApiModelProperty("描述")
    private String description;
}
