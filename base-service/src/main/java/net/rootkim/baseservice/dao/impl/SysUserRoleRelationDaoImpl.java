package net.rootkim.baseservice.dao.impl;

import com.alibaba.fastjson.JSONObject;
import net.rootkim.baseservice.dao.SysUserRoleRelationDao;
import net.rootkim.core.constant.RoleRedisKeyConstant;
import net.rootkim.core.dao.BaseDao;
import net.rootkim.baseservice.domain.po.SysUserRoleRelation;
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
public class SysUserRoleRelationDaoImpl extends BaseDao implements SysUserRoleRelationDao {

    @Override
    public void add(SysUserRoleRelation sysUserRoleRelation) {
        if(!ObjectUtils.isEmpty(sysUserRoleRelation)) {
            StringBuilder key = new StringBuilder(RoleRedisKeyConstant.SYS_USER_ROLE_RELATION);
            key.append(sysUserRoleRelation.getId());
            key.append("_");
            key.append(sysUserRoleRelation.getSysRoleId());
            key.append("_");
            key.append(sysUserRoleRelation.getSysUserId());
            stringRedisTemplate.opsForValue().set(key.toString(), JSONObject.toJSONString(sysUserRoleRelation));
        }
    }

    @Override
    public void delById(String id) {
        StringBuilder key = new StringBuilder(RoleRedisKeyConstant.SYS_USER_ROLE_RELATION);
        key.append(id);
        key.append("_*_*");
        Set<String> keys = stringRedisTemplate.keys(key.toString());
        stringRedisTemplate.delete(keys);
    }

    @Override
    public void delByUserId(String userId) {
        StringBuilder key = new StringBuilder(RoleRedisKeyConstant.SYS_USER_ROLE_RELATION);
        key.append("*_");
        key.append(userId);
        key.append("_*");
        Set<String> keys = stringRedisTemplate.keys(key.toString());
        stringRedisTemplate.delete(keys);
    }

    @Override
    public void delByRoleId(String roleId) {
        StringBuilder key = new StringBuilder(RoleRedisKeyConstant.SYS_USER_ROLE_RELATION);
        key.append("*_*_");
        key.append(roleId);
        Set<String> keys = stringRedisTemplate.keys(key.toString());
        stringRedisTemplate.delete(keys);
    }

    @Override
    public List<SysUserRoleRelation> queryAll() {
        StringBuilder key = new StringBuilder(RoleRedisKeyConstant.SYS_USER_ROLE_RELATION);
        key.append("*");
        Set<String> keys = stringRedisTemplate.keys(key.toString());
        return super.getList(keys, SysUserRoleRelation.class);
    }

    @Override
    public SysUserRoleRelation queryById(String id) {
        StringBuilder key = new StringBuilder(RoleRedisKeyConstant.SYS_USER_ROLE_RELATION);
        key.append(id);
        key.append("_*_*");
        Set<String> keys = stringRedisTemplate.keys(key.toString());
        if (CollectionUtils.isEmpty(keys)) {
            return null;
        }
        return super.getOne(keys.iterator().next(), SysUserRoleRelation.class);
    }

    @Override
    public List<SysUserRoleRelation> queryByUserId(String userId) {
        StringBuilder key = new StringBuilder(RoleRedisKeyConstant.SYS_USER_ROLE_RELATION);
        key.append("*_");
        key.append(userId);
        key.append("_*");
        Set<String> keys = stringRedisTemplate.keys(key.toString());
        return super.getList(keys, SysUserRoleRelation.class);
    }

    @Override
    public List<SysUserRoleRelation> queryByRoleId(String roleId) {
        StringBuilder key = new StringBuilder(RoleRedisKeyConstant.SYS_USER_ROLE_RELATION);
        key.append("*_*_");
        key.append(roleId);
        Set<String> keys = stringRedisTemplate.keys(key.toString());
        return super.getList(keys, SysUserRoleRelation.class);
    }
}
