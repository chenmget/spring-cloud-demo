package com.iwhalecloud.retail.promo.dto.resp;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author: wang.jiaxin110@iwhalecloud.com
 * @Date: 2019-02-19 17:02
 * @Description: 查询B2B商品适用活动响应参数
 **/
@Data
@ApiModel(value = "查询B2B商品适用活动响应参数")
public class MarketingGoodsActivityQueryResp implements Serializable {

    //属性 begin
    /**
     * ID
     */
    @ApiModelProperty(value = "ID")
    private java.lang.String id;

    /**
     * 营销活动code
     */
    @ApiModelProperty(value = "营销活动code")
    private java.lang.String code;

    /**
     * 营销活动名称
     */
    @ApiModelProperty(value = "营销活动名称")
    private java.lang.String name;

    /**
     * 支付定金开始时间
     */
    @ApiModelProperty(value = "支付定金开始时间")
    private java.util.Date preStartTime;

    /**
     * 支付定金结束时间
     */
    @ApiModelProperty(value = "支付定金结束时间")
    private java.util.Date preEndTime;

    /**
     * 支付尾款开始时间
     */
    @ApiModelProperty(value = "支付尾款开始时间")
    private java.util.Date tailPayStartTime;

    /**
     * 支付尾款结束时间
     */
    @ApiModelProperty(value = "支付尾款结束时间")
    private java.util.Date tailPayEndTime;

    /**
     * 开始时间
     */
    @ApiModelProperty(value = "活动开始时间")
    private java.util.Date startTime;

    /**
     * 结束时间
     */
    @ApiModelProperty(value = "活动结束时间")
    private java.util.Date endTime;

    /**
     * 活动类型
     */
    @ApiModelProperty(value = "活动类型，1001--预售\n" +
            "        1002--前置补贴")
    private java.lang.String activityType;

    @ApiModelProperty(value = "活动预付款")
    private Long advancePayAmount;

    /**
     * 优惠方式为直减时，保存减免的金额
     */
    @ApiModelProperty(value = "优惠方式为直减时，保存减免的金额")
    private java.lang.Long discountAmount;

}
