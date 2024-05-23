package net.rootkim.baseservice.mapper;

import net.rootkim.baseservice.domain.po.SysUser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 用户表 Mapper 接口
 * </p>
 *
 * @author RootKim[rootkim.net]
 * @since 2024-04-28
 */
@Mapper
public interface SysUserMapper extends BaseMapper<SysUser> {

}
