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
import net.rootkim.core.domain.vo.ResultVO;

/**
 * <p>
 * 管理系统菜单表
 * </p>
 *
 * @author RootKim[rootkim.net]
 * @since 2024-04-29
 */
@Getter
@Setter
@TableName("admin_menu")
@ApiModel(value = "AdminMenu对象", description = "管理系统菜单表")
public class AdminMenu implements Serializable {

    private static final long serialVersionUID = 1L;

    public interface ListView extends ResultVO.ResultView {
    }

    public interface ListTreeView extends ListView {
    }

    @TableId(value = "id", type = IdType.ASSIGN_UUID)
    @JsonView({ListView.class})
    private String id;

    @ApiModelProperty("菜单名称")
    @TableField("name")
    @JsonView({ListView.class})
    private String name;

    @ApiModelProperty("菜单路径(前端用)")
    @TableField("path")
    @JsonView({ListView.class})
    private String path;

    @ApiModelProperty("父级菜单id")
    @TableField("parent_id")
    @JsonView({ListView.class})
    private String parentId;

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
