package net.rootkim.baseservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import net.rootkim.baseservice.dao.SysUserRoleRelationDao;
import net.rootkim.baseservice.mapper.SysUserRoleRelationMapper;
import net.rootkim.baseservice.service.SysUserRoleRelationService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.rootkim.baseservice.domain.po.SysUserRoleRelation;
import net.rootkim.core.exception.ParamException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * <p>
 * 用户_角色_关联表 服务实现类
 * </p>
 *
 * @author RootKim[rootkim.net]
 * @since 2024-04-29
 */
@Service
@Transactional
public class SysUserRoleRelationServiceImpl extends ServiceImpl<SysUserRoleRelationMapper, SysUserRoleRelation> implements SysUserRoleRelationService {

    @Autowired
    private SysUserRoleRelationDao sysUserRoleRelationDao;

    @Override
    public void add(SysUserRoleRelation sysUserRoleRelation) {
        if (!StringUtils.hasText(sysUserRoleRelation.getSysUserId())) {
            throw new ParamException("用户id不可为空");
        }
        if (!StringUtils.hasText(sysUserRoleRelation.getSysRoleId())) {
            throw new ParamException("角色id不可为空");
        }
        long count = this.count(new LambdaQueryWrapper<SysUserRoleRelation>()
                .eq(SysUserRoleRelation::getSysUserId, sysUserRoleRelation.getSysUserId())
                .eq(SysUserRoleRelation::getSysRoleId, sysUserRoleRelation.getSysRoleId()));
        if (count > 0) {
            throw new ParamException("重复[用户_角色]关联数据");
        }
        this.save(sysUserRoleRelation);
        sysUserRoleRelation = this.getById(sysUserRoleRelation.getId());
        sysUserRoleRelationDao.add(sysUserRoleRelation);
    }

    @Override
    public void delById(String id) {
        this.removeById(id);
        sysUserRoleRelationDao.delById(id);
    }

    @Override
    public void delByUserId(String userId) {
        this.remove(new LambdaQueryWrapper<SysUserRoleRelation>().eq(SysUserRoleRelation::getSysUserId, userId));
        sysUserRoleRelationDao.delByUserId(userId);
    }

    @Override
    public void delByRoleId(String roleId) {
        this.remove(new LambdaQueryWrapper<SysUserRoleRelation>().eq(SysUserRoleRelation::getSysRoleId, roleId));
        sysUserRoleRelationDao.delByRoleId(roleId);
    }

    @Override
    public List<SysUserRoleRelation> queryAll() {
        List<SysUserRoleRelation> sysUserRoleRelations = sysUserRoleRelationDao.queryAll();
        if (CollectionUtils.isEmpty(sysUserRoleRelations)) {
            sysUserRoleRelations = this.list();
            if (!CollectionUtils.isEmpty(sysUserRoleRelations)) {
                sysUserRoleRelations.forEach(sysUserRoleRelationDao::add);
            }
        }
        return sysUserRoleRelations;
    }

    @Override
    public SysUserRoleRelation queryById(String id) {
        SysUserRoleRelation sysUserRoleRelation = sysUserRoleRelationDao.queryById(id);
        if (ObjectUtils.isEmpty(sysUserRoleRelation)) {
            sysUserRoleRelation = this.getById(id);
            if (!ObjectUtils.isEmpty(sysUserRoleRelation)) {
                sysUserRoleRelationDao.add(sysUserRoleRelation);
            }
        }
        return sysUserRoleRelation;
    }

    @Override
    public List<SysUserRoleRelation> queryByUserId(String userId) {
        List<SysUserRoleRelation> sysUserRoleRelations = sysUserRoleRelationDao.queryByUserId(userId);
        if (CollectionUtils.isEmpty(sysUserRoleRelations)) {
            sysUserRoleRelations = this.list(new LambdaQueryWrapper<SysUserRoleRelation>().eq(SysUserRoleRelation::getSysUserId, userId));
            if (!CollectionUtils.isEmpty(sysUserRoleRelations)) {
                sysUserRoleRelations.forEach(sysUserRoleRelationDao::add);
            }
        }
        return sysUserRoleRelations;
    }

    @Override
    public List<SysUserRoleRelation> queryByRoleId(String roleId) {
        List<SysUserRoleRelation> sysUserRoleRelations = sysUserRoleRelationDao.queryByRoleId(roleId);
        if (CollectionUtils.isEmpty(sysUserRoleRelations)) {
            sysUserRoleRelations = this.list(new LambdaQueryWrapper<SysUserRoleRelation>().eq(SysUserRoleRelation::getSysRoleId, roleId));
            if (!CollectionUtils.isEmpty(sysUserRoleRelations)) {
                sysUserRoleRelations.forEach(sysUserRoleRelationDao::add);
            }
        }
        return sysUserRoleRelations;
    }
}
