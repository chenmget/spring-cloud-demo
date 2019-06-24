package com.iwhalecloud.retail.order2b.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.order2b.dto.response.purapply.PurApplyDeliveryResp;
import com.iwhalecloud.retail.order2b.entity.PurApplyDelivery;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface PurApplyDeliveryMapper extends BaseMapper<PurApplyDelivery> {
	
	public String getSeqApplyItemDetailBatchId();

	public Page<PurApplyDeliveryResp>   getDeliveryInfoByApplyID(Page<PurApplyDeliveryResp> page, @Param("applyId") String applyId);
}
