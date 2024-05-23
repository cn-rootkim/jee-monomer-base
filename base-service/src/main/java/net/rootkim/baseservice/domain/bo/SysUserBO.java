package net.rootkim.baseservice.domain.bo;

import com.fasterxml.jackson.annotation.JsonView;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import net.rootkim.baseservice.domain.po.SysUser;

import java.util.List;

/**
 * @author RootKim[rootkim.net]
 * @since 2024/5/20
 */
@Getter
@Setter
@ApiModel(value = "SysUser业务对象")
public class SysUserBO extends SysUser{

    @JsonView({SysUser.PageView.class})
    @ApiModelProperty("创建人名称")
    private String createrName;

    @JsonView({SysUser.PageView.class})
    @ApiModelProperty("修改人名称")
    private String updaterName;

    @JsonView({InfoView.class})
    @ApiModelProperty("角色id集合")
    private List<String> roleIdList;
}
