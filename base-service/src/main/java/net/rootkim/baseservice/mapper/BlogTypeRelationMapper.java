package net.rootkim.baseservice.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import net.rootkim.baseservice.domain.po.BlogTypeRelation;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 博客_博客类型_关联表 Mapper 接口
 * </p>
 *
 * @author RootKim[rootkim.net]
 * @since 2024-03-27
 */
@Mapper
public interface BlogTypeRelationMapper extends BaseMapper<BlogTypeRelation> {

}
