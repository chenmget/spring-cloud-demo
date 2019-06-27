package com.iwhalecloud.retail.partner.service;

import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.partner.dto.req.LSSAddControlReq;

import java.util.List;

public interface NewParMerchAddProdService {

	/**
	 * 编辑选择零售商时设置默认赋权：a、所有机型经营权限； b、所有地市和所有机型的调拨权限
	 * @param req
	 * @return
	 */
	ResultVO<Integer> addProd(LSSAddControlReq req);

	/**
	 * 零售商 设置默认赋权：a、所有机型经营权限； b、所有地市和所有机型的调拨权限
	 * @param merchantId
	 * @return
	 */
	ResultVO<Integer> addRetailerDefaultRule(String merchantId);
}
