package net.rootkim.baseservice.service;

import net.rootkim.baseservice.domain.bo.AdminMenuFunctionBO;
import net.rootkim.baseservice.domain.dto.adminMenuFunction.ListDTO;
import net.rootkim.baseservice.domain.po.AdminMenuFunction;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 * 管理系统菜单功能表 服务类
 * </p>
 *
 * @author RootKim[rootkim.net]
 * @since 2024-04-29
 */
public interface AdminMenuFunctionService extends IService<AdminMenuFunction> {

    void add(AdminMenuFunction adminMenuFunction);

    void delById(String id);

    void batchDelete(List<String> idList);

    void updateDBAndRedis(AdminMenuFunction adminMenuFunction);

    List<AdminMenuFunction> queryAll();

    List<AdminMenuFunction> queryByAdminMenuId(String adminMenuId);

    AdminMenuFunction queryById(String id);

    List<AdminMenuFunctionBO> list(ListDTO listDTO, String userId);
}
