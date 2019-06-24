package com.iwhalecloud.retail.order2b.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.order2b.dto.response.purapply.JyPurApplyResp;
import com.iwhalecloud.retail.order2b.dto.resquest.purapply.PurApplyReq;

/**
 * 
 * @author lws
 * @date 2019-04-22
 */
public interface JyPurApplyService {

	public ResultVO<Page<JyPurApplyResp>> jycgSearchApply(PurApplyReq req) ;
	
}
