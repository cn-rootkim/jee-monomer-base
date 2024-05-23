package net.rootkim.baseservice.service;

import net.rootkim.baseservice.domain.po.SysRoleAdminMenuRelation;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 * 角色_管理系统菜单关联表 服务类
 * </p>
 *
 * @author RootKim[rootkim.net]
 * @since 2024-04-29
 */
public interface SysRoleAdminMenuRelationService extends IService<SysRoleAdminMenuRelation> {

    void add(SysRoleAdminMenuRelation sysRoleAdminMenuRelation);

    void delById(String id);

    void delByRoleId(String roleId);

    void delByAdminMenuId(String adminMenuId);

    List<SysRoleAdminMenuRelation> queryAll();

    SysRoleAdminMenuRelation queryById(String id);

    List<SysRoleAdminMenuRelation> queryByRoleId(String roleId);

    List<SysRoleAdminMenuRelation> queryByAdminMenuId(String adminMenuId);
}
