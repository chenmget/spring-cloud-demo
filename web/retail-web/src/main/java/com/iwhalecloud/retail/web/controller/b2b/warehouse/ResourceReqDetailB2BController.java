package com.iwhalecloud.retail.web.controller.b2b.warehouse;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.warehouse.dto.request.ResourceReqDetailPageReq;
import com.iwhalecloud.retail.warehouse.dto.response.ResourceReqDetailPageResp;
import com.iwhalecloud.retail.warehouse.service.ResourceReqDetailService;
import com.iwhalecloud.retail.web.controller.b2b.order.dto.ExcelTitleName;
import com.iwhalecloud.retail.web.controller.b2b.warehouse.utils.ExcelToNbrUtils;
import com.iwhalecloud.retail.web.controller.b2b.warehouse.utils.ResourceInstColum;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.util.List;

/**
 * @Author My
 * @Date 2019/04/18
 **/
@RestController
@RequestMapping("/api/b2b/resourceReqDetail")
@Slf4j
public class ResourceReqDetailB2BController {

    @Reference
    private ResourceReqDetailService resourceReqDetailService;

    @ApiOperation(value = "查询申请单详情串码分页列表", notes = "查询申请单详情串码分页列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "mktResReqId", value = "mktResReqId", paramType = "query", required = true, dataType = "String"),
            @ApiImplicitParam(name = "mktResInstNbr", value = "mktResInstNbr", paramType = "query", required = false, dataType = "String"),
            @ApiImplicitParam(name = "pageNo", value = "pageNo", paramType = "query", required = false, dataType = "Integer"),
            @ApiImplicitParam(name = "pageSize", value = "pageSize", paramType = "query", required = false, dataType = "Integer")
    })
    @ApiResponses({
            @ApiResponse(code=400,message="请求参数没填好"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
    @GetMapping(value="resourceRequestPage")
    public ResultVO<Page<ResourceReqDetailPageResp>> resourceRequestPage(String mktResReqId, String mktResInstNbr,
                                                                         Integer pageNo, Integer pageSize) {
        ResourceReqDetailPageReq req = new ResourceReqDetailPageReq();
        req.setMktResReqId(mktResReqId);
        req.setMktResInstNbr(mktResInstNbr);
        req.setPageNo(pageNo);
        req.setPageSize(pageSize);
        return resourceReqDetailService.resourceRequestPage(req);
    }


    @ApiOperation(value = "导出", notes = "导出串码数据")
    @ApiResponses({
            @ApiResponse(code=400,message="请求参数没填好"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
    @PostMapping(value="nbrExport")
    public void nbrExport(@RequestBody ResourceReqDetailPageReq req, HttpServletResponse response) {
        req.setPageNo(0);
        req.setPageSize(20000);
        ResultVO<Page<ResourceReqDetailPageResp>> resultVO = resourceReqDetailService.resourceRequestPage(req);
        List<ResourceReqDetailPageResp> list = resultVO.getResultData().getRecords();
        log.info("ResourceReqDetailB2BController.nbrExport resourceReqDetailService.resourceRequestPage req={}, resp={}", JSON.toJSONString(req),JSON.toJSONString(list));
        List<ExcelTitleName> excelTitleNames = ResourceInstColum.reqDetailColumn();
        OutputStream output = null;
        try{
            //创建Excel
            Workbook workbook = new HSSFWorkbook();
            ExcelToNbrUtils.builderOrderExcel(workbook, list, excelTitleNames, true);
            output = response.getOutputStream();
            response.reset();
            response.setHeader("Content-disposition", "attachment; filename=");
            response.setContentType("application/msexcel;charset=UTF-8");
            response.setCharacterEncoding("UTF-8");
            workbook.write(output);
        }catch (Exception e){
            log.error("申请单详情导出失败",e);
        } finally {
            try {
                if (null != output) {
                    output.close();
                }
            } catch (Exception e) {
                log.error("error:", e);
            }
        }
    }

}
