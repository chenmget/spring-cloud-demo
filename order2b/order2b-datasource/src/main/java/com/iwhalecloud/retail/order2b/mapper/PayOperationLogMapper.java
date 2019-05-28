package com.iwhalecloud.retail.order2b.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.iwhalecloud.retail.order2b.dto.resquest.pay.UpdateOrdOrderReq;
import com.iwhalecloud.retail.order2b.entity.PayOperationLog;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @Class: PayOperationLogMapper
 * @author autoCreate
 */
@Mapper
public interface PayOperationLogMapper extends BaseMapper<PayOperationLog>{

	public void UpdateOrdOrderStatus(@Param("req") UpdateOrdOrderReq req);
}