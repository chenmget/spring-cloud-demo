package com.iwhalecloud.retail.web.controller.b2b.report;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.report.dto.SupplierOperatingDayDTO;
import com.iwhalecloud.retail.report.dto.request.SupplierOperatingDayPageReq;
import com.iwhalecloud.retail.report.service.SupplierOperatingDayService;
import com.iwhalecloud.retail.web.controller.b2b.order.dto.ExcelTitleName;
import com.iwhalecloud.retail.web.controller.b2b.report.utils.ExcelToSupplierOperatingDayListUtils;
import com.iwhalecloud.retail.web.controller.b2b.report.utils.SupplierOperatingDayColumn;
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

    @ApiOperation(value = "获取 地包进销存 数据 分页 列表接口", notes = "获取 地包进销存 数据 分页 列表接口")
    @ApiResponses({
            @ApiResponse(code = 400, message = "请求参数没填好"),
            @ApiResponse(code = 404, message = "请求路径没有或页面跳转路径不对")
    })
    @PostMapping(value = "/page")
    public ResultVO<Page<SupplierOperatingDayDTO>> page(@RequestBody @ApiParam(value = "获取 地包进销存 数据 分页 列表接口参数", required = true) SupplierOperatingDayPageReq req) {
        return supplierOperatingDayService.page(req);
    }

    @ApiOperation(value = "地包进销存 数据列表导出", notes = "地包进销存 数据列表导出")
    @ApiResponses({
            @ApiResponse(code = 400, message = "请求参数没填好"),
            @ApiResponse(code = 404, message = "请求路径没有或页面跳转路径不对")
    })
    @PostMapping(value = "/export")
    public void export(@RequestBody SupplierOperatingDayPageReq req, HttpServletResponse response) {
        log.info("MerchantController.export() input: SupplierOperatingDayPageReq={}", JSON.toJSONString(req));
        req.setPageNo(1);
        //数据量控制在1万条
        req.setPageSize(10000);
        ResultVO<Page<SupplierOperatingDayDTO>> resultVO = supplierOperatingDayService.page(req);
        List<ExcelTitleName> excelTitleNames = SupplierOperatingDayColumn.showColumn();
        OutputStream output = null;
        try {
            //创建Excel
            Workbook workbook = new HSSFWorkbook();
            String fileName = "商家列表";
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
