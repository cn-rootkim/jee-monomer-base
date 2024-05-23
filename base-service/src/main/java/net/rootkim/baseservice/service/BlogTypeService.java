package net.rootkim.baseservice.service;

import com.baomidou.mybatisplus.extension.service.IService;
import net.rootkim.baseservice.domain.bo.BlogTypeBO;
import net.rootkim.baseservice.domain.po.BlogType;

import java.util.List;

/**
 * <p>
 * 博客类型表 服务类
 * </p>
 *
 * @author RootKim[rootkim.net]
 * @since 2024-03-27
 */
public interface BlogTypeService extends IService<BlogType> {

    void batchDelete(List<String> idList);

    List<BlogTypeBO> listTree();
}
