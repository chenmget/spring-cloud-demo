package com.iwhalecloud.retail.order2b.dto.resquest.order;

import com.iwhalecloud.retail.order2b.dto.base.SRequest;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class SelectOrderReq extends SRequest implements Serializable {

    @ApiModelProperty("订单id")
    private String orderId;

    @ApiModelProperty("订单list")
    private List<String> reqOrderList;
    @ApiModelProperty("订单状态")
    private String status;
    @ApiModelProperty("买家名称")
    private String userName;

    @ApiModelProperty("供应商名称")
    private String supplierName;
    private String supplierId;


    @ApiModelProperty("支付开始时间")
    private String payTimeStart;
    @ApiModelProperty("支付结束时间")
    private String payTimeEnd;

    @ApiModelProperty("下单开始时间")
    private String orderTimeStart;
    @ApiModelProperty("下单结束时间")
    private String orderTimeEnd;

    @ApiModelProperty("发货开始时间")
    private String shipTimeStart;
    @ApiModelProperty("发货结束时间")
    private String shipTimeEnd;

    @ApiModelProperty(value = "【必填】在线支付  3线下付款")
    private String paymentType;

    @ApiModelProperty("营销活动名称")
    private String activityPromoName;

    @ApiModelProperty(value = "商品名称")
    private String goodsName;
    @ApiModelProperty(value = "串码编号")
    private String resNbr;

    @ApiModelProperty("类型：1：采购，2销售，3管理")
    private String userExportType;

    @ApiModelProperty("默认0:普通订单，1预收订单")
    private List<String> orderCatList;

    @ApiModelProperty("编码")
    private String goodsSn;

    @ApiModelProperty("品牌名称")
    private String brandName;

    @ApiModelProperty("11.省包至地包交易订单\n" +
            "12.省包至零售商交易订单\n" +
            "13.地包到零售商交易订单\n" +
            "14.强制分货零售商订单")
    private String orderType;
}
