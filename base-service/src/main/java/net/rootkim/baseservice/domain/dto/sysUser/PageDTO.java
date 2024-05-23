package net.rootkim.baseservice.domain.dto.sysUser;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import net.rootkim.core.domain.dto.BasePageDTO;

/**
 * @author RootKim[rootkim.net]
 * @since 2024/3/6
 */
@Getter
@Setter
@ApiModel(value = "分页查询用户入参")
public class PageDTO extends BasePageDTO {

    @ApiModelProperty("用户类型：0.B端用户 1.C端用户")
    private Byte type;

    @ApiModelProperty("用户名（支持模糊查询）")
    private String username;

    @ApiModelProperty("姓名（支持模糊查询）")
    private String name;

    @ApiModelProperty("手机号（支持模糊查询）")
    private String phone;

    @ApiModelProperty("账号是否可用(0.可用1.不可用)")
    private Byte isEnabled;
}
