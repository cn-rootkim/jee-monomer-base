package net.rootkim.baseservice.dao.impl;

import com.alibaba.fastjson.JSONObject;
import net.rootkim.baseservice.dao.AdminMenuDao;
import net.rootkim.baseservice.domain.po.AdminMenu;
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
public class AdminMenuDaoImpl extends BaseDao implements AdminMenuDao {

    @Override
    public void add(AdminMenu adminMenu) {
        if(!ObjectUtils.isEmpty(adminMenu)) {
            StringBuilder key = new StringBuilder(RoleRedisKeyConstant.ADMIN_MENU);
            key.append(adminMenu.getId());
            stringRedisTemplate.opsForValue().set(key.toString(), JSONObject.toJSONString(adminMenu));
        }
    }

    @Override
    public void delById(String id) {
        StringBuilder key = new StringBuilder(RoleRedisKeyConstant.ADMIN_MENU);
        key.append(id);
        stringRedisTemplate.delete(key.toString());
    }

    @Override
    public List<AdminMenu> queryAll() {
        StringBuilder key = new StringBuilder(RoleRedisKeyConstant.ADMIN_MENU);
        key.append("*");
        Set<String> keys = stringRedisTemplate.keys(key.toString());
        return super.getList(keys, AdminMenu.class);
    }

    @Override
    public AdminMenu queryById(String id) {
        StringBuilder key = new StringBuilder(RoleRedisKeyConstant.ADMIN_MENU);
        key.append(id);
        return super.getOne(key.toString(), AdminMenu.class);
    }
}