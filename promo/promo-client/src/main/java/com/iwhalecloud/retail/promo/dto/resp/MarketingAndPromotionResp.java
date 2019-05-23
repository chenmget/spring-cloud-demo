package com.iwhalecloud.retail.promo.dto.resp;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author lhr 2019-03-06 16:37:30
 */
@Data
public class MarketingAndPromotionResp implements Serializable{

    private static final long serialVersionUID = -8956719275693721347L;
    /**
     * ID
     */
    @ApiModelProperty(value = "ID")
    private String id;
    /**
     * 营销活动名称
     */
    @ApiModelProperty(value = "营销活动名称")
    private String name;
    /**
     * 活动类型code
     */
    @ApiModelProperty(value = "活动类型 1001--预售  1002--前置补贴 1003--返利")
    private String activityType;
    /**
     * 优惠类型code
     */
    @ApiModelProperty(value = "优惠类型 10--减免 20--券 30--返利 40--赠送")
    private String promotionTypeCode;
    /**
     * 优惠价格
     */
    @ApiModelProperty(value = "优惠价格")
    private java.lang.String promotionPrice;
    /**
     * 产品ID
     */
    @ApiModelProperty(value = "产品ID")
    private java.lang.String productId;
    /**
     * 优惠名称
     */
    @ApiModelProperty(value = "优惠名称")
    private java.lang.String mktResName;
    
    /**
     * 优惠数量
     */
    @ApiModelProperty(value = "活动限制的数量")
    private java.lang.Long num;
}
