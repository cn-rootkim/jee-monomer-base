package net.rootkim.baseservice.mapper;

import net.rootkim.baseservice.domain.po.AdminMenu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 管理系统菜单表 Mapper 接口
 * </p>
 *
 * @author RootKim[rootkim.net]
 * @since 2024-04-29
 */
@Mapper
public interface AdminMenuMapper extends BaseMapper<AdminMenu> {

}
