package com.iwhalecloud.retail.warehouse.dubbo;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.partner.service.MerchantService;
import com.iwhalecloud.retail.warehouse.busiservice.ResouceInstTrackService;
import com.iwhalecloud.retail.warehouse.dto.request.TradeResourceInstReq;
import com.iwhalecloud.retail.warehouse.service.TradeResourceInstService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;


@Service
@Slf4j
public class TradeResourceInstOpenServiceImpl implements TradeResourceInstService {

    @Autowired
    @Qualifier("tradeResourceInstService")
    private TradeResourceInstService tradeResourceInstService;

    @Reference
    private MerchantService merchantService;

    @Autowired
    private ResouceInstTrackService resouceInstTrackService;



    @Override
    public ResultVO<Boolean> tradeOutResourceInst(TradeResourceInstReq req) {
        log.info("TradeResourceInstOpenServiceImpl.tradeOutResourceInst req={}", JSON.toJSONString(req));
        ResultVO resp = tradeResourceInstService.tradeOutResourceInst(req);
        resouceInstTrackService.asynTradeOutResourceInst(req, resp);
        return resp;
    }

    @Override
    public ResultVO<Boolean> tradeInResourceInst(TradeResourceInstReq req) {
        log.info("TradeResourceInstOpenServiceImpl.tradeInResourceInst req={}", JSON.toJSONString(req));
        ResultVO resp = tradeResourceInstService.tradeInResourceInst(req);
        resouceInstTrackService.asynTradeInResourceInst(req, resp);
        return resp;
    }

}