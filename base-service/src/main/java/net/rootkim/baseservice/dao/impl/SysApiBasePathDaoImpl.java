package net.rootkim.baseservice.dao.impl;

import com.alibaba.fastjson.JSONObject;
import net.rootkim.baseservice.dao.SysApiBasePathDao;
import net.rootkim.core.constant.RoleRedisKeyConstant;
import net.rootkim.core.dao.BaseDao;
import net.rootkim.baseservice.domain.po.SysApiBasePath;
import org.springframework.stereotype.Repository;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.Set;

/**
 * @author RootKim[rootkim.net]
 * @since 2024/4/29
 */
@Repository
public class SysApiBasePathDaoImpl extends BaseDao implements SysApiBasePathDao {

    @Override
    public void add(SysApiBasePath sysApiBasePath) {
        if (!ObjectUtils.isEmpty(sysApiBasePath)) {
            StringBuilder key = new StringBuilder(RoleRedisKeyConstant.SYS_API_BASE_PATH);
            key.append(sysApiBasePath.getId());
            stringRedisTemplate.opsForValue().set(key.toString(), JSONObject.toJSONString(sysApiBasePath));
        }
    }

    @Override
    public void delById(String id) {
        StringBuilder key = new StringBuilder(RoleRedisKeyConstant.SYS_API_BASE_PATH);
        key.append(id);
        stringRedisTemplate.delete(key.toString());
    }

    @Override
    public List<SysApiBasePath> queryAll() {
        StringBuilder key = new StringBuilder(RoleRedisKeyConstant.SYS_API_BASE_PATH);
        key.append("*");
        Set<String> keys = stringRedisTemplate.keys(key.toString());
        return super.getList(keys, SysApiBasePath.class);
    }

    @Override
    public SysApiBasePath queryById(String id) {
        StringBuilder key = new StringBuilder(RoleRedisKeyConstant.SYS_API_BASE_PATH);
        key.append(id);
        return super.getOne(key.toString(), SysApiBasePath.class);
    }
}
