package com.iwhalecloud.retail.order.dto.resquest;

import com.iwhalecloud.retail.order.dto.base.SelectBaseRequest;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class SelectOrderRequest extends SelectBaseRequest implements Serializable {


    @ApiModelProperty(value = " 待审核审核不通过：1 ；\n" +
            "     未付款、审核通过：2 ；\n" +
            "     已支付、待受理：3 ；\n" +
            "     已受理待发货：4 ；\n" +
            "     已发货待确认：5 ；\n" +
            "     确认收货：7 ；\n" +
            "     已完成：6 ；\n" +
            "     取消订单：-10 ；\n" +
            "     订单异常：99 ；")
    private String orderStatus;
    @ApiModelProperty(value = "分销商id",hidden = true)
    private String userId; 
    @ApiModelProperty(value = "供应商Id",hidden = true)
    private String supplyId;
    @ApiModelProperty(value = "开始时间")
    private String startTime;
    @ApiModelProperty(value = "结束时间")
    private String endTime;
    @ApiModelProperty(value = "订单id")
    private String orderId;

    @ApiModelProperty(value = "登录身份：{memberId：会员}，{operatorId:运营人员}",hidden = true)
    private String loginUserType;


}
