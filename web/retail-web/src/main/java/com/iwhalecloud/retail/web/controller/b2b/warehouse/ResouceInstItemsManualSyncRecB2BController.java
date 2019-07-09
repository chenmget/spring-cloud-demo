package com.iwhalecloud.retail.web.controller.b2b.warehouse;

import com.alibaba.dubbo.config.annotation.Reference;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.warehouse.dto.request.ResouceInstItmsManualSyncRecAddReq;
import com.iwhalecloud.retail.warehouse.dto.request.ResouceInstItmsManualSyncRecPageReq;
import com.iwhalecloud.retail.warehouse.dto.response.ResouceInstItmsManualSyncRecListResp;
import com.iwhalecloud.retail.warehouse.service.ResouceInstItemsManualSyncRecService;
import com.iwhalecloud.retail.web.annotation.UserLoginToken;
import com.iwhalecloud.retail.web.interceptor.UserContext;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

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
    @PutMapping(value="getDestLanIdByNbr")
    public ResultVO<ResouceInstItmsManualSyncRecListResp> getDestLanIdByNbr(@RequestParam String mktResInstNbr) {
        return resouceInstItemsManualSyncRecService.getDestLanIdByNbr(mktResInstNbr);
    }

}
