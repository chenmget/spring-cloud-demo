package com.iwhalecloud.retail.order.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Map;

/**
 * @Author My
 * @Date 2018/11/27
 **/
@Data
public class CartItemDTO implements Serializable {
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
    @ApiModelProperty(value = "合约信息")
    private String contract;
    @ApiModelProperty(value = "购买数量")
    private int num;
    @ApiModelProperty(value = "小计")
    private Double subtotal;
    @ApiModelProperty(value = "限购数量(对于赠品)")
    private Integer limitnum;
    private String defaultImage;
    private Integer point;
    @ApiModelProperty(value = "物品类型(0商品，1捆绑商品，2赠品)")
    private Integer itemtype;
    @ApiModelProperty(value = "捆绑促销的货号")
    private String sn;
    @ApiModelProperty(value = "配件字串")
    private String addon;
    private String specs;
    @ApiModelProperty(value = "是否货到付款")
    private Long catid;
    @ApiModelProperty(value = "扩展项,可通过 ICartItemFilter 进行过滤修改")
    private Map others;
    @ApiModelProperty(value = "购物车中选中结算的商品")
    private String isChecked;
    @ApiModelProperty(value = "商家编码")
    private String agentName;
    private int ctn;
    @ApiModelProperty(value = "重量")
    private Double weight;
}
