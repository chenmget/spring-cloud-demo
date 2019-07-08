package com.iwhalecloud.retail.partner.service.impl.workflow;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.iwhalecloud.retail.dto.ResultCodeEnum;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.partner.common.PartnerConst;
import com.iwhalecloud.retail.partner.dto.req.MerchantUpdateReq;
import com.iwhalecloud.retail.partner.service.MerchantService;
import com.iwhalecloud.retail.partner.service.workflow.FactoryMerchantSavePassService;
import com.iwhalecloud.retail.workflow.config.InvokeRouteServiceRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@Service
public class FactoryMerchantSavePassServiceImpl implements FactoryMerchantSavePassService {
    @Autowired
    private MerchantService merchantService;

    @Override
    public ResultVO run(InvokeRouteServiceRequest params) {
        log.info("FactoryMerchantPermissionApplyPassServiceImpl.run params={}", JSON.toJSONString(params));
        if (params == null) {
            return ResultVO.error(ResultCodeEnum.LACK_OF_PARAM);
        }
        String merchantId = params.getBusinessId();
        MerchantUpdateReq merchantUpdateReq=new MerchantUpdateReq();
        merchantUpdateReq.setMerchantId(merchantId);
        //商家状态变为有效
        merchantUpdateReq.setStatus(PartnerConst.MerchantStatusEnum.VALID.getType());
        return merchantService.updateMerchant(merchantUpdateReq);
    }
}
