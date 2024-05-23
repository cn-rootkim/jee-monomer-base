package net.rootkim.baseservice.dao.impl;

import com.alibaba.fastjson.JSONObject;
import net.rootkim.baseservice.dao.SysRoleApiRelationDao;
import net.rootkim.core.constant.RoleRedisKeyConstant;
import net.rootkim.core.dao.BaseDao;
import net.rootkim.baseservice.domain.po.SysRoleApiRelation;
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
public class SysRoleApiRelationDaoImpl extends BaseDao implements SysRoleApiRelationDao {

    @Override
    public void add(SysRoleApiRelation sysRoleApiRelation) {
        if (!ObjectUtils.isEmpty(sysRoleApiRelation)) {
            StringBuilder key = new StringBuilder(RoleRedisKeyConstant.SYS_ROLE_API_RELATION);
            key.append(sysRoleApiRelation.getId());
            key.append("_");
            key.append(sysRoleApiRelation.getSysRoleId());
            key.append("_");
            key.append(sysRoleApiRelation.getSysApiId());
            stringRedisTemplate.opsForValue().set(key.toString(), JSONObject.toJSONString(sysRoleApiRelation));
        }
    }

    @Override
    public void delById(String id) {
        StringBuilder key = new StringBuilder(RoleRedisKeyConstant.SYS_ROLE_API_RELATION);
        key.append(id);
        key.append("_*_*");
        Set<String> keys = stringRedisTemplate.keys(key.toString());
        stringRedisTemplate.delete(keys);
    }

    @Override
    public void delByRoleId(String roleId) {
        StringBuilder key = new StringBuilder(RoleRedisKeyConstant.SYS_ROLE_API_RELATION);
        key.append("*_");
        key.append(roleId);
        key.append("_*");
        Set<String> keys = stringRedisTemplate.keys(key.toString());
        stringRedisTemplate.delete(keys);
    }

    @Override
    public void delByApiId(String apiId) {
        StringBuilder key = new StringBuilder(RoleRedisKeyConstant.SYS_ROLE_API_RELATION);
        key.append("*_*_");
        key.append(apiId);
        Set<String> keys = stringRedisTemplate.keys(key.toString());
        stringRedisTemplate.delete(keys);
    }

    @Override
    public List<SysRoleApiRelation> queryAll() {
        StringBuilder key = new StringBuilder(RoleRedisKeyConstant.SYS_ROLE_API_RELATION);
        key.append("*");
        Set<String> keys = stringRedisTemplate.keys(key.toString());
        return super.getList(keys, SysRoleApiRelation.class);
    }

    @Override
    public SysRoleApiRelation queryById(String id) {
        StringBuilder key = new StringBuilder(RoleRedisKeyConstant.SYS_ROLE_API_RELATION);
        key.append(id);
        key.append("_*_*");
        Set<String> keys = stringRedisTemplate.keys(key.toString());
        if (CollectionUtils.isEmpty(keys)) {
            return null;
        }
        return super.getOne(keys.iterator().next(), SysRoleApiRelation.class);
    }

    @Override
    public List<SysRoleApiRelation> queryByRoleId(String roleId) {
        StringBuilder key = new StringBuilder(RoleRedisKeyConstant.SYS_ROLE_API_RELATION);
        key.append("*_");
        key.append(roleId);
        key.append("_*");
        Set<String> keys = stringRedisTemplate.keys(key.toString());
        return super.getList(keys, SysRoleApiRelation.class);
    }

    @Override
    public List<SysRoleApiRelation> queryByApiId(String apiId) {
        StringBuilder key = new StringBuilder(RoleRedisKeyConstant.SYS_ROLE_API_RELATION);
        key.append("*_*_");
        key.append(apiId);
        Set<String> keys = stringRedisTemplate.keys(key.toString());
        return super.getList(keys, SysRoleApiRelation.class);
    }
}
