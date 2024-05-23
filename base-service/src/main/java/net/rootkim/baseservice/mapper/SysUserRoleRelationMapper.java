package net.rootkim.baseservice.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import net.rootkim.baseservice.domain.po.SysUserRoleRelation;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 用户_角色_关联表 Mapper 接口
 * </p>
 *
 * @author RootKim[rootkim.net]
 * @since 2024-04-29
 */
@Mapper
public interface SysUserRoleRelationMapper extends BaseMapper<SysUserRoleRelation> {

}
