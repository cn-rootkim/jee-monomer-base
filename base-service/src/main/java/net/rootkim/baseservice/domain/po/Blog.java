package net.rootkim.baseservice.domain.po;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonView;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import net.rootkim.core.domain.vo.BasePageVO;
import net.rootkim.core.domain.vo.ResultVO;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 博客表
 * </p>
 *
 * @author RootKim[rootkim.net]
 * @since 2024-03-27
 */
@Getter
@Setter
@TableName("blog")
@ApiModel("博客")
public class Blog implements Serializable {

    private static final long serialVersionUID = 1L;

    public interface PageView extends BasePageVO.PageVOView {};

    public interface InfoView extends ResultVO.ResultView {};

    @TableId(value = "id", type = IdType.ASSIGN_UUID)
    @JsonView({PageView.class,InfoView.class})
    private String id;

    @TableField("title")
    @JsonView({PageView.class,InfoView.class})
    @ApiModelProperty("标题")
    private String title;

    @TableField("content")
    @JsonView({InfoView.class})
    @ApiModelProperty("内容")
    private String content;

    @TableField("sort_num")
    @JsonView({PageView.class,InfoView.class})
    @ApiModelProperty("排序数字")
    private Integer sortNum;

    @TableField("is_show")
    @JsonView({PageView.class,InfoView.class})
    @ApiModelProperty("是否显示（0.是1.否）")
    private Byte isShow;

    @TableField(value = "create_time", fill = FieldFill.INSERT)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @JsonView({PageView.class})
    @ApiModelProperty("创建时间")
    private Date createTime;

    @TableField(value = "creater", fill = FieldFill.INSERT)
    @ApiModelProperty("创建人id")
    private String creater;

    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @JsonView({PageView.class})
    @ApiModelProperty("修改时间")
    private Date updateTime;

    @TableField(value = "updater", fill = FieldFill.UPDATE)
    @ApiModelProperty("修改人id")
    private String updater;

    @TableField("is_deleted")
    @TableLogic
    private Byte isDeleted;
}
