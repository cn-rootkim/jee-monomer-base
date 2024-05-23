package net.rootkim.core.domain.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

/**
 * @author RootKim[net.rootkim]
 * @since 2024/3/16
 */
@Getter
@Setter
public class BasePageDTO {

    /**
     * 当前页（必填）
     */
    @NotNull(message = "当前页不能为空")
    @ApiModelProperty(value = "当前页不能为空",required = true)
    Long current;

    /**
     * 每页显示条数（必填）
     */
    @NotNull(message = "每页显示条数不能为空")
    @ApiModelProperty(value = "每页显示条数不能为空",required = true)
    Long size;
}
