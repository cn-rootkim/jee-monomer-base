package net.rootkim.baseservice.service.impl;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONObject;
import lombok.RequiredArgsConstructor;
import net.rootkim.baseservice.service.*;
import net.rootkim.core.constant.SmsRedisKeyConstant;
import net.rootkim.core.domain.bo.CacheBO;
import net.rootkim.baseservice.domain.dto.cache.QueryKeyListDTO;
import net.rootkim.baseservice.domain.dto.cache.ReloadDTO;
import net.rootkim.core.constant.RoleRedisKeyConstant;
import net.rootkim.core.exception.ParamException;
import net.rootkim.core.service.BaseCacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * @author RootKim[rootkim.net]
 * @since 2024/5/29
 */
@Service
@Transactional
public class CacheServiceImpl implements CacheService {

    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private SysRoleService sysRoleService;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private SysApiBasePathService sysApiBasePathService;
    @Autowired
    private SysApiService sysApiService;
    @Autowired
    private AdminMenuService adminMenuService;
    @Autowired
    private AdminMenuFunctionService adminMenuFunctionService;
    @Autowired
    private SysUserRoleRelationService sysUserRoleRelationService;
    @Autowired
    private SysRoleApiRelationService sysRoleApiRelationService;
    @Autowired
    private SysRoleAdminMenuRelationService sysRoleAdminMenuRelationService;
    @Autowired
    private SysRoleAdminMenuFunctionRelationService sysRoleAdminMenuFunctionRelationService;

    @PostConstruct
    public void init() {
        RoleRedisKeyConstant.cacheBOList = new ArrayList<>();
        RoleRedisKeyConstant.cacheBOList.add(new CacheBO(RoleRedisKeyConstant.SYS_USER_TOKEN, "用户token"));
        RoleRedisKeyConstant.cacheBOList.add(new CacheBO(RoleRedisKeyConstant.SYS_USER, "用户数据", sysUserService));
        RoleRedisKeyConstant.cacheBOList.add(new CacheBO(RoleRedisKeyConstant.SYS_ROLE, "角色数据", sysRoleService));
        RoleRedisKeyConstant.cacheBOList.add(new CacheBO(RoleRedisKeyConstant.SYS_API_BASE_PATH, "接口父路径数据", sysApiBasePathService));
        RoleRedisKeyConstant.cacheBOList.add(new CacheBO(RoleRedisKeyConstant.SYS_API, "接口数据", sysApiService));
        RoleRedisKeyConstant.cacheBOList.add(new CacheBO(RoleRedisKeyConstant.ADMIN_MENU, "管理系统菜单数据", adminMenuService));
        RoleRedisKeyConstant.cacheBOList.add(new CacheBO(RoleRedisKeyConstant.ADMIN_MENU_FUNCTION, "管理系统菜单功能数据", adminMenuFunctionService));
        RoleRedisKeyConstant.cacheBOList.add(new CacheBO(RoleRedisKeyConstant.SYS_USER_ROLE_RELATION, "用户_角色关联数据", sysUserRoleRelationService));
        RoleRedisKeyConstant.cacheBOList.add(new CacheBO(RoleRedisKeyConstant.SYS_ROLE_API_RELATION, "角色_接口关联数据", sysRoleApiRelationService));
        RoleRedisKeyConstant.cacheBOList.add(new CacheBO(RoleRedisKeyConstant.SYS_ROLE_ADMIN_MENU_RELATION, "角色_管理系统菜单关联数据", sysRoleAdminMenuRelationService));
        RoleRedisKeyConstant.cacheBOList.add(new CacheBO(RoleRedisKeyConstant.SYS_ROLE_ADMIN_MENU_FUNCTION_RELATION, "角色_管理系统菜单功能关联数据", sysRoleAdminMenuFunctionRelationService));

        RoleRedisKeyConstant.cacheBOList.add(new CacheBO(SmsRedisKeyConstant.SMS_CODE_RECORD, "手机号短信验证码发送记录"));
        RoleRedisKeyConstant.cacheBOList.add(new CacheBO(SmsRedisKeyConstant.SMS_CODE_TOTAL_PHONE, "手机号短信验证码发送总数"));
        RoleRedisKeyConstant.cacheBOList.add(new CacheBO(SmsRedisKeyConstant.SMS_CODE_TOTAL_IP, "IP短信验证码发送总数"));
        RoleRedisKeyConstant.cacheBOList.add(new CacheBO(SmsRedisKeyConstant.SMS_CODE_LOGIN, "短信验证码[登录业务]"));
        RoleRedisKeyConstant.cacheBOList.add(new CacheBO(SmsRedisKeyConstant.SMS_CODE_REGISTER, "短信验证码[注册业务]"));
        RoleRedisKeyConstant.cacheBOList.add(new CacheBO(SmsRedisKeyConstant.SMS_CODE_UPDATE_LOGIN_PASSWORD, "短信验证码[修改登录密码业务]"));
        RoleRedisKeyConstant.cacheBOList.add(new CacheBO(SmsRedisKeyConstant.SMS_CODE_UPDATE_PAY_PASSWORD, "短信验证码[修改支付密码业务]"));
        for (CacheBO cacheBO : RoleRedisKeyConstant.cacheBOList) {
            cacheBO.setKeyPrefix(cacheBO.getKeyPrefix() + "*");
        }
    }

    @Override
    public List<CacheBO> list() {
        return RoleRedisKeyConstant.cacheBOList;
    }

    @Override
    public Set<String> queryKeyList(QueryKeyListDTO queryKeyListDTO) {
        return stringRedisTemplate.keys(queryKeyListDTO.getKeyPrefix());
    }

    @Override
    public String queryValue(String key) {
        return stringRedisTemplate.opsForValue().get(key);
    }

    @Override
    public void reload(ReloadDTO reloadDTO) {
        if (StrUtil.isBlank(reloadDTO.getKeyPrefix())) {
            throw new ParamException("缓存key前缀不可为空");
        }
        Optional<CacheBO> any = RoleRedisKeyConstant.cacheBOList.stream().filter(cacheBO -> cacheBO.getKeyPrefix().equals(reloadDTO.getKeyPrefix())).findAny();
        if (!any.isPresent()) {
            throw new ParamException("keyPrefix不存在");
        }
        CacheBO findCacheBO = any.get();
        if (!findCacheBO.getCanReload()) {
            throw new ParamException("该缓存不能重新加载");
        }
        BaseCacheService baseCacheService = findCacheBO.getBaseCacheService();
        if (StrUtil.isBlank(reloadDTO.getKey())) {
            baseCacheService.reloadCache();
        } else {
            String valueStr = this.queryValue(reloadDTO.getKey());
            if (StrUtil.isBlank(valueStr)) {
                throw new ParamException("缓存key不存在");
            }
            baseCacheService.reloadCache(JSONObject.parseObject(valueStr).getString("id"));
        }
    }
}
