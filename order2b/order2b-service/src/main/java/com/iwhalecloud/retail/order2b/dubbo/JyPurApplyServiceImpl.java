package com.iwhalecloud.retail.order2b.dubbo;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.order2b.dto.response.purapply.JyPurApplyResp;
import com.iwhalecloud.retail.order2b.dto.response.purapply.WfTaskResp;
import com.iwhalecloud.retail.order2b.dto.resquest.purapply.PurApplyReq;
import com.iwhalecloud.retail.order2b.manager.JyPurApplyManager;
import com.iwhalecloud.retail.order2b.service.JyPurApplyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Service
@Slf4j
public class JyPurApplyServiceImpl implements JyPurApplyService {

	@Autowired
    private JyPurApplyManager jypurApplyManager;
	
	@Override
	public ResultVO<Page<JyPurApplyResp>> jycgSearchApply(PurApplyReq req) {
		Page<JyPurApplyResp> jypurApplyResp = jypurApplyManager.jycgSearchApply(req);
		
		List<JyPurApplyResp> list = jypurApplyResp.getRecords();
		
		for(int i=0;i<list.size();i++){
			JyPurApplyResp jyPurApplyResp = list.get(i);
			String applyId = jyPurApplyResp.getApplyId();
			WfTaskResp wfTaskResp = jypurApplyManager.getTaskItemId(applyId);
			
			jyPurApplyResp.setTaskId(wfTaskResp.getTaskId());
			jyPurApplyResp.setTaskItemId(wfTaskResp.getTaskItemId());
			
		}
		return ResultVO.success(jypurApplyResp);
	}

}
