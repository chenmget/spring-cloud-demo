package com.iwhalecloud.retail.partner.manager;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.iwhalecloud.retail.partner.dto.req.LSSAddControlReq;
import com.iwhalecloud.retail.partner.dto.resp.ProductIdListResp;
import com.iwhalecloud.retail.partner.mapper.NewParMerchAddProdMapper;

@Component
public class NewParMerchAddProdManager {

	@Resource
    private NewParMerchAddProdMapper newParMerchAddProdMapper;
	
	public List<String> selectProductIdList(){
		return newParMerchAddProdMapper.selectProductIdList();
	}
	
	public void addProd(LSSAddControlReq req) {
		newParMerchAddProdMapper.addProd(req);
		
	}
	
}
