package com.iwhalecloud.retail.oms.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 商品加购历史分页查询
 * @author generator
 * @version 1.0
 * @since 1.0
 */

@Data
@ApiModel(value = "对应模型R_GOODS_RANKINGS")
public class ListGoodsRankingsDTO implements java.io.Serializable {

    private static final long serialVersionUID = 1L;


    //属性 begin
    /**
     * 商品名称
     */
    @ApiModelProperty(value = "商品名称")
    private String name;

    //属性 begin
    /**
     * 商品名称
     */
    @ApiModelProperty(value = "商品数量")
    private Integer num;
}
