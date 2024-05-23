package net.rootkim.baseservice.domain.dto.blogType;

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
public class AddDTO {

    @NotBlank(message = "名称不能为空")
    @ApiModelProperty(value = "名称",required = true)
    private String name;

    @ApiModelProperty("父级类型id")
    private String parentId;

    @ApiModelProperty("排序数字")
    private Integer sortNum;
}
