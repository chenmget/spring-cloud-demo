package com.iwhalecloud.retail.partner.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.partner.dto.PartnerRelShopDTO;
import com.iwhalecloud.retail.partner.dto.PartnerShopDTO;
import com.iwhalecloud.retail.partner.dto.req.*;

import java.util.List;


public interface PartnerShopService{

//	PartnerShopDTO getPartnerShop(PartnerShopDTO request);
	PartnerShopDTO getPartnerShop(String partnerShopId);

	int updatePartnerShop(PartnerShopUpdateReq request);

	int updatePartnerShopState(PartnerShopStateUpdateReq req);

	/**
	 * 附近代理商查询
	 * @param req
	 * @return
	 */
	Page<PartnerRelShopDTO> pagePartnerNearby(PartnerShopListReq req);

	/**
	 * 分页查询查询厅店记录
	 * @param req
	 * @return
	 */
	Page<PartnerShopDTO> queryPartnerShopPage(PartnerShopQueryPageReq req);

	/**
	 * 查询查询厅店记录列表 （不分页）
	 * @param req
	 * @return
	 */
	List<PartnerShopDTO> queryPartnerShopList(PartnerShopQueryListReq req);

}