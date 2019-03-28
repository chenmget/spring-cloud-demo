package com.iwhalecloud.retail.order2b.dto.resquest.order;

import com.iwhalecloud.retail.order2b.dto.base.MRequest;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class OrderApplyReq extends MRequest implements Serializable {

    /**
     * 订单信息
     */
    @ApiModelProperty("订单")
    private String orderId;
    @ApiModelProperty("订单项")
    private String orderItemId;
    @ApiModelProperty("2退费关系、3换货关系、4退货关系")
    private String serviceType;
    @ApiModelProperty("数量")
    private Integer submitNum;
    @ApiModelProperty("货物状态：0未收到，1已收到")
    private String receiveState;

    /**
     *原因
     */
    @ApiModelProperty("申请凭证：1有发票、2有检测报告")
    private String applyProof;
    @ApiModelProperty("原因")
    private String returnReson;
    @ApiModelProperty("问题描述")
    private String questionDesc;
    @ApiModelProperty("图片url:数组")
    private String uploadImgUrl;

    @ApiModelProperty("串码,多个用回车符隔开")
    private String resNbrs;

    @ApiModelProperty("串码list")
    private List<String> resNbrList;

    /**
     * 方式
     */
    @ApiModelProperty("商品返回方式：1上门取件、2快递")
    private String goodReturnType;
    @ApiModelProperty("退款方式:1退款至账户余额、2原支付方式返回、3退款至银行卡，4：线下退款")
    private String refundType;
    @ApiModelProperty("退款金额")
    private String refundValue;

    private String returnedType;
    @ApiModelProperty(value = "【必填】1在线支付  3线下付款")
    private String returnedKind;

    /**
     * 银行信息
     */
    @ApiModelProperty("退款账号")
    private String returnedAccount;
    @ApiModelProperty("银行")
    private String bankInfo;
    @ApiModelProperty("退款人")
    private String accountHolderName;


    /**
     * 内部使用
     */
    private String handlerId;
    private String supplierName;

}
