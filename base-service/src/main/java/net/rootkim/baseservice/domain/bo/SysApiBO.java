package net.rootkim.baseservice.domain.bo;

import com.fasterxml.jackson.annotation.JsonView;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import net.rootkim.baseservice.domain.po.SysApi;
import net.rootkim.baseservice.domain.po.SysApiBasePath;

/**
 * @author RootKim[rootkim.net]
 * @since 2024/5/20
 */
@Getter
@Setter
@ApiModel(value = "SysApi业务对象")
public class SysApiBO extends SysApi {

    @ApiModelProperty("是否已授权")
    @JsonView({SysApiBasePath.ListView.class})
    private Boolean isAuth;
}
