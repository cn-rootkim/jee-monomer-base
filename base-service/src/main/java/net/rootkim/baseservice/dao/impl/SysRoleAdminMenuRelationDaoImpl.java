package net.rootkim.baseservice.dao.impl;

import com.alibaba.fastjson.JSONObject;
import net.rootkim.baseservice.dao.SysRoleAdminMenuRelationDao;
import net.rootkim.baseservice.domain.po.SysRoleAdminMenuRelation;
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
public class SysRoleAdminMenuRelationDaoImpl extends BaseDao implements SysRoleAdminMenuRelationDao {

    @Override
    public void add(SysRoleAdminMenuRelation sysRoleAdminMenuRelation) {
        if (!ObjectUtils.isEmpty(sysRoleAdminMenuRelation)) {
            StringBuilder key = new StringBuilder(RoleRedisKeyConstant.SYS_ROLE_ADMIN_MENU_RELATION);
            key.append(sysRoleAdminMenuRelation.getId());
            key.append("_");
            key.append(sysRoleAdminMenuRelation.getSysRoleId());
            key.append("_");
            key.append(sysRoleAdminMenuRelation.getAdminMenuId());
            stringRedisTemplate.opsForValue().set(key.toString(), JSONObject.toJSONString(sysRoleAdminMenuRelation));
        }
    }

    @Override
    public void delById(String id) {
        StringBuilder key = new StringBuilder(RoleRedisKeyConstant.SYS_ROLE_ADMIN_MENU_RELATION);
        key.append(id);
        key.append("_*_*");
        Set<String> keys = stringRedisTemplate.keys(key.toString());
        stringRedisTemplate.delete(keys);
    }

    @Override
    public void delAll() {
        StringBuilder key = new StringBuilder(RoleRedisKeyConstant.SYS_ROLE_ADMIN_MENU_RELATION);
        key.append("*");
        stringRedisTemplate.delete(key.toString());
    }

    @Override
    public void delByRoleId(String roleId) {
        StringBuilder key = new StringBuilder(RoleRedisKeyConstant.SYS_ROLE_ADMIN_MENU_RELATION);
        key.append("*_");
        key.append(roleId);
        key.append("_*");
        Set<String> keys = stringRedisTemplate.keys(key.toString());
        stringRedisTemplate.delete(keys);
    }

    @Override
    public void delByAdminMenuId(String adminMenuId) {
        StringBuilder key = new StringBuilder(RoleRedisKeyConstant.SYS_ROLE_ADMIN_MENU_RELATION);
        key.append("*_*_");
        key.append(adminMenuId);
        Set<String> keys = stringRedisTemplate.keys(key.toString());
        stringRedisTemplate.delete(keys);
    }

    @Override
    public List<SysRoleAdminMenuRelation> queryAll() {
        StringBuilder key = new StringBuilder(RoleRedisKeyConstant.SYS_ROLE_ADMIN_MENU_RELATION);
        key.append("*");
        Set<String> keys = stringRedisTemplate.keys(key.toString());
        return super.getList(keys, SysRoleAdminMenuRelation.class);
    }

    @Override
    public SysRoleAdminMenuRelation queryById(String id) {
        StringBuilder key = new StringBuilder(RoleRedisKeyConstant.SYS_ROLE_ADMIN_MENU_RELATION);
        key.append(id);
        key.append("_*_*");
        Set<String> keys = stringRedisTemplate.keys(key.toString());
        if (CollectionUtils.isEmpty(keys)) {
            return null;
        }
        return super.getOne(keys.iterator().next(), SysRoleAdminMenuRelation.class);
    }

    @Override
    public List<SysRoleAdminMenuRelation> queryByRoleId(String roleId) {
        StringBuilder key = new StringBuilder(RoleRedisKeyConstant.SYS_ROLE_ADMIN_MENU_RELATION);
        key.append("*_");
        key.append(roleId);
        key.append("_*");
        Set<String> keys = stringRedisTemplate.keys(key.toString());
        return super.getList(keys, SysRoleAdminMenuRelation.class);
    }

    @Override
    public List<SysRoleAdminMenuRelation> queryByAdminMenuId(String adminMenuId) {
        StringBuilder key = new StringBuilder(RoleRedisKeyConstant.SYS_ROLE_ADMIN_MENU_RELATION);
        key.append("*_*_");
        key.append(adminMenuId);
        Set<String> keys = stringRedisTemplate.keys(key.toString());
        return super.getList(keys, SysRoleAdminMenuRelation.class);
    }
}
