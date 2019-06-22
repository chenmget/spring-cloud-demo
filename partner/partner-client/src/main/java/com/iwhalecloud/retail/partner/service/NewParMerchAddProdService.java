package com.iwhalecloud.retail.partner.service;

import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.partner.dto.req.LSSAddControlReq;

import java.util.List;

public interface NewParMerchAddProdService {

	public List<String> selectProductIdList();
	
	public ResultVO<Integer> addProd(LSSAddControlReq req);
}
