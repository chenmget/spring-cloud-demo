package com.iwhalecloud.retail.promo.dto.req;

import com.iwhalecloud.retail.dto.AbstractRequest;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 根据卖家过滤营销活动请求对象
 * @author z
 */
@Data
public class MarketingActivityQueryBySupplierReq extends AbstractRequest implements Serializable {

    /**
     * 供应商编码 卖家
     */
    @ApiModelProperty(value = "卖家ID")
    private String supplierId;



    /**
     * 活动类型
     */
    @ApiModelProperty(value = "活动类型\n" +
            "1001-预售\n" +
            "1002-前置补贴\n" +
            "1003-返利")
    private String activityType;

}
