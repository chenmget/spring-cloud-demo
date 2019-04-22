package com.iwhalecloud.retail.order2b.dubbo;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.order2b.dto.response.purapply.JyPurApplyResp;
import com.iwhalecloud.retail.order2b.dto.response.purapply.PurApplyResp;
import com.iwhalecloud.retail.order2b.dto.resquest.purapply.PurApplyReq;
import com.iwhalecloud.retail.order2b.manager.JyPurApplyManager;
import com.iwhalecloud.retail.order2b.manager.PurApplyManager;
import com.iwhalecloud.retail.order2b.service.JyPurApplyService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class JyPurApplyServiceImpl implements JyPurApplyService {

	@Autowired
    private JyPurApplyManager jypurApplyManager;
	
	@Override
	public ResultVO<Page<JyPurApplyResp>> jycgSearchApply(PurApplyReq req) {
		Page<JyPurApplyResp> jypurApplyResp = jypurApplyManager.jycgSearchApply(req);
		return ResultVO.success(jypurApplyResp);
	}

}
