package net.rootkim.core.domain.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * @author RootKim[rootkim.net]
 * @since 2024/3/20
 */
@Getter
@Setter
public class BaseBatchDeleteDTO {

    /**
     * id集合
     */
    @NotEmpty(message = "id集合不能为空")
    @ApiModelProperty(value = "id集合",required = true)
    private List<String> idList;
}
