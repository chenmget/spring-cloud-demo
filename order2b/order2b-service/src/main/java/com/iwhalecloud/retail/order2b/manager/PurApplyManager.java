package com.iwhalecloud.retail.order2b.manager;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.order2b.dto.response.purapply.PurApplyResp;
import com.iwhalecloud.retail.order2b.dto.resquest.purapply.AddProductReq;
import com.iwhalecloud.retail.order2b.dto.resquest.purapply.ProcureApplyReq;
import com.iwhalecloud.retail.order2b.dto.resquest.purapply.PurApplyReq;
import com.iwhalecloud.retail.order2b.mapper.CartMapper;
import com.iwhalecloud.retail.order2b.mapper.PurApplyMapper;

@Component
public class PurApplyManager {
	
	@Resource
    private PurApplyMapper purApplyMapper;
	
	public Page<PurApplyResp> cgSearchApply(PurApplyReq req) {
		Page<PurApplyResp> page=new Page<>(req.getPageNo(),req.getPageSize());
		Page<PurApplyResp> pageReport =purApplyMapper.cgSearchApply(page,req);
		return pageReport;
	}

	public void tcProcureApply(ProcureApplyReq req){
		purApplyMapper.tcProcureApply(req);
	}
	
	public void crPurApplyFile(ProcureApplyReq req){
		purApplyMapper.crPurApplyFile(req);
	}
	
	public void crPurApplyItem(AddProductReq req){
		purApplyMapper.crPurApplyItem(req);
	}
	
	public void delSearchApply(PurApplyReq req){
		purApplyMapper.delSearchApply(req);
		purApplyMapper.delSearchApplyItem(req);
	}
}
