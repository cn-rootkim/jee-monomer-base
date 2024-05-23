package net.rootkim.baseservice.service;

import net.rootkim.baseservice.domain.po.SysRoleApiRelation;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 * 角色_接口关联表 服务类
 * </p>
 *
 * @author RootKim[rootkim.net]
 * @since 2024-04-29
 */
public interface SysRoleApiRelationService extends IService<SysRoleApiRelation> {

    void add(SysRoleApiRelation sysRoleApiRelation);

    void delById(String id);

    void delByRoleId(String roleId);

    void delByApiId(String apiId);

    List<SysRoleApiRelation> queryAll();

    SysRoleApiRelation queryById(String id);

    List<SysRoleApiRelation> queryByRoleId(String roleId);

    List<SysRoleApiRelation> queryByApiId(String apiId);
}
