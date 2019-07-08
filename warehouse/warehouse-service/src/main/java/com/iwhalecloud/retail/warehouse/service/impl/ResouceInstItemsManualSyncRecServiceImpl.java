package com.iwhalecloud.retail.warehouse.service.impl;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.goods2b.dto.resp.BrandUrlResp;
import com.iwhalecloud.retail.goods2b.dto.resp.TypeResp;
import com.iwhalecloud.retail.goods2b.service.dubbo.BrandService;
import com.iwhalecloud.retail.goods2b.service.dubbo.TypeService;
import com.iwhalecloud.retail.system.dto.CommonRegionDTO;
import com.iwhalecloud.retail.system.service.CommonRegionService;
import com.iwhalecloud.retail.warehouse.common.MarketingResConst;
import com.iwhalecloud.retail.warehouse.constant.Constant;
import com.iwhalecloud.retail.warehouse.dto.request.ResouceInstItmsManualSyncRecAddReq;
import com.iwhalecloud.retail.warehouse.dto.request.ResouceInstItmsManualSyncRecPageReq;
import com.iwhalecloud.retail.warehouse.dto.response.ResouceInstItmsManualSyncRecListResp;
import com.iwhalecloud.retail.warehouse.manager.ResouceInstTtmsManualSyncRecManager;
import com.iwhalecloud.retail.warehouse.service.ResouceInstItemsManualSyncRecService;
import com.iwhalecloud.retail.warehouse.util.MarketingZopClientUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("resouceInstItemsManualSyncRecService")
@Slf4j
public class ResouceInstItemsManualSyncRecServiceImpl implements ResouceInstItemsManualSyncRecService {

    @Autowired
    private ResouceInstTtmsManualSyncRecManager resouceInstTtmsManualSyncRecManager;
    @Autowired
    private MarketingZopClientUtil zopClientUtil;
    @Reference
    private TypeService typeService;
    @Reference
    private BrandService brandService;
    @Autowired
    private Constant constant;
    @Reference
    private CommonRegionService commonRegionService;


    @Override
    public ResultVO<Page<ResouceInstItmsManualSyncRecListResp>> listResourceItemsManualSyncRec(ResouceInstItmsManualSyncRecPageReq req) {
        Page<ResouceInstItmsManualSyncRecListResp> page = resouceInstTtmsManualSyncRecManager.listResourceItemsManualSyncRec(req);
        List<ResouceInstItmsManualSyncRecListResp> list = page.getRecords();
        log.info("ResouceInstItemsManualSyncRecServiceImpl.listResourceItemsManualSyncRec req={}, size={}", JSON.toJSONString(req), list.size());
        if (CollectionUtils.isEmpty(list)) {
            return ResultVO.success(page);
        }
        for (ResouceInstItmsManualSyncRecListResp resp : list) {
            String brandId = resp.getBrandId();
            ResultVO<BrandUrlResp> brandVO = brandService.getBrandByBrandId(brandId);
            if (brandVO.isSuccess() && null != brandVO.getResultData()) {
                resp.setBrandName(brandVO.getResultData().getName());
            }
            String typeId = resp.getProductType();
            ResultVO<TypeResp> typeVO = typeService.selectById(typeId);
            if (typeVO.isSuccess() && null != typeVO.getResultData()) {
                resp.setTypeName(typeVO.getResultData().getTypeName());
            }
            String destLanId = resp.getDestLanId();
            ResultVO<CommonRegionDTO> commonRegionVO = commonRegionService.getCommonRegionById(destLanId);
            if (commonRegionVO.isSuccess() && null != commonRegionVO.getResultData()) {
                resp.setLanName(commonRegionVO.getResultData().getRegionName());
            }
            if (StringUtils.isNotBlank(resp.getOrigLanId())) {
                resp.setOptionType(constant.getUpdate());
            } else {
                resp.setOptionType(constant.getAdd());
            }

        }
        return ResultVO.success(page);
    }

    @Override
    public ResultVO addResourceItemsManualSyncRec(ResouceInstItmsManualSyncRecAddReq req){
        StringBuffer params = new StringBuffer();
        params.append("city_code=").append(req.getDestLanId()).append("#warehouse=").append("#source=2").
                append("#factory=网络终端");
        Map request = new HashMap<>();
        request.put("deviceId", req.getMktResInstNbr());
        request.put("userName", req.getCreateStaff());
        request.put("code", MarketingResConst.ITME_METHOD.ADD);
        request.put("params", params.toString());
        ResultVO resultVO = zopClientUtil.callExcuteNoticeITMS(MarketingResConst.ServiceEnum.OrdInventoryChange.getCode(), MarketingResConst.ServiceEnum.OrdInventoryChange.getVersion(), request);
        log.info("ResouceInstItemsManualSyncRecServiceImpl.addResourceItemsManualSyncRec req={}, resultVO={}", JSON.toJSONString(req), JSON.toJSONString(resultVO));
        req.setStatusCd(MarketingResConst.ResultEnum.SUCESS.getCode());
        if (!resultVO.isSuccess()) {
            req.setStatusCd(MarketingResConst.ResultEnum.FAIL.getCode());
        } else {
            req.setStatusCd(MarketingResConst.ResultEnum.SUCESS.getCode());
        }
        Integer sucessNum = resouceInstTtmsManualSyncRecManager.addResourceItemsManualSyncRec(req);
        log.info("ResouceInstItemsManualSyncRecServiceImpl.addResourceItemsManualSyncRec sucessNum={}", sucessNum);
        return resultVO;
    }

    @Override
    public ResultVO updateResourceItemsManualSyncRec(ResouceInstItmsManualSyncRecAddReq req){
        StringBuffer params = new StringBuffer();
        params.append("city_code=").append(req.getDestLanId()).append("#warehouse=").append("#source=2").
                append("#factory=网络终端");
        Map request = new HashMap<>();
        request.put("deviceId", req.getMktResInstNbr());
        request.put("userName", req.getCreateStaff());
        request.put("code", MarketingResConst.ITME_METHOD.UPDATE);
        request.put("params", params.toString());
        ResultVO resultVO = zopClientUtil.callExcuteNoticeITMS(MarketingResConst.ServiceEnum.OrdInventoryChange.getCode(), MarketingResConst.ServiceEnum.OrdInventoryChange.getVersion(), request);
        log.info("ResouceInstItemsManualSyncRecServiceImpl.updateResourceItemsManualSyncRec req={}, resultVO={}", JSON.toJSONString(req), JSON.toJSONString(resultVO));
        req.setStatusCd(MarketingResConst.ResultEnum.SUCESS.getCode());
        if (!resultVO.isSuccess()) {
            req.setStatusCd(MarketingResConst.ResultEnum.FAIL.getCode());
        } else {
            req.setStatusCd(MarketingResConst.ResultEnum.SUCESS.getCode());
        }
        Integer sucessNum = resouceInstTtmsManualSyncRecManager.addResourceItemsManualSyncRec(req);
        log.info("ResouceInstItemsManualSyncRecServiceImpl.addResourceItemsManualSyncRec sucessNum={}", sucessNum);
        return resultVO;
    }

    @Override
    public ResultVO<ResouceInstItmsManualSyncRecListResp> getDestLanIdByNbr(String mktResInstNbr){
        return ResultVO.success(resouceInstTtmsManualSyncRecManager.getDestLanIdByNbr(mktResInstNbr));
    }
}
