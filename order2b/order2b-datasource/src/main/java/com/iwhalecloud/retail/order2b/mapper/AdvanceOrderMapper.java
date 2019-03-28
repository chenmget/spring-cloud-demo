package com.iwhalecloud.retail.order2b.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.iwhalecloud.retail.order2b.entity.AdvanceOrder;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AdvanceOrderMapper extends BaseMapper<AdvanceOrder> {

    /**
     * 根据订单Id查询预售订单信息
     * @param orderId
     * @return
     */
    AdvanceOrder selectAdvanceOrderByOrderId(AdvanceOrder orderId);

    /**
     * 查询超时未支付的订单
     * @return
     */
    List<AdvanceOrder> queryOverTimePayOrder();
}
