package com.iwhalecloud.retail.rights.dto.request;

import com.iwhalecloud.retail.dto.AbstractRequest;
import com.iwhalecloud.retail.rights.dto.SelectedCouponDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@ApiModel(value = "订单优惠券列表查询请求参数")
public class OrderCouponListReq extends AbstractRequest implements Serializable {
    private static final long serialVersionUID = -8065203696096660515L;

    @ApiModelProperty("用户id")
    private String userId;

    @ApiModelProperty("卖家商家id")
    private String merchantId;

    @ApiModelProperty("买家商家id(当前登录人）")
    private String userMerchantId;

    @ApiModelProperty("订单项列表")
    private List<CouponOrderItemDTO> orderItemList;

    @ApiModelProperty("已选中优惠券列表")
    private List<SelectedCouponDTO> selectedCouponList;


}
