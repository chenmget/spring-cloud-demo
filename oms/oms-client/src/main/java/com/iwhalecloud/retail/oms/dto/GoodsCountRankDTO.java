package com.iwhalecloud.retail.oms.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(value = "对应搜索GoodsCountRank模型, 对应实体GoodsCountRankDTO类")
public class GoodsCountRankDTO implements Serializable {

    @ApiModelProperty(value = "rankNo")
    private Integer rankNo;

    @ApiModelProperty(value = "goodsName")
    private String goodsName;

    @ApiModelProperty(value = "goodsEvenCount")
    private String goodsEventCount;
}
