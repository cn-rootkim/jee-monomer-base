package net.rootkim.baseservice.service;

import net.rootkim.baseservice.domain.po.SysRoleAdminMenuFunctionRelation;
import com.baomidou.mybatisplus.extension.service.IService;
import net.rootkim.core.service.BaseCacheService;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 * 角色_管理系统菜单功能关联表 服务类
 * </p>
 *
 * @author RootKim[rootkim.net]
 * @since 2024-04-29
 */
public interface SysRoleAdminMenuFunctionRelationService extends IService<SysRoleAdminMenuFunctionRelation>, BaseCacheService {

    void add(SysRoleAdminMenuFunctionRelation sysRoleAdminMenuFunctionRelation);

    void delById(String id);

    void delByRoleId(String roleId);

    void delByAdminMenuFunctionId(String adminMenuFunctionId);

    void delByRoleIdAndAdminMenuFunctionId(String roleId, String adminMenuFunctionId);

    List<SysRoleAdminMenuFunctionRelation> queryAll();

    SysRoleAdminMenuFunctionRelation queryById(String id);

    List<SysRoleAdminMenuFunctionRelation> queryByRoleId(String roleId);

    List<SysRoleAdminMenuFunctionRelation> queryByUserId(String userId);

    List<SysRoleAdminMenuFunctionRelation> queryByAdminMenuFunctionId(String adminMenuFunctionId);
}
