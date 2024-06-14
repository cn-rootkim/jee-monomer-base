package net.rootkim.baseservice.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.RequiredArgsConstructor;
import net.rootkim.baseservice.dao.SysRoleDao;
import net.rootkim.baseservice.domain.bo.SysRoleBO;
import net.rootkim.baseservice.domain.dto.sysRole.AuthApiDTO;
import net.rootkim.baseservice.domain.dto.sysRole.AuthMenuDTO;
import net.rootkim.baseservice.domain.dto.sysRole.AuthMenuFunctionDTO;
import net.rootkim.baseservice.domain.dto.sysRole.PageDTO;
import net.rootkim.baseservice.domain.po.*;
import net.rootkim.baseservice.mapper.SysRoleMapper;
import net.rootkim.baseservice.service.*;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.rootkim.core.domain.vo.BasePageVO;
import net.rootkim.core.exception.ParamException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;


import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 角色表 服务实现类
 * </p>
 *
 * @author RootKim[rootkim.net]
 * @since 2024-04-29
 */
@Service
@Transactional
//@RequiredArgsConstructor
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements SysRoleService {

    @Autowired
    private SysRoleDao sysRoleDao;
    private SysUserRoleRelationService sysUserRoleRelationService;
    private SysRoleApiRelationService sysRoleApiRelationService;
    private SysRoleAdminMenuRelationService sysRoleAdminMenuRelationService;
    private SysRoleAdminMenuFunctionRelationService sysRoleAdminMenuFunctionRelationService;
    private SysUserService sysUserService;

    @Override
    public void add(SysRole sysRole) {
        if (StrUtil.isBlank(sysRole.getRoleKey())) {
            throw new ParamException("角色key不可为空");
        }
        if (!ObjectUtils.isEmpty(this.queryByKey(sysRole.getRoleKey()))) {
            throw new ParamException("角色key已存在");
        }
        this.save(sysRole);
        sysRole = this.getById(sysRole.getId());
        sysRoleDao.add(sysRole);
    }

    @Override
    public void delById(String id) {
        sysUserRoleRelationService.delByRoleId(id);//删除用户_角色关联数据
        sysRoleApiRelationService.delByRoleId(id);//删除角色_接口关联数据
        sysRoleAdminMenuRelationService.delByRoleId(id);//删除角色_管理系统菜单关联数据
        sysRoleAdminMenuFunctionRelationService.delByRoleId(id);//删除角色_管理系统菜单功能关联数据
        //删除角色数据
        this.removeById(id);
        sysRoleDao.delById(id);
    }

    @Override
    public void batchDelete(List<String> idList) {
        for (String id : idList) {
            this.delById(id);
        }
    }

    @Override
    public void updateDBAndRedis(SysRole sysRole) {
        if (StrUtil.isBlank(sysRole.getRoleKey())) {
            throw new ParamException("角色key不可为空");
        }
        long count = this.count(new LambdaQueryWrapper<SysRole>().eq(SysRole::getRoleKey, sysRole.getRoleKey()).ne(SysRole::getId, sysRole.getId()));
        if (count > 0) {
            throw new ParamException("角色key已存在");
        }
        this.updateById(sysRole);
        sysRole = this.getById(sysRole.getId());
        sysRoleDao.add(sysRole);
    }

    @Override
    public List<SysRole> queryAll() {
        List<SysRole> sysRoles = sysRoleDao.queryAll();
        if (CollectionUtils.isEmpty(sysRoles)) {
            sysRoles = this.list();
            if (!CollectionUtils.isEmpty(sysRoles)) {
                sysRoles.forEach(sysRoleDao::add);
            }
        }
        return sysRoles;
    }

    @Override
    public SysRole queryById(String id) {
        SysRole sysRole = sysRoleDao.queryById(id);
        if (ObjectUtils.isEmpty(sysRole)) {
            sysRole = this.getById(id);
            sysRoleDao.add(sysRole);
        }
        return sysRole;
    }

    @Override
    public SysRole queryByKey(String key) {
        SysRole sysRole = sysRoleDao.queryByKey(key);
        if (ObjectUtils.isEmpty(sysRole)) {
            sysRole = this.getOne(new LambdaQueryWrapper<SysRole>().eq(SysRole::getRoleKey, key));
            sysRoleDao.add(sysRole);
        }
        return sysRole;
    }

    @Override
    public BasePageVO<SysRoleBO> page(PageDTO pageDTO) {
        BasePageVO<SysRoleBO> basePageVO = new BasePageVO<>(pageDTO.getCurrent(), pageDTO.getSize());
        Page<SysRole> page = new Page<>(pageDTO.getCurrent(), pageDTO.getSize());
        this.page(page, new LambdaQueryWrapper<SysRole>().eq(StrUtil.isNotBlank(pageDTO.getKey()), SysRole::getRoleKey, pageDTO.getKey())
                .like(StrUtil.isNotBlank(pageDTO.getName()), SysRole::getName, pageDTO.getName()));

        BeanUtils.copyProperties(page, basePageVO);
        if (!CollectionUtils.isEmpty(page.getRecords())) {
            basePageVO.setRecords(page.getRecords().stream().map(sysRole -> {
                SysRoleBO sysRoleBO = new SysRoleBO();
                BeanUtils.copyProperties(sysRole, sysRoleBO);
                SysUser sysUser = sysUserService.queryById(sysRole.getCreater());
                if (!ObjectUtils.isEmpty(sysUser)) {
                    sysRoleBO.setCreaterName(sysUser.getUsername());
                }
                sysUser = sysUserService.queryById(sysRole.getUpdater());
                if (!ObjectUtils.isEmpty(sysUser)) {
                    sysRoleBO.setUpdaterName(sysUser.getUsername());
                }
                return sysRoleBO;
            }).collect(Collectors.toList()));
        }
        return basePageVO;
    }

    @Override
    public void authMenu(AuthMenuDTO authMenuDTO) {
        //删除角色_管理系统菜单关联数据
        sysRoleAdminMenuRelationService.delByRoleId(authMenuDTO.getRoleId());
        //新增角色_管理系统菜单关联数据
        authMenuDTO.getMenuIdList().forEach(menuId -> {
            SysRoleAdminMenuRelation sysRoleAdminMenuRelation = new SysRoleAdminMenuRelation();
            sysRoleAdminMenuRelation.setSysRoleId(authMenuDTO.getRoleId());
            sysRoleAdminMenuRelation.setAdminMenuId(menuId);
            sysRoleAdminMenuRelationService.add(sysRoleAdminMenuRelation);
        });
    }

    @Override
    public void authMenuFunction(AuthMenuFunctionDTO authMenuFunctionDTO) {
        //删除角色_管理系统菜单功能关联数据
//        sysRoleAdminMenuFunctionRelationService.delByRoleIdAndAdminMenuId(authMenuFunctionDTO.getRoleId(), authMenuFunctionDTO.getMenuId());
        //新增角色_管理系统菜单功能关联数据
        if (!CollectionUtils.isEmpty(authMenuFunctionDTO.getMenuFunctionIdList())) {
            for (String menuFunctionId : authMenuFunctionDTO.getMenuFunctionIdList()) {
                SysRoleAdminMenuFunctionRelation sysRoleAdminMenuFunctionRelation = new SysRoleAdminMenuFunctionRelation();
                sysRoleAdminMenuFunctionRelation.setSysRoleId(authMenuFunctionDTO.getRoleId());
                sysRoleAdminMenuFunctionRelation.setAdminMenuFunctionId(menuFunctionId);
                sysRoleAdminMenuFunctionRelationService.add(sysRoleAdminMenuFunctionRelation);
            }
        }
    }

    @Override
    public void authApi(AuthApiDTO authApiDTO) {
        //删除角色_接口关联数据
        sysRoleApiRelationService.delByRoleId(authApiDTO.getRoleId());
        //新增角色_接口关联数据
        authApiDTO.getApiIdList().forEach(apiId -> {
            SysRoleApiRelation sysRoleApiRelation = new SysRoleApiRelation();
            sysRoleApiRelation.setSysRoleId(authApiDTO.getRoleId());
            sysRoleApiRelation.setSysApiId(apiId);
            sysRoleApiRelationService.add(sysRoleApiRelation);
        });
    }

    @Override
    public void reloadCache() {
        sysRoleDao.delAll();
        this.queryAll();
    }

    @Override
    public void reloadCache(String id) {
        sysRoleDao.delById(id);
        this.queryById(id);
    }
}
