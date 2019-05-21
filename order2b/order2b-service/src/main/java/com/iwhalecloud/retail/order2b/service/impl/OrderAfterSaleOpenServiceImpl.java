package com.iwhalecloud.retail.order2b.service.impl;

import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.order2b.busiservice.*;
import com.iwhalecloud.retail.order2b.consts.OmsCommonConsts;
import com.iwhalecloud.retail.order2b.consts.order.ActionFlowType;
import com.iwhalecloud.retail.order2b.consts.order.OrderAllStatus;
import com.iwhalecloud.retail.order2b.consts.order.OrderServiceType;
import com.iwhalecloud.retail.order2b.dto.base.CommonResultResp;
import com.iwhalecloud.retail.order2b.dto.model.order.UserMemberDTO;
import com.iwhalecloud.retail.order2b.dto.response.AfterSaleResNbrResp;
import com.iwhalecloud.retail.order2b.dto.response.AfterSaleResp;
import com.iwhalecloud.retail.order2b.dto.resquest.order.*;
import com.iwhalecloud.retail.order2b.entity.Order;
import com.iwhalecloud.retail.order2b.entity.OrderApply;
import com.iwhalecloud.retail.order2b.entity.OrderItem;
import com.iwhalecloud.retail.order2b.manager.AfterSaleManager;
import com.iwhalecloud.retail.order2b.manager.OrderManager;
import com.iwhalecloud.retail.order2b.model.SupplierModel;
import com.iwhalecloud.retail.order2b.reference.GoodsManagerReference;
import com.iwhalecloud.retail.order2b.reference.MemberInfoReference;
import com.iwhalecloud.retail.order2b.reference.TaskManagerReference;
import com.iwhalecloud.retail.order2b.service.OrderAfterSaleOpenService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class OrderAfterSaleOpenServiceImpl implements OrderAfterSaleOpenService {

    @Autowired
    private AfterSaleTHTKService afterSaleTHTKService;

    @Autowired
    private AfterSalesTHService afterSalesTHService;

    @Autowired
    private UpdateOrderFlowService updateOrderFlowService;

    @Autowired
    private AfterSaleManager afterSaleManager;

    @Autowired
    private AfterSalesHHService afterSalesHHService;

    @Autowired
    private ReceiveGoodsService receiveGoodsService;

    @Autowired
    private TaskManagerReference taskManagerReference;


    @Autowired
    private GoodsManagerReference goodsManagerReference;
    @Autowired
    private MemberInfoReference memberInfoReference;

    @Autowired
    private OrderManager orderManager;

    @Override
    public ResultVO<UserMemberDTO> getHHUserInfo(GetOrderUserInfoReq request) {
        ResultVO<UserMemberDTO> resp = new ResultVO<>();
        Order order = orderManager.getOrderById(request.getOrderId());
        OrderItem orderItem = orderManager.getOrderItemById(request.getOrderItemId());

        if (order == null || orderItem == null) {
            resp.setResultCode(OmsCommonConsts.RESULE_CODE_FAIL);
            resp.setResultMsg("未找到订单或订单项");
            return resp;
        }
        String handlerId = order.getMerchantId();
        CommonResultResp<String> hhObject = goodsManagerReference.getHHObject(orderItem.getProductId(), handlerId);
        if (hhObject.isSuccess()) {
            handlerId = hhObject.getResultData();
        } else {
            resp.setResultCode(OmsCommonConsts.RESULE_CODE_FAIL);
            resp.setResultMsg(hhObject.getResultMsg());
            return resp;
        }

        UserMemberDTO userMemberDTO = new UserMemberDTO();

        SupplierModel supplierModel = memberInfoReference.selectSuperById(handlerId);
        if (supplierModel != null) {
            userMemberDTO.setUserCode(handlerId);
            userMemberDTO.setUserName(supplierModel.getMerchantName());
            userMemberDTO.setPhoneNo(supplierModel.getPhoneNo());
        }
        resp.setResultCode(OmsCommonConsts.RESULE_CODE_SUCCESS);
        resp.setResultData(userMemberDTO);
        return resp;
    }

    @Override
    public ResultVO<AfterSaleResp> createAfter(OrderApplyReq request) {
        ResultVO resultVO = new ResultVO();
        CommonResultResp resp = afterSaleTHTKService.tkCheck(request);
        if (resp.isFailure()) {
            resultVO.setResultMsg(resp.getResultMsg());
            resultVO.setResultCode(resp.getResultCode());
            return resultVO;
        }
        OrderApply orderApply;
        OrderServiceType orderServiceType = OrderServiceType.matchOpCode(request.getServiceType());
        switch (orderServiceType) {
            case ORDER_SHIP_TYPE_2://退款
                orderApply = afterSaleTHTKService.initRequestToApply(request);
                resp = afterSaleTHTKService.builderOrderApply(request, orderApply);

                break;
            case ORDER_SHIP_TYPE_4://退货
                orderApply = afterSaleTHTKService.initRequestToApply(request);
                resp = afterSalesTHService.builderOrderApply(request, orderApply);
                break;
            case ORDER_SHIP_TYPE_3: //换货
                orderApply = afterSaleTHTKService.initRequestToApply(request);
                resp = afterSalesTHService.builderOrderApply(request, orderApply);
                break;
            default:
                resultVO.setResultCode(OmsCommonConsts.RESULE_CODE_FAIL);
                resultVO.setResultMsg("serviceType 不匹配");
                return resultVO;

        }

        /**
         * 插入我的代办
         */
        taskManagerReference.addTaskByHandleList("待审核", orderApply.getOrderId(), request.getUserId(), orderApply.getHandlerId());
        if (resp.isSuccess()) {
            AfterSaleResp afterSaleResp = new AfterSaleResp();
            BeanUtils.copyProperties(orderApply, afterSaleResp);
            resultVO.setResultData(afterSaleResp);
        }
        resultVO.setResultCode(resp.getResultCode());
        resultVO.setResultMsg(resp.getResultMsg());
        return resultVO;
    }


    @Override
    public ResultVO handlerApplying(UpdateApplyStatusRequest request) {
        ResultVO resultVO = new ResultVO();
        OrderApply apply = new OrderApply();
        CommonResultResp resp = updateOrderFlowService.checkFlowTypeApply(request, apply);
        if (resp.isFailure()) {
            resultVO.setResultMsg(resp.getResultMsg());
            resultVO.setResultCode(resp.getResultCode());
            return resultVO;
        }

        resp = afterSaleTHTKService.handlerApply(request, apply);
        if (resp.isFailure()) {
            resultVO.setResultMsg(resp.getResultMsg());
            resultVO.setResultCode(resp.getResultCode());
            return resultVO;
        }
        resultVO.setResultCode(OmsCommonConsts.RESULE_CODE_SUCCESS);
        return resultVO;
    }

    @Override
    public ResultVO userReturnGoods(THSendGoodsRequest request) {
        ResultVO resultVO = new ResultVO();
        request.setFlowType(ActionFlowType.ORDER_HANDLER_MJTH.getCode());
        OrderApply apply = afterSaleManager.selectOrderApplyById(request.getOrderApplyId());
        if (apply == null) {
            resultVO.setResultMsg("未找到申请单");
            resultVO.setResultCode(OmsCommonConsts.RESULE_CODE_FAIL);
            return resultVO;
        }
        CommonResultResp resp = updateOrderFlowService.checkFlowTypeApply(request, apply);
        if (resp.isFailure()) {
            resultVO.setResultCode(resp.getResultCode());
            resultVO.setResultMsg(resp.getResultMsg());
            return resultVO;
        }
        resp = afterSalesTHService.returnGoods(request);

        resultVO.setResultCode(resp.getResultCode());
        resultVO.setResultMsg(resp.getResultMsg());
        return resultVO;
    }

    @Override
    public ResultVO sellerReceiveGoods(THReceiveGoodsReq request) {
        ResultVO resultVO = new ResultVO();
        request.setFlowType(ActionFlowType.ORDER_HANDLER_THSH.getCode());
        request.setOrderId(request.getOrderApplyId());
        CommonResultResp resp = updateOrderFlowService.checkFlowTypeApply(request, new OrderApply());
        if (resp.isFailure()) {
            resultVO.setResultCode(resp.getResultCode());
            resultVO.setResultMsg(resp.getResultMsg());
            return resultVO;
        }
        resp = afterSalesTHService.receiveGoods(request);

        resultVO.setResultCode(resp.getResultCode());
        resultVO.setResultMsg(resp.getResultMsg());
        return resultVO;
    }

    @Override
    public ResultVO sellerDeliverGoods(SendGoodsRequest request) {
        ResultVO resultVO = new ResultVO();
         // 校验
        CommonResultResp resp = afterSalesHHService.sellerCheckDeliverGoods(request);
        if (resp.isFailure()) {
            resultVO.setResultCode(resp.getResultCode());
            resultVO.setResultMsg(resp.getResultMsg());
            resultVO.setResultData(resp.getResultData());
            return resultVO;
        }
        // 发货
        resp = afterSalesHHService.sellerDeliverGoods(request);
        resultVO.setResultCode(resp.getResultCode());
        resultVO.setResultMsg(resp.getResultMsg());
        return resultVO;
    }

    @Override
    public ResultVO userReceiveGoods(ReceiveGoodsReq request) {
        ResultVO resultVO = new ResultVO();
        /**
         * 校验
         */
        CommonResultResp commonResultResp = receiveGoodsService.receiveGoodsCheck(request);
        if (commonResultResp.isFailure()) {
            resultVO.setResultCode(commonResultResp.getResultCode());
            resultVO.setResultMsg(commonResultResp.getResultMsg());
            return resultVO;
        }
        //入库
        commonResultResp = receiveGoodsService.inResource(request);
        if (commonResultResp.isFailure()) {
            resultVO.setResultCode(commonResultResp.getResultCode());
            resultVO.setResultMsg(commonResultResp.getResultMsg());
            return resultVO;
        }

        //收货
        commonResultResp = afterSalesHHService.userReceiveGoods(request);
        if (commonResultResp.isFailure()) {
            resultVO.setResultCode(commonResultResp.getResultCode());
            resultVO.setResultMsg(commonResultResp.getResultMsg());
            return resultVO;
        }


        commonResultResp = afterSalesHHService.hhFinish(request);
        resultVO.setResultCode(commonResultResp.getResultCode());
        resultVO.setResultMsg(commonResultResp.getResultMsg());

        return resultVO;
    }

    @Override
    public ResultVO returnAmount(PayOrderRequest request) {
        ResultVO resultVO = new ResultVO();
        CommonResultResp resp = afterSaleTHTKService.returnAmount(request);
        resultVO.setResultCode(resp.getResultCode());
        resultVO.setResultMsg(resp.getResultMsg());
        return resultVO;
    }

    @Override
    public ResultVO<AfterSaleResNbrResp> selectTHReceiveResNbr(SelectAfterSalesReq req) {
        ResultVO resultVO = new ResultVO();
        CommonResultResp resp = afterSalesTHService.selectReceiveResNbr(req);
        resultVO.setResultCode(resp.getResultCode());
        resultVO.setResultMsg(resp.getResultMsg());
        resultVO.setResultData(resp.getResultData());
        return resultVO;
    }

    @Override
    public ResultVO<AfterSaleResNbrResp> selectHHReceiveResNbr(SelectAfterSalesReq req) {
        ResultVO resultVO = new ResultVO();
        CommonResultResp resp = afterSalesHHService.selectReceiveResNbr(req);
        resultVO.setResultCode(resp.getResultCode());
        resultVO.setResultMsg(resp.getResultMsg());
        resultVO.setResultData(resp.getResultData());
        return resultVO;
    }

    @Override
    public ResultVO cancelApply(UpdateApplyStatusRequest request) {
        ResultVO resultVO = new ResultVO();
        request.setFlowType(ActionFlowType.ORDER_HANDLER_QX.getCode());
        OrderApply apply = afterSaleManager.selectOrderApplyById(request.getOrderApplyId());
        if (apply == null) {
            resultVO.setResultMsg("未找到申请单");
            resultVO.setResultCode(OmsCommonConsts.RESULE_CODE_FAIL);
            return resultVO;
        }

        if (OrderAllStatus.ORDER_STATUS_10_.getCode().equals(apply.getApplyState())) {
            resultVO.setResultMsg("订单已经取消,不能重复操作");
            resultVO.setResultCode(OmsCommonConsts.RESULE_CODE_FAIL);
            return resultVO;
        }

        if (!OrderAllStatus.ORDER_STATUS_21.getCode().equals(apply.getApplyState())) {
            resultVO.setResultMsg("只有待审核的才能取消");
            resultVO.setResultCode(OmsCommonConsts.RESULE_CODE_FAIL);
            return resultVO;
        }
        CommonResultResp resp = afterSaleTHTKService.handlerApply(request, apply);
        if (resp.isFailure()) {
            resultVO.setResultMsg(resp.getResultMsg());
            resultVO.setResultCode(resp.getResultCode());
            return resultVO;
        }
        resultVO.setResultCode(OmsCommonConsts.RESULE_CODE_SUCCESS);
        return resultVO;
    }
}
