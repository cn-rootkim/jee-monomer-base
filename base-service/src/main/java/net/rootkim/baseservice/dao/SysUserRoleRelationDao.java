package net.rootkim.baseservice.dao;

import net.rootkim.baseservice.domain.po.SysUserRoleRelation;

import java.util.List;

/**
 * @author RootKim[rootkim.net]
 * @since 2024/4/30
 */
public interface SysUserRoleRelationDao {

    void add(SysUserRoleRelation sysUserRoleRelation);

    void delById(String id);

    void delAll();

    void delByUserId(String userId);

    void delByRoleId(String roleId);

    List<SysUserRoleRelation> queryAll();

    SysUserRoleRelation queryById(String id);

    List<SysUserRoleRelation> queryByUserId(String userId);

    List<SysUserRoleRelation> queryByRoleId(String roleId);
}
