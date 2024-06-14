package net.rootkim.baseservice.dao;

import net.rootkim.baseservice.domain.po.AdminMenu;

import java.util.List;

/**
 * @author RootKim[rootkim.net]
 * @since 2024/4/30
 */
public interface AdminMenuDao {

    void add(AdminMenu adminMenu);

    void delById(String id);

    void delAll();

    List<AdminMenu> queryAll();

    AdminMenu queryById(String id);
}
