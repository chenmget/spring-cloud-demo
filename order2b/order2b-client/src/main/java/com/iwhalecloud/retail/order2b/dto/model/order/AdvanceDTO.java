package com.iwhalecloud.retail.order2b.dto.model.order;

import com.iwhalecloud.retail.order2b.consts.order.OrderPayType;
import com.iwhalecloud.retail.order2b.consts.order.TypeStatus;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class AdvanceDTO extends OrderDTO implements Serializable {

    @ApiModelProperty("定金")
    private Double advanceAmount;

    @ApiModelProperty("定金支付时间")
    private String advancePayTime;

    @ApiModelProperty("定金支付方式")
    private String advancePayType;
    private String advancePayTypeName;

    @ApiModelProperty("定金付款情况")
    private String advancePayStatus;
    private String advancePayStatusName;



    @ApiModelProperty("尾款")
    private Double restAmount;

    @ApiModelProperty("尾款支付开始时间")
    private String restPayBegin;

    @ApiModelProperty("尾款付款情况")
    private String restPayStatus;
    private String restPayStatusName;

    @ApiModelProperty("尾款支付方式")
    private String restPayType;
    private String restPayTypeName;


    @ApiModelProperty("尾款支付时间")
    private String restPayTime;

    public String getAdvancePayStatusName() {
        return TypeStatus.matchOpCode(advancePayStatus,TypeStatus.PAY).getName();
    }

    public String getRestPayStatusName() {
        return TypeStatus.matchOpCode(restPayStatus,TypeStatus.PAY).getName();
    }

    public String getAdvancePayTypeName() {
        return OrderPayType.matchOpCode(advancePayType).getName();
    }

    public String getRestPayTypeName() {
        return OrderPayType.matchOpCode(restPayType).getName();
    }

    public Double getAdvanceAmount100() {
        if(advanceAmount==null){
            return advanceAmount;
        }
        return advanceAmount/100.0;
    }

    public Double getRestAmount100() {
        if(restAmount==null){
            return restAmount;
        }
        return restAmount/100.0;
    }
}
