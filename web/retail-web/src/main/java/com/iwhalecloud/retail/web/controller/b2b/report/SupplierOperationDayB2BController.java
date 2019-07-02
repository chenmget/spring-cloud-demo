package com.iwhalecloud.retail.web.controller.b2b.report;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.report.dto.request.SummarySaleBySupplierPageReq;
import com.iwhalecloud.retail.report.dto.response.SummarySaleBySupplierPageResp;
import com.iwhalecloud.retail.report.service.SupplierOperatingDayService;
import com.iwhalecloud.retail.system.common.SystemConst;
import com.iwhalecloud.retail.web.controller.b2b.order.dto.ExcelTitleName;
import com.iwhalecloud.retail.web.controller.b2b.report.utils.ExcelToSupplierOperatingDayListUtils;
import com.iwhalecloud.retail.web.controller.b2b.report.utils.SupplierOperatingDayColumn;
import com.iwhalecloud.retail.web.interceptor.UserContext;

import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @author wenlong.zhong
 * @date 2019/6/10
 */
@RestController
@RequestMapping("/api/b2b/supplierOperatingDay")
@Slf4j
@Api(value = "地包进销存 数据 控制器", tags = {"地包进销存 数据 控制器"})
public class SupplierOperationDayB2BController {

    @Reference
    private SupplierOperatingDayService supplierOperatingDayService;


    @ApiOperation(value = "获取 地包进销存数据（按地包商的维度） 分页 列表接口", notes = "获取 地包进销存数据（按地包商的维度） 分页 列表接口")
    @ApiResponses({
            @ApiResponse(code = 400, message = "请求参数没填好"),
            @ApiResponse(code = 404, message = "请求路径没有或页面跳转路径不对")
    })
    @PostMapping(value = "/pageSummarySaleBySupplier")
    public ResultVO<Page<SummarySaleBySupplierPageResp>> pageSummarySaleBySupplier(@RequestBody @ApiParam(value = "获取 地包进销存数据（按地包商的维度） 分页 列表接口参数", required = true) SummarySaleBySupplierPageReq req) {
    	//最大跨度查询三个月createTimeStart   createTimeEnd
		String itemDateStart = req.getStartDate();
		String itemDateEnd = req.getEndDate();
		Date date = new Date();
		DateFormat df = DateFormat.getDateInstance();//日期格式，精确到日  
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.add(Calendar.MONTH, -3);
		Date date3 = cal.getTime();
		SimpleDateFormat format3= new SimpleDateFormat("yyyy-MM-dd");
		if(itemDateStart==null && itemDateEnd==null){
			itemDateStart = format3.format(date3);
			itemDateEnd = df.format(date);
			req.setStartDate(itemDateStart);
			req.setEndDate(itemDateEnd);
		}
		int userType = UserContext.getUser().getUserFounder();
		List<String> list = new ArrayList<String>();
		if(userType == SystemConst.USER_FOUNDER_1  || userType == SystemConst.USER_FOUNDER_2) {//超级管理员  省管理员
		} else if (userType == SystemConst.USER_FOUNDER_9) {//地市管理员
			list.add(UserContext.getUser().getLanId());
			req.setLanIdList(list);
		} else if (userType == SystemConst.USER_FOUNDER_4 || userType == SystemConst.USER_FOUNDER_5) {//省供应商，地市供应商
			req.setSupplierId(UserContext.getUser().getRelCode());
		} else {
			ResultVO.error("当前用户没有权限");
		}
    	return supplierOperatingDayService.pageSummarySaleBySupplier(req);
    }

    @ApiOperation(value = "地包进销存 数据列表导出", notes = "地包进销存 数据列表导出")
    @ApiResponses({
            @ApiResponse(code = 400, message = "请求参数没填好"),
            @ApiResponse(code = 404, message = "请求路径没有或页面跳转路径不对")
    })
    @PostMapping(value = "/exportSummarySaleBySupplier")
    public void export(@RequestBody SummarySaleBySupplierPageReq req, HttpServletResponse response) {
    	//最大跨度查询三个月createTimeStart   createTimeEnd
		String itemDateStart = req.getStartDate();
		String itemDateEnd = req.getEndDate();
		Date date = new Date();
		DateFormat df = DateFormat.getDateInstance();//日期格式，精确到日  
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.add(Calendar.MONTH, -3);
		Date date3 = cal.getTime();
		SimpleDateFormat format3= new SimpleDateFormat("yyyy-MM-dd");
		if(itemDateStart==null && itemDateEnd==null){
			itemDateStart = format3.format(date3);
			itemDateEnd = df.format(date);
			req.setStartDate(itemDateStart);
			req.setEndDate(itemDateEnd);
		}
		int userType = UserContext.getUser().getUserFounder();
		List<String> list = new ArrayList<String>();
		if(userType == SystemConst.USER_FOUNDER_1  || userType == SystemConst.USER_FOUNDER_2) {//超级管理员  省管理员
		} else if (userType == SystemConst.USER_FOUNDER_9) {//地市管理员
			list.add(UserContext.getUser().getLanId());
			req.setLanIdList(list);
		} else if (userType == SystemConst.USER_FOUNDER_4 || userType == SystemConst.USER_FOUNDER_5) {//省供应商，地市供应商
			req.setSupplierId(UserContext.getUser().getRelCode());
		} else {
			ResultVO.error("当前用户没有权限");
		}
        log.info("MerchantController.export() input: SummarySaleBySupplierPageReq={}", JSON.toJSONString(req));
        req.setPageNo(1);
        //数据量控制在1万条
        req.setPageSize(10000);
        ResultVO<Page<SummarySaleBySupplierPageResp>> resultVO = supplierOperatingDayService.pageSummarySaleBySupplier(req);
        List<ExcelTitleName> excelTitleNames = SupplierOperatingDayColumn.showColumn();
        OutputStream output = null;
        try {
            //创建Excel
            Workbook workbook = new HSSFWorkbook();
            String fileName = "地包进销存数据汇总报表";
            ExcelToSupplierOperatingDayListUtils.builderOrderExcel(workbook, resultVO.getResultData().getRecords(), excelTitleNames);
            output = response.getOutputStream();
            response.reset();
            response.setHeader("Content-disposition", "attachment; filename=" + fileName + ".xls");
            response.setContentType("application/msexcel;charset=UTF-8");
            response.setCharacterEncoding("UTF-8");
            workbook.write(output);
        } catch (Exception e) {
            log.error("商家列表导出失败 error={}", e);
        } finally {
            if (null != output) {
                try {
                    output.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
