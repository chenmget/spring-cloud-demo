package com.iwhalecloud.retail.order2b.dto.model.order;

import com.iwhalecloud.retail.order2b.dto.base.SelectModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class PromotionDTO extends SelectModel implements Serializable {

    private String ordPromotioId;
    @ApiModelProperty("订单号")
    private String orderId;
    @ApiModelProperty("订单项id")
    private String orderItemId;
    @ApiModelProperty("")
    private String goodsId;
    private String productId;
    private String mktActId;
    @ApiModelProperty("营销活动名称")
    private String mktActName;
    private String promotionId;
    @ApiModelProperty("优惠名称")
    private String promotionName;
    @ApiModelProperty("优惠类型")
    private String promotionType;
    @ApiModelProperty("优惠实例ID")
    private String promotionInstId;
    @ApiModelProperty("抵扣金额")
    private String discount;
    private String statusCd;
    private String statusDate;
    @ApiModelProperty("创建名称")
    private String createDate;

    @ApiModelProperty("活动类型")
    private String mktActType;

    public String getStatusDate() {
        return timeFormat(statusDate);
    }

    public String getCreateDate() {
        return timeFormat(createDate);
    }
}
