package net.rootkim.baseservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.rootkim.baseservice.domain.bo.BlogBO;
import net.rootkim.baseservice.domain.dto.blog.AddDTO;
import net.rootkim.baseservice.domain.dto.blog.PageDTO;
import net.rootkim.baseservice.domain.dto.blog.UpdateDTO;
import net.rootkim.baseservice.domain.po.Blog;
import net.rootkim.baseservice.domain.po.BlogType;
import net.rootkim.baseservice.domain.po.BlogTypeRelation;
import net.rootkim.baseservice.domain.po.SysUser;
import net.rootkim.baseservice.mapper.BlogMapper;
import net.rootkim.baseservice.service.BlogService;
import net.rootkim.baseservice.service.BlogTypeRelationService;
import net.rootkim.baseservice.service.BlogTypeService;
import net.rootkim.baseservice.service.SysUserService;
import net.rootkim.core.domain.vo.BasePageVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 博客表 服务实现类
 * </p>
 *
 * @author RootKim[rootkim.net]
 * @since 2024-03-27
 */
@Service
@Transactional
public class BlogServiceImpl extends ServiceImpl<BlogMapper, Blog> implements BlogService {

    @Autowired
    private BlogTypeRelationService blogTypeRelationService;
    @Autowired
    private BlogTypeService blogTypeService;
    @Autowired
    private SysUserService sysUserService;

    @Override
    public void add(AddDTO addDTO) {
        Blog blog = new Blog();
        BeanUtils.copyProperties(addDTO, blog);
        this.save(blog);
        blogTypeRelationService.saveBatch(addDTO.getBlogTypeIdList().stream().map(blogTypeId -> {
            return BlogTypeRelation.builder().blogId(blog.getId()).blogTypeId(blogTypeId).build();
        }).collect(Collectors.toList()));
    }

    @Override
    public void batchDelete(List<String> idList) {
        blogTypeRelationService.remove(new LambdaQueryWrapper<BlogTypeRelation>().in(BlogTypeRelation::getBlogId, idList));
        this.removeBatchByIds(idList);
    }

    @Override
    public void update(UpdateDTO updateDTO) {
        Blog blog = new Blog();
        BeanUtils.copyProperties(updateDTO, blog);
        this.updateById(blog);
        blogTypeRelationService.remove(new LambdaQueryWrapper<BlogTypeRelation>().eq(BlogTypeRelation::getBlogId, updateDTO.getId()));
        blogTypeRelationService.saveBatch(updateDTO.getBlogTypeIdList().stream().map(blogTypeId -> {
            return BlogTypeRelation.builder().blogId(blog.getId()).blogTypeId(blogTypeId).build();
        }).collect(Collectors.toList()));
    }

    @Override
    public BasePageVO<BlogBO> pageBlogBO(PageDTO pageDTO) {
        BasePageVO<BlogBO> page = new BasePageVO<>(pageDTO.getCurrent(), pageDTO.getSize());
        BasePageVO<Blog> pageBlog = new BasePageVO<>(pageDTO.getCurrent(), pageDTO.getSize());
        List<BlogTypeRelation> blogTypeRelationList = null;
        if (StringUtils.hasText(pageDTO.getBlogTypeId())) {
            blogTypeRelationList = blogTypeRelationService.list(new LambdaQueryWrapper<BlogTypeRelation>()
                    .eq(BlogTypeRelation::getBlogTypeId, pageDTO.getBlogTypeId())
            );
            if (blogTypeRelationList == null || blogTypeRelationList.isEmpty()) {
                return page;
            }
        }
        QueryWrapper<Blog> BlogQueryWrapper = new QueryWrapper<Blog>()
                .like(StringUtils.hasText(pageDTO.getTitle()), "title", pageDTO.getTitle())
                .like(StringUtils.hasText(pageDTO.getContent()), "content", pageDTO.getContent())
                .eq(pageDTO.getIsShow() != null, "is_show", pageDTO.getIsShow());
        if (StringUtils.hasText(pageDTO.getBlogTypeId())) {
            BlogQueryWrapper.in("id"
                    , blogTypeRelationList.stream().map(BlogTypeRelation::getBlogId).distinct().collect(Collectors.toList()));
        }
        if (pageDTO.getSortMode() == 0) {
            BlogQueryWrapper.orderByDesc("update_time");
        } else {
            BlogQueryWrapper.orderByDesc("sort_num");
        }
        this.page(pageBlog, BlogQueryWrapper);
        BeanUtils.copyProperties(pageBlog, page);
        List<BlogBO> blogBOList = new ArrayList<>();
        if (pageBlog.getRecords() != null) {
            for (Blog record : pageBlog.getRecords()) {
                BlogBO blogBO = new BlogBO();
                BeanUtils.copyProperties(record, blogBO);
                SysUser sysUser = sysUserService.queryById(record.getCreater());
                if (sysUser != null) {
                    blogBO.setCreaterName(sysUser.getUsername());
                }
                sysUser = sysUserService.queryById(record.getUpdater());
                if (sysUser != null) {
                    blogBO.setUpdaterName(sysUser.getUsername());
                }
                blogTypeRelationList = blogTypeRelationService.list(new LambdaQueryWrapper<BlogTypeRelation>()
                        .eq(BlogTypeRelation::getBlogId, record.getId()));
                List<BlogType> blogTypeList = new ArrayList<>();
                for (BlogTypeRelation blogTypeRelation : blogTypeRelationList) {
                    BlogType blogType = blogTypeService.getById(blogTypeRelation.getBlogTypeId());
                    blogTypeList.add(blogType);
                }
                blogBO.setBlogTypeList(blogTypeList);
                blogBOList.add(blogBO);
            }
        }
        page.setRecords(blogBOList);
        return page;
    }

    @Override
    public BlogBO info(String id) {
        Blog blog = this.getById(id);
        BlogBO BlogBO = new BlogBO();
        BeanUtils.copyProperties(blog, BlogBO);
        List<BlogTypeRelation> blogTypeRelationList = blogTypeRelationService.list(new LambdaQueryWrapper<BlogTypeRelation>()
                .eq(BlogTypeRelation::getBlogId, id));
        BlogBO.setBlogTypeIdList(blogTypeRelationList.stream().map(BlogTypeRelation::getBlogTypeId).collect(Collectors.toList()));
        return BlogBO;
    }
}
