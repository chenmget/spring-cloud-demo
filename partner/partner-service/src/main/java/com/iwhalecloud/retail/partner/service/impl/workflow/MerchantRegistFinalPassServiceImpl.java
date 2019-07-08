package com.iwhalecloud.retail.partner.service.impl.workflow;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.iwhalecloud.retail.dto.ResultCodeEnum;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.partner.common.PartnerConst;
import com.iwhalecloud.retail.partner.entity.Merchant;
import com.iwhalecloud.retail.partner.manager.MerchantManager;
import com.iwhalecloud.retail.partner.service.workflow.MerchantRegistFinalPassService;
import com.iwhalecloud.retail.system.common.SystemConst;
import com.iwhalecloud.retail.system.service.UserService;
import com.iwhalecloud.retail.workflow.config.InvokeRouteServiceRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
@Service
public class MerchantRegistFinalPassServiceImpl implements MerchantRegistFinalPassService {
    @Autowired
    MerchantManager merchantManager;
    @Reference
    UserService userService;

    @Override
    public ResultVO run(InvokeRouteServiceRequest params) {
        log.info("DbsRegistFinalPassServiceImpl.run params={}", JSON.toJSONString(params));
        if (params == null) {
            log.info("DbsRegistFinalPassServiceImpl.run  params={}", JSON.toJSONString(params));
            return ResultVO.error(ResultCodeEnum.LACK_OF_PARAM);
        }
        String userId = params.getHandlerUserId();
        String merchantId = params.getBusinessId();
        //更改用户和商户状态
        Merchant merchant = new Merchant();
        merchant.setMerchantId(merchantId);
        merchant.setStatus(PartnerConst.MerchantStatusEnum.VALID.getType());
        int mcRt = merchantManager.updateMerchant(merchant);
        ResultVO userRt = userService.UpSysUserState(userId, SystemConst.ValidStatusEnum.HAVE_VALID.getCode());
        if(mcRt>0 && userRt.isSuccess())return ResultVO.success();
        return ResultVO.error();
    }
}
