package com.iwhalecloud.retail.order2b.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.order2b.authpay.PayAuthorizationService;
import com.iwhalecloud.retail.order2b.busiservice.CreateOrderService;
import com.iwhalecloud.retail.order2b.busiservice.SelectOrderService;
import com.iwhalecloud.retail.order2b.config.DBTableSequence;
import com.iwhalecloud.retail.order2b.config.Order2bContext;
import com.iwhalecloud.retail.order2b.config.WhaleCloudKeyGenerator;
import com.iwhalecloud.retail.order2b.consts.order.ActionFlowType;
import com.iwhalecloud.retail.order2b.consts.order.OrderAllStatus;
import com.iwhalecloud.retail.order2b.consts.order.OrderServiceType;
import com.iwhalecloud.retail.order2b.consts.order.TaskType;
import com.iwhalecloud.retail.order2b.dto.model.order.OrderLogDTO;
import com.iwhalecloud.retail.order2b.dto.response.CloseOrderApplyResp;
import com.iwhalecloud.retail.order2b.dto.resquest.order.CloseOrderReq;
import com.iwhalecloud.retail.order2b.dto.resquest.order.OrderApplyGetReq;
import com.iwhalecloud.retail.order2b.dto.resquest.order.OrderLogGetReq;
import com.iwhalecloud.retail.order2b.entity.Order;
import com.iwhalecloud.retail.order2b.entity.OrderApply;
import com.iwhalecloud.retail.order2b.entity.OrderLog;
import com.iwhalecloud.retail.order2b.manager.AfterSaleManager;
import com.iwhalecloud.retail.order2b.manager.OrderLogManager;
import com.iwhalecloud.retail.order2b.manager.OrderManager;
import com.iwhalecloud.retail.order2b.model.*;
import com.iwhalecloud.retail.order2b.reference.MemberInfoReference;
import com.iwhalecloud.retail.order2b.service.CloseOrderOpenService;
import com.iwhalecloud.retail.system.common.DateUtils;
import com.iwhalecloud.retail.workflow.dto.req.WorkTaskAddReq;
import com.iwhalecloud.retail.workflow.dto.req.WorkTaskHandleReq;
import com.iwhalecloud.retail.workflow.service.TaskService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.*;

/**
 * 关闭订单
 *
 * @author xu.qinyuan@ztesoft.com
 * @date 2019年03月25日
 */
@Service
@Slf4j
public class CloseOrderOpenServiceImpl implements CloseOrderOpenService {


    private OrderManager orderManager;
    private AfterSaleManager afterSaleManager;
    private OrderLogManager orderLogManager;
    private WhaleCloudKeyGenerator whaleCloudKeyGenerator;
    private MemberInfoReference memberInfoReference;
    private SelectOrderService selectOrderService;
    private CreateOrderService createOrderService;
    @Reference
    private TaskService taskService;

    @Autowired
    private PayAuthorizationService payAuthorizationService;

    @Autowired
    public CloseOrderOpenServiceImpl(OrderManager orderManager, AfterSaleManager afterSaleManager, OrderLogManager orderLogManager, WhaleCloudKeyGenerator whaleCloudKeyGenerator, MemberInfoReference memberInfoReference, SelectOrderService selectOrderService, CreateOrderService createOrderService) {
        this.orderManager = orderManager;
        this.afterSaleManager = afterSaleManager;
        this.orderLogManager = orderLogManager;
        this.whaleCloudKeyGenerator = whaleCloudKeyGenerator;
        this.memberInfoReference = memberInfoReference;
        this.selectOrderService = selectOrderService;
        this.createOrderService = createOrderService;
    }

    @Transactional
    @Override
    public ResultVO applyCloseOrder(CloseOrderReq req) {
        log.info("CloseOrderOpenServiceImpl.applyCloseOrder: orderId为: {}", req.getOrderId());
        String orderId = req.getOrderId();
        if (StringUtils.isEmpty(orderId)) {
            return ResultVO.error("参数订单Id不能为空");
        }
        Order order = orderManager.getOrderById(orderId);
        if (Objects.isNull(order)) {
            return ResultVO.error("找不到订单信息");
        }
        // 关闭之前的状态
        String status = order.getStatus();
        // 订单状态为已支付、未发货才能关闭
        if (!OrderAllStatus.ORDER_STATUS_4.getCode().equals(status)) {
            return ResultVO.error("只有已支付待发货的订单才能进行关闭");
        }
        OrderApply orderApply = this.buildOrderApply(order, req);
        // 添加申请记录
        afterSaleManager.insertInto(orderApply);
        log.info("------>> 添加申请记录成功");
        // 更新订单状态为 卖家申请关闭
        OrderUpdateAttrModel model = new OrderUpdateAttrModel();
        model.setStatus(OrderAllStatus.ORDER_STATUS_31.getCode());
        model.setOrderId(orderId);
        orderManager.updateOrderAttr(model);
        log.info("------->> 更新订单状态成功");
        // 插入日志表，记录订单状态
        this.addOrderLog(orderId, status, OrderAllStatus.ORDER_STATUS_31.getCode(), req.getUserId());
        log.info("------->> 插入日志表成功");
        // 添加代办
        ResultVO resultVO = this.addTask(order, req);
        if (!resultVO.isSuccess()) {
            return resultVO;
        }
        return ResultVO.success();
    }

    @Override
    public ResultVO<CloseOrderApplyResp> queryCloseOrderApply(OrderApplyGetReq req) {
        log.info("CloseOrderOpenServiceImpl.queryCloseOrderApply: orderId为: {}", req.getOrderId());
        String orderId = req.getOrderId();
        if (StringUtils.isEmpty(orderId)) {
            return ResultVO.error("参数订单id为空");
        }
        req.setServiceType(OrderServiceType.ORDER_SHIP_TYPE_5.getCode());
        req.setStatus(OrderAllStatus.ORDER_STATUS_21.getCode());
        List<CloseOrderApplyModel> closeOrderApplyModels = afterSaleManager.queryCloseOrderApply(req);
        if (CollectionUtils.isEmpty(closeOrderApplyModels)) {
            return ResultVO.error("关闭订单申请信息为空");
        }
        CloseOrderApplyModel closeOrderApplyModel = closeOrderApplyModels.get(0);
        CloseOrderApplyResp resp = new CloseOrderApplyResp();
        BeanUtils.copyProperties(closeOrderApplyModel, resp);
        String refundImgUrl = resp.getRefundImgUrl();
        if (!StringUtils.isEmpty(refundImgUrl)) {
            String[] strings = refundImgUrl.split(",");
            List<String> imgUrls = Arrays.asList(strings);
            resp.setImgUrls(imgUrls);
        }
        log.info("queryCloseOrderApply 返回参数为：{}", resp.toString());
        return ResultVO.success(resp);
    }

    @Transactional
    @Override
    public ResultVO agreeClose(CloseOrderReq req) {
        String orderId = req.getOrderId();
        if (StringUtils.isEmpty(orderId)) {
            return ResultVO.error("参数订单Id为空");
        }
        Order order = orderManager.getOrderById(orderId);
        if (Objects.isNull(order)) {
            return ResultVO.error("找不到订单信息");
        }
        if("1".equals(order.getPayType())) {//如果是翼支付的话
        	// 翼支付取消预授权
            Boolean flag = payAuthorizationService.AuthorizationCancellation(orderId);
            if(!flag){
                return ResultVO.error("翼支付取消预授权失败。");
            }
        }
        String status = order.getStatus();
        // 更新订单状态为已关闭
        OrderUpdateAttrModel model = new OrderUpdateAttrModel();
        model.setStatus(OrderAllStatus.ORDER_STATUS_4_.getCode());
        model.setOrderId(orderId);
        log.info("------>> 更新订单状态为已关闭");
        orderManager.updateOrderAttr(model);
        // 更新申请单状态
        OrderApply orderApply = new OrderApply();
        orderApply.setApplyState(OrderAllStatus.ORDER_STATUS_4_.getCode());
        orderApply.setServiceType(OrderServiceType.ORDER_SHIP_TYPE_5.getCode());
        orderApply.setOrderId(orderId);
        afterSaleManager.updateOrderApplyStateByCondition(orderApply);
        log.info("------>> 更新申请单状态");
        // 写入订单日志表
        this.addOrderLog(orderId, status, OrderAllStatus.ORDER_STATUS_4_.getCode(), req.getUserId());

        // 库存、优惠券、分货规则等回滚
        log.info("------->> 库存、优惠券、分货规则等回滚start-----");
        CreateOrderLogModel logModel = new CreateOrderLogModel();
        List<BuilderOrderModel> list = selectOrderService.builderOrderInfoRollback(orderId, logModel);
        if (!CollectionUtils.isEmpty(list)) {
            createOrderService.builderFinishOnFailure(list, logModel);
        }
        log.info("------->> 库存、优惠券、分货规则等回滚end-----");
        // 关闭流程
        log.info("----->> 开始关闭流程----");
        ResultVO resultVO = this.handleWorkTask(req, orderId);
        log.info("----->> 关闭流程结束，返参为: {}", resultVO);
        
        return ResultVO.success();
    }

    @Transactional
    @Override
    public ResultVO disagreeClose(CloseOrderReq req) {
        String orderId = req.getOrderId();
        if (StringUtils.isEmpty(orderId)) {
            return ResultVO.error("参数订单Id为空");
        }
        Order order = orderManager.getOrderById(orderId);
        if (Objects.isNull(order)) {
            return ResultVO.error("找不到订单信息");
        }

        String status = order.getStatus();
        // 更新订单状态为关闭前的状态
        OrderUpdateAttrModel model = new OrderUpdateAttrModel();
        model.setOrderId(orderId);
        OrderLogGetReq orderLogGetReq = new OrderLogGetReq();
        orderLogGetReq.setOrderId(orderId);
        orderLogGetReq.setPostStatus(OrderAllStatus.ORDER_STATUS_31.getCode());
        List<OrderLogDTO> orderLogDTOS = orderLogManager.queryOrderLogByCondition(orderLogGetReq);
        if (!CollectionUtils.isEmpty(orderLogDTOS)) {
            model.setStatus(orderLogDTOS.get(0).getPreStatus());
        }
        orderManager.updateOrderAttr(model);
        log.info("----->> 更新订单状态为关闭前的状态");

        // 更新申请单状态
        OrderApply orderApply = new OrderApply();
        orderApply.setApplyState(OrderAllStatus.ORDER_STATUS_21_.getCode());
        orderApply.setServiceType(OrderServiceType.ORDER_SHIP_TYPE_5.getCode());
        orderApply.setOrderId(orderId);
        afterSaleManager.updateOrderApplyStateByCondition(orderApply);
        log.info("------>> 更新申请单状态");

        // 写入订单日志表
        this.addOrderLog(orderId, status, model.getStatus(), req.getUserId());
        // 关闭流程
        log.info("----->> 开始关闭流程----");
        ResultVO resultVO = this.handleWorkTask(req, orderId);
        log.info("----->> 关闭流程结束，返参为: {}", resultVO.toString());
        return ResultVO.success();
    }

    /**
     * 获取订单申请信息
     *
     * @param order 订单信息
     * @param req   关闭订单的请求信息
     * @return OrderApply
     */
    private OrderApply buildOrderApply(Order order, CloseOrderReq req) {
        List<String> refundImgUrls = req.getRefundImgUrls();
        String refundImgUrl = String.join(",", refundImgUrls);
        OrderApply orderApply = new OrderApply();
        orderApply.setOrderApplyId(whaleCloudKeyGenerator.tableKeySeq(DBTableSequence.ORD_ORDER_APPLY.getCode()));
        orderApply.setOrderId(order.getOrderId());
        orderApply.setServiceType(OrderServiceType.ORDER_SHIP_TYPE_5.getCode());
        orderApply.setRefundImgUrl(refundImgUrl);
        orderApply.setApplicantId(order.getSupplierId());
        orderApply.setSupplierName(order.getSupplierName());
        orderApply.setHandlerId(order.getUserId());
        orderApply.setApplyState(OrderAllStatus.ORDER_STATUS_21.getCode());
        orderApply.setCreateUserId(req.getUserId());
        orderApply.setCreateTime(DateUtils.currentSysTimeForStr());
        orderApply.setSourceFrom(req.getSourceFrom());
        orderApply.setQuestionDesc(req.getQuestionDesc());
        return orderApply;
    }

    /**
     * 添加订单的日志
     *
     * @param orderId    订单id
     * @param preStatus  变更之前的状态
     * @param postStatus 变更之后的状态
     * @param userId     进行变更的用户Id
     */
    private void addOrderLog(String orderId, String preStatus, String postStatus, String userId) {
        log.info("---->> 进入添加日志信息，orderId=", orderId);
        OrderLog orderLog = new OrderLog();
        orderLog.setLogId(whaleCloudKeyGenerator.tableKeySeq(DBTableSequence.ORD_ORDER_LOG.getCode()));
        orderLog.setChangeAction(ActionFlowType.ORDER_HANDLER_SQGB.getCode());
        orderLog.setOrderId(orderId);
        orderLog.setCreateTime(new Date());
        orderLog.setPreStatus(preStatus);
        orderLog.setPostStatus(postStatus);
        orderLog.setUserId(userId);
        orderLog.setLanId(Order2bContext.getDubboRequest().getLanId());
        orderLogManager.insertInto(orderLog);
        log.info("----->> 添加日志结束");
    }

    /**
     * 添加代办
     *
     * @param order 订单信息
     */
    private ResultVO addTask(Order order, CloseOrderReq req) {

        WorkTaskAddReq workTaskAddReq = new WorkTaskAddReq();
        // 申请人Id
        workTaskAddReq.setCreateUserId(req.getUserId());
        // 申请人名称
        workTaskAddReq.setCreateUserName(order.getSupplierName());
        workTaskAddReq.setTaskTitle("待买家确认关闭");
        workTaskAddReq.setFormId(order.getOrderId());
        workTaskAddReq.setNextNodeName("待买家确认");
        workTaskAddReq.setTaskType(TaskType.TASK_TYPE_1.getCode());
        workTaskAddReq.setTaskSubType(TaskType.TASK_SUB_TYPE_WAIT.getCode());
        // 处理人
        List<WorkTaskAddReq.UserInfo> handIds = new ArrayList<>();
        String handlerCode = order.getUserId();
        List<UserInfoModel> handlerList = memberInfoReference.selectUserInfoByUserCode(handlerCode);
        if (CollectionUtils.isEmpty(handlerList)) {
            return ResultVO.error("处理人不能为空");
        }
        for (UserInfoModel handlerId : handlerList) {
            WorkTaskAddReq.UserInfo userInfo = new WorkTaskAddReq.UserInfo();
            userInfo.setUserId(handlerId.getUserId());
            userInfo.setUserName(handlerId.getUserName());
            handIds.add(userInfo);
        }
        workTaskAddReq.setHandlerUsers(handIds);
        log.info("------>> 开始添加代办信息");
        taskService.addWorkTask(workTaskAddReq);
        log.info("------>> 添加代办信息成功");
        return ResultVO.success();
    }

    /**
     * 关闭工单
     *
     * @param req
     * @param orderId
     */
    private ResultVO handleWorkTask(CloseOrderReq req, String orderId) {
        UserInfoModel userInfoModel = memberInfoReference.selectUserInfo(req.getUserId());
        WorkTaskHandleReq workTaskHandleReq = new WorkTaskHandleReq();
        workTaskHandleReq.setFormId(orderId);
        workTaskHandleReq.setHandlerUserId(req.getUserId());
        workTaskHandleReq.setHandlerUserName(userInfoModel.getUserName());
        workTaskHandleReq.setTaskType(TaskType.TASK_TYPE_1.getCode());
        workTaskHandleReq.setTaskSubType(TaskType.TASK_SUB_TYPE_WAIT.getCode());
        return taskService.handleWorkTask(workTaskHandleReq);
    }
}
