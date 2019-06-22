package com.iwhalecloud.retail.web.controller.partner;

import com.alibaba.dubbo.config.annotation.Reference;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.partner.dto.SupplyRelDTO;
import com.iwhalecloud.retail.partner.dto.req.SupplyRelAddReq;
import com.iwhalecloud.retail.partner.dto.req.SupplyRelDeleteReq;
import com.iwhalecloud.retail.partner.service.SupplyRelService;
import com.iwhalecloud.retail.web.controller.BaseController;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@CrossOrigin
@RequestMapping("/api/supplyRel")
public class SupplyRelController extends BaseController {
	
    @Reference
    private SupplyRelService deleteSupplyrel;


    @ApiOperation(value = "可供代理商删除绑定", notes = "可供代理商删除绑定")
    @ApiResponses({
            @ApiResponse(code=400,message="请求参数没填好"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
    @RequestMapping(value = "/deleteSupplyRel", method = RequestMethod.POST)
    ResultVO deleteSupplyRel(@RequestBody @ApiParam( value = "SupplyRelDeleteReq", required = true ) SupplyRelDeleteReq supplyRelDeleteReq)throws Exception{
        int result = deleteSupplyrel.deleteSupplyRel(supplyRelDeleteReq);
        if (result == 0){
            return failResultVO("删除失败");
        }
        return  successResultVO(result);
    }

    @ApiOperation(value = "可供代理商绑定", notes = "可供代理商绑定")
    @ApiResponses({
            @ApiResponse(code=400,message="请求参数没填好"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
    @RequestMapping(value = "/addSupplyRel", method = RequestMethod.POST)
    ResultVO addSupplyRel(@RequestBody @ApiParam( value = "SupplyRelAddReq", required = true ) SupplyRelAddReq req){
        SupplyRelDTO supplyRelDTO = deleteSupplyrel.addSupplyRel(req);
        if(supplyRelDTO == null){
            return failResultVO("新增失败");
        }
        return successResultVO(supplyRelDTO);
    }

}