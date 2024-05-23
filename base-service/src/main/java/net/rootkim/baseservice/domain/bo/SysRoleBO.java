package net.rootkim.baseservice.domain.bo;

import com.fasterxml.jackson.annotation.JsonView;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import net.rootkim.baseservice.domain.po.SysRole;

/**
 * @author RootKim[rootkim.net]
 * @since 2024/5/20
 */
@Getter
@Setter
@ApiModel(value = "SysRole业务对象")
public class SysRoleBO extends SysRole {

    @JsonView({PageView.class})
    @ApiModelProperty("创建人名称")
    private String createrName;

    @JsonView({PageView.class})
    @ApiModelProperty("修改人名称")
    private String updaterName;
}
