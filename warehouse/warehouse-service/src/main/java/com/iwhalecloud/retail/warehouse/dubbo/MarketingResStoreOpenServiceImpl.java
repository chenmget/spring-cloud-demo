package com.iwhalecloud.retail.warehouse.dubbo;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.warehouse.dto.request.markres.SynMarkResStoreReq;
import com.iwhalecloud.retail.warehouse.dto.request.markresswap.*;
import com.iwhalecloud.retail.warehouse.dto.response.markresswap.QryMktInstInfoByConditionItemSwapResp;
import com.iwhalecloud.retail.warehouse.dto.response.markresswap.QryStoreMktInstInfoItemSwapResp;
import com.iwhalecloud.retail.warehouse.dto.response.markresswap.StoreInventoryQuantityItemSwapResp;
import com.iwhalecloud.retail.warehouse.dto.response.markresswap.base.QueryMarkResQueryResultsSwapResp;
import com.iwhalecloud.retail.warehouse.service.MarketingResStoreService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

@Service(timeout = 500000)
@Slf4j
public class MarketingResStoreOpenServiceImpl implements MarketingResStoreService {

    @Autowired
    private MarketingResStoreService marketingResStoreService;


    @Override
    public ResultVO syncTerminal(SyncTerminalSwapReq req) {
        log.info("MarketingResStoreOpenServiceImpl.syncTerminal req={}", JSON.toJSONString(req));
        return marketingResStoreService.syncTerminal(req);
    }

    @Override
    public ResultVO ebuyTerminal(EBuyTerminalSwapReq req) {
        log.info("MarketingResStoreOpenServiceImpl.ebuyTerminal req={}", JSON.toJSONString(req));
        return marketingResStoreService.ebuyTerminal(req);
    }

    @Override
    public ResultVO synMktInstStatus(SynMktInstStatusSwapReq req) {
        log.info("MarketingResStoreOpenServiceImpl.synMktInstStatus req={}", JSON.toJSONString(req));
        return marketingResStoreService.synMktInstStatus(req);
    }

    @Override
    public ResultVO<QueryMarkResQueryResultsSwapResp<QryStoreMktInstInfoItemSwapResp>> qryStoreMktInstInfo(QryStoreMktInstInfoSwapReq req) {
        log.info("MarketingResStoreOpenServiceImpl.qryStoreMktInstInfo req={}", JSON.toJSONString(req));
        return  marketingResStoreService.qryStoreMktInstInfo(req);
    }

    @Override
    public ResultVO<QueryMarkResQueryResultsSwapResp<QryMktInstInfoByConditionItemSwapResp>> qryMktInstInfoByCondition(QryMktInstInfoByConditionSwapReq req) {
        log.info("MarketingResStoreOpenServiceImpl.qryMktInstInfoByCondition req={}", JSON.toJSONString(req));
        return marketingResStoreService.qryMktInstInfoByCondition(req);
    }

    @Override
    public ResultVO<QueryMarkResQueryResultsSwapResp<StoreInventoryQuantityItemSwapResp>> storeInventoryQuantity(StoreInventoryQuantitySwapReq req) {
        log.info("MarketingResStoreOpenServiceImpl.storeInventoryQuantity req={}", JSON.toJSONString(req));
        return marketingResStoreService.storeInventoryQuantity(req);
    }
    @Override
    public ResultVO synMarkResStore(SynMarkResStoreReq req){
        log.info("MarketingResStoreOpenServiceImpl.synMarkResStore req={}", JSON.toJSONString(req));
        return marketingResStoreService.synMarkResStore(req);
    }
}
