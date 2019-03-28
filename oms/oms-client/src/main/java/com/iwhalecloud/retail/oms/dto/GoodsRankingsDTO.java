package com.iwhalecloud.retail.oms.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


/**
 * 商品加购历史
 * @author generator
 * @version 1.0
 * @since 1.0
 */
@Data
@ApiModel(value = "对应模型R_ORDER_CART, 对应实体OrderCart类")
public class GoodsRankingsDTO implements java.io.Serializable {

    private static final long serialVersionUID = 1L;


    //属性 begin
    /**
     * 加购id
     */
    @ApiModelProperty(value = "加购id")
    private java.lang.String id;

    /**
     * 产品编号
     */
    @ApiModelProperty(value = "业务类型")
    private java.lang.String objId;

    /**
     * 产品编号
     */
    @ApiModelProperty(value = "产品Id")
    private java.lang.String objType;

    /**
     * 产品编号
     */
    @ApiModelProperty(value = "产品编号")
    private java.lang.String productId;

    /**
     * 商品编号
     */
    @ApiModelProperty(value = "商品编号")
    private java.lang.String goodsId;

    /**
     * 订单编号
     */
    @ApiModelProperty(value = "订单编号")
    private java.lang.String orderId;

    /**
     * 数量
     */
    @ApiModelProperty(value = "数量")
    private java.lang.Long num;

    /**
     * 回话id
     */
    @ApiModelProperty(value = "回话id")
    private java.lang.String sessionId;

    /**
     * 商品名称
     */
    @ApiModelProperty(value = "商品名称")
    private java.lang.String goodsName;

    /**
     * 会员id
     */
    @ApiModelProperty(value = "会员id")
    private java.lang.String memberId;


}
