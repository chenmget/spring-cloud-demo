package com.iwhalecloud.retail.warehouse.service.impl;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.alibaba.dubbo.config.annotation.Reference;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.partner.dto.MerchantDTO;
import com.iwhalecloud.retail.partner.dto.req.MerchantGetReq;
import com.iwhalecloud.retail.partner.service.MerchantService;
import com.iwhalecloud.retail.system.dto.CommonRegionDTO;
import com.iwhalecloud.retail.system.service.CommonRegionService;
import com.iwhalecloud.retail.warehouse.dto.request.ResourceInstsTrackDetailGetReq;
import com.iwhalecloud.retail.warehouse.dto.response.ResourceInstTrackDetailListResp;
import com.iwhalecloud.retail.warehouse.manager.ResouceInstTrackDetailManager;
import com.iwhalecloud.retail.warehouse.service.ResouceInstTrackDetailService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class ResouceInstTrackDetailServiceImpl implements ResouceInstTrackDetailService {

    @Autowired
    private ResouceInstTrackDetailManager resouceInstTrackDetailManager;
    @Reference
    private MerchantService merchantService;
    @Reference
    private CommonRegionService commonRegionService;

    @Override
    public ResultVO<List<ResourceInstTrackDetailListResp>> getResourceInstTrackDetailByNbr(ResourceInstsTrackDetailGetReq req){
        List<ResourceInstTrackDetailListResp> detailList = resouceInstTrackDetailManager.getResourceInstTrackDetailByNbr(req);
        if (CollectionUtils.isEmpty(detailList)) {
            return ResultVO.success();
        }

        for (ResourceInstTrackDetailListResp resp : detailList) {
            String sourceMerchantId = resp.getSourceMerchantId();
            String targetMerchantId = resp.getTargetMerchantId();
            if (StringUtils.isNotBlank(sourceMerchantId)) {
                MerchantGetReq merchantGetReq = new MerchantGetReq();
                merchantGetReq.setMerchantId(sourceMerchantId);
                ResultVO<MerchantDTO> merchantResultVO = merchantService.getMerchant(merchantGetReq);
                String sourceMerchantName = (merchantResultVO.isSuccess() && merchantResultVO.getResultData() != null) ? merchantResultVO.getResultData().getMerchantName() : "";
                resp.setSourceMerchantName(sourceMerchantName);
            }
            if (StringUtils.isNotBlank(targetMerchantId)) {
                MerchantGetReq merchantGetReq = new MerchantGetReq();
                merchantGetReq.setMerchantId(targetMerchantId);
                ResultVO<MerchantDTO> merchantResultVO = merchantService.getMerchant(merchantGetReq);
                String targetMerchantName = (merchantResultVO.isSuccess() && merchantResultVO.getResultData() != null) ? merchantResultVO.getResultData().getMerchantName() : "";
                resp.setTargetMerchantName(targetMerchantName);
            }

            String sourceLanId = resp.getSourceLanId();
            String targetLanId = resp.getTargetLanId();
            if (StringUtils.isNotBlank(sourceLanId)) {
                ResultVO<CommonRegionDTO> commonRegionResultVO = commonRegionService.getCommonRegionById(sourceLanId);
                String sourceRegionName = (commonRegionResultVO.isSuccess() && commonRegionResultVO.getResultData() != null) ? commonRegionResultVO.getResultData().getRegionName() : "";
                resp.setSourceLanName(sourceRegionName);
            }
            if (StringUtils.isNotBlank(targetLanId)) {
                ResultVO<CommonRegionDTO> commonRegionResultVO = commonRegionService.getCommonRegionById(sourceLanId);
                String targetRegionName = (commonRegionResultVO.isSuccess() && commonRegionResultVO.getResultData() != null) ? commonRegionResultVO.getResultData().getRegionName() : "";
                resp.setTargetLanName(targetRegionName);
            }
        }
        return ResultVO.success(detailList);
    }
}
