package net.rootkim.baseservice.dao.impl;

import com.alibaba.fastjson.JSONObject;
import net.rootkim.baseservice.dao.SysRoleDao;
import net.rootkim.baseservice.domain.po.SysRole;
import net.rootkim.core.constant.RoleRedisKeyConstant;
import net.rootkim.core.dao.BaseDao;
import org.springframework.stereotype.Repository;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.Set;

/**
 * @author RootKim[rootkim.net]
 * @since 2024/4/29
 */
@Repository
public class SysRoleDaoImpl extends BaseDao implements SysRoleDao {

    @Override
    public void add(SysRole sysRole) {
        if (!ObjectUtils.isEmpty(sysRole)) {
            StringBuilder key = new StringBuilder(RoleRedisKeyConstant.SYS_ROLE);
            key.append(sysRole.getId());
            key.append("_");
            key.append(sysRole.getRoleKey());
            stringRedisTemplate.opsForValue().set(key.toString(), JSONObject.toJSONString(sysRole));
        }
    }

    @Override
    public void delById(String id) {
        StringBuilder key = new StringBuilder(RoleRedisKeyConstant.SYS_ROLE);
        key.append(id);
        key.append("_*");
        Set<String> keys = stringRedisTemplate.keys(key.toString());
        stringRedisTemplate.delete(keys);
    }

    @Override
    public void delByKey(String roleKey) {
        StringBuilder key = new StringBuilder(RoleRedisKeyConstant.SYS_ROLE);
        key.append("*_");
        key.append(roleKey);
        Set<String> keys = stringRedisTemplate.keys(key.toString());
        stringRedisTemplate.delete(keys);
    }

    @Override
    public void delAll() {
        StringBuilder key = new StringBuilder(RoleRedisKeyConstant.SYS_ROLE);
        key.append("*");
        stringRedisTemplate.delete(key.toString());
    }

    @Override
    public List<SysRole> queryAll() {
        StringBuilder key = new StringBuilder(RoleRedisKeyConstant.SYS_ROLE);
        key.append("*");
        Set<String> keys = stringRedisTemplate.keys(key.toString());
        return super.getList(keys, SysRole.class);
    }

    @Override
    public SysRole queryById(String id) {
        StringBuilder key = new StringBuilder(RoleRedisKeyConstant.SYS_ROLE);
        key.append(id);
        key.append("_*");
        return super.getOne(key.toString(), SysRole.class);
    }

    @Override
    public SysRole queryByKey(String roleKey) {
        StringBuilder key = new StringBuilder(RoleRedisKeyConstant.SYS_ROLE);
        key.append("*_");
        key.append(roleKey);
        return super.getOne(key.toString(), SysRole.class);
    }
}
