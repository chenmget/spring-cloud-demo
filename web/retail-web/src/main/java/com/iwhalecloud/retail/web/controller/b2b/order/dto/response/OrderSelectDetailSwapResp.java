package com.iwhalecloud.retail.web.controller.b2b.order.dto.response;

import com.iwhalecloud.retail.order2b.dto.model.order.AdvanceOrderDTO;
import com.iwhalecloud.retail.order2b.dto.model.order.DeliveryDTO;
import com.iwhalecloud.retail.order2b.dto.model.order.OrderZFlowDTO;
import com.iwhalecloud.retail.order2b.dto.model.order.UserMemberDTO;
import com.iwhalecloud.retail.order2b.dto.response.OrderPayInfoResp;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;
@Data
public class OrderSelectDetailSwapResp extends OrderSelectSwapResp implements Serializable {

    List<OrderZFlowDTO> zFlowList;

    private String currentFlowType;

    private UserMemberDTO MemberInfo;

    @ApiModelProperty("1:采购，2，销售")
    private String userOrderType;

    private List<DeliveryDTO> deliveryModels;

    @ApiModelProperty("预售订单信息")
    private AdvanceOrderDTO advanceOrder;

    @ApiModelProperty("支付信息")
    List<OrderPayInfoResp> payLogList;
}
