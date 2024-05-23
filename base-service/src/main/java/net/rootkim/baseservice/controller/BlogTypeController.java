package net.rootkim.baseservice.controller;

import com.fasterxml.jackson.annotation.JsonView;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.rootkim.baseservice.domain.bo.BlogTypeBO;
import net.rootkim.baseservice.domain.dto.blogType.AddDTO;
import net.rootkim.baseservice.domain.dto.blogType.UpdateDTO;
import net.rootkim.baseservice.domain.po.BlogType;
import net.rootkim.baseservice.service.BlogTypeService;
import net.rootkim.core.domain.dto.BaseBatchDeleteDTO;
import net.rootkim.core.domain.vo.ResultVO;
import net.rootkim.core.exception.BusinessException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

/**
 * <p>
 * 博客类型表 前端控制器
 * </p>
 *
 * @author RootKim[rootkim.net]
 * @since 2024-03-27
 */
@RestController
@RequestMapping("blogType")
@Api(tags = "博客类型")
public class BlogTypeController {

    @Autowired
    private BlogTypeService blogTypeService;

    @PostMapping("add")
    @ApiOperation("新增博客类型")
    public ResultVO add(@Valid @RequestBody AddDTO addDTO) {
        BlogType blogType = new BlogType();
        BeanUtils.copyProperties(addDTO, blogType);
        blogTypeService.save(blogType);
        return ResultVO.success();
    }

    @PostMapping("batchDelete")
    @ApiOperation("批量删除博客类型")
    public ResultVO batchDelete(@Valid @RequestBody BaseBatchDeleteDTO baseBatchDeleteDTO) {
        blogTypeService.batchDelete(baseBatchDeleteDTO.getIdList());
        return ResultVO.success();
    }

    @PostMapping("update")
    @ApiOperation("修改博客类型")
    public ResultVO update(@Valid @RequestBody UpdateDTO updateDTO) {
        BlogType blogType = new BlogType();
        BeanUtils.copyProperties(updateDTO, blogType);
        blogTypeService.updateById(blogType);
        return ResultVO.success();
    }

    @PostMapping("listTree")
    @ApiOperation("博客类型列表（树状结构）")
    @JsonView(BlogType.ListTreeView.class)
    public ResultVO<List<BlogTypeBO>> listTree() {
        return ResultVO.success(blogTypeService.listTree());
    }
}
