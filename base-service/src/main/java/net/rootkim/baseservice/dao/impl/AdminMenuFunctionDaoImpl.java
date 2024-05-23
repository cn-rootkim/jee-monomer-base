package net.rootkim.baseservice.dao.impl;

import com.alibaba.fastjson.JSONObject;
import net.rootkim.baseservice.dao.AdminMenuFunctionDao;
import net.rootkim.baseservice.domain.po.AdminMenuFunction;
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
public class AdminMenuFunctionDaoImpl extends BaseDao implements AdminMenuFunctionDao {

    @Override
    public void add(AdminMenuFunction adminMenuFunction) {
        if (!ObjectUtils.isEmpty(adminMenuFunction)) {
            StringBuilder key = new StringBuilder(RoleRedisKeyConstant.ADMIN_MENU_FUNCTION);
            key.append(adminMenuFunction.getId());
            key.append("_");
            key.append(adminMenuFunction.getAdminMenuId());
            stringRedisTemplate.opsForValue().set(key.toString(), JSONObject.toJSONString(adminMenuFunction));
        }
    }

    @Override
    public void delById(String id) {
        StringBuilder key = new StringBuilder(RoleRedisKeyConstant.ADMIN_MENU_FUNCTION);
        key.append(id);
        key.append("_*");
        Set<String> keys = stringRedisTemplate.keys(key.toString());
        stringRedisTemplate.delete(keys);
    }

    @Override
    public void delByAdminMenuId(String adminMenuId) {
        StringBuilder key = new StringBuilder(RoleRedisKeyConstant.ADMIN_MENU_FUNCTION);
        key.append("*_");
        key.append(adminMenuId);
        Set<String> keys = stringRedisTemplate.keys(key.toString());
        stringRedisTemplate.delete(keys);
    }

    @Override
    public List<AdminMenuFunction> queryAll() {
        StringBuilder key = new StringBuilder(RoleRedisKeyConstant.ADMIN_MENU_FUNCTION);
        key.append("*");
        Set<String> keys = stringRedisTemplate.keys(key.toString());
        return super.getList(keys, AdminMenuFunction.class);
    }

    @Override
    public AdminMenuFunction queryById(String id) {
        StringBuilder key = new StringBuilder(RoleRedisKeyConstant.ADMIN_MENU_FUNCTION);
        key.append(id);
        key.append("_*");
        Set<String> keys = stringRedisTemplate.keys(key.toString());
        if (CollectionUtils.isEmpty(keys)) {
            return null;
        }
        return super.getOne(keys.iterator().next(), AdminMenuFunction.class);
    }

    @Override
    public List<AdminMenuFunction> queryByAdminMenuId(String adminMenuId) {
        StringBuilder key = new StringBuilder(RoleRedisKeyConstant.ADMIN_MENU_FUNCTION);
        key.append("*_");
        key.append(adminMenuId);
        Set<String> keys = stringRedisTemplate.keys(key.toString());
        return super.getList(keys, AdminMenuFunction.class);
    }
}