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
 * 用户表
 * </p>
 *
 * @author RootKim[rootkim.net]
 * @since 2024-04-28
 */
@Getter
@Setter
@TableName("sys_user")
@ApiModel(value = "SysUser对象", description = "用户表")
public class SysUser implements Serializable {

    private static final long serialVersionUID = 1L;

    public interface PageView extends BasePageVO.PageVOView {};
    public interface InfoView extends ResultVO.ResultView {};

    @TableId(value = "id", type = IdType.ASSIGN_UUID)
    @JsonView({PageView.class,InfoView.class})
    private String id;

    @ApiModelProperty("用户类型：0.B端用户 1.C端用户")
    @JsonView({PageView.class,InfoView.class})
    @TableField("type")
    private Byte type;

    @ApiModelProperty("用户名")
    @JsonView({PageView.class,InfoView.class})
    @TableField("username")
    private String username;

    @ApiModelProperty("密码")
    @TableField("password")
    private String password;

    @ApiModelProperty("姓名")
    @JsonView({PageView.class,InfoView.class})
    @TableField("name")
    private String name;

    @ApiModelProperty("手机号")
    @JsonView({PageView.class,InfoView.class})
    @TableField("phone")
    private String phone;

    @ApiModelProperty("账号是否可用(0.可用1.不可用)")
    @JsonView({PageView.class,InfoView.class})
    @TableField("is_enabled")
    private Byte isEnabled;

    @ApiModelProperty("微信小程序openid")
    @TableField("wx_xcx_open_id")
    private String wxXcxOpenId;

    @ApiModelProperty("微信公众号openid")
    @TableField("wx_gzh_open_id")
    private String wxGzhOpenId;

    @ApiModelProperty("微信移动端openid")
    @TableField("wx_app_open_id")
    private String wxAppOpenId;

    @ApiModelProperty("微信Web端openid")
    @TableField("wx_web_open_id")
    private String wxWebOpenId;

    @ApiModelProperty("微信跨平台id")
    @TableField("wx_unionid")
    private String wxUnionid;

    @TableField(value = "creater", fill = FieldFill.INSERT)
    @ApiModelProperty("创建人id")
    private String creater;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @JsonView({PageView.class})
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    @ApiModelProperty("创建时间")
    private Date createTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @JsonView({PageView.class})
    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    @ApiModelProperty("修改时间")
    private Date updateTime;

    @TableField(value = "updater", fill = FieldFill.UPDATE)
    @ApiModelProperty("修改人id")
    private String updater;

    @TableField("is_deleted")
    @TableLogic
    private Byte isDeleted;
}
