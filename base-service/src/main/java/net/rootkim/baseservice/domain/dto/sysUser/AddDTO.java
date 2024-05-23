package net.rootkim.baseservice.domain.dto.sysUser;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * @author RootKim[rootkim.net]
 * @since 2024/3/12
 */
@Getter
@Setter
@ApiModel(value = "新增用户入参")
public class AddDTO {

    @NotBlank(message = "用户名不能为空")
    @ApiModelProperty(value = "用户名",required = true)
    private String username;

    @NotBlank(message = "密码不能为空")
    @ApiModelProperty(value = "密码（加密后的密码）",required = true)
    private String password;

    @ApiModelProperty("姓名")
    private String name;

    @NotEmpty(message = "角色id集合不能为空")
    @ApiModelProperty("角色id集合")
    private List<String> roleIdList;
}
