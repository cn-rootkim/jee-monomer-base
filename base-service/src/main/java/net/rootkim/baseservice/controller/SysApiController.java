package net.rootkim.baseservice.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.rootkim.baseservice.domain.dto.sysApi.AddDTO;
import net.rootkim.baseservice.domain.dto.sysApi.UpdateDTO;
import net.rootkim.baseservice.domain.po.SysApi;
import net.rootkim.baseservice.service.SysApiService;
import net.rootkim.core.domain.dto.BaseBatchDeleteDTO;
import net.rootkim.core.domain.vo.ResultVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * <p>
 * 接口表 前端控制器
 * </p>
 *
 * @author RootKim[rootkim.net]
 * @since 2024-04-29
 */
@RestController
@RequestMapping("/sysApi")
@Api(tags = "接口")
public class SysApiController {

    @Autowired
    private SysApiService sysApiService;

    @PostMapping("add")
    @ApiOperation("新增接口")
    public ResultVO add(@Valid @RequestBody AddDTO addDTO) {
        SysApi sysApi = new SysApi();
        BeanUtils.copyProperties(addDTO, sysApi);
        sysApiService.add(sysApi);
        return ResultVO.success();
    }

    @PostMapping("batchDelete")
    @ApiOperation("批量删除接口")
    public ResultVO batchDelete(@Valid @RequestBody BaseBatchDeleteDTO baseBatchDeleteDTO) {
        sysApiService.batchDelete(baseBatchDeleteDTO.getIdList());
        return ResultVO.success();
    }

    @PostMapping("update")
    @ApiOperation("修改接口")
    public ResultVO update(@Valid @RequestBody UpdateDTO updateDTO) {
        SysApi sysApi = new SysApi();
        BeanUtils.copyProperties(updateDTO, sysApi);
        sysApiService.updateDBAndRedis(sysApi);
        return ResultVO.success();
    }
}
