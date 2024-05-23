package net.rootkim.baseservice.domain.bo;

import com.fasterxml.jackson.annotation.JsonView;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import net.rootkim.baseservice.domain.po.Blog;
import net.rootkim.baseservice.domain.po.BlogType;

import java.util.List;

/**
 * @author RootKim[rootkim.net]
 * @since 2024/5/23
 */
@Getter
@Setter
@ApiModel("博客业务对象")
public class BlogBO extends Blog {

    @JsonView({PageView.class})
    @ApiModelProperty("创建人名称")
    private String createrName;

    @JsonView({PageView.class})
    @ApiModelProperty("修改人名称")
    private String updaterName;

    @JsonView({InfoView.class})
    @ApiModelProperty("博客类型ID列表")
    private List<String> blogTypeIdList;

    @JsonView({PageView.class})
    @ApiModelProperty("博客类型列表")
    private List<BlogType> blogTypeList;
}
