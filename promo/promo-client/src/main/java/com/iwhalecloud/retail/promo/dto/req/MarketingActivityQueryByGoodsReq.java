package com.iwhalecloud.retail.promo.dto.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author: wang.jiaxin110@iwhalecloud.com
 * @Date: 2019-02-19 17:41
 * @Description:
 **/
@Data
public class MarketingActivityQueryByGoodsReq extends AbstractPageReq {

    /**
     * 产品id
     */
    @ApiModelProperty(value = "产品id")
    private String productId;

    /**
     * 供应商编码 卖家
     */
    @ApiModelProperty(value = "供应商编码")
    private String supplierCode;

    /**
     * 商家编码 买家
     */
    @ApiModelProperty(value = "商家编码")
    private java.lang.String merchantCode;

    /**
     * 活动类型
     */
    @ApiModelProperty(value = "活动类型")
    private java.lang.String activityType;

}
