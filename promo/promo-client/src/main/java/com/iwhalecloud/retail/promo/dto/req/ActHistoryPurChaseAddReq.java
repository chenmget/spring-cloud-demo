package com.iwhalecloud.retail.promo.dto.req;


import com.iwhalecloud.retail.promo.dto.PromotionDTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.io.Serializable;
import java.util.List;

/**
 * @author lhr 2019-03-13 14:46:30
 */
@Data
public class ActHistoryPurChaseAddReq extends AbstractPageReq implements Serializable{


    private static final long serialVersionUID = 4500860129388428835L;


    /**
     * ID
     */
    @ApiModelProperty(value = "ID")
    private java.lang.String id;

    /**
     * 营销活动编号
     */
    @ApiModelProperty(value = "营销活动编号")
    private java.lang.String marketingActivityId;

    /**
     * 商家类型
     */
    @ApiModelProperty(value = "商家类型")
    private java.lang.String merchantType;

//    /**
//     * 商家编码
//     */
//    @ApiModelProperty(value = "商家编码")
//    private java.lang.String merchantId;

    /**
     * 商家编码
     */
    @ApiModelProperty(value = "商家编码")
    private java.lang.String merchantCode;

    /**
     * 商家名称
     */
    @ApiModelProperty(value = "商家名称")
    private java.lang.String merchantName;

    /**
     * 参加活动时间
     */
    @ApiModelProperty(value = "参加活动时间")
    private java.util.Date purchaseTime;

    /**
     * 支付预付价格时间
     */
    @ApiModelProperty(value = "支付预付价格时间")
    private java.util.Date prePayTime;

    /**
     * 支付支付价格时间
     */
    @ApiModelProperty(value = "支付支付价格时间")
    private java.util.Date payTime;

    /**
     * 订单ID
     */
    @ApiModelProperty(value = "订单ID")
    private java.lang.String orderId;

    /**
     * 订单类型
     */
    @ApiModelProperty(value = "订单类型")
    private java.lang.String orderType;

    /**
     * 商品ID
     */
    @ApiModelProperty(value = "商品ID")
    private java.lang.String goodsId;

    /**
     * 产品ID
     */
    @ApiModelProperty(value = "产品ID")
    private java.lang.String productId;

    /**
     * 价格
     */
    @ApiModelProperty(value = "价格")
    private java.lang.String price;

    /**
     * 预付价格
     */
    @ApiModelProperty(value = "预付价格")
    private java.lang.String prePrice;

    /**
     * 优惠价格
     */
    @ApiModelProperty(value = "优惠价格")
    private java.lang.String promotionPrice;

    /**
     * 支付价格
     */
    @ApiModelProperty(value = "支付价格")
    private java.lang.String payMoney;

    /**
     * 优惠券实例id
     */
    @ApiModelProperty(value = "优惠券实例id")
    private java.lang.String couponInstId;

    /**
     * 支付类型
     */
    @ApiModelProperty(value = "支付类型")
    private java.lang.String payType;

    /**
     * 数量
     */
    @ApiModelProperty(value = "数量")
    private java.lang.Integer num;

    /**
     * 供应商编码
     */
    @ApiModelProperty(value = "供应商编码")
    private java.lang.String supplierCode;

    @ApiModelProperty(value = "创建人。")
    private java.lang.String creator;

    /**
     * 记录首次创建的时间。
     */
    @ApiModelProperty(value = "创建时间。")
    private java.util.Date gmtCreate;
    /**
     * 记录每次修改的员工标识。
     */
    @ApiModelProperty(value = "修改人。")
    private java.lang.String modifier;

    /**
     * 记录每次修改的时间。
     */
    @ApiModelProperty(value = "修改时间。")
    private java.util.Date gmtModified;
    /**
     * 是否删除：0未删、1删除。
     */
    @ApiModelProperty(value = "是否删除：0未删、1删除。")
    private String isDeleted;

    @ApiModelProperty("活动类型")
    private String activityType;

    @ApiModelProperty("订单项id")
    private String orderItemId;
}
