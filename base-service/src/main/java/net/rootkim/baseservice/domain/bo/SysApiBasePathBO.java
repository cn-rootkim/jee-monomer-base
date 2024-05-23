package net.rootkim.baseservice.domain.bo;

import com.fasterxml.jackson.annotation.JsonView;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import net.rootkim.baseservice.domain.po.SysApiBasePath;

import java.util.List;

/**
 * @author RootKim[rootkim.net]
 * @since 2024/5/20
 */
@Getter
@Setter
@ApiModel(value = "SysApiBasePath业务对象")
public class SysApiBasePathBO extends SysApiBasePath {

    @ApiModelProperty("接口列表")
    @JsonView({ListView.class})
    private List<SysApiBO> sysApiBOList;
}
