package net.rootkim.baseservice.service.impl;

import cn.hutool.core.util.StrUtil;
import lombok.RequiredArgsConstructor;
import net.rootkim.baseservice.dao.SysApiBasePathDao;
import net.rootkim.baseservice.domain.bo.SysApiBasePathBO;
import net.rootkim.baseservice.domain.dto.sysApiBasePath.ListDTO;
import net.rootkim.baseservice.service.SysApiService;
import net.rootkim.baseservice.domain.po.SysApiBasePath;
import net.rootkim.baseservice.mapper.SysApiBasePathMapper;
import net.rootkim.baseservice.service.SysApiBasePathService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.rootkim.core.exception.ParamException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;


import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 接口父路径表 服务实现类
 * </p>
 *
 * @author RootKim[rootkim.net]
 * @since 2024-04-29
 */
@Service
@Transactional
@RequiredArgsConstructor
public class SysApiBasePathServiceImpl extends ServiceImpl<SysApiBasePathMapper, SysApiBasePath> implements SysApiBasePathService {

    private final SysApiBasePathDao sysApiBasePathDao;
    private final SysApiService sysApiService;

    @Override
    public void add(SysApiBasePath sysApiBasePath) {
        if (StrUtil.isBlank(sysApiBasePath.getBasePath())) {
            throw new ParamException("接口父路径不能为空");
        }
        this.save(sysApiBasePath);
        sysApiBasePath = this.getById(sysApiBasePath.getId());
        sysApiBasePathDao.add(sysApiBasePath);
    }

    @Override
    public void delById(String id) {
        sysApiService.delBySysApiBasePathId(id);
        this.removeById(id);
        sysApiBasePathDao.delById(id);
    }

    @Override
    public void batchDelete(List<String> idList) {
        for (String id : idList) {
            this.delById(id);
        }
    }

    @Override
    public void updateDBAndRedis(SysApiBasePath sysApiBasePath) {
        if (StrUtil.isBlank(sysApiBasePath.getBasePath())) {
            throw new ParamException("接口父路径不能为空");
        }
        this.updateById(sysApiBasePath);
        sysApiBasePath = this.getById(sysApiBasePath.getId());
        sysApiBasePathDao.delById(sysApiBasePath.getId());
        sysApiBasePathDao.add(sysApiBasePath);
    }

    @Override
    public List<SysApiBasePath> queryAll() {
        List<SysApiBasePath> sysApiBasePaths = sysApiBasePathDao.queryAll();
        if (CollectionUtils.isEmpty(sysApiBasePaths)) {
            sysApiBasePaths = this.list();
            if (!CollectionUtils.isEmpty(sysApiBasePaths)) {
                sysApiBasePaths.forEach(sysApiBasePathDao::add);
            }
        }
        return sysApiBasePaths;
    }

    @Override
    public SysApiBasePath queryById(String id) {
        SysApiBasePath sysApiBasePath = sysApiBasePathDao.queryById(id);
        if (ObjectUtils.isEmpty(sysApiBasePath)) {
            sysApiBasePath = this.getById(id);
            if (!ObjectUtils.isEmpty(sysApiBasePath)) {
                sysApiBasePathDao.add(sysApiBasePath);
            }
        }
        return sysApiBasePath;
    }

    @Override
    public List<SysApiBasePathBO> list(ListDTO listDTO) {
        List<SysApiBasePathBO> list = this.queryAll().stream().map(sysApiBasePath -> {
            SysApiBasePathBO sysApiBasePathBO = new SysApiBasePathBO();
            BeanUtils.copyProperties(sysApiBasePath, sysApiBasePathBO);
            return sysApiBasePathBO;
        }).collect(Collectors.toList());
        if (listDTO.getIsNeedApiList()) {
            for (SysApiBasePathBO sysApiBasePathBO : list) {
                if (StrUtil.isNotBlank(listDTO.getRoleId())) {
                    sysApiBasePathBO.setSysApiBOList(sysApiService.queryByBasePathIdAndRoleId(sysApiBasePathBO.getId(), listDTO.getRoleId()));
                } else {
                    sysApiBasePathBO.setSysApiBOList(sysApiService.queryByBasePathId(sysApiBasePathBO.getId()));
                }
            }
        }
        return list;
    }

    @Override
    public void reloadCache() {
        sysApiBasePathDao.delAll();
        this.queryAll();
    }

    @Override
    public void reloadCache(String id) {
        sysApiBasePathDao.delById(id);
        this.queryById(id);
    }
}
