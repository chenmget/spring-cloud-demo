package com.iwhalecloud.retail.order2b.dto.model.order;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class OrderItemDetailListDTO extends OrderItemDTO implements Serializable {

    List<OrderItemDetailDTO> detailList;

}
