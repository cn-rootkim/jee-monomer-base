package net.rootkim.baseservice.controller;

import com.fasterxml.jackson.annotation.JsonView;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import net.rootkim.core.domain.bo.CacheBO;
import net.rootkim.baseservice.domain.dto.cache.QueryKeyListDTO;
import net.rootkim.baseservice.domain.dto.cache.QueryValueDTO;
import net.rootkim.baseservice.domain.dto.cache.ReloadDTO;
import net.rootkim.baseservice.service.CacheService;
import net.rootkim.core.domain.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;

/**
 * @author RootKim[rootkim.net]
 * @since 2024/5/29
 */
@RestController
@RequestMapping("cache")
@Api(tags = "缓存")
@RequiredArgsConstructor
public class CacheController {

    private final CacheService cacheService;

    @PostMapping("list")
    @ApiOperation("缓存列表")
    @JsonView(CacheBO.ListView.class)
    public ResultVO<List<CacheBO>> list() {
        return ResultVO.success(cacheService.list());
    }

    @PostMapping("queryKeyList")
    @ApiOperation("查询缓存key集合")
    public ResultVO<Set<String>> queryKeyList(@RequestBody QueryKeyListDTO queryKeyListDTO) {
        return ResultVO.success(cacheService.queryKeyList(queryKeyListDTO));
    }

    @PostMapping("queryValue")
    @ApiOperation("查询缓存value")
    public ResultVO<String> queryValue(@RequestBody QueryValueDTO queryValueDTO) {
        return ResultVO.success(cacheService.queryValue(queryValueDTO.getKey()));
    }

    @PostMapping("reload")
    @ApiOperation("重新加载缓存")
    public ResultVO reload(@RequestBody ReloadDTO reloadDTO) {
        cacheService.reload(reloadDTO);
        return ResultVO.success();
    }
}
