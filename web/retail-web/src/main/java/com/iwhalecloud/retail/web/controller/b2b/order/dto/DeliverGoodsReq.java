package com.iwhalecloud.retail.web.controller.b2b.order.dto;

import com.iwhalecloud.retail.oms.dto.response.FileManagerRespDTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;


@Data
public class DeliverGoodsReq implements Serializable {

    private String lanId;

    @ApiModelProperty(value = "订单号")
    private String orderId;
    @ApiModelProperty(value = "物流单号")
    private String logiNo;
    @ApiModelProperty(value = "物流公司")
    private String logiName;
    @ApiModelProperty(value = "物流ID")
    private String logiId;

    /**
     * 串码id
     */
    private List<String> resNbrList;

    private FileManagerRespDTO resNbrFile;

    @ApiModelProperty("申请单")
    private String orderApplyId;

    @ApiModelProperty("正常发货：1，换货发货：3")
    private String type;

    @ApiModelProperty("提货码")
    private String getGoodsCode;
}
