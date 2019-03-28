package com.iwhalecloud.retail.order2b.service.impl;

import com.alibaba.fastjson.JSON;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.order2b.busiservice.*;
import com.iwhalecloud.retail.order2b.consts.OmsCommonConsts;
import com.iwhalecloud.retail.order2b.consts.order.TypeStatus;
import com.iwhalecloud.retail.order2b.dto.base.CommonResultResp;
import com.iwhalecloud.retail.order2b.dto.resquest.order.ReceiveGoodsReq;
import com.iwhalecloud.retail.order2b.dto.resquest.order.SendGoodsRequest;
import com.iwhalecloud.retail.order2b.model.HHReceiveGoodsModel;
import com.iwhalecloud.retail.order2b.model.OrderInfoModel;
import com.iwhalecloud.retail.order2b.service.OrderDRGoodsOpenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

@Service
public class OrderDRGoodsOpenServiceImpl implements OrderDRGoodsOpenService {


    @Autowired
    private DeliverGoodsService deliverGoodsService;


    @Autowired
    private ReceiveGoodsService receiveGoodsService;

    @Autowired
    private AfterSalesHHService afterSalesHHService;

    @Autowired
    private OrderAfterSaleOpenServiceImpl orderAfterSaleOpenService;



    @Override
    public ResultVO deliverGoods(SendGoodsRequest request) {
        ResultVO resultVO = new ResultVO();
        CommonResultResp resp = deliverGoodsService.orderCheck(request);
        if (resp.isFailure()) {
            resultVO.setResultCode(resp.getResultCode());
            resultVO.setResultMsg(resp.getResultMsg());
            return resultVO;
        }
        OrderInfoModel orderInfoModel = JSON.parseObject(JSON.toJSONString(resp.getResultData()), OrderInfoModel.class);

        resp = deliverGoodsService.resNbrValidity(request);
        if (resp.isFailure()) {
            resultVO.setResultCode(resp.getResultCode());
            resultVO.setResultMsg(resp.getResultMsg());
            resultVO.setResultData(resp.getResultData());
            return resultVO;
        }

        resp = deliverGoodsService.nbrOutResource(request);
        if (resp.isFailure()) {
            resultVO.setResultCode(resp.getResultCode());
            resultVO.setResultMsg(resp.getResultMsg());
            return resultVO;
        }

        deliverGoodsService.SendGoodsRecord(request, orderInfoModel);

        deliverGoodsService.sendGoodsFinish(request);

        resultVO.setResultCode(OmsCommonConsts.RESULE_CODE_SUCCESS);
        return resultVO;
    }


    @Override
    public ResultVO receiveGoods(ReceiveGoodsReq request) {
        ResultVO resultVO = new ResultVO();

        /**
         * 找出换货的串码
         */
        HHReceiveGoodsModel hhReceiveGoodsModel=afterSalesHHService.hhResNbrNumList(request);

        request=hhReceiveGoodsModel.getReceiveGoodsReq();

        CommonResultResp commonResultResp = receiveGoodsService.receiveGoodsCheck(request);
        if (commonResultResp.isFailure()) {
            resultVO.setResultCode(commonResultResp.getResultCode());
            resultVO.setResultMsg(commonResultResp.getResultMsg());
            return resultVO;
        }
        commonResultResp = receiveGoodsService.inResource(request);
        if (commonResultResp.isFailure()) {
            resultVO.setResultCode(commonResultResp.getResultCode());
            resultVO.setResultMsg(commonResultResp.getResultMsg());
            return resultVO;
        }

        commonResultResp = receiveGoodsService.receiveGoods(request);
        if (commonResultResp.isFailure()) {
            resultVO.setResultCode(commonResultResp.getResultCode());
            resultVO.setResultMsg(commonResultResp.getResultMsg());
            return resultVO;
        }
        commonResultResp = receiveGoodsService.receiveGoodsFinish(request);

        if(commonResultResp.isFailure()){
            resultVO.setResultCode(commonResultResp.getResultCode());
            resultVO.setResultMsg(commonResultResp.getResultMsg());
            return resultVO;
        }

        //更新换货单
        if(!CollectionUtils.isEmpty(hhReceiveGoodsModel.getHhReceiveReq())){
            for (ReceiveGoodsReq req: hhReceiveGoodsModel.getHhReceiveReq()){
                req.setType(TypeStatus.TYPE_34.getCode());
                orderAfterSaleOpenService.userReceiveGoods(req);
            }
        }

        resultVO.setResultCode(commonResultResp.getResultCode());
        resultVO.setResultMsg(commonResultResp.getResultMsg());
        return resultVO;
    }

}
