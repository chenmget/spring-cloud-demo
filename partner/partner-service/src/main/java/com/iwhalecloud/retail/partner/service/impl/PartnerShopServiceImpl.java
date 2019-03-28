package com.iwhalecloud.retail.partner.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.partner.dto.PartnerRelShopDTO;
import com.iwhalecloud.retail.partner.dto.PartnerShopDTO;
import com.iwhalecloud.retail.partner.dto.req.*;
import com.iwhalecloud.retail.partner.manager.PartnerShopManager;
import com.iwhalecloud.retail.partner.service.PartnerShopService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Component("partnerShopService")
@Slf4j
@Service
public class PartnerShopServiceImpl implements PartnerShopService {

    @Autowired
    private PartnerShopManager partnerShopManager;

	@Override
	public PartnerShopDTO getPartnerShop(String partnerShopId) {
		return partnerShopManager.selectOne(partnerShopId);
	}

	@Override
	public int updatePartnerShop(PartnerShopUpdateReq request) {
		int total= partnerShopManager.update(request);
        return total;
	}

	@Override
	public int updatePartnerShopState(PartnerShopStateUpdateReq req) {
		int total= partnerShopManager.updatePartnerShopState(req);
        return total;
	}

	@Override
	public Page<PartnerRelShopDTO> pagePartnerNearby(PartnerShopListReq req) {
		Page<PartnerRelShopDTO> partnerPage = partnerShopManager.pagePartnerNearby(req);
		List<PartnerRelShopDTO> list = partnerPage.getRecords();
		for(PartnerRelShopDTO shopDTO:list){
			String distance = "";
			Double d =Double.valueOf(shopDTO.getDistance());
			if(d < 1){
				d = d*1000;
				distance = d + "m";
			}else{
				BigDecimal dis = new BigDecimal(String.valueOf(d)).setScale(3,RoundingMode.HALF_DOWN);;
				dis.setScale(2,BigDecimal.ROUND_HALF_DOWN);
				distance = dis + "km";
			}
			shopDTO.setDistance(distance);
		}
		return partnerPage;
	}

	@Override
	public Page<PartnerShopDTO> queryPartnerShopPage(PartnerShopQueryPageReq req) {
		return partnerShopManager.queryPartnerShopPage(req);
	}

	@Override
	public List<PartnerShopDTO> queryPartnerShopList(PartnerShopQueryListReq req) {
		return partnerShopManager.queryPartnerShopList(req);
	}


}