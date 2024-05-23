package net.rootkim.baseservice.domain.dto.blog;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author RootKim[rootkim.net]
 * @since 2024/5/23
 */
@Getter
@Setter
public class AddDTO {

    @NotBlank(message = "标题不能为空")
    @ApiModelProperty(value = "标题", required = true)
    private String title;

    @NotBlank(message = "内容不能为空")
    @ApiModelProperty(value = "内容", required = true)
    private String content;

    @NotNull(message = "是否显示不能为空")
    @ApiModelProperty(value = "是否显示（0.是1.否）", required = true)
    private Byte isShow;

    @ApiModelProperty("排序数字")
    private Integer sortNum;

    @NotEmpty(message = "博客类型ID列表不能为空")
    @ApiModelProperty(value = "博客类型ID列表", required = true)
    private List<String> blogTypeIdList;
}
