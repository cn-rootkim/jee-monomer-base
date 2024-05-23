package net.rootkim.baseservice.domain.dto.blog;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import net.rootkim.core.domain.dto.BasePageDTO;

/**
 * @author RootKim[rootkim.net]
 * @since 2024/5/23
 */
@Getter
@Setter
public class PageDTO extends BasePageDTO {

    @ApiModelProperty("标题（支持模糊查询）")
    private String title;

    @ApiModelProperty("内容（支持模糊查询）")
    private String content;

    @ApiModelProperty("是否显示（0.是1.否）")
    private Byte isShow;

    @ApiModelProperty("博客类型ID")
    private String blogTypeId;

    @ApiModelProperty("排序模式（0.修改时间【倒序】 1.排序数字【升序】）")
    private Integer sortMode=0;
}
