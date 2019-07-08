package com.iwhalecloud.retail.web.controller.b2b.order;

import com.alibaba.dubbo.config.annotation.Reference;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.order2b.dto.model.order.OrderApplyDTO;
import com.iwhalecloud.retail.order2b.dto.model.order.OrderItemDetailDTO;
import com.iwhalecloud.retail.order2b.dto.response.OrderApplyExportResp;
import com.iwhalecloud.retail.order2b.dto.resquest.order.*;
import com.iwhalecloud.retail.order2b.service.OrderAfterSaleOpenService;
import com.iwhalecloud.retail.order2b.service.SelectAfterSaleOpenService;
import com.iwhalecloud.retail.web.annotation.UserLoginToken;
import com.iwhalecloud.retail.web.controller.b2b.order.service.DeliveryGoodsResNberExcel;
import com.iwhalecloud.retail.web.controller.b2b.order.service.OrderExportUtil;
import com.iwhalecloud.retail.web.interceptor.UserContext;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping("/api/b2b/order/after")
@Slf4j
public class OrderAfterSaleB2BController {

    @Autowired
    private DeliveryGoodsResNberExcel deliveryGoodsResNberExcel;

    @Reference
    private OrderAfterSaleOpenService orderAfterSaleOpenService;

    @Reference
    private SelectAfterSaleOpenService selectAfterSaleOpenService;


    /**
     * 创建售后单
     */
    @RequestMapping(value = "/createAfter", method = RequestMethod.POST)
    @UserLoginToken
    public ResultVO createAfter(@RequestBody OrderApplyReq request) {
        request.setUserId(UserContext.getUserId());
        request.setUserCode(UserContext.getUser().getRelCode());
        return orderAfterSaleOpenService.createAfter(request);
    }
    /**
                 * 创建换货申请单时，查询换货用户的名称
     */
    @RequestMapping(value = "/getHHUserInfo", method = RequestMethod.POST)
    @UserLoginToken
    public ResultVO getHHUserInfo(@RequestBody GetOrderUserInfoReq request) {
        request.setUserId(UserContext.getUserId());
        request.setUserCode(UserContext.getUser().getRelCode());
        return orderAfterSaleOpenService.getHHUserInfo(request);
    }

    /**
     * 处理售后单
     */
    @RequestMapping(value = "/handlerApplying", method = RequestMethod.POST)
    @UserLoginToken
    public ResultVO handlerApplying(@RequestBody UpdateApplyStatusRequest request) {
        request.setUserId(UserContext.getUserId());
        request.setUserCode(UserContext.getUser().getRelCode());
        return orderAfterSaleOpenService.handlerApplying(request);
    }

    /**
     * 买家退货
     */
    @RequestMapping(value = "/userReturnGoods", method = RequestMethod.POST)
    @UserLoginToken
    public ResultVO userReturnGoods(@RequestBody THSendGoodsRequest request) {
        request.setUserId(UserContext.getUserId());
        request.setUserCode(UserContext.getUser().getRelCode());
        return orderAfterSaleOpenService.userReturnGoods(request);
    }

    /**
     * 商家收货
     */
    @RequestMapping(value = "/sellerReceiveGoods", method = RequestMethod.POST)
    @UserLoginToken
    public ResultVO sellerReceiveGoods(@RequestBody THReceiveGoodsReq request) {
        request.setUserId(UserContext.getUserId());
        request.setUserCode(UserContext.getUser().getRelCode());
        return orderAfterSaleOpenService.sellerReceiveGoods(request);
    }

    /**
     * 查询我发起的售后单
     */
    @RequestMapping(value = "/selectApply", method = RequestMethod.POST)
    @UserLoginToken
    public ResultVO selectApply(@RequestBody SelectAfterSalesReq request) {
        request.setUserId(UserContext.getUserId());
        request.setUserCode(UserContext.getUser().getRelCode());
        request.setLanId(UserContext.getUser().getLanId());
        return selectAfterSaleOpenService.selectApply(request);
    }

    /**
     * 查询我要处理的售后单
     */
    @RequestMapping(value = "/selectHandler", method = RequestMethod.POST)
    @UserLoginToken
    public ResultVO selectHandler(@RequestBody SelectAfterSalesReq request) {
        request.setUserId(UserContext.getUserId());
        request.setUserCode(UserContext.getUser().getRelCode());
        return selectAfterSaleOpenService.selectHandler(request);
    }


    /**
     * 查询详情
     */
    @RequestMapping(value = "/selectDetail", method = RequestMethod.GET)
    @UserLoginToken
    public ResultVO selectDetail(@RequestParam("orderApplyId") String orderApplyId,
                                 @RequestParam("lanId") String lanId) {
        SelectAfterSalesReq request = new SelectAfterSalesReq();
        request.setUserId(UserContext.getUserId());
        request.setUserCode(UserContext.getUser().getRelCode());
        request.setLanId(lanId);
        request.setOrderApplyId(orderApplyId);
        return selectAfterSaleOpenService.detail(request);
    }

    /**
     * 退款
     */
    @RequestMapping(value = "/retrunPay", method = RequestMethod.POST)
    @UserLoginToken
    public ResultVO retrunPay(@RequestBody PayOrderRequest request) {
        request.setUserId(UserContext.getUserId());
        request.setUserCode(UserContext.getUser().getRelCode());
        return orderAfterSaleOpenService.returnAmount(request);
    }

    /**
     * 换货收获查询
     */
    @RequestMapping(value = "/hhReceiveResNbr", method = RequestMethod.GET)
    @UserLoginToken
    public ResultVO hhReceiveResNbr(@RequestParam("orderApplyId") String orderApplyId,
                                    @RequestParam("lanId") String lanId) {
        SelectAfterSalesReq request = new SelectAfterSalesReq();
        request.setUserId(UserContext.getUserId());
        request.setUserCode(UserContext.getUser().getRelCode());
        request.setOrderApplyId(orderApplyId);
        request.setLanId(lanId);
        return orderAfterSaleOpenService.selectHHReceiveResNbr(request);
    }


    /**
     * 换货收获查询
     */
    @RequestMapping(value = "/thReceiveResNbr", method = RequestMethod.GET)
    @UserLoginToken
    public ResultVO thReceiveResNbr(@RequestParam("orderApplyId") String orderApplyId,
                                    @RequestParam("lanId") String lanId) {
        SelectAfterSalesReq request = new SelectAfterSalesReq();
        request.setUserId(UserContext.getUserId());
        request.setUserCode(UserContext.getUser().getRelCode());
        request.setOrderApplyId(orderApplyId);
        request.setLanId(lanId);
        return orderAfterSaleOpenService.selectTHReceiveResNbr(request);
    }

    /**
     * 换货收获查询
     */
    @RequestMapping(value = "/cancelApply", method = RequestMethod.POST)
    @UserLoginToken
    public ResultVO cancelApply(@RequestBody UpdateApplyStatusRequest request) {
        request.setUserId(UserContext.getUserId());
        request.setUserCode(UserContext.getUser().getRelCode());
        return orderAfterSaleOpenService.cancelApply(request);
    }

    /**
     * 申请单导出
     */
    @RequestMapping(value = "/orderApplyExport", method = RequestMethod.POST)
    @UserLoginToken
    public void orderApplyExport(@RequestBody SelectAfterSalesReq request, HttpServletResponse response) {
        request.setUserId(UserContext.getUserId());
        request.setUserCode(UserContext.getUser().getRelCode());
        ResultVO<OrderApplyExportResp> resultVO = selectAfterSaleOpenService.orderApplyExport(request);
        if (!resultVO.isSuccess()) {
            resultVO.setResultCode(resultVO.getResultCode());
            resultVO.setResultMsg(resultVO.getResultMsg());
            deliveryGoodsResNberExcel.outputResponse(response, resultVO);
            return;
        }
        /**
         * 申请单
         */
        List<OrderApplyDTO> data = resultVO.getResultData().getOrderApplyList();
        //创建Excel
        Workbook workbook = new HSSFWorkbook();
        //创建orderItemDetail
        deliveryGoodsResNberExcel.builderOrderExcel(workbook, data,
                OrderExportUtil.getOrderApply(), "申请单");

        List<OrderItemDetailDTO> nbrsList = resultVO.getResultData().getApplyNbrslist();
        deliveryGoodsResNberExcel.builderOrderExcel(workbook, nbrsList,
                OrderExportUtil.getOrderApplyDetail(), "申请单详情");

        deliveryGoodsResNberExcel.exportExcel("申请单导出", workbook, response);
//        return deliveryGoodsResNberExcel.uploadExcel(workbook);
    }


}
