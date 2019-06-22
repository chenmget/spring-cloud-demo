package com.iwhalecloud.retail.order2b.manager;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.order2b.dto.response.purapply.JyPurApplyResp;
import com.iwhalecloud.retail.order2b.dto.response.purapply.WfTaskResp;
import com.iwhalecloud.retail.order2b.dto.resquest.purapply.PurApplyReq;
import com.iwhalecloud.retail.order2b.mapper.JyPurApplyMapper;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class JyPurApplyManager {

	@Resource
    private JyPurApplyMapper jypurApplyMapper;
	
	public WfTaskResp getTaskItemId(String applyId){
		return jypurApplyMapper.getTaskItemId(applyId);
	}
	
	public Page<JyPurApplyResp> jycgSearchApply(PurApplyReq req) {
		Page<JyPurApplyResp> page=new Page<>(req.getPageNo(),req.getPageSize());
		Page<JyPurApplyResp> pageReport =jypurApplyMapper.jycgSearchApply(page,req);
		return pageReport;
	}
}
