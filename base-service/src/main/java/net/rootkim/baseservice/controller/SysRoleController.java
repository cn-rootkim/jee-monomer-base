package net.rootkim.baseservice.controller;

import com.fasterxml.jackson.annotation.JsonView;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import net.rootkim.baseservice.domain.bo.SysRoleBO;
import net.rootkim.baseservice.domain.dto.sysRole.*;
import net.rootkim.baseservice.domain.po.SysRole;
import net.rootkim.baseservice.service.SysRoleService;
import net.rootkim.core.domain.dto.BaseBatchDeleteDTO;
import net.rootkim.core.domain.vo.BasePageVO;
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
 * 角色表 前端控制器
 * </p>
 *
 * @author RootKim[rootkim.net]
 * @since 2024-04-29
 */
@RestController
@RequestMapping("/sysRole")
@Api(tags = "角色")
@RequiredArgsConstructor
public class SysRoleController {

    private final SysRoleService sysRoleService;

    @PostMapping("add")
    @ApiOperation("新增角色")
    public ResultVO add(@Valid @RequestBody AddDTO addDTO) {
        SysRole sysRole = new SysRole();
        BeanUtils.copyProperties(addDTO, sysRole);
        sysRoleService.add(sysRole);
        return ResultVO.success();
    }

    @PostMapping("batchDelete")
    @ApiOperation("批量删除角色")
    public ResultVO batchDelete(@Valid @RequestBody BaseBatchDeleteDTO baseBatchDeleteDTO) {
        sysRoleService.batchDelete(baseBatchDeleteDTO.getIdList());
        return ResultVO.success();
    }

    @PostMapping("update")
    @ApiOperation("修改角色")
    public ResultVO update(@Valid @RequestBody UpdateDTO updateDTO) {
        SysRole sysRole = new SysRole();
        BeanUtils.copyProperties(updateDTO, sysRole);
        sysRoleService.updateDBAndRedis(sysRole);
        return ResultVO.success();
    }

    @PostMapping("page")
    @ApiOperation("角色分页列表")
    @JsonView(SysRole.PageView.class)
    public ResultVO<BasePageVO<SysRoleBO>> page(@Valid @RequestBody PageDTO pageDTO) {
        return ResultVO.success(sysRoleService.page(pageDTO));
    }

    @PostMapping("list")
    @ApiOperation("角色列表")
    @JsonView(SysRole.ListView.class)
    public ResultVO<List<SysRole>> list() {
        return ResultVO.success(sysRoleService.queryAll());
    }

    @PostMapping("authMenu")
    @ApiOperation("授权菜单")
    public ResultVO authMenu(@Valid @RequestBody AuthMenuDTO authMenuDTO) {
        sysRoleService.authMenu(authMenuDTO);
        return ResultVO.success();
    }

    @PostMapping("authMenuFunction")
    @ApiOperation("授权菜单功能")
    public ResultVO authMenuFunction(@Valid @RequestBody AuthMenuFunctionDTO authMenuFunctionDTO){
        sysRoleService.authMenuFunction(authMenuFunctionDTO);
        return ResultVO.success();
    }

    @PostMapping("authApi")
    @ApiOperation("授权接口")
    public ResultVO authApi(@Valid @RequestBody AuthApiDTO authApiDTO){
        sysRoleService.authApi(authApiDTO);
        return ResultVO.success();
    }
}
