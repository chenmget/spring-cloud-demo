package com.iwhalecloud.retail.order2b.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.iwhalecloud.retail.order2b.dto.response.purapply.PurApplyDeliveryResp;
import com.iwhalecloud.retail.order2b.entity.PurApplyDelivery;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface PurApplyDeliveryMapper extends BaseMapper<PurApplyDelivery> {
	
	public String getSeqApplyItemDetailBatchId();

	public List<PurApplyDeliveryResp> getDeliveryInfoByApplyID(@Param("applyId") String applyId);
}
