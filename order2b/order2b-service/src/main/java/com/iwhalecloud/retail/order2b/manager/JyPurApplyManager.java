package com.iwhalecloud.retail.order2b.manager;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.order2b.dto.response.purapply.PurApplyResp;
import com.iwhalecloud.retail.order2b.dto.resquest.purapply.PurApplyReq;
import com.iwhalecloud.retail.order2b.mapper.JyPurApplyMapper;

@Component
public class JyPurApplyManager {

	@Resource
    private JyPurApplyMapper jypurApplyMapper;
	
	public Page<PurApplyResp> jycgSearchApply(PurApplyReq req) {
		Page<PurApplyResp> page=new Page<>(req.getPageNo(),req.getPageSize());
		Page<PurApplyResp> pageReport =jypurApplyMapper.jycgSearchApply(page,req);
		return pageReport;
	}
}
