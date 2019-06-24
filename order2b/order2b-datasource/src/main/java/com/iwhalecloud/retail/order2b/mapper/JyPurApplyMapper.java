package com.iwhalecloud.retail.order2b.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.order2b.dto.response.purapply.JyPurApplyResp;
import com.iwhalecloud.retail.order2b.dto.response.purapply.WfTaskResp;
import com.iwhalecloud.retail.order2b.dto.resquest.purapply.PurApplyReq;
import com.iwhalecloud.retail.order2b.entity.PurApply;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface JyPurApplyMapper extends BaseMapper<PurApply>  {
	
	public WfTaskResp getTaskItemId(@Param("applyId") String applyId);

	public Page<JyPurApplyResp> jycgSearchApply(Page<JyPurApplyResp> page,@Param("req") PurApplyReq purApplyReq) ;
}
