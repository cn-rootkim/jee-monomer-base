package net.rootkim.core.domain.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import net.rootkim.core.domain.vo.ResultVO;
import net.rootkim.core.service.BaseCacheService;

/**
 * @author RootKim[rootkim.net]
 * @since 2024/5/29
 */
@Getter
@Setter
@ApiModel(value = "缓存业务对象")
public class CacheBO {

    public interface ListView extends ResultVO.ResultView {
    }

    @ApiModelProperty("缓存key前缀")
    private String keyPrefix;

    @ApiModelProperty("描述")
    private String description;

    @ApiModelProperty("是否可以重新加载")
    private Boolean canReload;

    private BaseCacheService baseCacheService;

    public CacheBO(String keyPrefix, String description) {
        this.keyPrefix = keyPrefix;
        this.description = description;
        canReload = false;
    }

    public CacheBO(String keyPrefix, String description, BaseCacheService baseCacheService) {
        this.keyPrefix = keyPrefix;
        this.description = description;
        canReload = true;
        this.baseCacheService = baseCacheService;
    }
}
