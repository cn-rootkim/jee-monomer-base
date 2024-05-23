package net.rootkim.baseservice.dao;

import net.rootkim.baseservice.domain.po.SysRoleApiRelation;

import java.util.List;

/**
 * @author RootKim[rootkim.net]
 * @since 2024/4/30
 */
public interface SysRoleApiRelationDao {

    void add(SysRoleApiRelation sysRoleApiRelation);

    void delById(String id);

    void delByRoleId(String roleId);

    void delByApiId(String apiId);

    List<SysRoleApiRelation> queryAll();

    SysRoleApiRelation queryById(String id);

    List<SysRoleApiRelation> queryByRoleId(String roleId);

    List<SysRoleApiRelation> queryByApiId(String apiId);
}
