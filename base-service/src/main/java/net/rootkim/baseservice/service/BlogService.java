package net.rootkim.baseservice.service;

import com.baomidou.mybatisplus.extension.service.IService;
import net.rootkim.baseservice.domain.bo.BlogBO;
import net.rootkim.baseservice.domain.dto.blog.AddDTO;
import net.rootkim.baseservice.domain.dto.blog.PageDTO;
import net.rootkim.baseservice.domain.dto.blog.UpdateDTO;
import net.rootkim.baseservice.domain.po.Blog;
import net.rootkim.core.domain.vo.BasePageVO;

import java.util.List;

/**
 * <p>
 * 博客表 服务类
 * </p>
 *
 * @author RootKim[rootkim.net]
 * @since 2024-03-27
 */
public interface BlogService extends IService<Blog> {

    void add(AddDTO addDTO);

    void batchDelete(List<String> idList);

    void update(UpdateDTO updateDTO);

    BasePageVO<BlogBO> pageBlogBO(PageDTO pageDTO);

    BlogBO info(String id);
}
