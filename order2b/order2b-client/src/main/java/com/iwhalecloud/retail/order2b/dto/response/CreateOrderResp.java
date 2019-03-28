package com.iwhalecloud.retail.order2b.dto.response;

import com.iwhalecloud.retail.order2b.dto.model.order.CreateOrderDTO;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class CreateOrderResp implements Serializable {

    List<CreateOrderDTO> orderList;
}
