package net.rootkim.baseservice.domain.po;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 角色_管理系统菜单功能关联表
 * </p>
 *
 * @author RootKim[rootkim.net]
 * @since 2024-04-29
 */
@Getter
@Setter
@TableName("sys_role_admin_menu_function_relation")
@ApiModel(value = "SysRoleAdminMenuFunctionRelation对象", description = "角色_管理系统菜单功能关联表")
public class SysRoleAdminMenuFunctionRelation implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.ASSIGN_UUID)
    private String id;

    @ApiModelProperty("系统角色id")
    @TableField("sys_role_id")
    private String sysRoleId;

    @ApiModelProperty("管理系统菜单功能id")
    @TableField("admin_menu_function_id")
    private String adminMenuFunctionId;

    @TableField(value = "creater", fill = FieldFill.INSERT)
    @ApiModelProperty("创建人id")
    private String creater;

    @TableField(value = "create_time", fill = FieldFill.INSERT)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ApiModelProperty("创建时间")
    private Date createTime;

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
