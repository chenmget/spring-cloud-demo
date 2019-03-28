package com.iwhalecloud.retail.order2b.dto.model.cart;

import com.iwhalecloud.retail.order2b.dto.base.SelectModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Map;

/**
 * @Author My
 * @Date 2018/11/27
 **/
@Data
public class CartItemDTO extends SelectModel implements Serializable {
    @ApiModelProperty(value = "购物车ID")
    private String cartId;
    @ApiModelProperty(value = "产品ID")
    private String productId;
    @ApiModelProperty(value = "商品ID")
    private String goodsId;
    @ApiModelProperty(value = "商品名称")
    private String name;
    @ApiModelProperty(value = "市场单价")
    private Double mktprice;
    @ApiModelProperty(value = "单价")
    private Double price;
    @ApiModelProperty(value = "优惠后单价")
    private Double coupPrice;
    @ApiModelProperty(value = "购买数量")
    private int num;
    /**
     * 最小起批量
     */
    @ApiModelProperty(value = "最小起批量")
    private Long minNum;
    /**
     * 订购上限
     */
    @ApiModelProperty(value = "订购上限")
    private Long maxNum;
    @ApiModelProperty(value = "小计")
    private Double subtotal;
    private String specs;
    @ApiModelProperty(value = "购物车中选中结算的商品")
    private String isCheck;
    @ApiModelProperty(value = "商家编码")
    private String agentName;
    @ApiModelProperty(value = "供应商")
    private String supplierId;
    @ApiModelProperty(value = "产品图片")
    private String fileUrl;
    @ApiModelProperty(value = "产品名称")
    private String unitName;
}
