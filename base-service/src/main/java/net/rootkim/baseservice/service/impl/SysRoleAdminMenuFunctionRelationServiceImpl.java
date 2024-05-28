package net.rootkim.baseservice.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import net.rootkim.baseservice.dao.SysRoleAdminMenuFunctionRelationDao;
import net.rootkim.baseservice.dao.SysUserRoleRelationDao;
import net.rootkim.baseservice.dao.impl.SysUserRoleRelationDaoImpl;
import net.rootkim.baseservice.domain.po.AdminMenuFunction;
import net.rootkim.baseservice.domain.po.SysRoleAdminMenuFunctionRelation;
import net.rootkim.baseservice.domain.po.SysUserRoleRelation;
import net.rootkim.baseservice.mapper.AdminMenuFunctionMapper;
import net.rootkim.baseservice.mapper.SysRoleAdminMenuFunctionRelationMapper;
import net.rootkim.baseservice.service.AdminMenuFunctionService;
import net.rootkim.baseservice.service.SysRoleAdminMenuFunctionRelationService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.rootkim.baseservice.service.SysUserRoleRelationService;
import net.rootkim.core.exception.ParamException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 角色_管理系统菜单功能关联表 服务实现类
 * </p>
 *
 * @author RootKim[rootkim.net]
 * @since 2024-04-29
 */
@Service
@Transactional
public class SysRoleAdminMenuFunctionRelationServiceImpl extends ServiceImpl<SysRoleAdminMenuFunctionRelationMapper, SysRoleAdminMenuFunctionRelation> implements SysRoleAdminMenuFunctionRelationService {

    @Autowired
    private SysRoleAdminMenuFunctionRelationDao sysRoleAdminMenuFunctionRelationDao;
    @Autowired
    private SysUserRoleRelationService sysUserRoleRelationService;
    @Autowired
    private AdminMenuFunctionService adminMenuFunctionService;

    @Override
    public void add(SysRoleAdminMenuFunctionRelation sysRoleAdminMenuFunctionRelation) {
        if (StrUtil.isBlank(sysRoleAdminMenuFunctionRelation.getSysRoleId())) {
            throw new ParamException("系统角色id不可为空");
        }
        if (StrUtil.isBlank(sysRoleAdminMenuFunctionRelation.getAdminMenuFunctionId())) {
            throw new ParamException("管理系统菜单功能id不可为空");
        }
        long count = this.count(new LambdaQueryWrapper<SysRoleAdminMenuFunctionRelation>()
                .eq(SysRoleAdminMenuFunctionRelation::getSysRoleId, sysRoleAdminMenuFunctionRelation.getSysRoleId())
                .eq(SysRoleAdminMenuFunctionRelation::getAdminMenuFunctionId, sysRoleAdminMenuFunctionRelation.getAdminMenuFunctionId()));
        if (count > 0) {
            throw new ParamException("重复[角色_管理系统菜单功能]关联数据");
        }
        this.save(sysRoleAdminMenuFunctionRelation);
        sysRoleAdminMenuFunctionRelation = this.getById(sysRoleAdminMenuFunctionRelation.getId());
        sysRoleAdminMenuFunctionRelationDao.add(sysRoleAdminMenuFunctionRelation);
    }

    @Override
    public void delById(String id) {
        this.removeById(id);
        sysRoleAdminMenuFunctionRelationDao.delById(id);
    }

    @Override
    public void delByRoleId(String roleId) {
        this.remove(new LambdaQueryWrapper<SysRoleAdminMenuFunctionRelation>()
                .eq(SysRoleAdminMenuFunctionRelation::getSysRoleId, roleId));
        sysRoleAdminMenuFunctionRelationDao.delByRoleId(roleId);
    }

    @Override
    public void delByAdminMenuFunctionId(String adminMenuFunctionId) {
        this.remove(new LambdaQueryWrapper<SysRoleAdminMenuFunctionRelation>()
                .eq(SysRoleAdminMenuFunctionRelation::getAdminMenuFunctionId, adminMenuFunctionId));
        sysRoleAdminMenuFunctionRelationDao.delByAdminMenuFunctionId(adminMenuFunctionId);
    }

    @Override
    public void delByRoleIdAndAdminMenuId(String roleId, String adminMenuId) {
        List<AdminMenuFunction> adminMenuFunctions = adminMenuFunctionService.queryByAdminMenuId(adminMenuId);
        this.remove(new LambdaQueryWrapper<SysRoleAdminMenuFunctionRelation>()
                .eq(SysRoleAdminMenuFunctionRelation::getSysRoleId, roleId)
                .in(SysRoleAdminMenuFunctionRelation::getAdminMenuFunctionId, adminMenuFunctions.stream().map(AdminMenuFunction::getId).collect(Collectors.toList())));
        for (AdminMenuFunction adminMenuFunction : adminMenuFunctions) {
            sysRoleAdminMenuFunctionRelationDao.delByRoleIdAndAdminMenuFunctionId(roleId, adminMenuFunction.getId());
        }
    }

    @Override
    public List<SysRoleAdminMenuFunctionRelation> queryAll() {
        List<SysRoleAdminMenuFunctionRelation> sysRoleAdminMenuFunctionRelations = sysRoleAdminMenuFunctionRelationDao.queryAll();
        if (CollectionUtils.isEmpty(sysRoleAdminMenuFunctionRelations)) {
            sysRoleAdminMenuFunctionRelations = this.list();
            if (!CollectionUtils.isEmpty(sysRoleAdminMenuFunctionRelations)) {
                sysRoleAdminMenuFunctionRelations.forEach(sysRoleAdminMenuFunctionRelationDao::add);
            }
        }
        return sysRoleAdminMenuFunctionRelations;
    }

    @Override
    public SysRoleAdminMenuFunctionRelation queryById(String id) {
        SysRoleAdminMenuFunctionRelation sysRoleAdminMenuFunctionRelation = sysRoleAdminMenuFunctionRelationDao.queryById(id);
        if (ObjectUtils.isEmpty(sysRoleAdminMenuFunctionRelation)) {
            sysRoleAdminMenuFunctionRelation = this.getById(id);
            if (!ObjectUtils.isEmpty(sysRoleAdminMenuFunctionRelation)) {
                sysRoleAdminMenuFunctionRelationDao.add(sysRoleAdminMenuFunctionRelation);
            }
        }
        return sysRoleAdminMenuFunctionRelation;
    }

    @Override
    public List<SysRoleAdminMenuFunctionRelation> queryByRoleId(String roleId) {
        List<SysRoleAdminMenuFunctionRelation> sysRoleAdminMenuFunctionRelations = sysRoleAdminMenuFunctionRelationDao.queryByRoleId(roleId);
        if (CollectionUtils.isEmpty(sysRoleAdminMenuFunctionRelations)) {
            sysRoleAdminMenuFunctionRelations = this.list(new LambdaQueryWrapper<SysRoleAdminMenuFunctionRelation>()
                    .eq(SysRoleAdminMenuFunctionRelation::getSysRoleId, roleId));
            if (!CollectionUtils.isEmpty(sysRoleAdminMenuFunctionRelations)) {
                sysRoleAdminMenuFunctionRelations.forEach(sysRoleAdminMenuFunctionRelationDao::add);
            }
        }
        return sysRoleAdminMenuFunctionRelations;
    }

    @Override
    public List<SysRoleAdminMenuFunctionRelation> queryByUserId(String userId) {
        List<SysRoleAdminMenuFunctionRelation> list = new ArrayList<>();
        List<SysUserRoleRelation> sysUserRoleRelations = sysUserRoleRelationService.queryByUserId(userId);
        for (SysUserRoleRelation sysUserRoleRelation : sysUserRoleRelations) {
            list.addAll(this.queryByRoleId(sysUserRoleRelation.getSysRoleId()));
        }
        return list.stream().distinct().collect(Collectors.toList());
    }

    @Override
    public List<SysRoleAdminMenuFunctionRelation> queryByAdminMenuFunctionId(String adminMenuFunctionId) {
        List<SysRoleAdminMenuFunctionRelation> sysRoleAdminMenuFunctionRelations = sysRoleAdminMenuFunctionRelationDao.queryByAdminMenuFunctionId(adminMenuFunctionId);
        if (CollectionUtils.isEmpty(sysRoleAdminMenuFunctionRelations)) {
            sysRoleAdminMenuFunctionRelations = this.list(new LambdaQueryWrapper<SysRoleAdminMenuFunctionRelation>()
                    .eq(SysRoleAdminMenuFunctionRelation::getAdminMenuFunctionId, adminMenuFunctionId));
            if (!CollectionUtils.isEmpty(sysRoleAdminMenuFunctionRelations)) {
                sysRoleAdminMenuFunctionRelations.forEach(sysRoleAdminMenuFunctionRelationDao::add);
            }
        }
        return sysRoleAdminMenuFunctionRelations;
    }
}
