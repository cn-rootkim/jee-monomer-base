package net.rootkim.core.dao;

import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @author RootKim[rootkim.net]
 * @since 2024/4/22
 */
public class BaseDao {

    @Autowired
    protected StringRedisTemplate stringRedisTemplate;

    protected <T> T getOne(String key, Class<T> clazz) {
        if (!StringUtils.hasText(key)) {
            return null;
        }
        String objectStr = stringRedisTemplate.opsForValue().get(key);
        return JSONObject.parseObject(objectStr, clazz);
    }

    protected <T> List<T> getList(Set<String> keys, Class<T> clazz) {
        if (CollectionUtils.isEmpty(keys)) {
            return null;
        }
        List<T> list = new ArrayList<>();
        keys.forEach(key -> {
            T one = getOne(key, clazz);
            if (!ObjectUtils.isEmpty(one)) {
                list.add(one);
            }
        });
        return list;
    }
}
