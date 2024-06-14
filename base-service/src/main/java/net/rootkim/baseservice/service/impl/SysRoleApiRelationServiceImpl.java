package net.rootkim.baseservice.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.RequiredArgsConstructor;
import net.rootkim.baseservice.dao.SysRoleApiRelationDao;
import net.rootkim.baseservice.domain.po.SysRoleApiRelation;
import net.rootkim.baseservice.mapper.SysRoleApiRelationMapper;
import net.rootkim.baseservice.service.SysRoleApiRelationService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.rootkim.core.exception.ParamException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;


import java.util.List;

/**
 * <p>
 * 角色_接口关联表 服务实现类
 * </p>
 *
 * @author RootKim[rootkim.net]
 * @since 2024-04-29
 */
@Service
@Transactional
@RequiredArgsConstructor
public class SysRoleApiRelationServiceImpl extends ServiceImpl<SysRoleApiRelationMapper, SysRoleApiRelation> implements SysRoleApiRelationService {

    private final SysRoleApiRelationDao sysRoleApiRelationDao;

    @Override
    public void add(SysRoleApiRelation sysRoleApiRelation) {
        if (StrUtil.isBlank(sysRoleApiRelation.getSysRoleId())) {
            throw new ParamException("系统角色id不可为空");
        }
        if (StrUtil.isBlank(sysRoleApiRelation.getSysApiId())) {
            throw new ParamException("系统接口id不可为空");
        }
        long count = this.count(new LambdaQueryWrapper<SysRoleApiRelation>().eq(SysRoleApiRelation::getSysRoleId, sysRoleApiRelation.getSysRoleId())
                .eq(SysRoleApiRelation::getSysApiId, sysRoleApiRelation.getSysApiId()));
        if (count > 0) {
            throw new ParamException("重复[角色_接口]关联数据");
        }
        this.save(sysRoleApiRelation);
        sysRoleApiRelation = this.getById(sysRoleApiRelation.getId());
        sysRoleApiRelationDao.add(sysRoleApiRelation);
    }

    @Override
    public void delById(String id) {
        this.removeById(id);
        sysRoleApiRelationDao.delById(id);
    }

    @Override
    public void delByRoleId(String roleId) {
        this.remove(new LambdaQueryWrapper<SysRoleApiRelation>().eq(SysRoleApiRelation::getSysRoleId, roleId));
        sysRoleApiRelationDao.delByRoleId(roleId);
    }

    @Override
    public void delByApiId(String apiId) {
        this.remove(new LambdaQueryWrapper<SysRoleApiRelation>().eq(SysRoleApiRelation::getSysApiId, apiId));
        sysRoleApiRelationDao.delByApiId(apiId);
    }

    @Override
    public List<SysRoleApiRelation> queryAll() {
        List<SysRoleApiRelation> sysRoleApiRelations = sysRoleApiRelationDao.queryAll();
        if (CollectionUtils.isEmpty(sysRoleApiRelations)) {
            sysRoleApiRelations = this.list();
            if (!CollectionUtils.isEmpty(sysRoleApiRelations)) {
                sysRoleApiRelations.forEach(sysRoleApiRelationDao::add);
            }
        }
        return sysRoleApiRelations;
    }

    @Override
    public SysRoleApiRelation queryById(String id) {
        SysRoleApiRelation sysRoleApiRelation = sysRoleApiRelationDao.queryById(id);
        if (ObjectUtils.isEmpty(sysRoleApiRelation)) {
            sysRoleApiRelation = this.getById(id);
            if (!ObjectUtils.isEmpty(sysRoleApiRelation)) {
                sysRoleApiRelationDao.add(sysRoleApiRelation);
            }
        }
        return sysRoleApiRelation;
    }

    @Override
    public List<SysRoleApiRelation> queryByRoleId(String roleId) {
        List<SysRoleApiRelation> sysRoleApiRelations = sysRoleApiRelationDao.queryByRoleId(roleId);
        if (CollectionUtils.isEmpty(sysRoleApiRelations)) {
            sysRoleApiRelations = this.list(new LambdaQueryWrapper<SysRoleApiRelation>().eq(SysRoleApiRelation::getSysRoleId, roleId));
            if (!CollectionUtils.isEmpty(sysRoleApiRelations)) {
                sysRoleApiRelations.forEach(sysRoleApiRelationDao::add);
            }
        }
        return sysRoleApiRelations;
    }

    @Override
    public List<SysRoleApiRelation> queryByApiId(String apiId) {
        List<SysRoleApiRelation> sysRoleApiRelations = sysRoleApiRelationDao.queryByApiId(apiId);
        if (CollectionUtils.isEmpty(sysRoleApiRelations)) {
            sysRoleApiRelations = this.list(new LambdaQueryWrapper<SysRoleApiRelation>().eq(SysRoleApiRelation::getSysApiId, apiId));
            if (!CollectionUtils.isEmpty(sysRoleApiRelations)) {
                sysRoleApiRelations.forEach(sysRoleApiRelationDao::add);
            }
        }
        return sysRoleApiRelations;
    }

    @Override
    public void reloadCache() {
        sysRoleApiRelationDao.delAll();
        this.queryAll();
    }

    @Override
    public void reloadCache(String id) {
        sysRoleApiRelationDao.delById(id);
        this.queryById(id);
    }
}
