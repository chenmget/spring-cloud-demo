package com.iwhalecloud.retail.order2b.dto.resquest.order;

import com.iwhalecloud.retail.order2b.dto.base.OrderRequest;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 查询订单日志请秋入参
 *
 * @author xu.qinyuan@ztesoft.com
 * @date 2019年03月26日
 */
@Data
public class OrderLogGetReq  extends OrderRequest implements Serializable {

    @ApiModelProperty("订单id")
    private String orderId;

    @ApiModelProperty("变更后状态")
    private String postStatus;
}
