package net.rootkim.baseservice.domain.po;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * <p>
 * 博客_博客类型_关联表
 * </p>
 *
 * @author RootKim[rootkim.net]
 * @since 2024-03-27
 */
@Getter
@Setter
@TableName("blog_type_relation")
@Builder
@ApiModel("博客_博客类型_关联")
public class BlogTypeRelation implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.ASSIGN_UUID)
    private String id;

    @TableField("blog_id")
    @ApiModelProperty("博客id")
    private String blogId;

    @TableField("blog_type_id")
    @ApiModelProperty("博客类型id")
    private String blogTypeId;

    @TableField("is_deleted")
    @TableLogic
    private Byte isDeleted;
}
