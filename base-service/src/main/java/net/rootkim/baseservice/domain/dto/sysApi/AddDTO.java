package net.rootkim.baseservice.domain.dto.sysApi;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

/**
 * @author RootKim[rootkim.net]
 * @since 2024/5/20
 */
@Getter
@Setter
@ApiModel(value = "新增接口入参")
public class AddDTO {

    @NotBlank(message = "接口不能为空")
    @ApiModelProperty(value = "接口",required = true)
    private String api;

    @ApiModelProperty("api描述")
    private String description;

    @ApiModelProperty("系统接口父路径id")
    private String sysApiBasePathId;
}
