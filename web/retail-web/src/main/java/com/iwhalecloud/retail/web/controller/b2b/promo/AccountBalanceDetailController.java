package com.iwhalecloud.retail.web.controller.b2b.promo;

import com.alibaba.dubbo.config.annotation.Reference;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.promo.dto.req.MarketingActivityByMerchantListReq;
import com.iwhalecloud.retail.promo.dto.req.QueryAccountBalanceDetailAllReq;
import com.iwhalecloud.retail.promo.dto.resp.MarketingActivityByMerchantResp;
import com.iwhalecloud.retail.promo.dto.resp.QueryAccountBalanceDetailAllResp;
import com.iwhalecloud.retail.promo.dto.resp.UserDTO;
import com.iwhalecloud.retail.web.annotation.UserLoginToken;
import com.iwhalecloud.retail.web.controller.b2b.order.dto.ExcelTitleName;
import com.iwhalecloud.retail.web.controller.b2b.promo.utils.ReBateExcelColum;
import com.iwhalecloud.retail.web.controller.b2b.warehouse.utils.ExcelToNbrUtils;
import com.iwhalecloud.retail.web.controller.b2b.warehouse.utils.ResourceInstColum;
import com.iwhalecloud.retail.web.interceptor.UserContext;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import lombok.extern.slf4j.Slf4j;
import com.iwhalecloud.retail.promo.service.AccountBalanceDetailService;

import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/b2b/promo/accountBalanceDetail")
@Api(value="账户余额来源明细:", tags={"AccountBalanceDetailController"})
public class AccountBalanceDetailController {
	
    @Reference
    private AccountBalanceDetailService accountBalanceDetailService;


    @ApiOperation(value = "返利收入明细查询", notes = "条件分页查询")
    @ApiResponses({
            @ApiResponse(code=400,message="请求参数没填好"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
    @PostMapping(value="/queryAccountBalanceDetailAllForPage")
    @UserLoginToken
    public ResultVO<Page<QueryAccountBalanceDetailAllResp>> queryAccountBalanceDetailAllForPage(@RequestBody QueryAccountBalanceDetailAllReq req){
        log.info("AccountBalanceDetailController queryAccountBalanceDetailAllForPage QueryAccountBalanceDetailAllReq={} ", req);
        req.setCustId(UserContext.getMerchantId());
        return accountBalanceDetailService.queryAccountBalanceDetailAllForPage(req);
    }

    @ApiOperation(value = "返利明细导出", notes = "返利明细导出")
    @ApiResponses({
            @ApiResponse(code=400,message="请求参数没填好"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
    @PostMapping(value="/reBateDetailExportAll")
    @UserLoginToken
    public void reBateDetailExportAll(@RequestBody QueryAccountBalanceDetailAllReq req,HttpServletResponse response){
        log.info("AccountBalanceDetailController reBateDetailExportAll QueryAccountBalanceDetailAllReq={} ", req);
        req.setCustId(UserContext.getMerchantId());
        req.setPageNo(1);
        req.setPageSize(Integer.MAX_VALUE);
        ResultVO<Page<QueryAccountBalanceDetailAllResp>> resultVO = accountBalanceDetailService.queryAccountBalanceDetailAllForPage(req);
        if (!resultVO.isSuccess() || resultVO.getResultData() == null) {
            return;
        }
        Page<QueryAccountBalanceDetailAllResp> page = resultVO.getResultData();
        List<QueryAccountBalanceDetailAllResp> list = page.getRecords();
        List<ExcelTitleName> excelTitleNames = ReBateExcelColum.reBateDetailColumn();
        try {
            Workbook workbook = new HSSFWorkbook();
            String fileName = "返利活动明细";
            ExcelToNbrUtils.builderOrderExcel(workbook, list, excelTitleNames,false);
            OutputStream output = response.getOutputStream();
            response.reset();
            response.setHeader("Content-disposition", "attachment; filename=" + fileName + ".xls");
            response.setContentType("application/msexcel;charset=UTF-8");
            response.setCharacterEncoding("UTF-8");
            workbook.write(output);
            output.close();
        } catch (Exception e) {
            log.error("返利活动明细导出失败", e);
        }
    }
}