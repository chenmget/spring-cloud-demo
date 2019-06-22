package com.iwhalecloud.retail.promo.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.promo.common.PromoConst;
import com.iwhalecloud.retail.promo.constant.Constant;
import com.iwhalecloud.retail.promo.dto.req.ActHistoryPurChaseAddReq;
import com.iwhalecloud.retail.promo.dto.req.ActHistoryPurChaseUpReq;
import com.iwhalecloud.retail.promo.dto.req.HistoryPurchaseQueryExistReq;
import com.iwhalecloud.retail.promo.entity.HistoryPurchase;
import com.iwhalecloud.retail.promo.manager.HistoryPurchaseManager;
import com.iwhalecloud.retail.promo.service.HistoryPurchaseService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Slf4j
@Service
public class HistoryPurchaseServiceImpl implements HistoryPurchaseService {

    @Autowired
    private HistoryPurchaseManager historyPurchaseManager;
    @Autowired
    private Constant constant;
    @Override
    public ResultVO addActHistroyPurchase(List<ActHistoryPurChaseAddReq> req) {
        log.info("HistoryPurchaseServiceImpl.addActHistoryPurchase req={}", JSON.toJSON(req));
        List<HistoryPurchase> historyPurchaseList = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(req)) {
            for (ActHistoryPurChaseAddReq actHistoryPurChaseAddReq : req) {
                HistoryPurchase historyPurchase = new HistoryPurchase();
                historyPurchase.setActivityTypeCode(actHistoryPurChaseAddReq.getActivityType());
                BeanUtils.copyProperties(actHistoryPurChaseAddReq, historyPurchase);
                historyPurchase.setGmtCreate(new Date());
                historyPurchase.setGmtModified(new Date());
                historyPurchase.setIsDeleted(PromoConst.IsDelete.IS_DELETE_CD_0.getCode());
                historyPurchaseList.add(historyPurchase);
            }
            log.info("HistoryPurchaseServiceImpl.addActHistoryPurchase historyPurchaseList={}", JSON.toJSON(historyPurchaseList));
            historyPurchaseManager.saveBatch(historyPurchaseList);
        }
        return ResultVO.success();
    }


    @Override
    public ResultVO<Boolean> queryHistoryPurchaseQueryIsExist(HistoryPurchaseQueryExistReq req) {
        log.info("HistoryPurchaseServiceImpl.queryHistoryPurchaseQueryIsExist req={}", req);
        HistoryPurchase historyPurchase = new HistoryPurchase();
        BeanUtils.copyProperties(req, historyPurchase);
        log.info("HistoryPurchaseServiceImpl.queryHistoryPurchaseQueryIsExist historyPurchase={}", historyPurchase.toString());
        int rspNum = historyPurchaseManager.queryHistoryPurchaseQueryIsExist(historyPurchase);
        log.info("HistoryPurchaseServiceImpl.queryHistoryPurchaseQueryIsExist rspNum={}", rspNum);
        if (rspNum > 0) {
            return ResultVO.success(true);
        }
        return ResultVO.success(false);
    }

    @Override
    public ResultVO updateHistroyPurchase(ActHistoryPurChaseUpReq req) {
        log.info("HistoryPurchaseServiceImpl.updateHistroyPurchase req={}", JSON.toJSONString(req));
        if (StringUtils.isEmpty(req.getOrderId())){
            return ResultVO.error(constant.getNoOrderId());
        }
        req.setGmtCreate(new Date());
        HistoryPurchase historyPurchase = new HistoryPurchase();
        BeanUtils.copyProperties(req, historyPurchase);
        int resultNum = historyPurchaseManager.updateHistroyPurchase(historyPurchase);
        log.info("HistoryPurchaseServiceImpl.updateHistoryPurchase historyPurchaseManager.updateHistoryPurchase req={} resultNum={}", JSON.toJSONString(historyPurchase), resultNum);
        if (resultNum > 0) {
            return ResultVO.successMessage(constant.getUpdateSuccess());
        }
        return ResultVO.error(constant.getUpdateFaile());
    }
}