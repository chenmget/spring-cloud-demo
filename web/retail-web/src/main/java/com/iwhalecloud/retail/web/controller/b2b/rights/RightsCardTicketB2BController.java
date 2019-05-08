package com.iwhalecloud.retail.web.controller.b2b.rights;

import com.alibaba.dubbo.config.annotation.Reference;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.order2b.dto.model.order.OrderDTO;
import com.iwhalecloud.retail.order2b.dto.model.order.OrderItemDTO;
import com.iwhalecloud.retail.order2b.service.OrderSelectOpenService;
import com.iwhalecloud.retail.partner.service.MerchantService;
import com.iwhalecloud.retail.rights.dto.request.CouponNotUsedReq;
import com.iwhalecloud.retail.rights.dto.request.CouponUsedReq;
import com.iwhalecloud.retail.rights.dto.response.CouponNotUsedResp;
import com.iwhalecloud.retail.rights.dto.response.CouponUsedResp;
import com.iwhalecloud.retail.rights.service.CardTicketService;
import com.iwhalecloud.retail.web.annotation.UserLoginToken;
import com.iwhalecloud.retail.web.controller.b2b.order.service.DeliveryGoodsResNberExcel;
import com.iwhalecloud.retail.web.controller.b2b.rights.service.CouponExportUtil;
import com.iwhalecloud.retail.web.controller.b2b.warehouse.utils.ExcelToNbrUtils;
import com.iwhalecloud.retail.web.interceptor.UserContext;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Objects;

/**
 * @author lin.wenhui@iwhalecloud.com
 * @date 2019/2/25 10:42
 * @description: 权益--我的卡券
 */

@RestController
@RequestMapping("/api/b2b/rights/cardTicket")
@Slf4j
public class RightsCardTicketB2BController {

    @Reference
    private CardTicketService cardTicketService;

    @Reference
    private MerchantService merchantService;

    @Reference
    private OrderSelectOpenService orderSelectOpenService;

    @Autowired
    private DeliveryGoodsResNberExcel deliveryGoodsResNberExcel;

    @ApiOperation(value = "查询全部商家未使用的优惠券", notes = "查询全部商家未使用的优惠券")
    @ApiResponses({
            @ApiResponse(code = 400, message = "请求参数没填好"),
            @ApiResponse(code = 404, message = "请求路径没有或页面跳转路径不对")
    })
    @RequestMapping(value = "/queryAllBusinessCouponNotUsed", method = RequestMethod.POST)
    @UserLoginToken
    public ResultVO queryAllBusinessCouponNotUsed(@RequestBody @ApiParam(value = "查询条件", required = true) CouponNotUsedReq req) {
        log.info("CardTicketService queryAllBusinessCouponNotUsed req={}", req);
        req.setBusinessId(UserContext.getMerchantId());
        Page<CouponNotUsedResp> resultData = cardTicketService.queryAllBusinessCouponNotUsed(req);
        return ResultVO.success(resultData);
    }

    @ApiOperation(value = "查询全部商家已使用的优惠券", notes = "查询全部商家已使用的优惠券")
    @ApiResponses({
            @ApiResponse(code = 400, message = "请求参数没填好"),
            @ApiResponse(code = 404, message = "请求路径没有或页面跳转路径不对")
    })
    @RequestMapping(value = "/queryAllBusinessCouponUsed", method = RequestMethod.POST)
    @UserLoginToken
    public ResultVO queryAllBusinessCouponUsed(@RequestBody @ApiParam(value = "查询条件", required = true) CouponUsedReq req) {
        log.info("CardTicketService queryAllBusinessCouponUsed req={}", req);
        req.setBusinessId(UserContext.getMerchantId());
        Page<CouponUsedResp> resultData = cardTicketService.queryAllBusinessCouponUsed(req);
        if (resultData.getRecords().size() > 0) {
            for (int i = 0; i < resultData.getRecords().size(); i++) {
                if (null != resultData.getRecords().get(i).getOrderNo() && !"".equals(resultData.getRecords().get(i).getOrderNo())) {
                    String orderId = resultData.getRecords().get(i).getOrderNo();
                    //查询订单详情
                    ResultVO<OrderDTO> orderDTOResultVO = orderSelectOpenService.selectOrderById(orderId);
                    if (!"".equals(orderDTOResultVO.getResultData()) && orderDTOResultVO.getResultData() != null) {
//                        if (!StringUtils.isEmpty(orderDTOResultVO.getResultData().getOrderId()) && !StringUtils.isEmpty(orderDTOResultVO.getResultData().getStatus()) && !StringUtils.isEmpty(orderDTOResultVO.getResultData().getPayTime())) {
                        resultData.getRecords().get(i).setOrderNo("".equals(orderDTOResultVO.getResultData().getOrderId()) ? "" : orderDTOResultVO.getResultData().getOrderId());
                        resultData.getRecords().get(i).setOrderState("".equals(orderDTOResultVO.getResultData().getStatusName()) ? "" : orderDTOResultVO.getResultData().getStatusName());
                        resultData.getRecords().get(i).setDealTime("".equals(orderDTOResultVO.getResultData().getPayTime()) ? "" : orderDTOResultVO.getResultData().getPayTime());
                        resultData.getRecords().get(i).setSupplier("".equals(orderDTOResultVO.getResultData().getSupplierName()) ? "" : orderDTOResultVO.getResultData().getSupplierName());

//                        }
                    }
                    //查询订单项
                    OrderItemDTO orderItemById = orderSelectOpenService.getOrderItemById(orderId);
                    if (!Objects.isNull(orderItemById)) {
                        resultData.getRecords().get(i).setProductName(orderItemById.getProductName());
                    }
                }
            }
        }
        return ResultVO.success(resultData);
    }

    @ApiOperation(value = "导出", notes = "导出")
    @ApiResponses({
            @ApiResponse(code = 400, message = "请求参数没填好"),
            @ApiResponse(code = 404, message = "请求路径没有或页面跳转路径不对")
    })
    @RequestMapping(value = "/exportCoupon", method = RequestMethod.POST)
    @UserLoginToken
    public void exportCoupon(@RequestBody @ApiParam(value = "查询条件", required = true) CouponUsedReq req, HttpServletResponse response) {
        log.info("CardTicketService queryAllBusinessCouponUsed req={}", req);
        req.setBusinessId(UserContext.getMerchantId());
        List<CouponUsedResp> resultData = cardTicketService.queryAllBusinessCouponUsedNotPage(req);
        if (resultData.size() > 0) {
            for (int i = 0; i < resultData.size(); i++) {
                if (null != resultData.get(i).getOrderNo() && !"".equals(resultData.get(i).getOrderNo())) {
                    String orderId = resultData.get(i).getOrderNo();
                    //查询订单详情
                    ResultVO<OrderDTO> orderDTOResultVO = orderSelectOpenService.selectOrderById(orderId);
                    if (!"".equals(orderDTOResultVO.getResultData()) && orderDTOResultVO.getResultData() != null) {
//                        if (!StringUtils.isEmpty(orderDTOResultVO.getResultData().getOrderId()) && !StringUtils.isEmpty(orderDTOResultVO.getResultData().getStatus()) && !StringUtils.isEmpty(orderDTOResultVO.getResultData().getPayTime())) {
                        resultData.get(i).setOrderNo("".equals(orderDTOResultVO.getResultData().getOrderId()) ? "" : orderDTOResultVO.getResultData().getOrderId());
                        resultData.get(i).setOrderState("".equals(orderDTOResultVO.getResultData().getStatusName()) ? "" : orderDTOResultVO.getResultData().getStatusName());
                        resultData.get(i).setDealTime("".equals(orderDTOResultVO.getResultData().getPayTime()) ? "" : orderDTOResultVO.getResultData().getPayTime());
                        resultData.get(i).setSupplier("".equals(orderDTOResultVO.getResultData().getSupplierName()) ? "" : orderDTOResultVO.getResultData().getSupplierName());
//                        }
                    }
                    //查询订单项
                    OrderItemDTO orderItemById = orderSelectOpenService.getOrderItemById(orderId);
                    if (!Objects.isNull(orderItemById)) {
                        resultData.get(i).setProductName(orderItemById.getProductName());
                    }
                }
            }
        }
        OutputStream output = null;
        try {
            //创建Excel
            Workbook workbook = new HSSFWorkbook();
            String fileName = "优惠券列表";
            ExcelToNbrUtils.builderOrderExcel(workbook, resultData, CouponExportUtil.getCoupon(), false);
            output = response.getOutputStream();
            response.reset();
            response.setHeader("Content-disposition", "attachment; filename=" + fileName + ".xls");
            response.setContentType("application/msexcel;charset=UTF-8");
            response.setCharacterEncoding("UTF-8");
            workbook.write(output);
        } catch (Exception e) {
            log.error("优惠券列表导出失败", e);
        } finally {
            try {
                if (null != output) {
                    output.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

