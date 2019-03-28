package com.iwhalecloud.retail.rights.dto.response;

import com.iwhalecloud.retail.rights.dto.OrderCouponDTO;
import com.iwhalecloud.retail.rights.dto.OrderItemWithCouponDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@ApiModel(value = "订单优惠券列表查询请求返回参数")
public class OrderCouponListResp implements Serializable {

    private static final long serialVersionUID = 1035135865537372952L;

    @ApiModelProperty("订单项列表")
    private List<OrderItemWithCouponDTO> orderItemList;

    @ApiModelProperty("优惠券列表")
    private List<OrderCouponDTO> couponList;

}
