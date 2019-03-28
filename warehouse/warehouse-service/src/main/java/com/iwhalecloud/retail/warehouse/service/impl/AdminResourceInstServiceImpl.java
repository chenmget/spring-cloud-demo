package com.iwhalecloud.retail.warehouse.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.partner.dto.MerchantDTO;
import com.iwhalecloud.retail.partner.service.MerchantService;
import com.iwhalecloud.retail.warehouse.busiservice.ResourceInstService;
import com.iwhalecloud.retail.warehouse.common.ResourceConst;
import com.iwhalecloud.retail.warehouse.constant.Constant;
import com.iwhalecloud.retail.warehouse.dto.request.AdminResourceInstDelReq;
import com.iwhalecloud.retail.warehouse.dto.request.ResourceInstAddReq;
import com.iwhalecloud.retail.warehouse.dto.request.ResourceInstListReq;
import com.iwhalecloud.retail.warehouse.dto.request.StoreGetStoreIdReq;
import com.iwhalecloud.retail.warehouse.dto.response.ResourceInstAddResp;
import com.iwhalecloud.retail.warehouse.dto.response.ResourceInstListResp;
import com.iwhalecloud.retail.warehouse.service.AdminResourceInstService;
import com.iwhalecloud.retail.warehouse.service.ResouceStoreService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class AdminResourceInstServiceImpl implements AdminResourceInstService {
    @Autowired
    private ResourceInstService resourceInstService;

    @Reference
    private MerchantService merchantService;

    @Reference
    private ResouceStoreService resouceStoreService;

    @Autowired
    private Constant constant;

    @Override
    public ResultVO<Page<ResourceInstListResp>> getResourceInstList(ResourceInstListReq req) {
        log.info("AdminResourceInstServiceImpl.getResourceInstList req={}", JSON.toJSONString(req));
        return resourceInstService.getResourceInstList(req);
    }

    @Override
    public ResultVO<ResourceInstAddResp> addResourceInst(ResourceInstAddReq req) {
        log.info("AdminResourceInstServiceImpl.addResourceInst req={}", JSON.toJSONString(req));
        String merchantId = req.getMerchantId();
        ResultVO<MerchantDTO> merchantDTOResultVO = merchantService.getMerchantById(merchantId);
        if (!merchantDTOResultVO.isSuccess() || null == merchantDTOResultVO.getResultData()) {
            return ResultVO.error(constant.getCannotGetMerchantMsg());
        } else {
            MerchantDTO merchantDTO = merchantDTOResultVO.getResultData();
            req.setMerchantId(merchantDTO.getMerchantId());
            req.setMerchantType(merchantDTO.getMerchantType());
            req.setMerchantName(merchantDTO.getMerchantName());
            req.setMerchantCode(merchantDTO.getMerchantCode());
            req.setLanId(merchantDTO.getLanId());
            req.setRegionId(merchantDTO.getCity());
        }
        // 获取仓库
        StoreGetStoreIdReq storeGetStoreIdReq = new StoreGetStoreIdReq();
        storeGetStoreIdReq.setStoreSubType(ResourceConst.STORE_SUB_TYPE.STORE_TYPE_TERMINAL.getCode());
        storeGetStoreIdReq.setMerchantId(merchantId);
        String mktResStoreId = resouceStoreService.getStoreId(storeGetStoreIdReq);
        log.info("RetailerResourceInstMarketServiceImpl.addResourceInstByGreenChannel resouceStoreService.getStoreId req={},resp={}", JSON.toJSONString(storeGetStoreIdReq), mktResStoreId);
        if (StringUtils.isBlank(mktResStoreId)) {
            return ResultVO.error(constant.getCannotGetStoreMsg());
        }
        req.setDestStoreId(mktResStoreId);
        req.setMktResStoreId(ResourceConst.NULL_STORE_ID);
        req.setSourceType(merchantDTOResultVO.getResultData().getMerchantType());
        return resourceInstService.addResourceInst(req);
    }

    @Override
    public ResultVO updateResourceInstByIds(AdminResourceInstDelReq req) {
        log.info("AdminResourceInstServiceImpl.updateResourceInstByIds req={}", JSON.toJSONString(req));
        return resourceInstService.updateResourceInstByIds(req);
    }
}
