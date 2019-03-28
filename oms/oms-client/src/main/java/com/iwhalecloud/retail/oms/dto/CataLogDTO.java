package com.iwhalecloud.retail.oms.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @Auther: lin.wh
 * @Date: 2018/10/25 14:05
 * @Description:
 */
@Data
@ApiModel(value = "对应模型t_catalog, 对应实体CataLogDTO类")
public class CataLogDTO implements Serializable {

    @ApiModelProperty(value = "目录id")
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

