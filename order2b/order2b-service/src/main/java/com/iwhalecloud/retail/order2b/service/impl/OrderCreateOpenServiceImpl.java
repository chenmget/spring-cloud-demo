package com.iwhalecloud.retail.order2b.service.impl;

import com.alibaba.fastjson.JSON;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.order2b.busiservice.CreateOrderService;
import com.iwhalecloud.retail.order2b.busiservice.MarketingActivitiesService;
import com.iwhalecloud.retail.order2b.busiservice.SelectCartGoodsService;
import com.iwhalecloud.retail.order2b.consts.OmsCommonConsts;
import com.iwhalecloud.retail.order2b.dto.base.CommonResultResp;
import com.iwhalecloud.retail.order2b.dto.model.cart.CartOrderAmountDTO;
import com.iwhalecloud.retail.order2b.dto.response.PreCheckOrderResp;
import com.iwhalecloud.retail.order2b.dto.model.order.PromotionDTO;
import com.iwhalecloud.retail.order2b.dto.response.CreateOrderResp;
import com.iwhalecloud.retail.order2b.dto.resquest.order.CreateOrderRequest;
import com.iwhalecloud.retail.order2b.dto.resquest.order.PreCreateOrderReq;
import com.iwhalecloud.retail.order2b.entity.Order;
import com.iwhalecloud.retail.order2b.model.BuilderOrderModel;
import com.iwhalecloud.retail.order2b.model.CartItemModel;
import com.iwhalecloud.retail.order2b.model.CreateOrderLogModel;
import com.iwhalecloud.retail.order2b.reference.ResNbrManagerReference;
import com.iwhalecloud.retail.order2b.service.OrderCreateOpenService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class OrderCreateOpenServiceImpl implements OrderCreateOpenService {

    @Autowired
    private CreateOrderService createOrderService;

    @Autowired
    private SelectCartGoodsService selectCartGoodsService;

    @Autowired
    private MarketingActivitiesService marketingActivitiesService;


    @Autowired
    private ResNbrManagerReference resNbrManagerReference;


    @Override
    public ResultVO<PreCheckOrderResp> preCheckOrderItem(PreCreateOrderReq req) {
        ResultVO<PreCheckOrderResp> resultVO = new ResultVO<>();
        PreCheckOrderResp orderResp = new PreCheckOrderResp();
        if (CollectionUtils.isEmpty(req.getGoodsItem())) {
            resultVO.setResultMsg("订单项不能为空");
            resultVO.setResultCode(OmsCommonConsts.RESULE_CODE_FAIL);
            return resultVO;
        }

        CommonResultResp resp = selectCartGoodsService.builderCartData(req);
        List<CartItemModel> cartItemModelList=JSON.parseArray(JSON.toJSONString(resp.getResultData()),
                CartItemModel.class);
        BuilderOrderModel builderOrderModel = selectCartGoodsService.builderOrderItems(cartItemModelList);

        //规则校验
         resp = createOrderService.checkRule(req, cartItemModelList);
        if (resp.isFailure()) {
            resultVO.setResultCode(resp.getResultCode());
            resultVO.setResultMsg(resp.getResultMsg());
            return resultVO;
        }

        /**
         * 参与活动计算
         */
        resp = marketingActivitiesService.orderActivityAmount(req, builderOrderModel);
        if (resp.isFailure()) {
            resultVO.setResultMsg("优惠券使用失败，" + resp.getResultMsg());
            resultVO.setResultCode(OmsCommonConsts.RESULE_CODE_FAIL);
            return resultVO;
        }
        if (!CollectionUtils.isEmpty(builderOrderModel.getPromotionList())) {
            orderResp.setPromotionList(JSON.parseArray(JSON.toJSONString(builderOrderModel.getPromotionList()),
                    PromotionDTO.class));
        }


        /**
         * 订单金额
         */
        resp = selectCartGoodsService.selectOrderAmount(builderOrderModel);
        if (resp.isSuccess()) {
            CartOrderAmountDTO catOrder = (CartOrderAmountDTO) resp.getResultData();
            orderResp.setOrderAmount(catOrder);
        }
        resultVO.setResultCode(OmsCommonConsts.RESULE_CODE_SUCCESS);
        resultVO.setResultData(orderResp);
        return resultVO;
    }

    @Override
    public ResultVO<CreateOrderResp> createOrder(CreateOrderRequest request) {

        ResultVO<CreateOrderResp> resultVO = new ResultVO<>();

        //请求数据
        CommonResultResp resp = createOrderService.initRequestToOrder(request);
        if (!resp.isSuccess()) {
            resultVO.setResultCode(resp.getResultCode());
            resultVO.setResultMsg(resp.getResultMsg());
            return resultVO;
        }
        Order order = new Order();
        BeanUtils.copyProperties(resp.getResultData(), order);

        //订单项数据
        resp = createOrderService.selectCartGoods(request);
        if (!resp.isSuccess()) {
            resultVO.setResultCode(resp.getResultCode());
            resultVO.setResultMsg(resp.getResultMsg());
            return resultVO;
        }
        List<CartItemModel> cartItemModels = JSON.parseArray(JSON.toJSONString(resp.getResultData()), CartItemModel.class);

        //规则校验
        resp = createOrderService.checkRule(request, cartItemModels);
        if (resp.isFailure()) {
            resultVO.setResultCode(resp.getResultCode());
            resultVO.setResultMsg(resp.getResultMsg());
            return resultVO;
        }

        List<BuilderOrderModel> orderModelList = new ArrayList<>();
        CreateOrderLogModel logModel = new CreateOrderLogModel();
        try {
            //组装订单项数据
            resp = createOrderService.AssembleOrderItem(cartItemModels, order);
            if (!resp.isSuccess()) {
                resultVO.setResultCode(resp.getResultCode());
                resultVO.setResultMsg(resp.getResultMsg());
                return resultVO;
            }
            orderModelList = JSON.parseArray(JSON.toJSONString(resp.getResultData()), BuilderOrderModel.class);

            /**
             *  订单（优惠券，库存，处理）
             */
            resp = createOrderService.orderOtherHandler(request, orderModelList);
            if (resp.isFailure()) {
                BeanUtils.copyProperties(resp.getResultData(), logModel);
                throw new Exception(logModel.getErrorMessage());
            }

            //创建订单
            resp = createOrderService.builderOrder(request, orderModelList);
            if (resp.isFailure()) {
                BeanUtils.copyProperties(resp.getResultData(), logModel);
                throw new Exception(logModel.getErrorMessage());
            }

            resp = createOrderService.builderFinishOnSuccess(request, orderModelList);
            resultVO.setResultData((CreateOrderResp) resp.getResultData());
            resultVO.setResultCode(resp.getResultCode());
            resultVO.setResultMsg(resp.getResultMsg());
        } catch (Exception e) {
            log.error("OrderCreateOpenServiceImpl.createOrder  create order error,", e);
            resp = createOrderService.builderFinishOnFailure(orderModelList, logModel);
            resultVO.setResultCode(OmsCommonConsts.RESULE_CODE_FAIL);
            if(StringUtils.isEmpty(logModel.getErrorMessage())){
                logModel.setErrorMessage("下单异常");
            }
            resultVO.setResultMsg(logModel.getErrorMessage());
        }
        return resultVO;
    }


}
