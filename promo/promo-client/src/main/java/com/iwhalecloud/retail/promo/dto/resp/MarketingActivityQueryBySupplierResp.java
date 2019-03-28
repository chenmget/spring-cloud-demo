package com.iwhalecloud.retail.promo.dto.resp;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 根据卖家过滤营销活动出参对象
 * @author z
 */
@Data
@ApiModel(value = "根据卖家过滤营销活动出参对象")
public class MarketingActivityQueryBySupplierResp implements Serializable {
    /**
     * ID
     */
    @ApiModelProperty(value = "ID")
    private java.lang.String id;

    /**
     * 营销活动名称
     */
    @ApiModelProperty(value = "营销活动名称")
    private java.lang.String name;

    /**
     * 活动类型
     */
    @ApiModelProperty(value = "活动类型\n" +
            "1001-预售\n" +
            "1002-前置补贴\n" +
            "1003-返利")
    private java.lang.String activityType;
}
