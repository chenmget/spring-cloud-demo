package com.iwhalecloud.retail.web.controller.b2b.goods;


import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import com.iwhalecloud.retail.dto.ResultCodeEnum;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.goods2b.common.GoodsResultCodeEnum;
import com.iwhalecloud.retail.goods2b.dto.ProductDTO;
import com.iwhalecloud.retail.goods2b.dto.req.*;
import com.iwhalecloud.retail.goods2b.dto.resp.ProductPageResp;
import com.iwhalecloud.retail.goods2b.dto.resp.ProductResourceResp;
import com.iwhalecloud.retail.goods2b.dto.resp.ProductResp;
import com.iwhalecloud.retail.goods2b.service.dubbo.ProductService;
import com.iwhalecloud.retail.partner.service.MerchantRulesService;
import com.iwhalecloud.retail.system.common.SystemConst;
import com.iwhalecloud.retail.web.annotation.UserLoginToken;
import com.iwhalecloud.retail.web.controller.b2b.goods.request.ProductAddReqDTO;
import com.iwhalecloud.retail.web.controller.b2b.goods.request.ProductUpdateReqDTO;
import com.iwhalecloud.retail.web.interceptor.UserContext;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author he.sw
 * @date 2018/12/24
 */
@RestController
@RequestMapping("/api/b2b/goodsProduct")
@Slf4j
public class GoodsProductB2BController {

	@Reference
    private ProductService productService;

    @Reference
    private MerchantRulesService merchantRulesService;


	@ApiOperation(value = "产品详情查询", notes = "传入产品ID进行查询操作")
	@ApiImplicitParams({
        @ApiImplicitParam(name = "productId", value = "productId", paramType = "query", required = true, dataType = "String")
    })
    @ApiResponses({
            @ApiResponse(code=400,message="请求参数没填好"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
    @GetMapping(value="getProduct")
    public ResultVO<ProductResp> getProduct(@RequestParam(value = "productId", required = true) String productId) {
        if (StringUtils.isEmpty(productId)) {
            ResultVO.errorEnum(ResultCodeEnum.LACK_OF_PARAM);
        }
        ProductGetByIdReq req = new ProductGetByIdReq();
        req.setProductId(productId);
        return productService.getProduct(req);
    }



	@ApiOperation(value = "添加产品", notes = "添加操作")
    @ApiResponses({
            @ApiResponse(code=400,message="请求参数没填好"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
    @PostMapping(value="addProduct")
    @UserLoginToken
    public ResultVO<String> addProduct(@RequestBody @Valid ProductAddReqDTO dto){
        // 获取userId
        String userId = UserContext.getUserId();
        ProductAddReq req = new ProductAddReq();
        BeanUtils.copyProperties(dto, req);
        req.setCreateStaff(userId);
        return ResultVO.success(productService.addProduct(req));
        
    }

    @ApiOperation(value = "添加产品-中台", notes = "添加操作-中台")
    @ApiResponses({
            @ApiResponse(code=400,message="请求参数没填好"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
    @PostMapping(value="addProductByZT")
    public ResultVO<String> addProductByZT(@RequestBody @Valid ProductAddReqDTO dto){
        // 获取userId
        String userId = UserContext.getUserId();
        ProductAddReq req = new ProductAddReq();
        BeanUtils.copyProperties(dto, req);
        req.setCreateStaff(userId);
        return productService.addProductByZT(req);

    }

	@ApiOperation(value = "更新产品", notes = "更新操作")
    @ApiResponses({
            @ApiResponse(code=400,message="请求参数没填好"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
    @PutMapping(value="updateProdProduct")
    @UserLoginToken
    public ResultVO<Integer> updateProdProduct(@Valid @RequestBody ProductUpdateReqDTO dto) {
        // 获取userId
        String userId = UserContext.getUserId();
        ProductUpdateReq req = new ProductUpdateReq();
        BeanUtils.copyProperties(dto, req);
        req.setUpdateStaff(userId);
        return productService.updateProdProduct(req);
        
    }

    @ApiOperation(value = "批量更新产品", notes = "批量更新产品")
    @ApiResponses({
            @ApiResponse(code=400,message="请求参数没填好"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
    @PutMapping(value="bacthUpdateProdProduct")
    @UserLoginToken
    public ResultVO<Integer> bacthUpdateProdProduct(@Valid @RequestBody List<ProductUpdateReqDTO> dtoList) {
        List<ProductUpdateReq> productUpdateReqs =new ArrayList<>();
        for(ProductUpdateReqDTO dto: dtoList){
            // 获取userId
            String userId = UserContext.getUserId();
            ProductUpdateReq req = new ProductUpdateReq();
            BeanUtils.copyProperties(dto, req);
            req.setUpdateStaff(userId);
            productUpdateReqs.add(req);
        }
        return productService.batchUpdateProdProduct(productUpdateReqs);
    }

    @ApiOperation(value = "删除产品", notes = "删除操作")
    @ApiResponses({
            @ApiResponse(code=400,message="请求参数没填好"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
    @DeleteMapping(value="deleteProdProduct")
    public ResultVO<Integer> deleteProdProduct(@RequestParam String productId) {
        PrdoProductDeleteReq req = new PrdoProductDeleteReq();
        req.setProductId(productId);
	    return productService.deleteProdProduct(req);
    }

    @ApiOperation(value = "批量删除产品", notes = "批量删除操作")
    @ApiResponses({
            @ApiResponse(code=400,message="请求参数没填好"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
    @DeleteMapping(value="batchDeleteProdProduct")
    public ResultVO<Integer> batchDeleteProdProduct(@RequestParam List<String> productIds) {
        List<PrdoProductDeleteReq> reqs = new ArrayList<>();
        if(!CollectionUtils.isEmpty(productIds)){
            for(String productId : productIds){
                PrdoProductDeleteReq req = new PrdoProductDeleteReq();
                req.setProductId(productId);
                reqs.add(req);
            }
        }
        return productService.batchDeleteProdProduct(reqs);
    }

    @ApiOperation(value = "查询产品", notes = "查询操作")
    @ApiResponses({
            @ApiResponse(code=400,message="请求参数没填好"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
    @PostMapping(value="selectProduct")
    @UserLoginToken
    public ResultVO<Page<ProductDTO>> selectProduct(@RequestBody ProductGetReq req){
        List<String> productIdList = null;
        req.setProductIdList(productIdList);
        Boolean isAdminType = UserContext.isAdminType();
        String merchantId = null;
        Boolean getPermission = false;
        if (UserContext.getUserOtherMsg() != null && UserContext.getUserOtherMsg().getMerchant() != null && !isAdminType) {
            merchantId = UserContext.getUserOtherMsg().getMerchant().getMerchantId();
        }else if(isAdminType && StringUtils.isNotBlank(req.getMerchantId())){
            // 管理员选产品如果传过来商家id则查看权限；没传的话查看全部
            merchantId = req.getMerchantId();
        }else if(isAdminType && StringUtils.isBlank(req.getMerchantId())){
            // 管理员选产品如果没传过来商家id；查看全部
            ResultVO<Page<ProductDTO>> pageResultVO = productService.selectProduct(req);
            List<ProductDTO> list = pageResultVO.getResultData().getRecords();
            log.info("GoodsProductB2BController.selectProduct req={}, resp={}", JSON.toJSONString(req), JSON.toJSONString(list));
            return pageResultVO;
        }

        try {
            ResultVO<List<String>> listResultVO = merchantRulesService.getProductAndBrandPermission(merchantId);
            log.info("GoodsProductB2BController.selectProduct.getProductAndBrandPermission req={}, merchantId={}", merchantId, JSON.toJSONString(listResultVO));
            if (listResultVO.isSuccess() && !CollectionUtils.isEmpty(listResultVO.getResultData())) {
                productIdList = listResultVO.getResultData();
                req.setProductIdList(productIdList);
            } else {
                return ResultVO.success(new Page<ProductDTO>());
            }
        } catch (Exception ex) {
            log.error("GoodsProductB2BController.selectProduct getProductAndBrandPermission throw exception ex={}", ex);
            return ResultVO.error(GoodsResultCodeEnum.INVOKE_PARTNER_SERVICE_EXCEPTION);
        }

        ResultVO<Page<ProductDTO>> pageResultVO = productService.selectProduct(req);
        List<ProductDTO> list = pageResultVO.getResultData().getRecords();
        log.info("GoodsProductB2BController.selectProduct req={}, resp={}", JSON.toJSONString(req), JSON.toJSONString(list));
        return pageResultVO;
    }

    @ApiOperation(value = "分页查询产品", notes = "条件分页查询")
    @ApiResponses({
            @ApiResponse(code=400,message="请求参数没填好"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
    @PostMapping(value="selectPageProductAdmin")
    @UserLoginToken
    public ResultVO<Page<ProductPageResp>> selectPageProductAdmin(@RequestBody ProductsPageReq req) {
        Boolean isAdminType = UserContext.isAdminType();
        String merchantId = null;
        Integer userFounder = UserContext.getUser().getUserFounder();
        if (UserContext.getUserOtherMsg() != null && UserContext.getUserOtherMsg().getMerchant() != null && !isAdminType && SystemConst.USER_FOUNDER_8 != userFounder) {
            merchantId = UserContext.getUserOtherMsg().getMerchant().getMerchantId();
        }else if(isAdminType){
            // 管理员查看所有
            ResultVO<Page<ProductPageResp>> productPageRespPage = productService.selectPageProductAdmin(req);
            List<ProductPageResp> list = productPageRespPage.getResultData().getRecords();
            log.info("GoodsProductB2BController.selectPageProductAdmin.getProductAndBrandPermission req={}, resp={}", JSON.toJSONString(req), JSON.toJSONString(list));
            return productPageRespPage;
        }else if(SystemConst.USER_FOUNDER_8 == userFounder){
            // 厂商查看自己的产品
            merchantId = UserContext.getUserOtherMsg().getMerchant().getMerchantId();
            req.setManufacturerId(merchantId);
            ResultVO<Page<ProductPageResp>> productPageRespPage = productService.selectPageProductAdmin(req);
            List<ProductPageResp> list = productPageRespPage.getResultData().getRecords();
            log.info("GoodsProductB2BController.selectPageProductAdmin req={}, resp={}", JSON.toJSONString(req), JSON.toJSONString(list));
            return productPageRespPage;
        }

        // 供应商、零售商
        ResultVO<List<String>> productIdListVO = merchantRulesService.getProductAndBrandPermission(merchantId);
        log.info("GoodsProductB2BController.selectPageProductAdmin.getProductAndBrandPermission req={}, merchantId={}", merchantId, JSON.toJSONString(productIdListVO));
        if (productIdListVO.isSuccess() && !CollectionUtils.isEmpty(productIdListVO.getResultData())) {
            // // 设置机型权限
            List<String> productIdList = productIdListVO.getResultData();
            List<String> originProductList = req.getProductIdList();
            if (!CollectionUtils.isEmpty(originProductList)) {
                String nullListValue = "null";
                originProductList = originProductList.stream().filter(t -> productIdList.contains(t)).collect(Collectors.toList());
                originProductList = CollectionUtils.isEmpty(originProductList) ? Lists.newArrayList(nullListValue) : originProductList;
                req.setProductIdList(originProductList);
            }else {
                req.setProductIdList(productIdList);
            }
        }

        ResultVO<Page<ProductPageResp>> productPageRespPage = productService.selectPageProductAdmin(req);
        List<ProductPageResp> list = productPageRespPage.getResultData().getRecords();
        log.info("GoodsProductB2BController.selectPageProductAdmin req={}, resp={}", JSON.toJSONString(req), JSON.toJSONString(list));
        return productPageRespPage;
    }


    @ApiOperation(value = "分页查询产品(采购专用)", notes = "条件分页查询(采购专用)")
    @ApiResponses({
            @ApiResponse(code=400,message="请求参数没填好"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
    @PostMapping(value="selectPageProductAdmincg")
    @UserLoginToken
    public ResultVO<Page<ProductPageResp>> selectPageProductAdmincg(@RequestBody ProductsPageReq req) {
        String merchantId = null ;
        Boolean isAdminType = UserContext.isAdminType();
        Integer userFounder = UserContext.getUser().getUserFounder();
        if (UserContext.getUserOtherMsg() != null && UserContext.getUserOtherMsg().getMerchant() != null && !isAdminType && SystemConst.USER_FOUNDER_8 != userFounder) {
            merchantId = UserContext.getUserOtherMsg().getMerchant().getMerchantId();
        }else if(isAdminType){
            // 管理员查看所有
            ResultVO<Page<ProductPageResp>> productPageRespPage = productService.selectPageProductAdmin(req);
            List<ProductPageResp> list = productPageRespPage.getResultData().getRecords();
            log.info("GoodsProductB2BController.selectPageProductAdmin.getProductAndBrandPermission req={}, resp={}", JSON.toJSONString(req), JSON.toJSONString(list));
            return productPageRespPage;
        }else if(SystemConst.USER_FOUNDER_8 == userFounder){
            // 厂商查看自己的产品
            merchantId = UserContext.getUserOtherMsg().getMerchant().getMerchantId();
            req.setManufacturerId(merchantId);
            ResultVO<Page<ProductPageResp>> productPageRespPage = productService.selectPageProductAdmin(req);
            List<ProductPageResp> list = productPageRespPage.getResultData().getRecords();
            log.info("GoodsProductB2BController.selectPageProductAdmin req={}, resp={}", JSON.toJSONString(req), JSON.toJSONString(list));
            return productPageRespPage;
        }
        merchantId = req.getManufacturerId();
        // 供应商、零售商
        ResultVO<List<String>> productIdListVO = merchantRulesService.getProductAndBrandPermission(merchantId);
        log.info("GoodsProductB2BController.selectPageProductAdmin.getProductAndBrandPermission req={}, merchantId={}", merchantId, JSON.toJSONString(productIdListVO));
        if (productIdListVO.isSuccess() && !CollectionUtils.isEmpty(productIdListVO.getResultData())) {
            // // 设置机型权限
            List<String> productIdList = productIdListVO.getResultData();
            List<String> originProductList = req.getProductIdList();
            if (!CollectionUtils.isEmpty(originProductList)) {
                String nullListValue = "null";
                originProductList = originProductList.stream().filter(t -> productIdList.contains(t)).collect(Collectors.toList());
                originProductList = CollectionUtils.isEmpty(originProductList) ? Lists.newArrayList(nullListValue) : originProductList;
                req.setProductIdList(originProductList);
            }else {
                req.setProductIdList(productIdList);
            }
        }

        ResultVO<Page<ProductPageResp>> productPageRespPage = productService.selectPageProductAdmin(req);
        List<ProductPageResp> list = productPageRespPage.getResultData().getRecords();
        log.info("GoodsProductB2BController.selectPageProductAdmin req={}, resp={}", JSON.toJSONString(req), JSON.toJSONString(list));
        return productPageRespPage;

    }

    @ApiOperation(value = "绿色通道添加串码查询产品", notes = "查询操作")
    @ApiResponses({
            @ApiResponse(code=400,message="请求参数没填好"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
    @PostMapping(value="greenChannelSelectProduct")
    @UserLoginToken
    public ResultVO<Page<ProductPageResp>> greenChannelSelectProduct(@RequestBody ProductsPageReq req){
        List<String> productIdList = null;
        req.setProductIdList(productIdList);
        return productService.selectPageProductAdmin(req);
    }


    @ApiOperation(value = "分页查询产品(无权限过滤)", notes = "条件分页查询,无需登陆")
    @ApiResponses({
            @ApiResponse(code=400,message="请求参数没填好"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
    @PostMapping(value="selectPageProductAdminWithNoRight")
    public ResultVO<Page<ProductPageResp>> selectPageProductAdminWithNoRight(@RequestBody ProductsPageReq req) {
        ResultVO<Page<ProductPageResp>> productPageRespPage = productService.selectPageProductAdmin(req);
        List<ProductPageResp> list = productPageRespPage.getResultData().getRecords();
        log.info("GoodsProductB2BController.selectPageProductAdminWithNoRight req={}, resp={}", JSON.toJSONString(req), JSON.toJSONString(list));
        return productPageRespPage;
    }

    @ApiOperation(value = "根据产品名称或编码查询产品", notes = "根据产品名称或编码查询产品")
    @ApiResponses({
            @ApiResponse(code=400,message="请求参数没填好"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
    @PostMapping(value="getProductResource")
    public ResultVO<List<ProductResourceResp>> getProductResource(@RequestBody ProductResourceInstGetReq req) {
        ResultVO<List<ProductResourceResp>> productPageRespPage = productService.getProductResource(req);
        List<ProductResourceResp> list = productPageRespPage.getResultData();
        log.info("GoodsProductB2BController.getProductResource req={}, resp={}", JSON.toJSONString(req), JSON.toJSONString(list));
        return productPageRespPage;
    }
    @PostMapping(value="getDuplicate")
    public ResultVO<Integer> getDuplicate(@RequestBody ProductGetDuplicateReq req) {
        ResultVO<Integer> integerResultVO = productService.getDuplicate(req);
        log.info("GoodsProductB2BController.getDuplicate req={}, resp={}", JSON.toJSONString(req), integerResultVO);
        return integerResultVO;
    }
}
