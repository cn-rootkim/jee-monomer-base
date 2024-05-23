package net.rootkim.core.domain.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

/**
 * @author RootKim[net.rootkim]
 * @since 2024/3/16
 */
@Getter
@Setter
public class BaseUpdateDTO {

    @NotBlank(message = "id不能为空")
    private String id;
}
