package com.iwhalecloud.retail.order2b.dubbo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.order2b.dto.response.purapply.PurApplyResp;
import com.iwhalecloud.retail.order2b.dto.resquest.purapply.AddProductReq;
import com.iwhalecloud.retail.order2b.dto.resquest.purapply.ProcureApplyReq;
import com.iwhalecloud.retail.order2b.dto.resquest.purapply.PurApplyReq;
import com.iwhalecloud.retail.order2b.manager.PurApplyManager;
import com.iwhalecloud.retail.order2b.service.PurApplyService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class PurApplyServiceImpl implements PurApplyService {

	@Autowired
    private PurApplyManager purApplyManager;
	
	@Override
	public ResultVO<Page<PurApplyResp>> cgSearchApply(PurApplyReq req) {
		Page<PurApplyResp> purApplyResp = purApplyManager.cgSearchApply(req);
		return ResultVO.success(purApplyResp);
	}

	@Override
	@Transactional
	public void tcProcureApply(ProcureApplyReq req) {
		purApplyManager.tcProcureApply(req);
	}
	
	@Override
	@Transactional
	public void crPurApplyFile(ProcureApplyReq req) {
		purApplyManager.crPurApplyFile(req);
	}

	@Override
	@Transactional
	public void crPurApplyItem(AddProductReq req) {
		purApplyManager.crPurApplyItem(req);
	}

	@Override
	@Transactional
	public void delSearchApply(PurApplyReq req) {
		purApplyManager.delSearchApply(req);
	}

}
