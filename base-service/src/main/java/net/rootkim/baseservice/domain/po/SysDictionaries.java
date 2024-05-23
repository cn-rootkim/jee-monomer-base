package net.rootkim.baseservice.domain.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 
 * </p>
 *
 * @author RootKim[rootkim.net]
 * @since 2024-05-21
 */
@Getter
@Setter
@TableName("sys_dictionaries")
@ApiModel(value = "SysDictionaries对象", description = "")
public class SysDictionaries implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "DICTIONARIES_ID", type = IdType.ASSIGN_UUID)
    private String dictionariesId;

    @ApiModelProperty("名称")
    @TableField("NAME")
    private String name;

    @ApiModelProperty("英文")
    @TableField("NAME_EN")
    private String nameEn;

    @ApiModelProperty("编码")
    @TableField("BIANMA")
    private String bianma;

    @ApiModelProperty("排序")
    @TableField("ORDER_BY")
    private Integer orderBy;

    @ApiModelProperty("上级ID")
    @TableField("PARENT_ID")
    private String parentId;

    @ApiModelProperty("备注")
    @TableField("BZ")
    private String bz;

    @ApiModelProperty("排查表")
    @TableField("TBSNAME")
    private String tbsname;

    @TableField("TBFIELD")
    private String tbfield;

    @TableField("YNDEL")
    private String yndel;

    @TableField("NAME_ZN")
    private String nameZn;
}
