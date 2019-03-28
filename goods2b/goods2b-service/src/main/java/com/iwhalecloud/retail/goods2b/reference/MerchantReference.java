package com.iwhalecloud.retail.goods2b.reference;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.partner.dto.MerchantDTO;
import com.iwhalecloud.retail.partner.dto.req.MerchantGetReq;
import com.iwhalecloud.retail.partner.dto.req.MerchantListReq;
import com.iwhalecloud.retail.partner.service.MerchantService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by z on 2019/1/29.
 */
@Service
@Slf4j
public class MerchantReference {


    @Reference
    private MerchantService merchantService;

    /**
     * 获取商家
     * @param merchantId
     * @return
     */
    public MerchantDTO getMerchantById(String merchantId) {

        log.info("MerchantReference.getMerchantById merchantId={}",merchantId);

        MerchantGetReq merchantGetReq = new MerchantGetReq();
        merchantGetReq.setMerchantId(merchantId);
        ResultVO<MerchantDTO> merchantDTOResultVO = merchantService.getMerchant(merchantGetReq);
        log.info("MerchantReference.getMerchantById merchantDTOResultVO={}", JSON.toJSONString(merchantDTOResultVO));
        if (!merchantDTOResultVO.isSuccess()) {
            log.error("MerchantReference.getMerchantById get MerchantDTO is null or error,merchantId={}", merchantId);
            return null;
        }
        return merchantDTOResultVO.getResultData();
    }

    /**
     * 获取商家
     * @param merchantCode 商家编码
     * @return
     */
    public MerchantDTO getMerchantByCode(String merchantCode) {

        log.info("MerchantReference.getMerchantByCode merchantCode={}",merchantCode);
        ResultVO<MerchantDTO> merchantDTOResultVO = merchantService.getMerchantByCode(merchantCode);
        log.info("MerchantReference.getMerchantByCode merchantDTOResultVO={}", JSON.toJSONString(merchantDTOResultVO));
        if (!merchantDTOResultVO.isSuccess()) {
            log.error("MerchantReference.getMerchantByCode get MerchantDTO is null or error,merchantCode={}", merchantCode);
            return null;
        }
        return merchantDTOResultVO.getResultData();
    }

    /**
     * 根据商家编码集合获取商家集合
     * @param merchantCodes 商家code集合
     * @param businessEntityCodes 经营主体code集合
     * @return
     */
    public ResultVO<List<MerchantDTO>> queryMerchant(List<String> merchantCodes,List<String> businessEntityCodes) {
        log.info("MerchantReference.queryMerchantByMerchantCodes merchantCodes={}",JSON.toJSONString(merchantCodes));
        MerchantListReq req = new MerchantListReq();
        req.setMerchantCodeList(merchantCodes);
        req.setBusinessEntityCodeList(businessEntityCodes);
        ResultVO<List<MerchantDTO>> rv = merchantService.listMerchant(req);
        log.info("MerchantReference.queryMerchantByMerchantCodes rv={}", JSON.toJSONString(rv));
        if (!rv.isSuccess()) {
            log.error("MerchantReference.queryMerchantByMerchantCodes get listMerchant error,rv={}", JSON.toJSONString(rv));
        }
        return rv;
    }

}
