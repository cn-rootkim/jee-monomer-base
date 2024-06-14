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
@ApiModel(value = "查询缓存key集合入参")
public class QueryKeyListDTO {

    @NotBlank(message = "缓存key前缀不能为空")
    @ApiModelProperty(value = "缓存key前缀", required = true)
    private String keyPrefix;
}
