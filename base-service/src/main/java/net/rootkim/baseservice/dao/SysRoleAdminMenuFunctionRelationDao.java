package net.rootkim.baseservice.dao;

import net.rootkim.baseservice.domain.po.SysRoleAdminMenuFunctionRelation;

import java.util.List;

/**
 * @author RootKim[rootkim.net]
 * @since 2024/4/30
 */
public interface SysRoleAdminMenuFunctionRelationDao {

    void add(SysRoleAdminMenuFunctionRelation sysRoleAdminMenuFunctionRelation);

    void delById(String id);

    void delByRoleId(String roleId);

    void delByAdminMenuFunctionId(String adminMenuFunctionId);

    void delByRoleIdAndAdminMenuFunctionId(String roleId, String adminMenuFunctionId);

    List<SysRoleAdminMenuFunctionRelation> queryAll();

    SysRoleAdminMenuFunctionRelation queryById(String id);

    List<SysRoleAdminMenuFunctionRelation> queryByRoleId(String roleId);

    List<SysRoleAdminMenuFunctionRelation> queryByAdminMenuFunctionId(String adminMenuFunctionId);
}
