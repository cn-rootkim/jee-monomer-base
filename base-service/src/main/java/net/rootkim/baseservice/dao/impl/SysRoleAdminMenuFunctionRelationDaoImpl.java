package net.rootkim.baseservice.dao.impl;

import com.alibaba.fastjson.JSONObject;
import net.rootkim.baseservice.dao.SysRoleAdminMenuFunctionRelationDao;
import net.rootkim.baseservice.domain.po.SysRoleAdminMenuFunctionRelation;
import net.rootkim.core.constant.RoleRedisKeyConstant;
import net.rootkim.core.dao.BaseDao;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.Set;

/**
 * @author RootKim[rootkim.net]
 * @since 2024/4/29
 */
@Repository
public class SysRoleAdminMenuFunctionRelationDaoImpl extends BaseDao implements SysRoleAdminMenuFunctionRelationDao {

    @Override
    public void add(SysRoleAdminMenuFunctionRelation sysRoleAdminMenuFunctionRelation) {
        if (!ObjectUtils.isEmpty(sysRoleAdminMenuFunctionRelation)) {
            StringBuilder key = new StringBuilder(RoleRedisKeyConstant.SYS_ROLE_ADMIN_MENU_FUNCTION_RELATION);
            key.append(sysRoleAdminMenuFunctionRelation.getId());
            key.append("_");
            key.append(sysRoleAdminMenuFunctionRelation.getSysRoleId());
            key.append("_");
            key.append(sysRoleAdminMenuFunctionRelation.getAdminMenuFunctionId());
            stringRedisTemplate.opsForValue().set(key.toString(), JSONObject.toJSONString(sysRoleAdminMenuFunctionRelation));
        }
    }

    @Override
    public void delById(String id) {
        StringBuilder key = new StringBuilder(RoleRedisKeyConstant.SYS_ROLE_ADMIN_MENU_FUNCTION_RELATION);
        key.append(id);
        key.append("_*_*");
        Set<String> keys = stringRedisTemplate.keys(key.toString());
        stringRedisTemplate.delete(keys);
    }

    @Override
    public void delByRoleId(String roleId) {
        StringBuilder key = new StringBuilder(RoleRedisKeyConstant.SYS_ROLE_ADMIN_MENU_FUNCTION_RELATION);
        key.append("*_");
        key.append(roleId);
        key.append("_*");
        Set<String> keys = stringRedisTemplate.keys(key.toString());
        stringRedisTemplate.delete(keys);
    }

    @Override
    public void delByAdminMenuFunctionId(String adminMenuFunctionId) {
        StringBuilder key = new StringBuilder(RoleRedisKeyConstant.SYS_ROLE_ADMIN_MENU_FUNCTION_RELATION);
        key.append("*_*_");
        key.append(adminMenuFunctionId);
        Set<String> keys = stringRedisTemplate.keys(key.toString());
        stringRedisTemplate.delete(keys);
    }

    @Override
    public void delByRoleIdAndAdminMenuFunctionId(String roleId, String adminMenuFunctionId) {
        StringBuilder key = new StringBuilder(RoleRedisKeyConstant.SYS_ROLE_ADMIN_MENU_FUNCTION_RELATION);
        key.append("*_");
        key.append(roleId);
        key.append(adminMenuFunctionId);
        Set<String> keys = stringRedisTemplate.keys(key.toString());
        stringRedisTemplate.delete(keys);
    }

    @Override
    public List<SysRoleAdminMenuFunctionRelation> queryAll() {
        StringBuilder key = new StringBuilder(RoleRedisKeyConstant.SYS_ROLE_ADMIN_MENU_FUNCTION_RELATION);
        key.append("*");
        Set<String> keys = stringRedisTemplate.keys(key.toString());
        return super.getList(keys, SysRoleAdminMenuFunctionRelation.class);
    }

    @Override
    public SysRoleAdminMenuFunctionRelation queryById(String id) {
        StringBuilder key = new StringBuilder(RoleRedisKeyConstant.SYS_ROLE_ADMIN_MENU_FUNCTION_RELATION);
        key.append(id);
        key.append("_*_*");
        Set<String> keys = stringRedisTemplate.keys(key.toString());
        if (CollectionUtils.isEmpty(keys)) {
            return null;
        }
        return super.getOne(keys.iterator().next(), SysRoleAdminMenuFunctionRelation.class);
    }

    @Override
    public List<SysRoleAdminMenuFunctionRelation> queryByRoleId(String roleId) {
        StringBuilder key = new StringBuilder(RoleRedisKeyConstant.SYS_ROLE_ADMIN_MENU_FUNCTION_RELATION);
        key.append("*_");
        key.append(roleId);
        key.append("_*");
        Set<String> keys = stringRedisTemplate.keys(key.toString());
        return super.getList(keys, SysRoleAdminMenuFunctionRelation.class);
    }

    @Override
    public List<SysRoleAdminMenuFunctionRelation> queryByAdminMenuFunctionId(String adminMenuFunctionId) {
        StringBuilder key = new StringBuilder(RoleRedisKeyConstant.SYS_ROLE_ADMIN_MENU_FUNCTION_RELATION);
        key.append("*_*_");
        key.append(adminMenuFunctionId);
        Set<String> keys = stringRedisTemplate.keys(key.toString());
        return super.getList(keys, SysRoleAdminMenuFunctionRelation.class);
    }
}
