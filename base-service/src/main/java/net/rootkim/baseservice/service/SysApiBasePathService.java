package net.rootkim.baseservice.service;

import net.rootkim.baseservice.domain.bo.SysApiBasePathBO;
import net.rootkim.baseservice.domain.dto.sysApiBasePath.ListDTO;
import net.rootkim.baseservice.domain.po.SysApiBasePath;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 * 接口父路径表 服务类
 * </p>
 *
 * @author RootKim[rootkim.net]
 * @since 2024-04-29
 */
public interface SysApiBasePathService extends IService<SysApiBasePath> {

    void add(SysApiBasePath sysApiBasePath);

    void delById(String id);

    void batchDelete(List<String> idList);

    void updateDBAndRedis(SysApiBasePath sysApiBasePath);

    List<SysApiBasePath> queryAll();

    SysApiBasePath queryById(String id);

    List<SysApiBasePathBO> list(ListDTO listDTO);
}
