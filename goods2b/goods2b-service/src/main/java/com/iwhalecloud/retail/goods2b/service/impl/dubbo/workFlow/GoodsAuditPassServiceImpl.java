package com.iwhalecloud.retail.goods2b.service.impl.dubbo.workFlow;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.iwhalecloud.retail.dto.ResultCodeEnum;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.goods2b.common.GoodsConst;
import com.iwhalecloud.retail.goods2b.dto.req.GoodsAuditStateReq;
import com.iwhalecloud.retail.goods2b.service.dubbo.GoodsService;
import com.iwhalecloud.retail.goods2b.service.dubbo.workFlow.GoodsAuditPassService;
import com.iwhalecloud.retail.workflow.config.InvokeRouteServiceRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author mzl
 * @date 2019/1/29
 */
@Slf4j
@Component
@Service
public class GoodsAuditPassServiceImpl implements GoodsAuditPassService {

    @Autowired
    private GoodsService goodsService;

    @Override
    public ResultVO run(InvokeRouteServiceRequest params) {
        log.info("GoodsAuditPassServiceImpl.run params={}", JSON.toJSONString(params));
        if (params == null) {
            log.info("GoodsAuditPassServiceImpl.run LACK_OF_PARAM params={}", JSON.toJSONString(params));
            return ResultVO.error(ResultCodeEnum.LACK_OF_PARAM);
        }
        String goodsId = params.getBusinessId();
        String marketAuditState = GoodsConst.AuditStateEnum.AUDITED.getCode();
        GoodsAuditStateReq goodsAuditStateReq = new GoodsAuditStateReq();
        goodsAuditStateReq.setGoodsId(goodsId);
        goodsAuditStateReq.setAuditState(marketAuditState);
        return goodsService.updateAuditState(goodsAuditStateReq);
    }
}
