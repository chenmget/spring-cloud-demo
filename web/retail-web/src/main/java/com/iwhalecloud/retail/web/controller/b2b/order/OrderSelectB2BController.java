package com.iwhalecloud.retail.web.controller.b2b.order;


import com.alibaba.dubbo.config.annotation.Reference;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.oms.OmsCommonConsts;
import com.iwhalecloud.retail.order2b.dto.model.order.OrderItemDetailDTO;
import com.iwhalecloud.retail.order2b.dto.response.CommonListResp;
import com.iwhalecloud.retail.order2b.dto.response.OrderListExportResp;
import com.iwhalecloud.retail.order2b.dto.response.OrderSelectDetailResp;
import com.iwhalecloud.retail.order2b.dto.response.OrderSelectResp;
import com.iwhalecloud.retail.order2b.dto.resquest.order.AdvanceOrderReq;
import com.iwhalecloud.retail.order2b.dto.resquest.order.SelectNbrReq;
import com.iwhalecloud.retail.order2b.dto.resquest.order.SelectOrderReq;
import com.iwhalecloud.retail.order2b.service.OrderSelectOpenService;
import com.iwhalecloud.retail.system.dto.UserDTO;
import com.iwhalecloud.retail.web.annotation.UserLoginToken;
import com.iwhalecloud.retail.web.controller.b2b.order.dto.response.OrderSelectDetailSwapResp;
import com.iwhalecloud.retail.web.controller.b2b.order.dto.response.OrderSelectSwap;
import com.iwhalecloud.retail.web.controller.b2b.order.dto.response.OrderSelectSwapResp;
import com.iwhalecloud.retail.web.controller.b2b.order.service.DeliveryGoodsResNberExcel;
import com.iwhalecloud.retail.web.controller.b2b.order.service.OrderExportUtil;
import com.iwhalecloud.retail.web.interceptor.UserContext;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api/b2b/order/select")
@Slf4j
public class OrderSelectB2BController {

    @Reference
    private OrderSelectOpenService orderSelectOpenService;

    @Autowired
    private DeliveryGoodsResNberExcel deliveryGoodsResNberExcel;

    /**
     * 采购订单list
     */
    @RequestMapping(value = "/purchaseOrderList", method = RequestMethod.POST)
    @UserLoginToken
    public ResultVO<IPage<OrderSelectSwapResp>> purchaseOrderList(@RequestBody SelectOrderReq request) {
        request.setUserId(UserContext.getUserId());
        request.setUserCode(UserContext.getUser().getRelCode());
        request.setLanId(UserContext.getUser().getLanId());
        ResultVO<IPage<OrderSelectResp>> resultVO = orderSelectOpenService.purchaseOrderList(request);
        ResultVO<IPage<OrderSelectSwapResp>> resultSwap = OrderSelectSwap.swapResultOrderSelectRespList(resultVO);
        return resultSwap;
    }


    /**
     * 导出订单
     */
    @RequestMapping(value = "/orderExport", method = RequestMethod.POST)
    @UserLoginToken
    public void orderExport(@RequestBody AdvanceOrderReq request, HttpServletResponse response) {
        ResultVO result = new ResultVO();
        request.setUserId(UserContext.getUserId());
        request.setUserCode(UserContext.getUser().getRelCode());
        ResultVO<OrderListExportResp> resultVO = orderSelectOpenService.orderExport(request);
        if (!resultVO.isSuccess()) {
            result.setResultCode(OmsCommonConsts.RESULE_CODE_FAIL);
            result.setResultMsg(resultVO.getResultMsg());
            deliveryGoodsResNberExcel.outputResponse(response,resultVO);
            return;
        }
        OrderListExportResp data = resultVO.getResultData();
        //创建Excel
        Workbook workbook = new HSSFWorkbook();
        //创建order
        deliveryGoodsResNberExcel.builderOrderExcel(workbook, data.getOrderList(),
                OrderExportUtil.getOrder(), "订单");
        deliveryGoodsResNberExcel.builderOrderExcel(workbook, data.getAdvanceList(),
                OrderExportUtil.getAdvanceOrder(), "预售单");
        //创建orderItem
        deliveryGoodsResNberExcel.builderOrderExcel(workbook, data.getOrderItemList(),
                OrderExportUtil.getOrderItem(), "订单项");
        //创建orderItemDetail
        deliveryGoodsResNberExcel.builderOrderExcel(workbook, data.getOrderItemDetailList(),
                OrderExportUtil.getOrderItemDetail(), "订单项明细");

        deliveryGoodsResNberExcel.exportExcel("订单导出",workbook,response);
//        return deliveryGoodsResNberExcel.uploadExcel(workbook);
    }

    /**
     * 查询串码信息
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/queryOrderNbr", method = RequestMethod.POST)
    @UserLoginToken
    public ResultVO<CommonListResp<OrderItemDetailDTO>> queryOrderNbr(@RequestBody SelectNbrReq request,HttpServletResponse response) {
        request.setUserId(UserContext.getUserId());
        request.setUserCode(UserContext.getUser().getRelCode());
        return orderSelectOpenService.orderItemDetailExport(request);
    }

    /**
     * 导出串码
     */
    @RequestMapping(value = "/orderNbrExport", method = RequestMethod.POST)
    @UserLoginToken
    public void orderNbrExport(@RequestBody SelectNbrReq request,HttpServletResponse response) {
        ResultVO result = new ResultVO();
        request.setUserId(UserContext.getUserId());
        request.setUserCode(UserContext.getUser().getRelCode());
        ResultVO<CommonListResp<OrderItemDetailDTO>> resultVO = orderSelectOpenService.orderItemDetailExport(request);
        if (!resultVO.isSuccess() || resultVO.getResultData() == null
                || CollectionUtils.isEmpty(resultVO.getResultData().getList())) {
            result.setResultCode(OmsCommonConsts.RESULE_CODE_FAIL);
            result.setResultMsg( resultVO.getResultMsg());
            deliveryGoodsResNberExcel.outputResponse(response,resultVO);
            return;
        }
        List<OrderItemDetailDTO> data = resultVO.getResultData().getList();
        //创建Excel
        Workbook workbook = new HSSFWorkbook();
        //创建orderItemDetail
        deliveryGoodsResNberExcel.builderOrderExcel(workbook, data,
                OrderExportUtil.getOrderItemDetail(), "串码");

        deliveryGoodsResNberExcel.exportExcel("导出订单串码",workbook,response);
//        return deliveryGoodsResNberExcel.uploadExcel(workbook);
    }


    /**
     * 管理员
     */
    @RequestMapping(value = "/managerOrderList", method = RequestMethod.POST)
    @UserLoginToken
    public ResultVO<IPage<OrderSelectSwapResp>> managerOrderList(@RequestBody SelectOrderReq request) {
        ResultVO<IPage<OrderSelectResp>> resultVO = orderSelectOpenService.managerOrderList(request);
        ResultVO<IPage<OrderSelectSwapResp>> resultSwap = OrderSelectSwap.swapResultOrderSelectRespList(resultVO);
        return resultSwap;
    }

    /**
     * 销售订单list
     */
    @RequestMapping(value = "/salesOrderList", method = RequestMethod.POST)
    @UserLoginToken
    public ResultVO<IPage<OrderSelectSwapResp>> salesOrderList(@RequestBody SelectOrderReq request) {
        request.setUserId(UserContext.getUserId());
        request.setUserCode(UserContext.getUser().getRelCode());

        ResultVO<IPage<OrderSelectResp>> resultVO = orderSelectOpenService.salesOrderList(request);
        ResultVO<IPage<OrderSelectSwapResp>> resultSwap = OrderSelectSwap.swapResultOrderSelectRespList(resultVO);
        return resultSwap;
    }

    /**
     * 订单详情
     */
    @RequestMapping(value = "/orderDetail", method = RequestMethod.GET)
    @UserLoginToken
    public ResultVO<OrderSelectDetailSwapResp> orderDetail(@RequestParam("orderId") String orderId,
                                                           @RequestParam(value = "lanId", required = false) String lanId) {
        ResultVO resultVO = new ResultVO();
        if (orderId == null) {
            resultVO.setResultMsg("订单id不能为空");
            resultVO.setResultCode(OmsCommonConsts.RESULE_CODE_FAIL);
            return resultVO;
        }
        SelectOrderReq request = new SelectOrderReq();
        request.setOrderId(orderId);
        request.setUserId(UserContext.getUserId());
        request.setLanId(lanId);
        request.setUserCode(UserContext.getUser().getRelCode());
        ResultVO<OrderSelectDetailResp> orderDetail = orderSelectOpenService.orderDetail(request);
        ResultVO<OrderSelectDetailSwapResp> result = OrderSelectSwap.swapResultOrderSelectDetailResp(orderDetail);
        return  result;
    }


    @RequestMapping(value = "/receiveGoodsList", method = RequestMethod.GET)
    @UserLoginToken
    public ResultVO receiveGoodsList(@RequestParam("orderId") String orderId,
                                     @RequestParam(value = "lanId", required = false) String lanId) {
        SelectOrderReq req = new SelectOrderReq();
        req.setOrderId(orderId);
        req.setUserId(UserContext.getUserId());
        req.setLanId(lanId);
        return orderSelectOpenService.selectOrderItemDetail(req);
    }

    /**
     * 查询预售活动订单（我购买的）
     * @param request
     * @return
     */
    @RequestMapping(value = "/queryPurchaseAdvanceOrder", method = RequestMethod.POST)
    @UserLoginToken
    public ResultVO queryPurchaseAdvanceOrder(@RequestBody AdvanceOrderReq request) {
        boolean isAdmin = UserContext.isAdminType();
        if (!isAdmin) {
            UserDTO user = UserContext.getUser();
            if(Objects.isNull(user)) {
                return ResultVO.error("您尚未登陆，请登录");
            }
            request.setUserId(user.getRelCode());
    }
        return orderSelectOpenService.queryAdvanceOrderList(request);
    }

    /**
     * 查询预售活动订单（我销售的）
     * @param request
     * @return
     */
    @RequestMapping(value = "/querySalesAdvanceOrder", method = RequestMethod.POST)
    @UserLoginToken
    public ResultVO querySalesAdvanceOrder(@RequestBody AdvanceOrderReq request) {
        boolean isAdmin = UserContext.isAdminType();
        if (!isAdmin) {
            UserDTO user = UserContext.getUser();
            if(Objects.isNull(user)) {
                return ResultVO.error("您尚未登陆，请登录");
            }
            Integer userFounder = user.getUserFounder();
            // 非零售商
            if (userFounder != 3) {
                request.setMerchantId(user.getRelCode());
            } else {
                return ResultVO.error("您是零售商用户，没有销售订单！");
            }
        }
        return orderSelectOpenService.queryAdvanceOrderList(request);
    }
}
