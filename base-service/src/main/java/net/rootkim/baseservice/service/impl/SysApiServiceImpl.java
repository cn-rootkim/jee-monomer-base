package net.rootkim.baseservice.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.RequiredArgsConstructor;
import net.rootkim.baseservice.dao.SysApiDao;
import net.rootkim.baseservice.domain.bo.SysApiBO;
import net.rootkim.baseservice.domain.po.SysRoleApiRelation;
import net.rootkim.baseservice.domain.po.SysUserRoleRelation;
import net.rootkim.baseservice.service.SysRoleApiRelationService;
import net.rootkim.baseservice.domain.po.SysApi;
import net.rootkim.baseservice.mapper.SysApiMapper;
import net.rootkim.baseservice.service.SysApiService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.rootkim.baseservice.service.SysUserRoleRelationService;
import net.rootkim.core.exception.ParamException;
import org.springframework.beans.BeanUtils;
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
 * 接口表 服务实现类
 * </p>
 *
 * @author RootKim[rootkim.net]
 * @since 2024-04-29
 */
@Service
@Transactional
@RequiredArgsConstructor
public class SysApiServiceImpl extends ServiceImpl<SysApiMapper, SysApi> implements SysApiService {

    private final SysApiDao sysApiDao;
    private final SysRoleApiRelationService sysRoleApiRelationService;
    private final SysUserRoleRelationService sysUserRoleRelationService;

    @Override
    public void add(SysApi sysApi) {
        if (StrUtil.isBlank(sysApi.getApi())) {
            throw new ParamException("接口不能为空");
        }
        if (StrUtil.isBlank(sysApi.getSysApiBasePathId())) {
            throw new ParamException("接口父路径id不能为空");
        }
        this.save(sysApi);
        sysApi = this.getById(sysApi.getId());
        sysApiDao.add(sysApi);
    }

    @Override
    public void delById(String id) {
        sysRoleApiRelationService.delByApiId(id);
        this.removeById(id);
        sysApiDao.delById(id);
    }

    @Override
    public void batchDelete(List<String> idList) {
        for (String id : idList) {
            this.delById(id);
        }
    }

    @Override
    public void delBySysApiBasePathId(String sysApiBasePathId) {
        List<SysApi> list = this.list(new LambdaQueryWrapper<SysApi>().eq(SysApi::getSysApiBasePathId, sysApiBasePathId));
        if (!CollectionUtils.isEmpty(list)) {
            list.forEach(sysApi -> {
                this.delById(sysApi.getId());
            });
        }
    }

    @Override
    public void updateDBAndRedis(SysApi sysApi) {
        if (StrUtil.isBlank(sysApi.getApi())) {
            throw new ParamException("接口不能为空");
        }
        if (StrUtil.isBlank(sysApi.getSysApiBasePathId())) {
            throw new ParamException("接口父路径id不能为空");
        }
        this.updateById(sysApi);
        sysApi = this.getById(sysApi.getId());
        sysApiDao.delById(sysApi.getId());
        sysApiDao.add(sysApi);
    }

    @Override
    public List<SysApi> queryAll() {
        List<SysApi> sysApis = sysApiDao.queryAll();
        if (CollectionUtils.isEmpty(sysApis)) {
            sysApis = this.list();
            if (!CollectionUtils.isEmpty(sysApis)) {
                sysApis.forEach(sysApiDao::add);
            }
        }
        return sysApis;
    }

    @Override
    public SysApi queryById(String id) {
        SysApi sysApi = sysApiDao.queryById(id);
        if (ObjectUtils.isEmpty(sysApi)) {
            sysApi = this.getById(id);
            if (!ObjectUtils.isEmpty(sysApi)) {
                sysApiDao.add(sysApi);
            }
        }
        return sysApi;
    }

    @Override
    public List<SysApi> queryByUserId(String userId) {
        List<SysApi> list = new ArrayList<>();
        List<SysUserRoleRelation> sysUserRoleRelations = sysUserRoleRelationService.queryByUserId(userId);
        for (SysUserRoleRelation sysUserRoleRelation : sysUserRoleRelations) {
            List<SysRoleApiRelation> sysRoleApiRelations = sysRoleApiRelationService.queryByRoleId(sysUserRoleRelation.getSysRoleId());
            for (SysRoleApiRelation sysRoleApiRelation : sysRoleApiRelations) {
                SysApi sysApi = this.queryById(sysRoleApiRelation.getSysApiId());
                if (!ObjectUtils.isEmpty(sysApi)) {
                    list.add(sysApi);
                }
            }
        }
        return list.stream().distinct().collect(Collectors.toList());
    }

    @Override
    public List<SysApiBO> queryByBasePathId(String sysApiBasePathId) {
        return this.queryAll().stream().map(sysApi -> {
            SysApiBO sysApiBO = new SysApiBO();
            BeanUtils.copyProperties(sysApi, sysApiBO);
            return sysApiBO;
        }).filter(sysApiBO -> sysApiBasePathId.equals(sysApiBO.getSysApiBasePathId())).collect(Collectors.toList());
    }

    @Override
    public List<SysApiBO> queryByBasePathIdAndRoleId(String sysApiBasePathId, String roleId) {
        List<SysRoleApiRelation> sysRoleApiRelations = sysRoleApiRelationService.queryByRoleId(roleId);
        List<SysApiBO> list = this.queryByBasePathId(sysApiBasePathId);
        for (SysApiBO sysApiBO : list) {
            sysApiBO.setIsAuth(sysRoleApiRelations.stream().anyMatch(sysRoleApiRelation -> sysApiBO.getId().equals(sysRoleApiRelation.getSysApiId())));
        }
        return list;
    }

    @Override
    public void reloadCache() {
        sysApiDao.delAll();
        this.queryAll();
    }

    @Override
    public void reloadCache(String id) {
        sysApiDao.delById(id);
        this.queryById(id);
    }
}
