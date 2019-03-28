package com.iwhalecloud.retail.order2b.dto.response;

import com.iwhalecloud.retail.order2b.dto.model.order.AdvanceOrderDTO;
import com.iwhalecloud.retail.order2b.dto.model.order.OrderDTO;
import com.iwhalecloud.retail.order2b.dto.model.order.OrderItemDTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 预售订单返回体
 *
 * @author xu.qinyuan@ztesoft.com
 * @date 2019年03月04日
 */
@Data
public class AdvanceOrderResp extends OrderSelectResp implements Serializable {


    @ApiModelProperty("预售订单信息")
    private AdvanceOrderDTO advanceOrder;
}
