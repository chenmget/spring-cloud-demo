package com.iwhalecloud.retail.order2b.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.iwhalecloud.retail.order2b.dto.model.order.OrderLogDTO;
import com.iwhalecloud.retail.order2b.dto.resquest.order.OrderLogGetReq;
import com.iwhalecloud.retail.order2b.entity.OrderLog;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Class: OrderLogMapper
 * @author autoCreate
 */
@Mapper
public interface OrderLogMapper extends BaseMapper<OrderLog>{

    /**
     * 根据条件查询订单日志
     *
     * @param req 查询入参
     * @return
     */
    public List<OrderLogDTO> queryOrderLogByCondition(@Param("req") OrderLogGetReq req);

}