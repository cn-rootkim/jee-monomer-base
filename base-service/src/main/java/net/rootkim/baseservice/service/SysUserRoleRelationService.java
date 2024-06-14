package net.rootkim.baseservice.service;

import com.baomidou.mybatisplus.extension.service.IService;
import net.rootkim.baseservice.domain.po.SysUserRoleRelation;
import net.rootkim.core.service.BaseCacheService;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 * 用户_角色_关联表 服务类
 * </p>
 *
 * @author RootKim[rootkim.net]
 * @since 2024-04-29
 */
public interface SysUserRoleRelationService extends IService<SysUserRoleRelation>, BaseCacheService {

    void add(SysUserRoleRelation sysUserRoleRelation);

    void delById(String id);

    void delByUserId(String userId);

    void delByRoleId(String roleId);

    List<SysUserRoleRelation> queryAll();

    SysUserRoleRelation queryById(String id);

    List<SysUserRoleRelation>  queryByUserId(String userId);

    List<SysUserRoleRelation> queryByRoleId(String roleId);
}
