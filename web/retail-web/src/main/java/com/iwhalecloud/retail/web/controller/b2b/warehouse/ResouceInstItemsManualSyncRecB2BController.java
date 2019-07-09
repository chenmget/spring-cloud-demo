package com.iwhalecloud.retail.web.controller.b2b.warehouse;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.warehouse.dto.request.ResouceInstItmsManualSyncRecAddReq;
import com.iwhalecloud.retail.warehouse.dto.request.ResouceInstItmsManualSyncRecPageReq;
import com.iwhalecloud.retail.warehouse.dto.response.ResouceInstItmsManualSyncRecListResp;
import com.iwhalecloud.retail.warehouse.service.ResouceInstItemsManualSyncRecService;
import com.iwhalecloud.retail.web.annotation.UserLoginToken;
import com.iwhalecloud.retail.web.controller.b2b.order.dto.ExcelTitleName;
import com.iwhalecloud.retail.web.controller.b2b.warehouse.utils.ExportCSVUtils;
import com.iwhalecloud.retail.web.controller.b2b.warehouse.utils.ResourceInstColum;
import com.iwhalecloud.retail.web.interceptor.UserContext;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.util.List;

/**
 * @Author My
 * @Date 2019/07/05
 **/
@RestController
@RequestMapping("/api/b2b/resouceInstItemsManualSyncRec")
@Slf4j
public class ResouceInstItemsManualSyncRecB2BController {

    @Reference
    private ResouceInstItemsManualSyncRecService resouceInstItemsManualSyncRecService;

    @ApiOperation(value = "ITMS新增串码入库", notes = "添加操作")
    @ApiResponses({
            @ApiResponse(code=400,message="请求参数没填好"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
    @PostMapping(value="addResourceItemsManualSyncRec")
    @UserLoginToken
    public ResultVO addResourceItemsManualSyncRec(@RequestBody ResouceInstItmsManualSyncRecAddReq req) {
        req.setCreateStaff(UserContext.getUserId());
        return resouceInstItemsManualSyncRecService.addResourceItemsManualSyncRec(req);
    }

    @ApiOperation(value = "ITMS更新串码入库", notes = "添加操作")
    @ApiResponses({
            @ApiResponse(code=400,message="请求参数没填好"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
    @PostMapping(value="updateResourceItemsManualSyncRec")
    @UserLoginToken
    public ResultVO updateResourceItemsManualSyncRec(@RequestBody ResouceInstItmsManualSyncRecAddReq req) {
        req.setCreateStaff(UserContext.getUserId());
        return resouceInstItemsManualSyncRecService.updateResourceItemsManualSyncRec(req);
    }


    @ApiOperation(value = "ITMS串码查询", notes = "查询操作")
    @ApiResponses({
            @ApiResponse(code=400,message="请求参数没填好"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
    @PostMapping(value="listResourceItemsManualSyncRec")
    public ResultVO listResourceItemsManualSyncRec(@RequestBody ResouceInstItmsManualSyncRecPageReq req) {
        return resouceInstItemsManualSyncRecService.listResourceItemsManualSyncRec(req);
    }

    @ApiOperation(value = "根据串码查询推送ITMS最新记录", notes = "查询操作")
    @ApiResponses({
            @ApiResponse(code=400,message="请求参数没填好"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
    @GetMapping(value="getDestLanIdByNbr")
    public ResultVO<ResouceInstItmsManualSyncRecListResp> getDestLanIdByNbr(@RequestParam String mktResInstNbr) {
        return resouceInstItemsManualSyncRecService.getDestLanIdByNbr(mktResInstNbr);
    }


    @ApiOperation(value = "导出", notes = "导出串码数据")
    @ApiResponses({
            @ApiResponse(code=400,message="请求参数没填好"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
    @PostMapping(value="itmsExport")
    @UserLoginToken
    public void itmsExport(@RequestBody ResouceInstItmsManualSyncRecPageReq req, HttpServletResponse response) {
        req.setPageNo(0);
        req.setPageSize(1000000);
        ResultVO<Page<ResouceInstItmsManualSyncRecListResp>> dataVO = resouceInstItemsManualSyncRecService.listResourceItemsManualSyncRec(req);
        if (!dataVO.isSuccess() || CollectionUtils.isEmpty(dataVO.getResultData().getRecords())) {
            return;
        }
        List<ResouceInstItmsManualSyncRecListResp> list = dataVO.getResultData().getRecords();
        log.info("ResouceInstItemsManualSyncRecB2BController.itmsExport resouceInstItemsManualSyncRecService.listResourceItemsManualSyncRec req={}, respSize={}", JSON.toJSONString(req), list.size());
        List<ExcelTitleName> excelTitleNames = ResourceInstColum.itmsColumn();
        try{
            OutputStream output = response.getOutputStream();
            ExportCSVUtils.exportItms(output, list, excelTitleNames);
            response.setContentType("application/ms-txt.numberformat:@");
            response.setCharacterEncoding("UTF-8");
            response.setHeader("Pragma", "public");
            response.setHeader("Cache-Control", "max-age=30");
            output.close();
        }catch (Exception e){
            log.error("导出失败",e);
        }
    }

}
