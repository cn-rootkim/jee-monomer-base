package net.rootkim.baseservice.dao;

import net.rootkim.baseservice.domain.po.SysUser;
import net.rootkim.core.domain.bo.Platform;

/**
 * @author RootKim[rootkim.net]
 * @since 2024/4/30
 */
public interface SysUserDao {

    String addToken(String userId, Platform platform, Byte userType);

    void delToken(String userId, Platform platform, Byte userType);

    boolean checkToken(String userId, Platform platform, String userType, String token);

    void add(SysUser sysUser);

    void delById(String id);

    SysUser queryById(String id);
}
