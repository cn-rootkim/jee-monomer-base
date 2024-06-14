package net.rootkim.baseservice.dao;

import net.rootkim.baseservice.domain.po.SysRoleAdminMenuRelation;

import java.util.List;

/**
 * @author RootKim[rootkim.net]
 * @since 2024/4/30
 */
public interface SysRoleAdminMenuRelationDao {

    void add(SysRoleAdminMenuRelation sysRoleAdminMenuRelation);

    void delById(String id);

    void delAll();

    void delByRoleId(String roleId);

    void delByAdminMenuId(String adminMenuId);

    List<SysRoleAdminMenuRelation> queryAll();

    SysRoleAdminMenuRelation queryById(String id);

    List<SysRoleAdminMenuRelation> queryByRoleId(String roleId);

    List<SysRoleAdminMenuRelation> queryByAdminMenuId(String adminMenuId);
}
