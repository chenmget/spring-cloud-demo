package com.iwhalecloud.retail.order2b.dto.resquest.order;

import com.iwhalecloud.retail.order2b.dto.base.SRequest;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class SelectAfterSalesReq extends SRequest implements Serializable {
    @ApiModelProperty("2:退款，3换货，4退款，多个类型用,分开")
    private String serviceType;

    @ApiModelProperty("商品名称")
    private String goodsName;

    @ApiModelProperty("状态")
    private String status;

    @ApiModelProperty("开始时间")
    private String startTime;

    @ApiModelProperty("结束时间")
    private String endTime;

    @ApiModelProperty("订单号")
    private String orderId;

    @ApiModelProperty("串码")
    private String resNbr;

    @ApiModelProperty("供应商名称")
    private String supplierName;

    @ApiModelProperty("申请单id")
    private String orderApplyId;

    @ApiModelProperty("类型：1：我申请的，2我处理的，3管理")
    private String userExportType;

    @ApiModelProperty("编码")
    private String goodsSn;

    @ApiModelProperty("品牌名称")
    private String brandName;

}
