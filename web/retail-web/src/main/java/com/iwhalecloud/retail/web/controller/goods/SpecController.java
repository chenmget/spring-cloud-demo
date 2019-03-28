package com.iwhalecloud.retail.web.controller.goods;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import java.util.List;

import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.dubbo.config.annotation.Reference;
import com.iwhalecloud.retail.goods.dto.ResultVO;
import com.iwhalecloud.retail.goods.dto.req.ProdSpecValuesAddReq;
import com.iwhalecloud.retail.goods.dto.req.ProdSpecValuesGetReq;
import com.iwhalecloud.retail.goods.dto.req.ProdSpecificationAddReq;
import com.iwhalecloud.retail.goods.dto.req.ProdSpecificationDeleteReq;
import com.iwhalecloud.retail.goods.dto.req.ProdSpecificationGetReq;
import com.iwhalecloud.retail.goods.dto.req.ProdSpecificationUpdateReq;
import com.iwhalecloud.retail.goods.dto.resp.ProdSpecValuesGetResp;
import com.iwhalecloud.retail.goods.dto.resp.ProdSpecificationGetResp;
import com.iwhalecloud.retail.goods.service.dubbo.ProdSpecValuesService;
import com.iwhalecloud.retail.goods.service.dubbo.ProdSpecificationService;

/**
 * @author he.sw
 * @date 2018/12/04
 */
@RestController
@RequestMapping("/api/spec")
@Slf4j
public class SpecController {

	@Reference
    private ProdSpecificationService prodSpecificationService;
	
	@Reference
	private ProdSpecValuesService prodSpecValuesService;

	@ApiOperation(value = "查看所有规格", notes = "查看所有可用规格")
    @ApiResponses({
            @ApiResponse(code=400,message="请求参数没填好"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
    @GetMapping(value="listSpec")
    public ResultVO<List<ProdSpecificationGetResp>> listSpec() {
        return prodSpecificationService.listSpec();
    }

	
    @ApiOperation(value = "添加规格", notes = "传入ProdSpecificationAddReq，进行添加操作")
    @ApiResponses({
            @ApiResponse(code=400,message="请求参数没填好"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
    @PostMapping(value="addSpec")
    public ResultVO<Integer> addSpec(@RequestBody ProdSpecificationAddReq req) {
        return prodSpecificationService.addSpec(req);
        
    }

    
    @ApiOperation(value = "更新规格", notes = "传入ProdSpecificationUpdateReq，进行更新操作")
    @ApiResponses({
            @ApiResponse(code=400,message="请求参数没填好"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
    @PutMapping(value="updateSpec")
    public ResultVO<Integer> updateSpec(@RequestBody ProdSpecificationUpdateReq req) {
    	return prodSpecificationService.updateSpec(req);
    }
    

    @ApiOperation(value = "删除规格", notes = "传入ProdSpecificationDeleteReq，进行删除操作")
    @ApiResponses({
            @ApiResponse(code=400,message="请求参数没填好"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
    @DeleteMapping(value="deleteSpec")
    public ResultVO<Integer> deleteSpec(@RequestBody ProdSpecificationDeleteReq req) {
    	return prodSpecificationService.deleteSpec(req);
    }

    
    @ApiOperation(value = "通过ID查看规格", notes = "传入specId，进行查询操作")
    @ApiImplicitParam(name = "specId", value = "specId", paramType = "query", required = true, dataType = "String")
    @ApiResponses({
            @ApiResponse(code=400,message="请求参数没填好"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
    @GetMapping(value="getSpec")
    public ResultVO<ProdSpecificationGetResp> getSpec(@RequestParam(value = "specId") String specId) {
        log.info("SpecController.getSpec specId={}", specId);
        ProdSpecificationGetReq req = new ProdSpecificationGetReq();
        req.setSpecId(specId);
        return prodSpecificationService.getSpec(req);
    }

    
    @ApiOperation(value = "添加规格值", notes = "传入ProdSpecValuesAddReq")
    @ApiResponses({
            @ApiResponse(code=400,message="请求参数没填好"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
    @PostMapping(value="addSpecValue")
    public ResultVO<Integer> addSpecValue(@RequestBody ProdSpecValuesAddReq req) {
        return prodSpecValuesService.addSpecValues(req);
    }

    
    @ApiOperation(value = "通过规格ID查看规格值", notes = "传入specId，进行查询操作")
    @ApiImplicitParam(name = "specId", value = "specId", paramType = "query", required = true, dataType = "String")
    @ApiResponses({
            @ApiResponse(code=400,message="请求参数没填好"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
    @GetMapping(value="listSpecValue")
    public ResultVO<List<ProdSpecValuesGetResp>> listSpecValue(@RequestParam(value = "specId") String specId) {
        ProdSpecValuesGetReq req = new ProdSpecValuesGetReq();
        req.setSpecId(specId);
        return prodSpecValuesService.listSpecValues(req);
       
    }

    
    @ApiOperation(value = "通过主键查看规格值", notes = "传入valueId，进行查询操作")
    @ApiImplicitParam(name = "valueId", value = "valueId", paramType = "query", required = true, dataType = "String")
    @ApiResponses({
            @ApiResponse(code=400,message="请求参数没填好"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
    @GetMapping(value="getSpecValue")
    public ResultVO<ProdSpecValuesGetResp> getSpecValue(@RequestParam(value = "valueId") String valueId) {
        ProdSpecValuesGetReq req = new ProdSpecValuesGetReq();
        req.setSpecValueId(valueId);
        return prodSpecValuesService.getSpecValues(req);
        
    }
}
