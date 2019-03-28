package com.iwhalecloud.retail.oms.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @Auther: lin.wh
 * @Date: 2018/10/30 19:48
 * @Description:
 */

@Data
@TableName("t_catalog")
public class TCatalog implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "目录id")
    @TableId(type = IdType.ID_WORKER)
    private Long cataid; //目录id

    @ApiModelProperty(value = "目录名称")
    private String name; //目录名称

    @ApiModelProperty(value = "上级目录")
    private Long parentCataId; //上级目录

    @ApiModelProperty(value = "目录层级")
    private String catalogLevel; //目录层级

    @ApiModelProperty(value = "操作人")
    private String oprId; //操作人

    @ApiModelProperty(value = "更新时间")
    private Date updDate; //更新时间

    @ApiModelProperty(value = "是否可删除")
    private Integer canDel; //是否可删除	0不可删除 1可删除
}

