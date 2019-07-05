package com.iwhalecloud.retail.warehouse.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.warehouse.common.MarketingResConst;
import com.iwhalecloud.retail.warehouse.dto.request.ResouceInstItmsManualSyncRecAddReq;
import com.iwhalecloud.retail.warehouse.dto.request.ResouceInstItmsManualSyncRecPageReq;
import com.iwhalecloud.retail.warehouse.dto.response.ResouceInstItmsManualSyncRecListResp;
import com.iwhalecloud.retail.warehouse.manager.ResouceInstTtmsManualSyncRecManager;
import com.iwhalecloud.retail.warehouse.service.ResouceInstItemsManualSyncRecService;
import com.iwhalecloud.retail.warehouse.util.MarketingZopClientUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class ResouceInstItemsManualSyncRecServiceImpl implements ResouceInstItemsManualSyncRecService {

    @Autowired
    private ResouceInstTtmsManualSyncRecManager resouceInstTtmsManualSyncRecManager;
    @Autowired
    private MarketingZopClientUtil zopClientUtil;


    @Override
    public ResultVO<Page<ResouceInstItmsManualSyncRecListResp>> listResourceItemsManualSyncRec(ResouceInstItmsManualSyncRecPageReq req){
        return ResultVO.success(resouceInstTtmsManualSyncRecManager.listResourceItemsManualSyncRec(req));
    }

    @Override
    public ResultVO<Integer> addResourceItemsManualSyncRec(ResouceInstItmsManualSyncRecAddReq req){
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
        resouceInstTtmsManualSyncRecManager.addResourceItemsManualSyncRec(req);
        return resultVO;
    }

    @Override
    public ResultVO<Integer> updateResourceItemsManualSyncRec(ResouceInstItmsManualSyncRecAddReq req){
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
        resouceInstTtmsManualSyncRecManager.addResourceItemsManualSyncRec(req);
        return resultVO;
    }
}
