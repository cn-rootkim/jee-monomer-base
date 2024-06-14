package net.rootkim.baseservice.dao.impl;

import com.alibaba.fastjson.JSONObject;
import net.rootkim.baseservice.dao.SysApiDao;
import net.rootkim.core.constant.RoleRedisKeyConstant;
import net.rootkim.core.dao.BaseDao;
import net.rootkim.baseservice.domain.po.SysApi;
import org.springframework.stereotype.Repository;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.Set;

/**
 * @author RootKim[rootkim.net]
 * @since 2024/4/29
 */
@Repository
public class SysApiDaoImpl extends BaseDao implements SysApiDao {

    @Override
    public void add(SysApi sysApi) {
        if (!ObjectUtils.isEmpty(sysApi)) {
            StringBuilder key = new StringBuilder(RoleRedisKeyConstant.SYS_API);
            key.append(sysApi.getId());
            stringRedisTemplate.opsForValue().set(key.toString(), JSONObject.toJSONString(sysApi));
        }
    }

    @Override
    public void delById(String id) {
        StringBuilder key = new StringBuilder(RoleRedisKeyConstant.SYS_API);
        key.append(id);
        stringRedisTemplate.delete(key.toString());
    }

    @Override
    public void delAll() {
        StringBuilder key = new StringBuilder(RoleRedisKeyConstant.SYS_API);
        key.append("*");
        stringRedisTemplate.delete(key.toString());
    }

    @Override
    public List<SysApi> queryAll() {
        StringBuilder key = new StringBuilder(RoleRedisKeyConstant.SYS_API);
        key.append("*");
        Set<String> keys = stringRedisTemplate.keys(key.toString());
        return super.getList(keys, SysApi.class);
    }

    @Override
    public SysApi queryById(String id) {
        StringBuilder key = new StringBuilder(RoleRedisKeyConstant.SYS_API);
        key.append(id);
        return super.getOne(key.toString(), SysApi.class);
    }
}
