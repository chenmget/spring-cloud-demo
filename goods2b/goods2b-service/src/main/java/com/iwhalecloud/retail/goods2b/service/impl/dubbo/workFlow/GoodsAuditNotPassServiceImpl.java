package com.iwhalecloud.retail.goods2b.service.impl.dubbo.workFlow;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.iwhalecloud.retail.dto.ResultCodeEnum;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.goods2b.common.GoodsConst;
import com.iwhalecloud.retail.goods2b.dto.req.GoodsAuditStateReq;
import com.iwhalecloud.retail.goods2b.service.dubbo.GoodsService;
import com.iwhalecloud.retail.goods2b.service.dubbo.workFlow.GoodsAuditNotPassService;
import com.iwhalecloud.retail.workflow.config.InvokeRouteServiceRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author mzl
 * @date 2019/2/27
 */
@Slf4j
@Component
@Service
public class GoodsAuditNotPassServiceImpl implements GoodsAuditNotPassService {

    @Autowired
    private GoodsService goodsService;

    @Override
    public ResultVO run(InvokeRouteServiceRequest params) {
        log.info("GoodsAuditNotPassServiceImpl.run params={}", JSON.toJSONString(params));
        if (params == null) {
            log.info("GoodsAuditNotPassServiceImpl.run LACK_OF_PARAM params={}", JSON.toJSONString(params));
            return ResultVO.error(ResultCodeEnum.LACK_OF_PARAM);
        }
        String goodsId = params.getBusinessId();
        String marketAuditState = GoodsConst.AuditStateEnum.NOT_AUDITED.getCode();
        GoodsAuditStateReq goodsAuditStateReq = new GoodsAuditStateReq();
        goodsAuditStateReq.setGoodsId(goodsId);
        goodsAuditStateReq.setAuditState(marketAuditState);
        return goodsService.updateAuditState(goodsAuditStateReq);
    }
}
