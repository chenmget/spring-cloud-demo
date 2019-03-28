package com.iwhalecloud.retail.order2b.dto.resquest.order;

import com.iwhalecloud.retail.order2b.dto.base.OrderRequest;
import lombok.Data;

import java.io.Serializable;

@Data
public class OrderInfoIDReq extends OrderRequest implements Serializable {

    private String orderId;

    private String orderItemId;

    private String resNbr;
}
