package com.iwhalecloud.retail.web.controller.b2b.goods;

import com.alibaba.dubbo.config.annotation.Reference;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.goods2b.dto.req.ProductExtAddReq;
import com.iwhalecloud.retail.goods2b.dto.req.ProductExtDeleteReq;
import com.iwhalecloud.retail.goods2b.dto.req.ProductExtGetReq;
import com.iwhalecloud.retail.goods2b.dto.req.ProductExtUpdateReq;
import com.iwhalecloud.retail.goods2b.dto.resp.ProductExtGetResp;
import com.iwhalecloud.retail.goods2b.service.dubbo.ProductExtService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author he.sw
 * @date 2018/12/04
 */
@RestController
@RequestMapping("/api/b2b/productExt")
@Slf4j
public class ProductExtB2BController {

	@Reference
    private ProductExtService productExtService;

    @ApiOperation(value = "通过ID查看产品扩展参数", notes = "传入ID，进行查询操作")
    @ApiImplicitParam(name = "productBaseId", value = "productBaseId", paramType = "query", required = true, dataType = "String")
    @ApiResponses({
            @ApiResponse(code=400,message="请求参数没填好"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
    @GetMapping(value="getSpec")
    public ResultVO<ProductExtGetResp> getSpec(@RequestParam(value = "productBaseId") String productBaseId) {
        ProductExtGetReq req = new ProductExtGetReq();
        req.setProductBaseId(productBaseId);
        return productExtService.getProductExt(req);
    }


    @ApiOperation(value = "查看所有产品扩展参数", notes = "查看所有可用产品扩展参数")
    @ApiResponses({
            @ApiResponse(code=400,message="请求参数没填好"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
    @GetMapping(value="listAll")
    public ResultVO<List<ProductExtGetResp>> listAll() {
        return productExtService.listAll();
    }

	
    @ApiOperation(value = "添加产品扩展参数", notes = "ProductExtAddReq，进行添加操作")
    @ApiResponses({
            @ApiResponse(code=400,message="请求参数没填好"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
    @PostMapping(value="addProductExt")
    public ResultVO<Integer> addProductExt(@RequestBody ProductExtAddReq req) {
        return productExtService.addProductExt(req);
        
    }

    
    @ApiOperation(value = "更新产品扩展参数", notes = "传入ProdSpecificationUpdateReq，进行更新操作")
    @ApiResponses({
            @ApiResponse(code=400,message="请求参数没填好"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
    @PutMapping(value="updateProductExt")
    public ResultVO<Integer> updateProductExt(@RequestBody ProductExtUpdateReq req) {
    	return productExtService.updateProductExt(req);
    }
    

    @ApiOperation(value = "删除产品扩展参数", notes = "传入ProdSpecificationDeleteReq，进行删除操作")
    @ApiImplicitParam(name = "productBaseId", value = "productBaseId", paramType = "query", required = true, dataType = "String")
    @ApiResponses({
            @ApiResponse(code=400,message="请求参数没填好"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
    @DeleteMapping(value="deleteProductExt")
    public ResultVO<Integer> deleteProductExt(@RequestParam(value = "productBaseId") String productBaseId) {
        ProductExtDeleteReq req = new ProductExtDeleteReq();
        req.setProductBaseId(productBaseId);
        return productExtService.deleteProductExt(req);
    }

}
