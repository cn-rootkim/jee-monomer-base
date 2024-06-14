package net.rootkim.baseservice.dao;

import net.rootkim.baseservice.domain.po.SysApiBasePath;

import java.util.List;

/**
 * @author RootKim[rootkim.net]
 * @since 2024/4/30
 */
public interface SysApiBasePathDao {

    void add(SysApiBasePath sysApiBasePath);

    void delById(String id);

    void delAll();

    List<SysApiBasePath> queryAll();

    SysApiBasePath queryById(String id);
}
