package com.iwhalecloud.retail.partner.manager;

import com.iwhalecloud.retail.partner.dto.req.LSSAddControlReq;
import com.iwhalecloud.retail.partner.mapper.NewParMerchAddProdMapper;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

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
