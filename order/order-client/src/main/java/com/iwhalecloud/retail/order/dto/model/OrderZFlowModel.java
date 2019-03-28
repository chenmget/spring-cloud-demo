package com.iwhalecloud.retail.order.dto.model;

import com.iwhalecloud.retail.order.dto.base.SelectBaseRequest;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.util.StringUtils;

import java.io.Serializable;

@Data
/**
 * ord_order_Z_FLOW
 */
public class OrderZFlowModel implements Serializable {


    @ApiModelProperty(value = "id")
    private String flowId;
    @ApiModelProperty(value = "顺序")
    private Integer sort;
    @ApiModelProperty(value = "订单号")
    private String orderId;
    @ApiModelProperty(value = "绑定类型：0，1合约机")
    private String bindType;
    @ApiModelProperty(value = "操作人")
    private String handlerId;
    @ApiModelProperty(value = "是否执行，0未，1执行")
    private String isExe;
    @ApiModelProperty(value = "操作类型：对应更行订单操作flowType")
    private String flowType;
    @ApiModelProperty(value = "更新时间")
    private String updateTime;
    @ApiModelProperty(value = "创建时间")
    private String createTime;
    @ApiModelProperty(value = "订单监控标志")
    private String remindFlag;

    public String getUpdateTime() {
        return timeFormat(updateTime);
    }

    public String getCreateTime() {
        return timeFormat(createTime);
    }

    private String timeFormat(String payTime){
        if(StringUtils.isEmpty(payTime)){
            return payTime;
        }
        payTime=payTime.replace(".0","");
        return payTime;
    }

}
