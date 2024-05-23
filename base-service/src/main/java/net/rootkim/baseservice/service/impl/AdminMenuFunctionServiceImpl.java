package net.rootkim.baseservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import net.rootkim.baseservice.dao.AdminMenuFunctionDao;
import net.rootkim.baseservice.domain.bo.AdminMenuFunctionBO;
import net.rootkim.baseservice.domain.dto.adminMenuFunction.ListDTO;
import net.rootkim.baseservice.domain.po.AdminMenuFunction;
import net.rootkim.baseservice.domain.po.SysRoleAdminMenuFunctionRelation;
import net.rootkim.baseservice.mapper.AdminMenuFunctionMapper;
import net.rootkim.baseservice.service.AdminMenuFunctionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.rootkim.baseservice.service.SysRoleAdminMenuFunctionRelationService;
import net.rootkim.core.exception.ParamException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 管理系统菜单功能表 服务实现类
 * </p>
 *
 * @author RootKim[rootkim.net]
 * @since 2024-04-29
 */
@Service
@Transactional
public class AdminMenuFunctionServiceImpl extends ServiceImpl<AdminMenuFunctionMapper, AdminMenuFunction> implements AdminMenuFunctionService {

    @Autowired
    private AdminMenuFunctionDao adminMenuFunctionDao;
    @Autowired
    private SysRoleAdminMenuFunctionRelationService sysRoleAdminMenuFunctionRelationService;

    @Override
    public void add(AdminMenuFunction adminMenuFunction) {
        if (!StringUtils.hasText(adminMenuFunction.getName())) {
            throw new ParamException("菜单功能名称不能为空");
        }
        if (!StringUtils.hasText(adminMenuFunction.getAdminMenuId())) {
            throw new ParamException("菜单ID不能为空");
        }
        this.save(adminMenuFunction);
        adminMenuFunction = this.getById(adminMenuFunction.getId());
        adminMenuFunctionDao.add(adminMenuFunction);
    }

    @Override
    public void delById(String id) {
        //删除角色_菜单功能关联数据
        sysRoleAdminMenuFunctionRelationService.delByAdminMenuFunctionId(id);
        //删除菜单功能数据
        this.removeById(id);
        adminMenuFunctionDao.delById(id);
    }

    @Override
    public void batchDelete(List<String> idList) {
        for (String id : idList) {
            this.delById(id);
        }
    }

    @Override
    public void updateDBAndRedis(AdminMenuFunction adminMenuFunction) {
        if (!StringUtils.hasText(adminMenuFunction.getName())) {
            throw new ParamException("菜单功能名称不能为空");
        }
        if (!StringUtils.hasText(adminMenuFunction.getAdminMenuId())) {
            throw new ParamException("菜单ID不能为空");
        }
        this.updateById(adminMenuFunction);
        adminMenuFunction = this.getById(adminMenuFunction.getId());
        adminMenuFunctionDao.delById(adminMenuFunction.getId());
        adminMenuFunctionDao.add(adminMenuFunction);
    }

    @Override
    public List<AdminMenuFunction> queryAll() {
        List<AdminMenuFunction> adminMenuFunctions = adminMenuFunctionDao.queryAll();
        if (CollectionUtils.isEmpty(adminMenuFunctions)) {
            adminMenuFunctions = this.list();
            if (!CollectionUtils.isEmpty(adminMenuFunctions)) {
                adminMenuFunctions.forEach(adminMenuFunctionDao::add);
            }
        }
        return adminMenuFunctions;
    }

    @Override
    public List<AdminMenuFunction> queryByAdminMenuId(String adminMenuId) {
        List<AdminMenuFunction> adminMenuFunctions = adminMenuFunctionDao.queryByAdminMenuId(adminMenuId);
        if (CollectionUtils.isEmpty(adminMenuFunctions)) {
            adminMenuFunctions = this.list(new LambdaQueryWrapper<AdminMenuFunction>()
                    .eq(AdminMenuFunction::getAdminMenuId, adminMenuId));
            if (!CollectionUtils.isEmpty(adminMenuFunctions)) {
                adminMenuFunctions.forEach(adminMenuFunctionDao::add);
            }
        }
        return adminMenuFunctions;
    }

    @Override
    public AdminMenuFunction queryById(String id) {
        AdminMenuFunction adminMenuFunction = adminMenuFunctionDao.queryById(id);
        if (ObjectUtils.isEmpty(adminMenuFunction)) {
            adminMenuFunction = this.getById(id);
            if (!ObjectUtils.isEmpty(adminMenuFunction)) {
                adminMenuFunctionDao.add(adminMenuFunction);
            }
        }
        return adminMenuFunction;
    }

    @Override
    public List<AdminMenuFunctionBO> list(ListDTO listDTO, String userId) {
        List<AdminMenuFunctionBO> list;
        if (StringUtils.hasText(listDTO.getAdminMenuId())) {
            list = this.queryByAdminMenuId(listDTO.getAdminMenuId()).stream().map(adminMenuFunction -> {
                AdminMenuFunctionBO adminMenuFunctionBO = new AdminMenuFunctionBO();
                BeanUtils.copyProperties(adminMenuFunction, adminMenuFunctionBO);
                return adminMenuFunctionBO;
            }).collect(Collectors.toList());
        } else {
            list = this.queryAll().stream().map(adminMenuFunction -> {
                AdminMenuFunctionBO adminMenuFunctionBO = new AdminMenuFunctionBO();
                BeanUtils.copyProperties(adminMenuFunction, adminMenuFunctionBO);
                return adminMenuFunctionBO;
            }).collect(Collectors.toList());
        }
        if (StringUtils.hasText(listDTO.getName())) {
            list = list.stream().filter(adminMenuFunctionBO -> adminMenuFunctionBO.getName().contains(listDTO.getName())).collect(Collectors.toList());
        }
        if(listDTO.getMode()==1){
            if (!StringUtils.hasText(listDTO.getRoleId())) {
                throw new ParamException("角色ID不能为空");
            }
            List<SysRoleAdminMenuFunctionRelation> sysRoleAdminMenuFunctionRelations = sysRoleAdminMenuFunctionRelationService.queryByRoleId(listDTO.getRoleId());
            for (AdminMenuFunctionBO adminMenuFunctionBO : list) {
                adminMenuFunctionBO.setIsAuth(sysRoleAdminMenuFunctionRelations.stream().anyMatch(sysRoleAdminMenuFunctionRelation -> sysRoleAdminMenuFunctionRelation.getAdminMenuFunctionId().equals(adminMenuFunctionBO.getId())));
            }
        }else if(listDTO.getMode()==2){
            List<SysRoleAdminMenuFunctionRelation> sysRoleAdminMenuFunctionRelations = sysRoleAdminMenuFunctionRelationService.queryByUserId(userId);
            for (AdminMenuFunctionBO adminMenuFunctionBO : list) {
                adminMenuFunctionBO.setIsAuth(sysRoleAdminMenuFunctionRelations.stream().anyMatch(sysRoleAdminMenuFunctionRelation -> sysRoleAdminMenuFunctionRelation.getAdminMenuFunctionId().equals(adminMenuFunctionBO.getId())));
            }
        }
        return list;
    }
}