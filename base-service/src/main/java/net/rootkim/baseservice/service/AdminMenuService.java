package net.rootkim.baseservice.service;

import net.rootkim.baseservice.domain.bo.AdminMenuBO;
import net.rootkim.baseservice.domain.dto.adminMenu.ListTreeDTO;
import net.rootkim.baseservice.domain.po.AdminMenu;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 管理系统菜单表 服务类
 * </p>
 *
 * @author RootKim[rootkim.net]
 * @since 2024-04-29
 */
public interface AdminMenuService extends IService<AdminMenu> {

    void add(AdminMenu adminMenu);

    void delById(String id);

    void batchDelete(List<String> idList);

    void updateDBAndRedis(AdminMenu adminMenu);

    List<AdminMenu> queryAll();

    AdminMenu queryById(String id);

    /**
     * 根据用户id查询已授权的菜单
     *
     * @param userId
     * @return
     */
    List<AdminMenu> queryByUserId(String userId);

    List<AdminMenu> queryByRoleId(String roleId);

    List<AdminMenuBO> listTree(ListTreeDTO listTreeDTO, String userId);
}
