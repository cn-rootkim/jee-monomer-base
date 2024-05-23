package net.rootkim.baseservice.domain.bo;

import com.fasterxml.jackson.annotation.JsonView;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import net.rootkim.baseservice.domain.po.AdminMenuFunction;

/**
 * @author RootKim[rootkim.net]
 * @since 2024/5/20
 */
@Getter
@Setter
@ApiModel(value = "AdminMenuFunction业务对象")
public class AdminMenuFunctionBO extends AdminMenuFunction {

    @JsonView({ListView.class})
    @ApiModelProperty("是否已授权")
    private Boolean isAuth;
}
