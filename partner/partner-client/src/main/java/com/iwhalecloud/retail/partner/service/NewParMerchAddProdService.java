package com.iwhalecloud.retail.partner.service;

import java.util.List;

import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.partner.dto.req.LSSAddControlReq;
import com.iwhalecloud.retail.partner.dto.resp.ProductIdListResp;

public interface NewParMerchAddProdService {

	public List<String> selectProductIdList();
	
	public ResultVO<Integer> addProd(LSSAddControlReq req);
}
