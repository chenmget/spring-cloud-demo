package com.iwhalecloud.retail.order2b.dto.resquest.order;

import com.iwhalecloud.retail.order2b.dto.base.OrderRequest;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 查询订单申请信息请求入参
 *
 * @author xu.qinyuan@ztesoft.com
 * @date 2019年03月25日
 */
@Data
public class OrderApplyGetReq extends OrderRequest implements Serializable {

    @ApiModelProperty("订单id")
    private String orderId;

    private String serviceType;
    private String status;
}
