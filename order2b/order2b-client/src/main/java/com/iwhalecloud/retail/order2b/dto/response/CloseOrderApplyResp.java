package com.iwhalecloud.retail.order2b.dto.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 关闭订单返回体
 *
 * @author xu.qinyuan@ztesoft.com
 * @date 2019年03月25日
 */
@Data
public class CloseOrderApplyResp implements Serializable {

    @ApiModelProperty("申请订单id")
    private String orderApplyId;

    @ApiModelProperty("订单id")
    private String orderId;

    @ApiModelProperty("服务类型：1订购关系、2退费关系、3换货关系、4退货关系、5关闭订单")
    private String serviceType;

    @ApiModelProperty("问题描述")
    private String questionDesc;

    @ApiModelProperty("处理人Id")
    private String handlerId;

    @ApiModelProperty("申请人Id")
    private String applicantId;

    @ApiModelProperty("申请状态")
    private String applyState;

    @ApiModelProperty("退款凭证")
    private String refundImgUrl;

    @ApiModelProperty("创建时间")
    private String createTime;

    @ApiModelProperty("凭证列表")
    private List<String> imgUrls;
}
