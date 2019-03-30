package com.iwhalecloud.retail.web.controller.b2b.promo;

import com.alibaba.dubbo.config.annotation.Reference;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.promo.dto.req.QueryAccountBalanceDetailAllReq;
import com.iwhalecloud.retail.promo.dto.req.QueryAccountBalancePayoutReq;
import com.iwhalecloud.retail.promo.dto.resp.QueryAccountBalanceDetailAllResp;
import com.iwhalecloud.retail.promo.dto.resp.QueryAccountBalancePayoutResp;
import com.iwhalecloud.retail.web.annotation.UserLoginToken;
import com.iwhalecloud.retail.web.controller.b2b.order.dto.ExcelTitleName;
import com.iwhalecloud.retail.web.controller.b2b.promo.utils.ReBateExcelColum;
import com.iwhalecloud.retail.web.controller.b2b.warehouse.utils.ExcelToNbrUtils;
import com.iwhalecloud.retail.web.interceptor.UserContext;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.bind.annotation.*;

import lombok.extern.slf4j.Slf4j;
import com.iwhalecloud.retail.promo.service.AccountBalancePayoutService;

import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.util.List;

@Slf4j
@RestController
@CrossOrigin
@RequestMapping("/api/b2b/promo/accountBalancePayout")
@Api(value="账户支出明细:", tags={"AccountBalancePayoutController"})

public class AccountBalancePayoutController {
	
    @Reference
    private AccountBalancePayoutService accountBalancePayoutService;

    @ApiOperation(value = "返利支出明细查询", notes = "条件分页查询")
    @ApiResponses({
            @ApiResponse(code=400,message="请求参数没填好"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
    @PostMapping(value="/queryAccountBalanceDetailAllForPage")
    @UserLoginToken
    public ResultVO<Page<QueryAccountBalancePayoutResp>> queryAccountBalanceDetailAllForPage(@RequestBody QueryAccountBalancePayoutReq req){
        log.info("AccountBalancePayoutController queryAccountBalanceDetailAllForPage QueryAccountBalancePayoutReq={} ", req);
        req.setCustId(UserContext.getMerchantId());
        return accountBalancePayoutService.queryAccountBalancePayoutForPage(req);
    }

    @ApiOperation(value = "返利使用明细导出", notes = "返利使用明细导出")
    @ApiResponses({
            @ApiResponse(code=400,message="请求参数没填好"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
    @PostMapping(value="/reBatePayoutExportAll")
    @UserLoginToken
    public void reBatePayoutExportAll(@RequestBody QueryAccountBalancePayoutReq req,HttpServletResponse response){
        log.info("AccountBalancePayoutController reBatePayoutExportAll QueryAccountBalancePayoutReq={} ", req);
        req.setCustId(UserContext.getMerchantId());
        req.setPageNo(1);
        req.setPageSize(Integer.MAX_VALUE);
        ResultVO<Page<QueryAccountBalancePayoutResp>> resultVO = accountBalancePayoutService.queryAccountBalancePayoutForPage(req);
        if (!resultVO.isSuccess() || resultVO.getResultData() == null) {
            return;
        }
        Page<QueryAccountBalancePayoutResp> page = resultVO.getResultData();
        List<QueryAccountBalancePayoutResp> list = page.getRecords();
        List<ExcelTitleName> excelTitleNames = ReBateExcelColum.reBateDetailColumn();
        try {
            Workbook workbook = new HSSFWorkbook();
            String fileName = "返利使用明细";
            ExcelToNbrUtils.builderOrderExcel(workbook, list, excelTitleNames);
            OutputStream output = response.getOutputStream();
            response.reset();
            response.setHeader("Content-disposition", "attachment; filename=" + fileName + ".xls");
            response.setContentType("application/msexcel;charset=UTF-8");
            response.setCharacterEncoding("UTF-8");
            workbook.write(output);
            output.close();
        } catch (Exception e) {
            log.error("返利使用明细导出失败", e);
        }
    }
    
    
    
}