package net.rootkim.baseservice.domain.dto.sysUser;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import net.rootkim.core.domain.dto.BaseUpdateDTO;

import java.util.List;

/**
 * @author RootKim[rootkim.net]
 * @since 2024/3/12
 */
@Getter
@Setter
@ApiModel(value = "修改用户入参")
public class UpdateDTO extends BaseUpdateDTO {

    @ApiModelProperty("用户名")
    private String username;

    @ApiModelProperty("姓名")
    private String name;

    @ApiModelProperty("密码")
    private String password;

    @ApiModelProperty("账号是否可用(0.可用1.不可用)")
    private Byte isEnabled;

    @ApiModelProperty("角色id集合")
    private List<String> roleIdList;
}
