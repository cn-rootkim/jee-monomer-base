package net.rootkim.baseservice.controller;

import com.fasterxml.jackson.annotation.JsonView;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import net.rootkim.baseservice.domain.bo.AdminMenuBO;
import net.rootkim.baseservice.domain.dto.adminMenu.AddDTO;
import net.rootkim.baseservice.domain.dto.adminMenu.ListTreeDTO;
import net.rootkim.baseservice.domain.dto.adminMenu.UpdateDTO;
import net.rootkim.baseservice.domain.po.AdminMenu;
import net.rootkim.baseservice.service.AdminMenuService;
import net.rootkim.core.domain.dto.BaseBatchDeleteDTO;
import net.rootkim.core.domain.vo.ResultVO;
import net.rootkim.core.utils.HttpHeaderUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * <p>
 * 管理系统菜单表 前端控制器
 * </p>
 *
 * @author RootKim[rootkim.net]
 * @since 2024-04-29
 */
@RestController
@RequestMapping("/adminMenu")
@Api(tags = "管理系统菜单")
@RequiredArgsConstructor
public class AdminMenuController {

    private final AdminMenuService adminMenuService;

    @PostMapping("add")
    @ApiOperation("新增菜单")
    public ResultVO add(@Valid @RequestBody AddDTO addDTO, @RequestAttribute(HttpHeaderUtil.USER_ID_KEY) String userId) {
        AdminMenu adminMenu = new AdminMenu();
        BeanUtils.copyProperties(adminMenu, addDTO);
        adminMenuService.add(adminMenu);
        return ResultVO.success();
    }

    @PostMapping("batchDelete")
    @ApiOperation("批量删除菜单")
    public ResultVO batchDelete(@Valid @RequestBody BaseBatchDeleteDTO baseBatchDeleteDTO) {
        adminMenuService.batchDelete(baseBatchDeleteDTO.getIdList());
        return ResultVO.success();
    }

    @PostMapping("update")
    @ApiOperation("修改菜单")
    public ResultVO update(@Valid @RequestBody UpdateDTO updateDTO) {
        AdminMenu adminMenu = new AdminMenu();
        BeanUtils.copyProperties(updateDTO, adminMenu);
        adminMenuService.updateDBAndRedis(adminMenu);
        return ResultVO.success();
    }

    @PostMapping("listTree")
    @ApiOperation("管理系统菜单列表（树状结构）")
    @JsonView(AdminMenu.ListTreeView.class)
    public ResultVO<List<AdminMenuBO>> listTree(@Valid @RequestBody ListTreeDTO listTreeDTO, @RequestAttribute(value = HttpHeaderUtil.USER_ID_KEY, required = false) String userId) {
        return ResultVO.success(adminMenuService.listTree(listTreeDTO, userId));
    }
}
