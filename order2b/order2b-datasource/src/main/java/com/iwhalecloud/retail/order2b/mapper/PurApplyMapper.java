package com.iwhalecloud.retail.order2b.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.order2b.dto.response.purapply.PurApplyResp;
import com.iwhalecloud.retail.order2b.dto.resquest.purapply.AddProductReq;
import com.iwhalecloud.retail.order2b.dto.resquest.purapply.ProcureApplyReq;
import com.iwhalecloud.retail.order2b.dto.resquest.purapply.PurApplyReq;
import com.iwhalecloud.retail.order2b.entity.PurApply;

@Mapper
public interface PurApplyMapper extends BaseMapper<PurApply>  {

	public Page<PurApplyResp> cgSearchApply(Page<PurApplyResp> page,@Param("req") PurApplyReq purApplyReq) ;
	
	public void tcProcureApply(@Param("req") ProcureApplyReq procureApplyReq);
	
	public void crPurApplyFile(@Param("req") ProcureApplyReq procureApplyReq);
	
	public void crPurApplyItem(@Param("req") AddProductReq addProductReq);
	
	public void delSearchApply(@Param("req") PurApplyReq purApplyReq);
	
	public void delSearchApplyItem(@Param("req") PurApplyReq purApplyReq);
}
