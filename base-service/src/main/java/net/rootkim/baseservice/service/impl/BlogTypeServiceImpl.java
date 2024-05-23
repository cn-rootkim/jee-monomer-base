package net.rootkim.baseservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.rootkim.baseservice.domain.bo.BlogTypeBO;
import net.rootkim.baseservice.domain.po.BlogType;
import net.rootkim.baseservice.domain.po.BlogTypeRelation;
import net.rootkim.baseservice.mapper.BlogTypeMapper;
import net.rootkim.baseservice.service.BlogTypeRelationService;
import net.rootkim.baseservice.service.BlogTypeService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 博客类型表 服务实现类
 * </p>
 *
 * @author RootKim[rootkim.net]
 * @since 2024-03-27
 */
@Service
@Transactional
public class BlogTypeServiceImpl extends ServiceImpl<BlogTypeMapper, BlogType> implements BlogTypeService {

    @Autowired
    private BlogTypeRelationService blogTypeRelationService;

    @Override
    public void batchDelete(List<String> idList) {
        idList.forEach(id -> {
            List<String> blogTypeIdList = new ArrayList<>();
            //递归出所有子类型（包含了当前博客类型）
            recursionBlogTypeId(id, blogTypeIdList);
            //删除博客类型关联数据
            blogTypeRelationService.remove(new LambdaQueryWrapper<>(BlogTypeRelation.class)
                    .in(BlogTypeRelation::getBlogTypeId, blogTypeIdList));
            //删除所有子类型和当前类型
            this.removeByIds(blogTypeIdList);
        });
    }

    @Override
    public List<BlogTypeBO> listTree() {
        List<BlogTypeBO> blogTypeBOList = new ArrayList<>();
        List<BlogType> blogTypeList = this.list(new LambdaQueryWrapper<BlogType>().isNull(BlogType::getParentId)
                .orderByDesc(BlogType::getSortNum));
        //添加顶级分类
        blogTypeList.forEach(blogType -> {
            BlogTypeBO blogTypeBO = new BlogTypeBO();
            BeanUtils.copyProperties(blogType, blogTypeBO);
            blogTypeBOList.add(blogTypeBO);
        });
        //递归出所有子分类
        blogTypeBOList.forEach(blogTypeBO -> {
            recursionListTree(blogTypeBO);
        });
        return blogTypeBOList;
    }

    private void recursionBlogTypeId(String id, List<String> blogTypeIdList) {
        blogTypeIdList.add(id);
        List<BlogType> childList = this.list(new LambdaQueryWrapper<BlogType>().eq(BlogType::getParentId, id));
        if(childList==null || childList.isEmpty()) return;
        childList.forEach(blogType -> {
            recursionBlogTypeId(blogType.getId(), blogTypeIdList);
        });
    }

    private void recursionListTree(BlogTypeBO parent) {
        List<BlogType> childList = this.list(new LambdaQueryWrapper<BlogType>().eq(BlogType::getParentId, parent.getId())
                .orderByDesc(BlogType::getSortNum));
        if(childList==null || childList.isEmpty()) return;
        childList.forEach(blogType -> {
            if(parent.getChildList()==null) parent.setChildList(new ArrayList<>());
            BlogTypeBO blogTypeBOChild = new BlogTypeBO();
            BeanUtils.copyProperties(blogType, blogTypeBOChild);
            parent.getChildList().add(blogTypeBOChild);
            recursionListTree(blogTypeBOChild);
        });
    }
}