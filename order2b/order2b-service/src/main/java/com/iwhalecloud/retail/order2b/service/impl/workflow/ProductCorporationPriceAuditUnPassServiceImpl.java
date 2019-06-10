package com.iwhalecloud.retail.order2b.service.impl.workflow;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.iwhalecloud.retail.dto.ResultCodeEnum;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.order2b.consts.PurApplyConsts;
import com.iwhalecloud.retail.order2b.dto.resquest.purapply.ProdProductChangeDetail;
import com.iwhalecloud.retail.order2b.dto.resquest.purapply.ProdProductChangeReq;
import com.iwhalecloud.retail.order2b.manager.PurApplyManager;
import com.iwhalecloud.retail.order2b.service.workflow.ProductCorporationPriceAuditUnPassService;
import com.iwhalecloud.retail.workflow.config.InvokeRouteServiceRequest;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ProductCorporationPriceAuditUnPassServiceImpl implements ProductCorporationPriceAuditUnPassService {
	
	@Autowired
    private PurApplyManager purApplyManager;
	
	 @Override
	 public ResultVO run(InvokeRouteServiceRequest params) {
		 log.info("ProductCorporationPriceAuditPassServiceImpl.run params={}", JSON.toJSONString(params));
	        if (params == null || StringUtils.isEmpty(params.getBusinessId())) {//拿到业务ID（batch_id）
	            return ResultVO.error(ResultCodeEnum.LACK_OF_PARAM);
	        }
	        List<ProdProductChangeDetail> listProdProductChangeDetail = purApplyManager.selectProdProductChangeDetail(params.getBusinessId());//查出修改详情
	        for(int i=0;i<listProdProductChangeDetail.size();i++) {
	        	ProdProductChangeDetail prodProductChangeDetail = listProdProductChangeDetail.get(i);
	        	String changeId = prodProductChangeDetail.getChangeId();//得到变更流水ID
	        	//审核通过，把PROD_PRODUCT_CHANGE(产品变更记录表)表的状态更改
	            ProdProductChangeReq prodProductChangeReq = new ProdProductChangeReq();
	            prodProductChangeReq.setChangeId(changeId);
	            prodProductChangeReq.setAuditState(PurApplyConsts.AUDIT_STATE_NO_PASS);
	            int j = purApplyManager.updateProductChange(prodProductChangeReq);//修改change表的状态
	        	if(j ==0) {
	        		return ResultVO.error();
	        	}
	        }
	        
	        return ResultVO.success();
	     
	 }
}
