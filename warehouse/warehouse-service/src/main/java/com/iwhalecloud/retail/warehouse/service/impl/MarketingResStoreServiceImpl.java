package com.iwhalecloud.retail.warehouse.service.impl;

import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.warehouse.common.MarketingResConst;
import com.iwhalecloud.retail.warehouse.dto.request.markres.SynMarkResStoreReq;
import com.iwhalecloud.retail.warehouse.dto.request.markresswap.*;
import com.iwhalecloud.retail.warehouse.dto.response.markres.QryMktInstInfoByConditionItemResp;
import com.iwhalecloud.retail.warehouse.dto.response.markres.QryStoreMktInstInfoItemResp;
import com.iwhalecloud.retail.warehouse.dto.response.markres.StoreInventoryQuantityItemResp;
import com.iwhalecloud.retail.warehouse.dto.response.markres.base.ExcuteMarkResResultItemResp;
import com.iwhalecloud.retail.warehouse.dto.response.markres.base.QueryMarkResQueryResultsResp;
import com.iwhalecloud.retail.warehouse.dto.response.markresswap.QryMktInstInfoByConditionItemSwapResp;
import com.iwhalecloud.retail.warehouse.dto.response.markresswap.QryStoreMktInstInfoItemSwapResp;
import com.iwhalecloud.retail.warehouse.dto.response.markresswap.StoreInventoryQuantityItemSwapResp;
import com.iwhalecloud.retail.warehouse.dto.response.markresswap.base.QueryMarkResQueryResultsSwapResp;
import com.iwhalecloud.retail.warehouse.service.MarketingResStoreService;
import com.iwhalecloud.retail.warehouse.util.MarkResReqSwapUtil;
import com.iwhalecloud.retail.warehouse.util.MarkResRespSwapUtil;
import com.iwhalecloud.retail.warehouse.util.MarketingZopClientUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class MarketingResStoreServiceImpl implements MarketingResStoreService {

    @Autowired
    private MarketingZopClientUtil zopClientUtil;

    @Value("${zopServiceEnv}")
    private String zopServiceEnv;

    @Override
    public ResultVO<QueryMarkResQueryResultsSwapResp<QryStoreMktInstInfoItemSwapResp>> qryStoreMktInstInfo(QryStoreMktInstInfoSwapReq req) {
        if (req == null) {
            return ResultVO.error("请求参数为空");
        }
        if (StringUtils.isEmpty(req.getBarCode()) && StringUtils.isEmpty(req.getStoreId())) {
            return ResultVO.error("请求参数错误,串码和仓库ID必须填写一个");
        }
        ResultVO<QueryMarkResQueryResultsResp> resultVO = zopClientUtil.callQueryRest(MarketingResConst.ServiceEnum.QryStoreMktInstInfo.getCode()+zopServiceEnv,
                MarketingResConst.ServiceEnum.QryStoreMktInstInfo.getVersion(), MarkResReqSwapUtil.swapQryStoreMktInstInfoSwapReq(req),
                QryStoreMktInstInfoItemResp.class);


        return MarkResRespSwapUtil.swapQueryMarkResQueryResultsResp(resultVO);
    }

    @Override
    public ResultVO syncTerminal(SyncTerminalSwapReq req) {

        return zopClientUtil.callExcuteSimplRest(MarketingResConst.ServiceEnum.SyncTerminal.getCode()+zopServiceEnv,
                MarketingResConst.ServiceEnum.SyncTerminal.getVersion(), MarkResReqSwapUtil.swapSyncTerminalReq(req));
    }

    @Override
    public ResultVO ebuyTerminal(EBuyTerminalSwapReq req) {
        return zopClientUtil.callExcuteSimplRest(MarketingResConst.ServiceEnum.EBuyTerminal.getCode()+zopServiceEnv,
                MarketingResConst.ServiceEnum.EBuyTerminal.getVersion(), MarkResReqSwapUtil.swapEBuyTerminalReq(req));
    }

    @Override
    public ResultVO<Boolean> synMktInstStatus(SynMktInstStatusSwapReq req) {
        ResultVO<List<ExcuteMarkResResultItemResp>> resultVO = zopClientUtil.callExcuteRest(MarketingResConst.ServiceEnum.SynMktInstStatus.getCode()+zopServiceEnv,
                MarketingResConst.ServiceEnum.SynMktInstStatus.getVersion(), MarkResReqSwapUtil.swapSynMktInstStatusReq(req));
        ResultVO<Boolean> resultVOTemp = zopClientUtil.swapCallExcuteRestResultVO(resultVO);

        return resultVOTemp;


    }


    @Override
    public ResultVO<QueryMarkResQueryResultsSwapResp<QryMktInstInfoByConditionItemSwapResp>> qryMktInstInfoByCondition(QryMktInstInfoByConditionSwapReq req) {

        ResultVO<QueryMarkResQueryResultsResp> resultVO = zopClientUtil.callQueryRest(MarketingResConst.ServiceEnum.QryMktInstInfoByCondition.getCode()+zopServiceEnv,
                MarketingResConst.ServiceEnum.QryMktInstInfoByCondition.getVersion(), MarkResReqSwapUtil.swapQryMktInstInfoByConditionReq(req), QryMktInstInfoByConditionItemResp.class);
        return MarkResRespSwapUtil.swapQryMktInstInfoByCondition(resultVO);
    }

    @Override
    public ResultVO<QueryMarkResQueryResultsSwapResp<StoreInventoryQuantityItemSwapResp>> storeInventoryQuantity(StoreInventoryQuantitySwapReq req) {
        ResultVO<QueryMarkResQueryResultsResp> resultVO = zopClientUtil.callQueryRest(MarketingResConst.ServiceEnum.StoreInventoryQuantity.getCode()+zopServiceEnv,
                MarketingResConst.ServiceEnum.StoreInventoryQuantity.getVersion(), MarkResReqSwapUtil.swapStoreInventoryQuantityReq(req), StoreInventoryQuantityItemResp.class);

        return MarkResRespSwapUtil.swapStoreInventoryQuantityResp(resultVO);

    }

    @Override
    public ResultVO synMarkResStore(SynMarkResStoreReq req) {

//        return zopClientUtil.callExcuteSimplRest(MarketingResConst.ServiceEnum.SyncTerminal.getCode(),
//                MarketingResConst.ServiceEnum.SyncTerminal.getVersion(), req);
        return ResultVO.error("未提供");
    }
}
