package com.iwhalecloud.retail.order2b.dto.model.order;

import com.iwhalecloud.retail.order2b.dto.base.SelectModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class CreateOrderDTO extends SelectModel implements Serializable {

    @ApiModelProperty(value = "订单号")
    private String orderId;

    @ApiModelProperty("订单金额")
    private Double orderAmount;

    @ApiModelProperty("支付类型")
    private String payType;

    @ApiModelProperty("创建时间")
    private String createTime;

    private String supperName;
    @ApiModelProperty("买家名称")
    private String userName;

    private String status;

    private String advancePayType;

    @ApiModelProperty("1：买家确认，0不需要")
    private String isMerchantConfirm;

    @ApiModelProperty("定金")
    private Double advanceAmount;

    @ApiModelProperty("尾款")
    private Double restAmount;

    @ApiModelProperty("1:预售单，0普通订单")
    private String orderCat;

    private String getGoodsCode;

    private List<OrderItemDTO> orderItem;

    private List<PromotionDTO> promotionList;

    private UserMemberDTO userInfo;

    public String getCreateTime() {
        return timeFormat(createTime);
    }


}
