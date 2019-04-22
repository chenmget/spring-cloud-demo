package com.iwhalecloud.retail.order2b.manager;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.order2b.dto.response.purapply.JyPurApplyResp;
import com.iwhalecloud.retail.order2b.dto.response.purapply.PurApplyResp;
import com.iwhalecloud.retail.order2b.dto.resquest.purapply.PurApplyReq;
import com.iwhalecloud.retail.order2b.mapper.JyPurApplyMapper;

@Component
public class JyPurApplyManager {

	@Resource
    private JyPurApplyMapper jypurApplyMapper;
	//采购申请单
	public Page<JyPurApplyResp> jycgSearchApplycgsqd(PurApplyReq req) {
		Page<JyPurApplyResp> page=new Page<>(req.getPageNo(),req.getPageSize());
		Page<JyPurApplyResp> pageReport =jypurApplyMapper.jycgSearchApplycgsqd(page,req);
		return pageReport;
	}
	//采购单
	public Page<JyPurApplyResp> jycgSearchApplycgd(PurApplyReq req) {
		Page<JyPurApplyResp> page=new Page<>(req.getPageNo(),req.getPageSize());
		Page<JyPurApplyResp> pageReport =jypurApplyMapper.jycgSearchApplycgd(page,req);
		return pageReport;
	}
}
