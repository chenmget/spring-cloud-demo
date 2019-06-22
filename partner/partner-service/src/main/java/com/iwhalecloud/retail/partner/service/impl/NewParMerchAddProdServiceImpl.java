package com.iwhalecloud.retail.partner.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.google.common.collect.Lists;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.partner.dto.req.LSSAddControlReq;
import com.iwhalecloud.retail.partner.entity.MerchantRules;
import com.iwhalecloud.retail.partner.manager.MerchantRulesManager;
import com.iwhalecloud.retail.partner.manager.NewParMerchAddProdManager;
import com.iwhalecloud.retail.partner.service.NewParMerchAddProdService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Slf4j
@Service
public class NewParMerchAddProdServiceImpl implements NewParMerchAddProdService {
	
	@Autowired
    private NewParMerchAddProdManager newParMerchAddProdManager;
	
	@Autowired
    private MerchantRulesManager merchantRulesManager;
	
	@Override
	public List<String> selectProductIdList(){
		return newParMerchAddProdManager.selectProductIdList();
	}
	
	@Override
	@Transactional
	public ResultVO<Integer> addProd(LSSAddControlReq req) {
//		newParMerchAddProdManager.addProd(req);
		log.info("NewParMerchAddProdServiceImpl.addProd(), 入参LSSAddControlReq={} ", req);
        int resultInt = 0;
        if (!CollectionUtils.isEmpty(req.getTargetIdList())) {
            // 批量插入
            List<MerchantRules> merchantRulesList = Lists.newArrayList();
            for (String targetId : req.getTargetIdList()) {
                MerchantRules merchantRules = new MerchantRules();
                BeanUtils.copyProperties(req, merchantRules);
                merchantRules.setTargetId(targetId);
                merchantRulesList.add(merchantRules);
//                resultInt =  resultInt + merchantRulesManager.insert(merchantRules);
            }
            if (merchantRulesManager.saveBatch(merchantRulesList)) {
                // 保存成功
                resultInt = 1;
            }
        }
        log.info("NewParMerchAddProdServiceImpl.addProd(), 出参resultInt={} ", resultInt);
        if (resultInt <= 0) {
            return ResultVO.error("新增的零售商赋权失败");
        }
        return ResultVO.success(resultInt);
	}

}
