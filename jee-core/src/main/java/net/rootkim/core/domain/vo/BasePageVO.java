package net.rootkim.core.domain.vo;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fasterxml.jackson.annotation.JsonView;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

import java.util.List;

/**
 * 分页VO
 * @author RootKim[rootkim.net]
 * @since 2024/3/23
 */
@Getter
public class BasePageVO<T> extends Page<T> {

    public interface PageVOView extends ResultVO.ResultView {};

    public BasePageVO(long current, long size){
        super(current,size);
    }

    @Override
    @ApiModelProperty("数据列表")
    @JsonView({PageVOView.class})
    public List<T> getRecords() {
        return super.getRecords();
    }

    @Override
    @ApiModelProperty("总数据量")
    @JsonView({PageVOView.class})
    public long getTotal() {
        return super.getTotal();
    }

    @Override
    @ApiModelProperty("每页显示条数")
    @JsonView({PageVOView.class})
    public long getSize() {
        return super.getSize();
    }

    @Override
    @ApiModelProperty("当前页")
    @JsonView({PageVOView.class})
    public long getCurrent() {
        return super.getCurrent();
    }

    @Override
    @ApiModelProperty("总数据页")
    @JsonView({PageVOView.class})
    public long getPages() {
        return super.getPages();
    }

}
