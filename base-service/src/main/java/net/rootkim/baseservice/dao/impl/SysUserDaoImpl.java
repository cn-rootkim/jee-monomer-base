package net.rootkim.baseservice.dao.impl;

import com.alibaba.fastjson.JSONObject;
import net.rootkim.baseservice.dao.SysUserDao;
import net.rootkim.baseservice.domain.po.SysUser;
import net.rootkim.core.constant.RoleRedisKeyConstant;
import net.rootkim.core.constant.EncryptSecretConstant;
import net.rootkim.core.dao.BaseDao;
import net.rootkim.core.domain.bo.Platform;
import net.rootkim.core.utils.JWTUtil;
import org.springframework.stereotype.Repository;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.concurrent.TimeUnit;

/**
 * @author RootKim[rootkim.net]
 * @since 2024/4/28
 */
@Repository
public class SysUserDaoImpl extends BaseDao implements SysUserDao {

    @Override
    public String addToken(String userId, Platform platform, Byte userType) {
        //生成JWT
        String JWTToken = JWTUtil.create(EncryptSecretConstant.JWT_SECRET, userId, userType);
        //根据客户端平台和用户id生成redisKey
        StringBuilder redisKey = new StringBuilder(RoleRedisKeyConstant.SYS_USER_TOKEN);
        redisKey.append(userType);
        redisKey.append(":");
        redisKey.append(platform);
        redisKey.append(":");
        redisKey.append(userId);
        //存储token
        stringRedisTemplate.opsForValue().set(redisKey.toString(), JWTToken, RoleRedisKeyConstant.SYS_USER_TOKEN_EXPIRES_DAY, TimeUnit.DAYS);
        return JWTToken;
    }

    @Override
    public void delToken(String userId, Platform platform, Byte userType) {
        StringBuilder sysUserTokenKey;
        if (!ObjectUtils.isEmpty(platform)) {
            //删除指定平台的token
            sysUserTokenKey = new StringBuilder(RoleRedisKeyConstant.SYS_USER_TOKEN);
            sysUserTokenKey.append(userType);
            sysUserTokenKey.append(":");
            sysUserTokenKey.append(platform);
            sysUserTokenKey.append(":");
            sysUserTokenKey.append(userId);
            stringRedisTemplate.delete(sysUserTokenKey.toString());
        } else {
            //删除所有平台的token
            for (Platform platformEnum : Platform.values()) {
                sysUserTokenKey = new StringBuilder(RoleRedisKeyConstant.SYS_USER_TOKEN);
                sysUserTokenKey.append(userType);
                sysUserTokenKey.append(":");
                sysUserTokenKey.append(platformEnum);
                sysUserTokenKey.append(":");
                sysUserTokenKey.append(userId);
                stringRedisTemplate.delete(sysUserTokenKey.toString());
            }
        }
    }

    @Override
    public boolean checkToken(String userId, Platform platform, String userType, String token) {
        if (!StringUtils.hasText(userId) || !StringUtils.hasText(userType) || !StringUtils.hasText(token)) {
            return false;
        }
        //根据客户端平台和用户id生成redisKey
        StringBuilder redisKey = new StringBuilder(RoleRedisKeyConstant.SYS_USER_TOKEN);
        redisKey.append(userType);
        redisKey.append(":");
        redisKey.append(platform);
        redisKey.append(":");
        redisKey.append(userId);
        String redisToken = stringRedisTemplate.opsForValue().get(redisKey.toString());
        if (StringUtils.hasText(redisToken) && redisToken.equals(token)) {
            return true;
        }
        return false;
    }

    @Override
    public void add(SysUser sysUser) {
        if (!ObjectUtils.isEmpty(sysUser)) {
            StringBuilder key = new StringBuilder(RoleRedisKeyConstant.SYS_USER);
            key.append(sysUser.getId());
            stringRedisTemplate.opsForValue().set(key.toString(), JSONObject.toJSONString(sysUser));
        }
    }

    @Override
    public void delById(String id) {
        StringBuilder key = new StringBuilder(RoleRedisKeyConstant.SYS_USER);
        key.append(id);
        stringRedisTemplate.delete(key.toString());
    }

    @Override
    public SysUser queryById(String id) {
        StringBuilder key = new StringBuilder(RoleRedisKeyConstant.SYS_USER);
        key.append(id);
        return super.getOne(key.toString(), SysUser.class);
    }
}
