package net.rootkim.baseservice.controller;

import com.fasterxml.jackson.annotation.JsonView;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.rootkim.baseservice.domain.bo.SysUserBO;
import net.rootkim.baseservice.domain.dto.sysUser.*;
import net.rootkim.baseservice.domain.po.SysUser;
import net.rootkim.baseservice.domain.vo.sysUser.*;
import net.rootkim.baseservice.service.SysUserService;
import net.rootkim.core.domain.dto.BaseBatchDeleteDTO;
import net.rootkim.core.domain.vo.BasePageVO;
import net.rootkim.core.domain.vo.ResultVO;
import net.rootkim.core.utils.HttpHeaderUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author RootKim[rootkim.net]
 * @since 2024-04-28
 */
@RestController
@RequestMapping("/sysUser")
@Api(tags = "用户")
public class SysUserController {

    @Autowired
    private SysUserService sysUserService;

    @ApiOperation("新增用户")
    @PostMapping("add")
    public ResultVO add(@Valid @RequestBody AddDTO addDTO, @RequestAttribute(HttpHeaderUtil.USER_ID_KEY) String userId) {
        SysUser sysUser = new SysUser();
        BeanUtils.copyProperties(addDTO, sysUser);
        sysUser.setType(new Byte("0"));
        sysUser.setCreater(userId);
        sysUser.setUpdater(userId);
        sysUserService.add(sysUser, addDTO.getRoleIdList());
        return ResultVO.success();
    }

    @ApiOperation("批量删除用户")
    @PostMapping("batchDelete")
    public ResultVO batchDelete(@Valid @RequestBody BaseBatchDeleteDTO baseBatchDeleteDTO) {
        baseBatchDeleteDTO.getIdList().forEach(sysUserService::del);
        return ResultVO.success();
    }

    @ApiOperation("修改用户")
    @PostMapping("update")
    public ResultVO update(@Valid @RequestBody UpdateDTO updateDTO, @RequestAttribute(HttpHeaderUtil.USER_ID_KEY) String userId) {
        SysUser sysUser = new SysUser();
        BeanUtils.copyProperties(updateDTO, sysUser);
        sysUser.setUpdater(userId);
        sysUserService.updateDBAndRedis(sysUser, updateDTO.getRoleIdList());
        return ResultVO.success();
    }

    @ApiOperation("分页查询用户")
    @PostMapping("page")
    @JsonView(SysUser.PageView.class)
    public ResultVO<BasePageVO<SysUserBO>> page(@Valid @RequestBody PageDTO pageDTO) {
        return ResultVO.success(sysUserService.page(pageDTO));
    }

    @ApiOperation("获取用户信息")
    @PostMapping("info")
    @JsonView(SysUser.InfoView.class)
    public ResultVO<SysUserBO> info(@Valid @RequestBody InfoDTO infoDTO) {
        return ResultVO.success(sysUserService.info(infoDTO));
    }

    @ApiOperation("用户名密码登录[B端]")
    @PostMapping("loginByUsernameAndPassword")
    public ResultVO<LoginVO> loginByUsernameAndPassword(@Valid @RequestBody LoginByUsernameAndPasswordDTO loginByUsernameAndPasswordDTO,
                                                        @RequestHeader(HttpHeaderUtil.USER_AGENT_KEY) String userAgent) {
        return ResultVO.success(new LoginVO(sysUserService.loginByUsernameAndPassword(
                HttpHeaderUtil.getClientPlatform(userAgent),
                loginByUsernameAndPasswordDTO.getUsername(),
                loginByUsernameAndPasswordDTO.getPassword(),
                new Byte("0"))));
    }

    @ApiOperation("退出登录")
    @PostMapping("logout")
    public ResultVO logout(@RequestAttribute(HttpHeaderUtil.USER_ID_KEY) String userId,
                           @RequestHeader(HttpHeaderUtil.USER_AGENT_KEY) String userAgent,
                           @RequestAttribute(HttpHeaderUtil.USER_TYPE_KEY) String userType) {
        sysUserService.logout(HttpHeaderUtil.getClientPlatform(userAgent), userId, new Byte(userType));
        return ResultVO.success();
    }
}
