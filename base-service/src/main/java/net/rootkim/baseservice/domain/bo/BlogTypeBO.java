package net.rootkim.baseservice.domain.bo;

import com.fasterxml.jackson.annotation.JsonView;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import net.rootkim.baseservice.domain.po.BlogType;

import java.util.List;

/**
 * @author RootKim[rootkim.net]
 * @since 2024/5/23
 */
@Getter
@Setter
@ApiModel("博客类型业务对象")
public class BlogTypeBO extends BlogType {

    @JsonView({ListTreeView.class})
    @ApiModelProperty("子级列表")
    private List<BlogTypeBO> childList;
}
