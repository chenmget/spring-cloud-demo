package com.iwhalecloud.retail.order2b.dto.resquest.promo;

import com.iwhalecloud.retail.order2b.dto.base.OrderRequest;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author zhou.zc
 * @date 2019年03月07日
 * @Description:新增前置补贴活动补录记录
 */
@Data
public class AddActSupRecordReq extends OrderRequest implements Serializable{

    private static final long serialVersionUID = 4817965850679960441L;

    @ApiModelProperty(value = "活动Id")
    private String marketingActivityId;

    @ApiModelProperty(value = "活动开始时间")
    private Date startTime;

    @ApiModelProperty(value = "活动结束时间")
    private Date endTime;

    @ApiModelProperty(value = "订单id")
    private String orderId;

    @ApiModelProperty(value = "串码")
    private String resNbr;
}
