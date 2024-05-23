package net.rootkim.baseservice.controller;

import com.fasterxml.jackson.annotation.JsonView;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.rootkim.baseservice.domain.bo.BlogBO;
import net.rootkim.baseservice.domain.dto.blog.AddDTO;
import net.rootkim.baseservice.domain.dto.blog.InfoDTO;
import net.rootkim.baseservice.domain.dto.blog.PageDTO;
import net.rootkim.baseservice.domain.dto.blog.UpdateDTO;
import net.rootkim.baseservice.domain.po.Blog;
import net.rootkim.baseservice.service.BlogService;
import net.rootkim.core.domain.dto.BaseBatchDeleteDTO;
import net.rootkim.core.domain.vo.BasePageVO;
import net.rootkim.core.domain.vo.ResultVO;
import net.rootkim.core.exception.BusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * <p>
 * 博客表 前端控制器
 * </p>
 *
 * @author RootKim[rootkim.net]
 * @since 2024-03-27
 */
@RestController
@RequestMapping("blog")
@Api(tags = "博客")
public class BlogController {
    @Autowired
    private BlogService blogService;

    @PostMapping("add")
    @ApiOperation("新增博客")
    public ResultVO add(@Valid @RequestBody AddDTO addDTO) {
        blogService.add(addDTO);
        return ResultVO.success();
    }

    @PostMapping("batchDelete")
    @ApiOperation("批量删除博客")
    public ResultVO batchDelete(@Valid @RequestBody BaseBatchDeleteDTO baseBatchDeleteDTO) {
        blogService.batchDelete(baseBatchDeleteDTO.getIdList());
        return ResultVO.success();
    }

    @PostMapping("update")
    @ApiOperation("修改博客")
    public ResultVO update(@Valid @RequestBody UpdateDTO updateDTO) {
        blogService.update(updateDTO);
        return ResultVO.success();
    }

    @PostMapping("page")
    @ApiOperation("分页查询博客")
    @JsonView(Blog.PageView.class)
    public ResultVO<BasePageVO<BlogBO>> page(@Valid @RequestBody PageDTO pageDTO) {
        return ResultVO.success(blogService.pageBlogBO(pageDTO));
    }

    @PostMapping("info")
    @ApiOperation("博客详情")
    @JsonView(Blog.InfoView.class)
    public ResultVO<BlogBO> info(@Valid @RequestBody InfoDTO infoDTO){
        return ResultVO.success(blogService.info(infoDTO.getId()));
    }
}
