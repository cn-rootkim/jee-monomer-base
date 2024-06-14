package net.rootkim.baseservice.controller;

import com.fasterxml.jackson.annotation.JsonView;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import net.rootkim.baseservice.domain.bo.SysApiBasePathBO;
import net.rootkim.baseservice.domain.dto.sysApiBasePath.AddDTO;
import net.rootkim.baseservice.domain.dto.sysApiBasePath.ListDTO;
import net.rootkim.baseservice.domain.dto.sysApiBasePath.UpdateDTO;
import net.rootkim.baseservice.domain.po.SysApiBasePath;
import net.rootkim.baseservice.service.SysApiBasePathService;
import net.rootkim.core.domain.dto.BaseBatchDeleteDTO;
import net.rootkim.core.domain.vo.ResultVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

/**
 * <p>
 * 接口父路径表 前端控制器
 * </p>
 *
 * @author RootKim[rootkim.net]
 * @since 2024-04-29
 */
@RestController
@RequestMapping("/sysApiBasePath")
@Api(tags = "接口父路径")
@RequiredArgsConstructor
public class SysApiBasePathController {

    private final SysApiBasePathService sysApiBasePathService;

    @PostMapping("add")
    @ApiOperation("新增接口父路径")
    public ResultVO add(@Valid @RequestBody AddDTO addDTO) {
        SysApiBasePath sysApiBasePath = new SysApiBasePath();
        BeanUtils.copyProperties(addDTO, sysApiBasePath);
        sysApiBasePathService.add(sysApiBasePath);
        return ResultVO.success();
    }

    @PostMapping("batchDelete")
    @ApiOperation("批量删除接口父路径")
    public ResultVO batchDelete(@Valid @RequestBody BaseBatchDeleteDTO baseBatchDeleteDTO) {
        sysApiBasePathService.batchDelete(baseBatchDeleteDTO.getIdList());
        return ResultVO.success();
    }

    @PostMapping("update")
    @ApiOperation("修改接口父路径")
    public ResultVO update(@Valid @RequestBody UpdateDTO updateDTO) {
        SysApiBasePath sysApiBasePath = new SysApiBasePath();
        BeanUtils.copyProperties(updateDTO, sysApiBasePath);
        sysApiBasePathService.updateDBAndRedis(sysApiBasePath);
        return ResultVO.success();
    }

    @PostMapping("list")
    @ApiOperation("接口父路径列表")
    @JsonView(SysApiBasePath.ListView.class)
    public ResultVO<List<SysApiBasePathBO>> list(@Valid @RequestBody ListDTO listDTO) {
        return ResultVO.success(sysApiBasePathService.list(listDTO));
    }
}
