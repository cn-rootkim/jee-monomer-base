package net.rootkim.baseservice.service;

import net.rootkim.core.domain.bo.CacheBO;
import net.rootkim.baseservice.domain.dto.cache.QueryKeyListDTO;
import net.rootkim.baseservice.domain.dto.cache.ReloadDTO;

import java.util.List;
import java.util.Set;

/**
 * 缓存服务
 *
 * @author RootKim[rootkim.net]
 * @since 2024/5/29
 */
public interface CacheService {

    List<CacheBO> list();

    Set<String> queryKeyList(QueryKeyListDTO queryKeyListDTO);

    String queryValue(String key);

    void reload(ReloadDTO reloadDTO);
}
