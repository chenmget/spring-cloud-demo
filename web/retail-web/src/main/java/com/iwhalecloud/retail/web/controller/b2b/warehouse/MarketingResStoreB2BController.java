package com.iwhalecloud.retail.web.controller.b2b.warehouse;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.warehouse.dto.request.markresswap.*;
import com.iwhalecloud.retail.warehouse.dto.response.markresswap.QryMktInstInfoByConditionItemSwapResp;
import com.iwhalecloud.retail.warehouse.dto.response.markresswap.QryStoreMktInstInfoItemSwapResp;
import com.iwhalecloud.retail.warehouse.dto.response.markresswap.StoreInventoryQuantityItemSwapResp;
import com.iwhalecloud.retail.warehouse.dto.response.markresswap.base.QueryMarkResQueryResultsSwapResp;
import com.iwhalecloud.retail.warehouse.service.MarketingResStoreService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/b2b/markRes")
@Slf4j
@Api(value="云货架营销资源接口", tags={"云货架营销资源接口"})
public class MarketingResStoreB2BController {


    @Reference
    private MarketingResStoreService marketingResStoreService;


    @ApiOperation(value = "移动串码入库", notes = "移动串码入库")
    @ApiResponses({
            @ApiResponse(code=400,message="请求参数没填好"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
    @PostMapping(value="/syncTerminal")
    public ResultVO syncTerminal(@RequestBody SyncTerminalSwapReq req) {
        log.info(MarketingResStoreB2BController.class.getName()+" syncTerminal req={} ", JSON.toJSON(req));

        return marketingResStoreService.syncTerminal(req);

    }
    @ApiOperation(value = "固网串码入库", notes = "固网串码入库")
    @ApiResponses({
            @ApiResponse(code=400,message="请求参数没填好"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
    @PostMapping(value="/ebuyTerminal")
    public ResultVO ebuyTerminal(@RequestBody EBuyTerminalSwapReq req) {
        log.info(MarketingResStoreB2BController.class.getName()+" ebuyTerminal req={} ", JSON.toJSON(req));

        return marketingResStoreService.ebuyTerminal(req);
    }
    @ApiOperation(value = "同步串码实例的变更信息到零售商仓库", notes = "同步串码实例的变更信息到零售商仓库")
    @ApiResponses({
            @ApiResponse(code=400,message="请求参数没填好"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
    @PostMapping(value="/sysSerialCode")
    public ResultVO synMktInstStatus(@RequestBody SynMktInstStatusSwapReq req) {
        log.info(MarketingResStoreB2BController.class.getName()+" synMktInstStatus req={} ", JSON.toJSON(req));

        return marketingResStoreService.synMktInstStatus(req);
    }
    @ApiOperation(value = "按串码查询零售商仓库终端的实例信息", notes = "按串码查询零售商仓库终端的实例信息")
    @ApiResponses({
            @ApiResponse(code=400,message="请求参数没填好"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
    @PostMapping(value="/qryStoreMktInstInfo")
    public ResultVO<QueryMarkResQueryResultsSwapResp<QryStoreMktInstInfoItemSwapResp>> qryStoreMktInstInfo(@RequestBody QryStoreMktInstInfoSwapReq req) {
        log.info(MarketingResStoreB2BController.class.getName()+" qryStoreMktInstInfo req={} ", JSON.toJSON(req));
        return marketingResStoreService.qryStoreMktInstInfo(req);

    }
    @ApiOperation(value = "按多种条件查询零售商仓库终端的实例列表", notes = "按多种条件查询零售商仓库终端的实例列表")
    @ApiResponses({
            @ApiResponse(code=400,message="请求参数没填好"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
    @PostMapping(value="/qryMktInstInfoByCondition")
    public ResultVO<QueryMarkResQueryResultsSwapResp<QryMktInstInfoByConditionItemSwapResp>> qryMktInstInfoByCondition(@RequestBody QryMktInstInfoByConditionSwapReq req) {
        log.info(MarketingResStoreB2BController.class.getName()+" qryMktInstInfoByCondition req={} ", JSON.toJSON(req));
        return marketingResStoreService.qryMktInstInfoByCondition(req);
    }
    @ApiOperation(value = "按串码查询零售商仓库终端的实例信息", notes = "按串码查询零售商仓库终端的实例信息")
    @ApiResponses({
            @ApiResponse(code=400,message="请求参数没填好"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
    @PostMapping(value="/storeInventoryQuantity")
    public ResultVO<QueryMarkResQueryResultsSwapResp<StoreInventoryQuantityItemSwapResp>>storeInventoryQuantity(@RequestBody StoreInventoryQuantitySwapReq req) {
        log.info(MarketingResStoreB2BController.class.getName()+" qryMktInstInfoByCondition req={} ", JSON.toJSON(req));
        return marketingResStoreService.storeInventoryQuantity(req);
    }


}
