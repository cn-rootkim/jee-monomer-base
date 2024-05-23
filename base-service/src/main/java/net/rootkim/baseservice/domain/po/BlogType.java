package net.rootkim.baseservice.domain.po;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonView;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import net.rootkim.core.domain.vo.ResultVO;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 博客类型表
 * </p>
 *
 * @author RootKim[rootkim.net]
 * @since 2024-03-27
 */
@Getter
@Setter
@TableName("blog_type")
@ApiModel("博客类型")
public class BlogType implements Serializable {

    private static final long serialVersionUID = 1L;

    public interface ListTreeView extends ResultVO.ResultView {}

    @TableId(value = "id", type = IdType.ASSIGN_UUID)
    @JsonView({ListTreeView.class, Blog.PageView.class})
    private String id;

    @TableField("name")
    @JsonView({ListTreeView.class, Blog.PageView.class})
    @ApiModelProperty("名称")
    private String name;

    @TableField("parent_id")
    @JsonView({ListTreeView.class})
    @ApiModelProperty("父级类型id")
    private String parentId;

    @TableField("sort_num")
    @JsonView({ListTreeView.class})
    @ApiModelProperty("排序数字")
    private Integer sortNum;

    @TableField(value = "create_time", fill = FieldFill.INSERT)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ApiModelProperty("创建时间")
    private Date createTime;

    @TableField(value = "creater", fill = FieldFill.INSERT)
    @ApiModelProperty("创建人id")
    private String creater;

    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ApiModelProperty("修改时间")
    private Date updateTime;

    @TableField(value = "updater", fill = FieldFill.UPDATE)
    @ApiModelProperty("修改人id")
    private String updater;

    @TableField("is_deleted")
    @TableLogic
    private Byte isDeleted;
}
