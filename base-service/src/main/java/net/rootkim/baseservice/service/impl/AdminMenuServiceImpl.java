package net.rootkim.baseservice.service.impl;

import cn.hutool.core.util.StrUtil;
import net.rootkim.baseservice.dao.AdminMenuDao;
import net.rootkim.baseservice.domain.bo.AdminMenuBO;
import net.rootkim.baseservice.domain.dto.adminMenu.ListTreeDTO;
import net.rootkim.baseservice.domain.po.AdminMenu;
import net.rootkim.baseservice.domain.po.AdminMenuFunction;
import net.rootkim.baseservice.domain.po.SysRoleAdminMenuRelation;
import net.rootkim.baseservice.mapper.AdminMenuMapper;
import net.rootkim.baseservice.service.AdminMenuFunctionService;
import net.rootkim.baseservice.service.AdminMenuService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.rootkim.baseservice.service.SysRoleAdminMenuRelationService;
import net.rootkim.baseservice.service.SysUserRoleRelationService;
import net.rootkim.baseservice.domain.po.SysUserRoleRelation;
import net.rootkim.core.exception.ParamException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;


import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 管理系统菜单表 服务实现类
 * </p>
 *
 * @author RootKim[rootkim.net]
 * @since 2024-04-29
 */
@Service
@Transactional
public class AdminMenuServiceImpl extends ServiceImpl<AdminMenuMapper, AdminMenu> implements AdminMenuService {

    @Autowired
    private AdminMenuDao adminMenuDao;
    @Autowired
    private AdminMenuFunctionService adminMenuFunctionService;
    @Autowired
    private SysRoleAdminMenuRelationService sysRoleAdminMenuRelationService;
    @Autowired
    private SysUserRoleRelationService sysUserRoleRelationService;

    @Override
    public void add(AdminMenu adminMenu) {
        if (StrUtil.isBlank(adminMenu.getName())) {
            throw new ParamException("菜单名称不能为空");
        }
        this.save(adminMenu);
        adminMenu = this.getById(adminMenu.getId());
        adminMenuDao.add(adminMenu);
    }

    @Override
    public void delById(String id) {
        List<AdminMenu> adminMenuAll = this.queryAll();
        AdminMenu parent = this.queryById(id);
        List<AdminMenu> adminMenus = new ArrayList<>();//菜单结果集
        adminMenus.add(parent);
        //递归出所有子菜单
        recursionAdminMenu(parent, adminMenuAll, adminMenus);
        for (AdminMenu adminMenu : adminMenus) {
            //删除菜单功能、角色_菜单功能关联数据
            List<AdminMenuFunction> adminMenuFunctions = adminMenuFunctionService.queryByAdminMenuId(adminMenu.getId());
            for (AdminMenuFunction adminMenuFunction : adminMenuFunctions) {
                adminMenuFunctionService.delById(adminMenuFunction.getId());
            }
            //删除角色_菜单关联数据
            sysRoleAdminMenuRelationService.delByAdminMenuId(adminMenu.getId());
            //删除菜单数据
            this.removeById(adminMenu.getId());
            adminMenuDao.delById(adminMenu.getId());
        }
    }

    @Override
    public void batchDelete(List<String> idList) {
        for (String id : idList) {
            this.delById(id);
        }
    }

    @Override
    public void updateDBAndRedis(AdminMenu adminMenu) {
        if (StrUtil.isBlank(adminMenu.getName())) {
            throw new ParamException("菜单名称不能为空");
        }
        this.updateById(adminMenu);
        adminMenu = this.getById(adminMenu.getId());
        adminMenuDao.delById(adminMenu.getId());
        adminMenuDao.add(adminMenu);
    }

    @Override
    public List<AdminMenu> queryAll() {
        List<AdminMenu> adminMenus = adminMenuDao.queryAll();
        if (CollectionUtils.isEmpty(adminMenus)) {
            adminMenus = this.list();
            if (!CollectionUtils.isEmpty(adminMenus)) {
                adminMenus.forEach(adminMenuDao::add);
            }
        }
        return adminMenus;
    }

    @Override
    public AdminMenu queryById(String id) {
        AdminMenu adminMenu = adminMenuDao.queryById(id);
        if (ObjectUtils.isEmpty(adminMenu)) {
            adminMenu = this.getById(id);
            if (!ObjectUtils.isEmpty(adminMenu)) {
                adminMenuDao.add(adminMenu);
            }
        }
        return adminMenu;
    }

    @Override
    public List<AdminMenu> queryByUserId(String userId) {
        List<AdminMenu> list = new ArrayList<>();
        List<SysUserRoleRelation> sysUserRoleRelations = sysUserRoleRelationService.queryByUserId(userId);
        if (CollectionUtils.isEmpty(sysUserRoleRelations)) {
            return null;
        }
        sysUserRoleRelations.forEach(sysUserRoleRelation -> {
            list.addAll(this.queryByRoleId(sysUserRoleRelation.getSysRoleId()));
        });
        return list.stream().distinct().collect(Collectors.toList());
    }

    @Override
    public List<AdminMenu> queryByRoleId(String roleId) {
        List<AdminMenu> list = new ArrayList<>();
        List<SysRoleAdminMenuRelation> sysRoleAdminMenuRelations = sysRoleAdminMenuRelationService.queryByRoleId(roleId);
        for (SysRoleAdminMenuRelation sysRoleAdminMenuRelation : sysRoleAdminMenuRelations) {
            list.add(this.queryById(sysRoleAdminMenuRelation.getAdminMenuId()));
        }
        return list;
    }

    @Override
    public List<AdminMenuBO> listTree(ListTreeDTO listTreeDTO, String userId) {
        List<AdminMenu> adminMenus = this.queryAll();
        List<AdminMenuBO> adminMenuBOList = adminMenus.stream().map(adminMenu -> {
            AdminMenuBO adminMenuBO = new AdminMenuBO();
            BeanUtils.copyProperties(adminMenu, adminMenuBO);
            return adminMenuBO;
        }).collect(Collectors.toList());
        if (listTreeDTO.getMode() == 1) {//根据角色id标记已授权菜单
            List<AdminMenu> adminMenusByRole = this.queryByRoleId(listTreeDTO.getRoleId());
            for (AdminMenuBO adminMenuBO : adminMenuBOList) {
                adminMenuBO.setIsAuth(adminMenusByRole.stream().anyMatch(adminMenu -> adminMenu.getId().equals(adminMenuBO.getId())));
            }
        } else if (listTreeDTO.getMode() == 2) {//根据当前登录用户标记已授权菜单
            List<AdminMenu> adminMenusByUserId = this.queryByUserId(userId);
            for (AdminMenuBO adminMenuBO : adminMenuBOList) {
                adminMenuBO.setIsAuth(adminMenusByUserId.stream().anyMatch(adminMenu -> adminMenu.getId().equals(adminMenuBO.getId())));
            }
        }
        List<AdminMenuBO> adminMenuTreeBOList = new ArrayList<>();
        //递归出菜单Tree
        for (AdminMenuBO adminMenuBO : adminMenuBOList) {
            if (StrUtil.isBlank(adminMenuBO.getParentId())) {
                AdminMenuBO adminMenuTreeBO = new AdminMenuBO();
                BeanUtils.copyProperties(adminMenuBO, adminMenuTreeBO);
                recursionAdminMenuTreeBO(adminMenuTreeBO, adminMenuBOList);
                adminMenuTreeBOList.add(adminMenuTreeBO);
            }
        }
        return adminMenuTreeBOList;
    }

    private void recursionAdminMenuTreeBO(AdminMenuBO parent, List<AdminMenuBO> adminMenuBOList) {
        for (AdminMenuBO adminMenuBO : adminMenuBOList) {
            if (parent.getId().equals(adminMenuBO.getParentId())) {
                if (ObjectUtils.isEmpty(parent.getChildList())) {
                    parent.setChildList(new ArrayList<>());
                }
                AdminMenuBO adminMenuTreeBO = new AdminMenuBO();
                BeanUtils.copyProperties(adminMenuBO, adminMenuTreeBO);
                parent.getChildList().add(adminMenuTreeBO);
                recursionAdminMenuTreeBO(adminMenuTreeBO, adminMenuBOList);
            }
        }
    }

    /**
     * 递归菜单
     *
     * @param parent       父级菜单
     * @param adminMenuAll 所有菜单
     * @param adminMenus   菜单结果集
     */
    private void recursionAdminMenu(AdminMenu parent, List<AdminMenu> adminMenuAll, List<AdminMenu> adminMenus) {
        adminMenuAll.forEach(adminmenu -> {
            if (parent.getId().equals(adminmenu.getParentId())) {
                adminMenus.add(adminmenu);
                recursionAdminMenu(adminmenu, adminMenuAll, adminMenus);
            }
        });
    }
}
