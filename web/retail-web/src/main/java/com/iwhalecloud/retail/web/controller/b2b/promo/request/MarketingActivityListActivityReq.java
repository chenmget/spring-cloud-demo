package com.iwhalecloud.retail.web.controller.b2b.promo.request;

import com.iwhalecloud.retail.dto.AbstractRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

import java.io.Serializable;
@Data
@ApiModel("商品适用活动查询请求参数")
public class MarketingActivityListActivityReq extends AbstractRequest implements Serializable {

    /**
     * 产品id
     */
    @ApiModelProperty(value = "产品id")
    @NotEmpty(message = "产品id不能为空")
    private String productId;

    /**
     * 供应商编码
     */
    @ApiModelProperty(value = "卖家ID")
    @NotEmpty(message = "供应商编码不能为空")
    private String supplierCode;

    /**
     * 商家编码
     */
    @ApiModelProperty(value = "买家ID")
    private java.lang.String merchantCode;

    /**
     * 活动类型
     */
    @ApiModelProperty(value = "活动类型，1001--预售\n" +
            "        1002--前置补贴")
    private java.lang.String activityType;
}
