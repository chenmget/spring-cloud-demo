package com.iwhalecloud.retail.order2b.service;

import com.iwhalecloud.retail.order2b.dto.model.order.SettleRecordOrderDTO;

import java.util.List;

/**
 * Created by Administrator on 2019/3/29.
 */
public interface SettleRecordOrderService {

    List<SettleRecordOrderDTO> getSettleRecordOrder(List<String> orderIds);
}
