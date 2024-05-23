package net.rootkim.baseservice.domain.dto.sysRole;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import net.rootkim.core.domain.dto.BasePageDTO;

/**
 * @author RootKim[rootkim.net]
 * @since 2024/5/20
 */
@Getter
@Setter
@ApiModel(value = "角色分页列表入参")
public class PageDTO extends BasePageDTO {

    @ApiModelProperty("角色名称(支持模糊查询)")
    private String name;

    @ApiModelProperty("角色key")
    private String key;
}
