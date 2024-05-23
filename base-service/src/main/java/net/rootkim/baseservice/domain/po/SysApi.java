package net.rootkim.baseservice.domain.po;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonView;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 接口表
 * </p>
 *
 * @author RootKim[rootkim.net]
 * @since 2024-04-29
 */
@Getter
@Setter
@TableName("sys_api")
@ApiModel(value = "SysApi对象", description = "接口表")
public class SysApi implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.ASSIGN_UUID)
    @JsonView({SysApiBasePath.ListView.class})
    private String id;

    @ApiModelProperty("接口")
    @TableField("api")
    @JsonView({SysApiBasePath.ListView.class})
    private String api;

    @ApiModelProperty("api描述")
    @TableField("description")
    @JsonView({SysApiBasePath.ListView.class})
    private String description;

    @ApiModelProperty("系统接口父路径id")
    @TableField("sys_api_base_path_id")
    @JsonView({SysApiBasePath.ListView.class})
    private String sysApiBasePathId;

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
