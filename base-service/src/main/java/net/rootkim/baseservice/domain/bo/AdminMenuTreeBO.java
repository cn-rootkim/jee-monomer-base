package net.rootkim.baseservice.domain.bo;

import com.fasterxml.jackson.annotation.JsonView;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author RootKim[rootkim.net]
 * @since 2024/5/9
 */
@Getter
@Setter
@ApiModel(value = "AdminMenu树形业务对象")
public class AdminMenuTreeBO extends AdminMenuBO {

    @JsonView({ListTreeView.class})
    @ApiModelProperty("子菜单集合")
    private List<AdminMenuBO> childList;
}
