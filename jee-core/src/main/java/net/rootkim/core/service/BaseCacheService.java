package net.rootkim.core.service;

/**
 * 缓存父服务
 *
 * @author RootKim[rootkim.net]
 * @since 2024/5/29
 */
public interface BaseCacheService {

    /**
     * 重新加载所有缓存
     */
    void reloadCache();

    /**
     * 重新加载单个缓存
     *
     * @param id
     */
    void reloadCache(String id);
}
