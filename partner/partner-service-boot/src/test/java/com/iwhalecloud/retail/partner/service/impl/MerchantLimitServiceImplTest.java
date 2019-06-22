package com.iwhalecloud.retail.partner.service.impl;

import com.alibaba.fastjson.JSON;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.partner.PartnerServiceApplication;
import com.iwhalecloud.retail.partner.dto.req.MerchantLimitSaveReq;
import com.iwhalecloud.retail.partner.dto.req.MerchantLimitUpdateReq;
import com.iwhalecloud.retail.partner.service.MerchantLimitService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author wenlong.zhong
 * @date 2019/3/21
 */
@SpringBootTest(classes = PartnerServiceApplication.class)
@RunWith(SpringRunner.class)
@Slf4j
public class MerchantLimitServiceImplTest {

    @Autowired
    private MerchantLimitService merchantLimitService;

    @Test
    public void saveMerchantLimit() {
        MerchantLimitSaveReq req = new MerchantLimitSaveReq();
        req.setMerchantId("1");
        req.setMaxSerialNum(11L);
        ResultVO resultVO = merchantLimitService.saveMerchantLimit(req);
        log.info("MerchantLimitServiceImplTest.saveMerchantLimit resp={}", JSON.toJSON(resultVO.getResultData()));
    }


    @Test
    public void updateMerchantLimit() {
        MerchantLimitUpdateReq req = new MerchantLimitUpdateReq();
        req.setMerchantId("1");
        req.setSerialNumUsed(11L);
        ResultVO resultVO = merchantLimitService.updateMerchantLimit(req);
        log.info("MerchantLimitServiceImplTest.saveMerchantLimit resp={}", JSON.toJSON(resultVO.getResultData()));
    }

    @Test
    public void getMerchantLimit() {
        String merchantId = "1";
        ResultVO resultVO = merchantLimitService.getMerchantLimit(merchantId);
        log.info("MerchantLimitServiceImplTest.getMerchantLimit resp={}", JSON.toJSON(resultVO.getResultData()));
    }

}

