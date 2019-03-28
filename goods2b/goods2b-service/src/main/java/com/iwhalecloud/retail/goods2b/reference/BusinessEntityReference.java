package com.iwhalecloud.retail.goods2b.reference;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.partner.dto.BusinessEntityDTO;
import com.iwhalecloud.retail.partner.dto.req.BusinessEntityGetReq;
import com.iwhalecloud.retail.partner.service.BusinessEntityService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * Created by z on 2019/1/30.
 */
@Slf4j
@Service
public class BusinessEntityReference {

    @Reference
    private BusinessEntityService businessEntityService;

    public BusinessEntityDTO getBusinessEntityByCode(String businessEntityCode) {

        log.info("BusinessEntityReference.getBusinessEntityByCode businessEntityCode={}",businessEntityCode);
        BusinessEntityGetReq req = new BusinessEntityGetReq();
        req.setBusinessEntityCode(businessEntityCode);

        ResultVO<BusinessEntityDTO> rv = businessEntityService.getBusinessEntity(req);
        log.info("BusinessEntityReference.getBusinessEntityByCode rv={}", JSON.toJSONString(rv));

        if (!rv.isSuccess() || rv.getResultData()==null) {
            log.warn("BusinessEntityReference.getBusinessEntityByCode get BusinessEntityDTO is null or error,businessEntityCode={}",businessEntityCode);
            return null;
        }

        return rv.getResultData();
    }
}
