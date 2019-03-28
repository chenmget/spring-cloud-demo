package com.iwhalecloud.retail.promo.dto.resp;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@ApiModel(value = "订单优惠券列表请求结果")
public class OrderPromotionListResp<T> implements Serializable {
    private static final long serialVersionUID = 8336930456509145113L;

    @ApiModelProperty(value = "订单可用优惠券列表")
    private List<T> enabledPromotions;

    @ApiModelProperty(value = "订单不可用优惠券列表")
    private List<T> disabledPromotions;

}
