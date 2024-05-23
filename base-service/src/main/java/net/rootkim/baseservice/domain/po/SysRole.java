package net.rootkim.baseservice.domain.po;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonView;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import net.rootkim.core.domain.vo.BasePageVO;
import net.rootkim.core.domain.vo.ResultVO;

/**
 * <p>
 * 角色表
 * </p>
 *
 * @author RootKim[rootkim.net]
 * @since 2024-04-29
 */
@Getter
@Setter
@TableName("sys_role")
@ApiModel(value = "SysRole对象", description = "角色表")
public class SysRole implements Serializable {

    private static final long serialVersionUID = 1L;

    public interface PageView extends BasePageVO.PageVOView {
    }

    public interface ListView extends ResultVO.ResultView {
    }

    @JsonView({PageView.class, ListView.class})
    @TableId(value = "id", type = IdType.ASSIGN_UUID)
    private String id;

    @JsonView({PageView.class, ListView.class})
    @ApiModelProperty("角色名称")
    @TableField("name")
    private String name;

    @JsonView({PageView.class})
    @ApiModelProperty("角色key")
    @TableField("role_key")
    private String roleKey;

    @JsonView({PageView.class})
    @ApiModelProperty("角色描述")
    @TableField("description")
    private String description;

    @JsonView({PageView.class})
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ApiModelProperty("创建时间")
    private Date createTime;

    @TableField(value = "creater", fill = FieldFill.INSERT)
    @ApiModelProperty("创建人id")
    private String creater;

    @JsonView({PageView.class})
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
