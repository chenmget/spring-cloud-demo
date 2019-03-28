package com.iwhalecloud.retail.order2b.dto.resquest.order;

import com.iwhalecloud.retail.order2b.dto.base.OrderRequest;
import lombok.Data;

import java.io.Serializable;

/**
 * @author li.yulong
 * @date 2019/3/15 20:50
 */

@Data
public class DeliveryReq extends OrderRequest implements Serializable {
     String orderId;
     String batchId;
}
