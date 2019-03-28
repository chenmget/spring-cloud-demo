package com.iwhalecloud.retail.order2b.dto.response;

import com.iwhalecloud.retail.order2b.dto.model.order.AdvanceDTO;
import com.iwhalecloud.retail.order2b.dto.model.order.OrderDTO;
import com.iwhalecloud.retail.order2b.dto.model.order.OrderItemDTO;
import com.iwhalecloud.retail.order2b.dto.model.order.OrderItemDetailDTO;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class OrderListExportResp implements Serializable {

    private List<OrderDTO> orderList;

    private List<AdvanceDTO> advanceList;

    private List<OrderItemDTO> orderItemList;

    private List<OrderItemDetailDTO> orderItemDetailList;
}
