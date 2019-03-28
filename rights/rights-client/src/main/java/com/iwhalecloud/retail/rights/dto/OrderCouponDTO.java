package com.iwhalecloud.retail.rights.dto;

import com.iwhalecloud.retail.rights.dto.request.CouponOrderItemDTO;
import com.iwhalecloud.retail.rights.dto.response.CouponInstDetailDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@ApiModel("优惠券对应订单信息对象")
public class OrderCouponDTO implements Serializable {

//    @ApiModelProperty("优惠券实例id")
//    private String couponInstId;
//
//    @ApiModelProperty("优惠券名称")
//    private String 	mktResName;
//
//    @ApiModelProperty(value = "优惠方式LOVB=RES-C-0041定额、折扣、随机等")
//    private java.lang.String discountType;
//
//    @ApiModelProperty("优惠券编码")
//    private String mktResNbr;
//
//    @ApiModelProperty("优惠券标识")
//    private String mktResId;
//
//    @ApiModelProperty(value = "生效时间")
//    private java.util.Date effDate;
//
//    @ApiModelProperty(value = "失效时间")
//    private java.util.Date expDate;

    @ApiModelProperty("优惠券实例")
    private CouponInstDetailDTO couponInst;

    @ApiModelProperty("优惠券对订单是否有效, 默认无效")
    private Boolean isValid = false;

    @ApiModelProperty("优惠券是否已选中, 默认无效，选中 selectedOrderItem才有值")
    private Boolean isSelected = false;

    @ApiModelProperty("优惠券使用在哪个订单项，isSelected=true 时才有值")
    private CouponOrderItemDTO bindOrderItem;

    @ApiModelProperty("优惠券适用订单项列表")
    private List<CouponOrderItemDTO> applyOrderItemList;

//    @ApiModelProperty("当前优惠券适用的订单项（为空表示没有适用订单项）")
//    private CouponOrderItemDTO couponOrderItem;

}
