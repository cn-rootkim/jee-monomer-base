package net.rootkim.baseservice.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import net.rootkim.baseservice.domain.po.Blog;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 博客表 Mapper 接口
 * </p>
 *
 * @author RootKim[rootkim.net]
 * @since 2024-03-27
 */
@Mapper
public interface BlogMapper extends BaseMapper<Blog> {

}
