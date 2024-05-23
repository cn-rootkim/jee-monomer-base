package net.rootkim.baseservice.domain.dto.blog;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

/**
 * @author RootKim[rootkim.net]
 * @since 2024/5/23
 */
@Getter
@Setter
public class InfoDTO {

    @NotBlank(message = "id不能为空")
    @ApiModelProperty(value = "id",required = true)
    private String id;
}
