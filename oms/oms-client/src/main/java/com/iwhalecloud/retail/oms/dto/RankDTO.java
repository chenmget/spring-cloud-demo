package com.iwhalecloud.retail.oms.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Auther: lin.wh
 * @Date: 2018/11/8 16:33
 * @Description:
 */

@Data
@ApiModel(value = "对应查询结果类")
public class RankDTO implements Serializable {

    @ApiModelProperty(value = "排名")
    private int rank;

    @ApiModelProperty(value = "关键词")
    private String eventExtend;

    @ApiModelProperty(value = "搜索次数")
    private String eventCount;

    @ApiModelProperty(value = "商品名称")
    private String eventExtendExtra1;
}

