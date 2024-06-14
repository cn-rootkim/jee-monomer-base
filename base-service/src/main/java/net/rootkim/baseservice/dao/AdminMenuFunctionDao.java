package net.rootkim.baseservice.dao;

import net.rootkim.baseservice.domain.po.AdminMenuFunction;

import java.util.List;

/**
 * @author RootKim[rootkim.net]
 * @since 2024/4/30
 */
public interface AdminMenuFunctionDao {

    void add(AdminMenuFunction adminMenuFunction);

    void delById(String id);

    void delAll();

    void delByAdminMenuId(String adminMenuId);

    List<AdminMenuFunction> queryAll();

    AdminMenuFunction queryById(String id);

    List<AdminMenuFunction> queryByAdminMenuId(String adminMenuId);
}
