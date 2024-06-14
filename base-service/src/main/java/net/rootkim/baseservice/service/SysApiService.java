package net.rootkim.baseservice.service;

import net.rootkim.baseservice.domain.bo.SysApiBO;
import net.rootkim.baseservice.domain.po.SysApi;
import com.baomidou.mybatisplus.extension.service.IService;
import net.rootkim.core.service.BaseCacheService;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 * 接口表 服务类
 * </p>
 *
 * @author RootKim[rootkim.net]
 * @since 2024-04-29
 */
public interface SysApiService extends IService<SysApi>, BaseCacheService {

    void add(SysApi sysApi);

    void delById(String id);

    void batchDelete(List<String> idList);

    void delBySysApiBasePathId(String sysApiBasePathId);

    void updateDBAndRedis(SysApi sysApi);

    List<SysApi> queryAll();

    SysApi queryById(String id);

    List<SysApi> queryByUserId(String userId);

    List<SysApiBO> queryByBasePathId(String sysApiBasePathId);

    List<SysApiBO> queryByBasePathIdAndRoleId(String sysApiBasePathId, String roleId);
}
