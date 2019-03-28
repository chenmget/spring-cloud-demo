package com.iwhalecloud.retail.order2b.model;

import com.iwhalecloud.retail.order2b.entity.AdvanceOrder;
import com.iwhalecloud.retail.order2b.entity.Order;
import com.iwhalecloud.retail.order2b.entity.OrderItem;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 预售订单信息
 *
 * @author xu.qinyuan@ztesoft.com
 * @date 2019年03月04日
 */
@Data
public class AdvanceOrderInfoModel extends OrderInfoModel implements Serializable {
    private AdvanceOrder advanceOrder;
}
