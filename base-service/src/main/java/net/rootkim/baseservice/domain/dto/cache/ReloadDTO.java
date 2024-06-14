package net.rootkim.baseservice.domain.dto.cache;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

/**
 * @author RootKim[rootkim.net]
 * @since 2024/5/29
 */
@Getter
@Setter
@ApiModel(value = "重新加载缓存入参")
public class ReloadDTO {

    @NotBlank(message = "缓存key前缀不可为空")
    @ApiModelProperty(value = "缓存key前缀",required = true)
    private String keyPrefix;

    @ApiModelProperty("缓存key")
    private String key;
}
