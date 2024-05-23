package net.rootkim.baseservice.dao;

import net.rootkim.baseservice.domain.po.SysApi;

import java.util.List;

/**
 * @author RootKim[rootkim.net]
 * @since 2024/4/30
 */
public interface SysApiDao {

    void add(SysApi sysApi);

    void delById(String id);

    List<SysApi> queryAll();

    SysApi queryById(String id);
}
