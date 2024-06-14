package net.rootkim.baseservice.service;

import net.rootkim.baseservice.domain.bo.SysUserBO;
import net.rootkim.baseservice.domain.dto.sysUser.PageDTO;
import net.rootkim.baseservice.domain.po.SysUser;
import com.baomidou.mybatisplus.extension.service.IService;
import net.rootkim.baseservice.domain.dto.sysUser.InfoDTO;
import net.rootkim.core.domain.bo.OpenIdType;
import net.rootkim.core.domain.bo.Platform;
import net.rootkim.core.domain.vo.BasePageVO;
import net.rootkim.core.service.BaseCacheService;

import java.util.List;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author RootKim[rootkim.net]
 * @since 2024-04-28
 */
public interface SysUserService extends IService<SysUser>, BaseCacheService {

    void add(SysUser sysUser, List<String> roleIdList);

    void del(String userId);

    void updateDBAndRedis(SysUser sysUser, List<String> roleIdList);

    void updateLoginPassword(String userId, String password);

    void updateLoginPasswordByOldPassword(String userId, String oldPassword, String newPassword);

    void updateLoginPasswordBySmsCode(String userId, String password, String smsCode);

    SysUser queryById(String id);

    BasePageVO<SysUserBO> page(PageDTO pageDTO);

    SysUserBO info(InfoDTO infoDTO);

    String loginByUsernameAndPassword(Platform platform, String username, String password, Byte userType);

    String loginByOpenId(Platform platform, String openId, OpenIdType openIdType, Byte userType);

    String loginByPhone(Platform platform, String phone, String smsCode, Byte userType);

    void logout(Platform platform, String userId, Byte userType);
}
