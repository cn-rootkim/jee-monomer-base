package net.rootkim.baseservice.service;

import net.rootkim.baseservice.domain.bo.SysRoleBO;
import net.rootkim.baseservice.domain.dto.sysRole.AuthApiDTO;
import net.rootkim.baseservice.domain.dto.sysRole.AuthMenuDTO;
import net.rootkim.baseservice.domain.dto.sysRole.AuthMenuFunctionDTO;
import net.rootkim.baseservice.domain.dto.sysRole.PageDTO;
import net.rootkim.baseservice.domain.po.SysRole;
import com.baomidou.mybatisplus.extension.service.IService;
import net.rootkim.core.domain.vo.BasePageVO;
import net.rootkim.core.service.BaseCacheService;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 * 角色表 服务类
 * </p>
 *
 * @author RootKim[rootkim.net]
 * @since 2024-04-29
 */
public interface SysRoleService extends IService<SysRole>, BaseCacheService {

    void add(SysRole sysRole);

    void delById(String id);

    void batchDelete(List<String> idList);

    void updateDBAndRedis(SysRole sysRole);

    List<SysRole> queryAll();

    SysRole queryById(String id);

    SysRole queryByKey(String key);

    BasePageVO<SysRoleBO> page(PageDTO pageDTO);

    void authMenu(AuthMenuDTO authMenuDTO);

    void authMenuFunction(AuthMenuFunctionDTO authMenuFunctionDTO);

    void authApi(AuthApiDTO authApiDTO);
}
