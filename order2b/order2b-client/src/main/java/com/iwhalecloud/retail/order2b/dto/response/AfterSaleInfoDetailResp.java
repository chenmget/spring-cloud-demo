package com.iwhalecloud.retail.order2b.dto.response;

import com.iwhalecloud.retail.order2b.dto.model.order.OrderDTO;
import com.iwhalecloud.retail.order2b.dto.model.order.OrderZFlowDTO;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class AfterSaleInfoDetailResp implements Serializable {

   private AfterSaleResp afterSale;

    private List<OrderZFlowDTO> zFlowList;

    private String currentFlowType;

    private OrderDTO order;

    private List<String> applyResNbrs;

    private List<String> hhResNbrs;

}
