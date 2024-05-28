package net.rootkim.baseservice.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import net.rootkim.baseservice.dao.SysRoleAdminMenuRelationDao;
import net.rootkim.baseservice.domain.po.SysRoleAdminMenuRelation;
import net.rootkim.baseservice.mapper.SysRoleAdminMenuRelationMapper;
import net.rootkim.baseservice.service.SysRoleAdminMenuRelationService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.rootkim.core.exception.ParamException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;


import java.util.Collections;
import java.util.List;

/**
 * <p>
 * 角色_管理系统菜单关联表 服务实现类
 * </p>
 *
 * @author RootKim[rootkim.net]
 * @since 2024-04-29
 */
@Service
@Transactional
public class SysRoleAdminMenuRelationServiceImpl extends ServiceImpl<SysRoleAdminMenuRelationMapper, SysRoleAdminMenuRelation> implements SysRoleAdminMenuRelationService {

    @Autowired
    private SysRoleAdminMenuRelationDao sysRoleAdminMenuRelationDao;

    @Override
    public void add(SysRoleAdminMenuRelation sysRoleAdminMenuRelation) {
        if (StrUtil.isBlank(sysRoleAdminMenuRelation.getSysRoleId())) {
            throw new ParamException("系统角色id不可为空");
        }
        if (StrUtil.isBlank(sysRoleAdminMenuRelation.getAdminMenuId())) {
            throw new ParamException("管理系统菜单id不可为空");
        }
        long count = this.count(new LambdaQueryWrapper<SysRoleAdminMenuRelation>()
                .eq(SysRoleAdminMenuRelation::getSysRoleId, sysRoleAdminMenuRelation.getSysRoleId())
                .eq(SysRoleAdminMenuRelation::getAdminMenuId, sysRoleAdminMenuRelation.getAdminMenuId()));
        if (count > 0) {
            throw new ParamException("重复[角色_管理系统菜单]关联数据");
        }
        this.save(sysRoleAdminMenuRelation);
        sysRoleAdminMenuRelation = this.getById(sysRoleAdminMenuRelation.getId());
        sysRoleAdminMenuRelationDao.add(sysRoleAdminMenuRelation);
    }

    @Override
    public void delById(String id) {
        this.removeById(id);
        sysRoleAdminMenuRelationDao.delById(id);
    }

    @Override
    public void delByRoleId(String roleId) {
        this.remove(new LambdaQueryWrapper<SysRoleAdminMenuRelation>()
                .eq(SysRoleAdminMenuRelation::getSysRoleId, roleId));
        sysRoleAdminMenuRelationDao.delByRoleId(roleId);
    }

    @Override
    public void delByAdminMenuId(String adminMenuId) {
        this.remove(new LambdaQueryWrapper<SysRoleAdminMenuRelation>()
                .eq(SysRoleAdminMenuRelation::getAdminMenuId, adminMenuId));
        sysRoleAdminMenuRelationDao.delByAdminMenuId(adminMenuId);
    }

    @Override
    public List<SysRoleAdminMenuRelation> queryAll() {
        List<SysRoleAdminMenuRelation> sysRoleAdminMenuRelations = sysRoleAdminMenuRelationDao.queryAll();
        if (CollectionUtils.isEmpty(sysRoleAdminMenuRelations)) {
            sysRoleAdminMenuRelations = this.list();
            if (!CollectionUtils.isEmpty(sysRoleAdminMenuRelations)) {
                sysRoleAdminMenuRelations.forEach(sysRoleAdminMenuRelationDao::add);
            }
        }
        return sysRoleAdminMenuRelations;
    }

    @Override
    public SysRoleAdminMenuRelation queryById(String id) {
        SysRoleAdminMenuRelation sysRoleAdminMenuRelation = sysRoleAdminMenuRelationDao.queryById(id);
        if (ObjectUtils.isEmpty(sysRoleAdminMenuRelation)) {
            sysRoleAdminMenuRelation = this.getById(id);
            if (!ObjectUtils.isEmpty(sysRoleAdminMenuRelation)) {
                sysRoleAdminMenuRelationDao.add(sysRoleAdminMenuRelation);
            }
        }
        return sysRoleAdminMenuRelation;
    }

    @Override
    public List<SysRoleAdminMenuRelation> queryByRoleId(String roleId) {
        List<SysRoleAdminMenuRelation> sysRoleAdminMenuRelations = sysRoleAdminMenuRelationDao.queryByRoleId(roleId);
        if (CollectionUtils.isEmpty(sysRoleAdminMenuRelations)) {
            sysRoleAdminMenuRelations = this.list(new LambdaQueryWrapper<SysRoleAdminMenuRelation>()
                    .eq(SysRoleAdminMenuRelation::getSysRoleId, roleId));
            if (!CollectionUtils.isEmpty(sysRoleAdminMenuRelations)) {
                sysRoleAdminMenuRelations.forEach(sysRoleAdminMenuRelationDao::add);
            }
        }
        return sysRoleAdminMenuRelations;
    }

    @Override
    public List<SysRoleAdminMenuRelation> queryByAdminMenuId(String adminMenuId) {
        List<SysRoleAdminMenuRelation> sysRoleAdminMenuRelations = sysRoleAdminMenuRelationDao.queryByAdminMenuId(adminMenuId);
        if (CollectionUtils.isEmpty(sysRoleAdminMenuRelations)) {
            sysRoleAdminMenuRelations = this.list(new LambdaQueryWrapper<SysRoleAdminMenuRelation>()
                    .eq(SysRoleAdminMenuRelation::getAdminMenuId, adminMenuId));
            if (!CollectionUtils.isEmpty(sysRoleAdminMenuRelations)) {
                sysRoleAdminMenuRelations.forEach(sysRoleAdminMenuRelationDao::add);
            }
        }
        return sysRoleAdminMenuRelations;
    }
}
