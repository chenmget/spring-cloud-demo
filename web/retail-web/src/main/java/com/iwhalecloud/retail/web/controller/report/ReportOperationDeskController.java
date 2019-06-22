package com.iwhalecloud.retail.web.controller.report;

import com.alibaba.dubbo.config.annotation.Reference;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.oms.OmsCommonConsts;
import com.iwhalecloud.retail.oms.consts.GoodsCountRankConstants;
import com.iwhalecloud.retail.oms.consts.ReportOpeDataConstants;
import com.iwhalecloud.retail.oms.dto.response.report.ReportOperationDeskBottomRespDTO;
import com.iwhalecloud.retail.oms.dto.response.report.ReportOperationDeskTopRespDTO;
import com.iwhalecloud.retail.oms.dto.resquest.report.ReportOperationDeskReq;
import com.iwhalecloud.retail.oms.service.EventOperationDeskService;
import com.iwhalecloud.retail.order.dto.resquest.ReportOrderOpeDeskReq;
import com.iwhalecloud.retail.order.service.OrderOperationDeskService;
import com.iwhalecloud.retail.web.annotation.UserLoginToken;
import com.iwhalecloud.retail.web.interceptor.UserContext;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@Slf4j
@RestController
@CrossOrigin
@RequestMapping("/api/reportOperationDesk")
public class ReportOperationDeskController {

    @Reference
    private OrderOperationDeskService orderOperationDeskService;

    @Reference
    private EventOperationDeskService eventOperationDeskService;

    @ApiOperation(value = "查询运营人员工作台上部展示数据")
    @ApiResponses({@ApiResponse(code = 400, message = "请求参数未填对"),@ApiResponse(code = 404, message = "请求路径没有或页面跳转路径不对")})
    @RequestMapping(value = "/topPartData", method = RequestMethod.POST)
    @UserLoginToken
    public ResultVO<ReportOperationDeskTopRespDTO> getTopPartData(@RequestBody ReportOperationDeskReq request){
        ResultVO<ReportOperationDeskTopRespDTO> resultVO = new ResultVO<>();
        ReportOperationDeskTopRespDTO respDTO = genTopPartRespDTO(request);
        resultVO.setResultCode(OmsCommonConsts.RESULE_CODE_SUCCESS);
        resultVO.setResultData(respDTO);
        resultVO.setResultMsg("SUCCESS");
        return resultVO;
    }

    @ApiOperation(value = "查询运营人员工作台下部展示数据")
    @ApiResponses({@ApiResponse(code = 400, message = "请求参数未填对"),@ApiResponse(code = 404, message = "请求路径没有或页面跳转路径不对")})
    @RequestMapping(value = "/bottomPartData", method = RequestMethod.POST)
    @UserLoginToken
    public ResultVO<ReportOperationDeskBottomRespDTO> getBottomPartData(@RequestBody ReportOperationDeskReq request)throws Exception {
        ResultVO<ReportOperationDeskBottomRespDTO> resultVO = new ResultVO<>();
        ReportOperationDeskBottomRespDTO respDTO = genBottomPartRespDTO(request);
        if (null != respDTO) {
            resultVO.setResultCode(OmsCommonConsts.RESULE_CODE_SUCCESS);
            resultVO.setResultData(respDTO);
            resultVO.setResultMsg("SUCCESS");
        } else {
            resultVO.setResultCode(OmsCommonConsts.RESULE_CODE_FAIL);
            resultVO.setResultMsg("请求失败，请检查参数输入!");
        }
        return resultVO;
    }

    private ReportOperationDeskBottomRespDTO genBottomPartRespDTO(ReportOperationDeskReq request)throws Exception{
        ReportOperationDeskBottomRespDTO respDTO = new ReportOperationDeskBottomRespDTO();
        // 通过用户获取其归属区域
        String areaCode = request.getAreaCode()!=null? request.getAreaCode() : UserContext.getUser().getRegionId();
        request.setAreaCode(areaCode);
        String type = request.getReportType();
        if (null == type){ // 类型不能为空
            return null;
        }
        ReportOrderOpeDeskReq reportOrderOpeDeskReq = new ReportOrderOpeDeskReq();
        BeanUtils.copyProperties(request,reportOrderOpeDeskReq);
        List shopRankList;
        List reportDataList;
        if (type.equals(ReportOpeDataConstants.TYPE_SALE_AMOUNT)){
            // 获取销售量
            shopRankList = orderOperationDeskService.getShopSaleAmountRankByArea(reportOrderOpeDeskReq);
            reportDataList = orderOperationDeskService.getTimeIntervalAmountByArea(reportOrderOpeDeskReq);
        } else if (type.equals(ReportOpeDataConstants.TYPE_VISIT_VOLUME)){
            // 获取访问量
            shopRankList = eventOperationDeskService.getShopVisitVolumeRankByArea(request);
            reportDataList = eventOperationDeskService.getTimeIntervalVisitByArea(request);
        } else if (type.equals(ReportOpeDataConstants.TYPE_WORK_TIME)){
            shopRankList = eventOperationDeskService.getShopWorkTimeRankByArea(request);
            //根据日期、区域查询云货架工作时长
            reportDataList = eventOperationDeskService.getOnOffLineTimeAboutDevice(request);
        } else {
            return null;
        }
        respDTO.setShopRankList(shopRankList);
        respDTO.setReportDataList(reportDataList);
        return respDTO;
    }

    private ReportOperationDeskTopRespDTO genTopPartRespDTO(ReportOperationDeskReq request){
        ReportOperationDeskTopRespDTO respDTO = new ReportOperationDeskTopRespDTO();
        // 通过用户获取其归属区域
        String areaCode = request.getAreaCode()!=null? request.getAreaCode() : UserContext.getUser().getRegionId();
        request.setAreaCode(areaCode);
        ReportOrderOpeDeskReq reportOrderOpeDeskReq = new ReportOrderOpeDeskReq();
        BeanUtils.copyProperties(request,reportOrderOpeDeskReq);
        // 订单量
        respDTO.setOrderCount(orderOperationDeskService.getOrderCountByArea(reportOrderOpeDeskReq));
        // 销售额
        respDTO.setOrderAmount(orderOperationDeskService.getOrderAmountByArea(reportOrderOpeDeskReq));
        // 加购数
        request.setEventCode(GoodsCountRankConstants.GOODS_CART_EVENT);
        int cartEventCount = eventOperationDeskService.getEventCountByArea(request);
        respDTO.setCartCount(cartEventCount);
        // 成交量
        int saleCount = orderOperationDeskService.getSaleCountByArea(reportOrderOpeDeskReq);
        request.setEventCode(GoodsCountRankConstants.GOODS_CLICK_EVENT);
        int clickCount = eventOperationDeskService.getEventCountByArea(request);
        double transRate = 0;
        if (0 != clickCount){
            transRate = (saleCount/clickCount)*100;
        }
        // 转化率
        String transRateStr = new BigDecimal(transRate).setScale(1,BigDecimal.ROUND_HALF_UP).toString() + "%";
        respDTO.setTransRate(transRateStr);
        return respDTO;
    }


}
