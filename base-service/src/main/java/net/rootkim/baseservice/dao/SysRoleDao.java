package net.rootkim.baseservice.dao;

import net.rootkim.baseservice.domain.po.SysRole;

import java.util.List;

/**
 * @author RootKim[rootkim.net]
 * @since 2024/4/30
 */
public interface SysRoleDao {

    void add(SysRole sysRole);

    void delById(String id);

    void delByKey(String roleKey);

    List<SysRole> queryAll();

    SysRole queryById(String id);

    SysRole queryByKey(String roleKey);
}
