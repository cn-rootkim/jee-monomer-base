package net.rootkim.baseservice.controller;

import com.fasterxml.jackson.annotation.JsonView;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import net.rootkim.baseservice.domain.bo.AdminMenuFunctionBO;
import net.rootkim.baseservice.domain.dto.adminMenuFunction.AddDTO;
import net.rootkim.baseservice.domain.dto.adminMenuFunction.ListDTO;
import net.rootkim.baseservice.domain.dto.adminMenuFunction.UpdateDTO;
import net.rootkim.baseservice.domain.po.AdminMenuFunction;
import net.rootkim.baseservice.service.AdminMenuFunctionService;
import net.rootkim.core.domain.dto.BaseBatchDeleteDTO;
import net.rootkim.core.domain.vo.ResultVO;
import net.rootkim.core.utils.HttpHeaderUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * <p>
 * 管理系统菜单功能表 前端控制器
 * </p>
 *
 * @author RootKim[rootkim.net]
 * @since 2024-04-29
 */
@RestController
@RequestMapping("/adminMenuFunction")
@Api(tags = "管理系统菜单功能")
@RequiredArgsConstructor
public class AdminMenuFunctionController {

    private final AdminMenuFunctionService adminMenuFunctionService;

    @PostMapping("add")
    @ApiOperation("新增菜单功能")
    public ResultVO add(@Valid @RequestBody AddDTO addDTO) {
        AdminMenuFunction adminMenuFunction = new AdminMenuFunction();
        BeanUtils.copyProperties(addDTO, adminMenuFunction);
        adminMenuFunctionService.add(adminMenuFunction);
        return ResultVO.success();
    }

    @PostMapping("batchDelete")
    @ApiOperation("批量删除菜单功能")
    public ResultVO batchDelete(@Valid @RequestBody BaseBatchDeleteDTO baseBatchDeleteDTO) {
        adminMenuFunctionService.batchDelete(baseBatchDeleteDTO.getIdList());
        return ResultVO.success();
    }

    @PostMapping("update")
    @ApiOperation("修改菜单功能")
    public ResultVO update(@Valid @RequestBody UpdateDTO updateDTO) {
        AdminMenuFunction adminMenuFunction = new AdminMenuFunction();
        BeanUtils.copyProperties(updateDTO, adminMenuFunction);
        adminMenuFunctionService.updateDBAndRedis(adminMenuFunction);
        return ResultVO.success();
    }

    @PostMapping("list")
    @ApiOperation("菜单功能列表")
    @JsonView(AdminMenuFunction.ListView.class)
    public ResultVO<List<AdminMenuFunctionBO>> list(@Valid @RequestBody ListDTO listDTO, @RequestAttribute(value = HttpHeaderUtil.USER_ID_KEY, required = false) String userId) {
        return ResultVO.success(adminMenuFunctionService.list(listDTO, userId));
    }
}
