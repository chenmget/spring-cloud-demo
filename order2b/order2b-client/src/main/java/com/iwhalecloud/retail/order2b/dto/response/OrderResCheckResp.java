package com.iwhalecloud.retail.order2b.dto.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author zhou.zc
 * @date 2019年03月07日
 * @Description:前置补贴活动补录的订单串码校验
 */
@Data
public class OrderResCheckResp implements Serializable {

    private static final long serialVersionUID = -5308884941104045149L;

//    /**
//     * 商家类型，同商家表中商家类型
//     */
//    @ApiModelProperty(value = "商家类型，同商家表中商家类型")
//    private String merchantType;
//
//    /**
//     * 商家编码
//     */
//    @ApiModelProperty(value = "商家编码")
//    private String merchantCode;
//
//    /**
//     * 商家名称
//     */
//    @ApiModelProperty(value = "商家名称")
//    private String merchantName;
//
//    /**
//     * 订单的创建时间
//     */
//    @ApiModelProperty(value = "订单的创建时间")
//    private java.util.Date orderTime;

    /**
     * 订单ID
     */
    @ApiModelProperty(value = "订单ID")
    private String orderId;

//    /**
//     * 订单项ID
//     */
//    @ApiModelProperty(value = "订单项ID")
//    private String orderItemId;
//
//    /**
//     * 商品ID
//     */
//    @ApiModelProperty(value = "商品ID")
//    private String goodsId;

//    /**
//     * 产品ID
//     */
//    @ApiModelProperty(value = "产品ID")
//    private String productId;
//
//    /**
//     * 价格
//     */
//    @ApiModelProperty(value = "价格")
//    private Long price;

//    /**
//     * 优惠额或补贴额
//     */
//    @ApiModelProperty(value = "优惠额或补贴额")
//    private Long discount;

    /**
     * 订单中包含的串码
     */
    @ApiModelProperty(value = "订单中包含的串码")
    private String resNbr;

//    /**
//     * 串码的发货时间
//     */
//    @ApiModelProperty(value = "串码的发货时间")
//    private java.util.Date shipTime;

//    /**
//     * 供应商编码
//     */
//    @ApiModelProperty(value = "供应商编码")
//    private String supplierCode;

//    /**
//     * 供应商名称
//     */
//    @ApiModelProperty(value = "供应商名称")
//    private String supplierName;

//    @ApiModelProperty(value = "供应商id")
//    private String supplierId;

    @ApiModelProperty(value = "提交补录记录")
    private String checkRecord;

    @ApiModelProperty(value = "审核标识")
    private String checkFlag;

    @ApiModelProperty(value = "审核结果")
    private String result;
}
