package net.rootkim.baseservice.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.rootkim.baseservice.domain.po.BlogTypeRelation;
import net.rootkim.baseservice.mapper.BlogTypeRelationMapper;
import net.rootkim.baseservice.service.BlogTypeRelationService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 博客_博客类型_关联表 服务实现类
 * </p>
 *
 * @author RootKim[rootkim.net]
 * @since 2024-03-27
 */
@Service
public class BlogTypeRelationServiceImpl extends ServiceImpl<BlogTypeRelationMapper, BlogTypeRelation> implements BlogTypeRelationService {

}
