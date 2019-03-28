package com.iwhalecloud.retail.order2b.dto.response;

import com.iwhalecloud.retail.order2b.dto.model.order.OrderApplyDTO;
import com.iwhalecloud.retail.order2b.dto.model.order.OrderItemDetailDTO;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class OrderApplyExportResp implements Serializable {

    List<OrderApplyDTO> orderApplyList;

    List<OrderItemDetailDTO> applyNbrslist;
}
