package com.iwhalecloud.retail.web.controller.b2b.goods;


import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.dto.ResultCodeEnum;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.goods2b.dto.req.*;
import com.iwhalecloud.retail.goods2b.dto.resp.ProductBaseActivityQueryResp;
import com.iwhalecloud.retail.goods2b.dto.resp.ProductBaseGetResp;
import com.iwhalecloud.retail.goods2b.dto.resp.ProductDetailResp;
import com.iwhalecloud.retail.goods2b.exception.ProductException;
import com.iwhalecloud.retail.goods2b.service.dubbo.ProductBaseService;
import com.iwhalecloud.retail.goods2b.service.dubbo.ProductFlowService;
import com.iwhalecloud.retail.goods2b.service.dubbo.ProductService;
import com.iwhalecloud.retail.partner.service.MerchantRulesService;
import com.iwhalecloud.retail.promo.dto.ActivityProductDTO;
import com.iwhalecloud.retail.promo.dto.req.ActivityProductListReq;
import com.iwhalecloud.retail.promo.service.ActivityProductService;
import com.iwhalecloud.retail.system.common.SystemConst;
import com.iwhalecloud.retail.web.annotation.UserLoginToken;
import com.iwhalecloud.retail.web.controller.b2b.goods.request.ProductBaseAddReqDTO;
import com.iwhalecloud.retail.web.controller.b2b.goods.request.ProductBaseUpdateReqDTO;
import com.iwhalecloud.retail.web.interceptor.UserContext;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author he.sw
 * @date 2018/12/24
 */
@RestController
@RequestMapping("/api/b2b/goodsProductBase")
@Slf4j
public class GoodsProductBaseB2BController {


	@Reference(timeout = 200000)
    private ProductBaseService prodProductBaseService;

    @Reference
    private MerchantRulesService merchantRulesService;

    @Reference
    private ActivityProductService activityProductService;

    @Reference
    private ProductService productService;

    @Reference
    private ProductFlowService productFlowService;

	@ApiOperation(value = "产品基本信息查询", notes = "传入产品基本信息ID进行查询操作")
	@ApiImplicitParams({
        @ApiImplicitParam(name = "productBaseId", value = "productBaseId", paramType = "query", required = true, dataType = "String")
    })
    @ApiResponses({
            @ApiResponse(code=400,message="请求参数没填好"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
    @GetMapping(value="getProductBase")
    public ResultVO<ProductBaseGetResp> getProductBase(@RequestParam(value = "productBaseId", required = true) String productBaseId) {
        if (StringUtils.isEmpty(productBaseId)) {
            ResultVO.errorEnum(ResultCodeEnum.LACK_OF_PARAM);
        }
        ProductBaseGetByIdReq req = new ProductBaseGetByIdReq();
        req.setProductBaseId(productBaseId);
        return prodProductBaseService.getProductBase(req);
    }

	@ApiOperation(value = "查询产品基本信息查询", notes = "查询操作")
    @ApiResponses({
            @ApiResponse(code=400,message="请求参数没填好"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
    @PostMapping(value="getProductBaseList")
    @UserLoginToken
    public ResultVO<Page<ProductBaseGetResp>> getProductBaseList(@RequestBody ProductBaseListReq req) {

        if (!UserContext.isUserLogin()) {
            // 没有登陆，直接返回不能查到数据
            log.info("GoodsProductBaseB2BController 用户未登陆");
            return ResultVO.success(new Page<ProductBaseGetResp>());
        }

        String merchantId = null;
        Boolean isAdminType = UserContext.isAdminType();
        Integer userFounder = UserContext.getUser().getUserFounder();
        if (UserContext.getUserOtherMsg() != null && UserContext.getUserOtherMsg().getMerchant() != null && !isAdminType &&SystemConst.USER_FOUNDER_8 != userFounder) {
            merchantId = UserContext.getUserOtherMsg().getMerchant().getMerchantId();
        }else if(isAdminType && StringUtils.isNotBlank(req.getMerchantId())){
            // 管理员选产品如果传过来商家id则查看权限；没传的话查看全部
            merchantId = req.getMerchantId();
        }else if(isAdminType && StringUtils.isBlank(req.getMerchantId())){
            // 管理员选产品如果没传过来商家id；查看全部
            return prodProductBaseService.getProductBaseList(req);
        }else if(SystemConst.USER_FOUNDER_8 == userFounder){
            // 厂商查看自己建的产品
            merchantId = UserContext.getUserOtherMsg().getMerchant().getMerchantId();
            req.setManufacturerId(merchantId);
            return prodProductBaseService.getProductBaseList(req);
        }

        // 供应商、零售商
        ResultVO<List<String>> productIdListVO = merchantRulesService.getProductAndBrandPermission(merchantId);
        log.info("GoodsProductBaseB2BController.getProductBaseList.getProductAndBrandPermission req={}, productIdListVO={}", merchantId, JSON.toJSONString(productIdListVO));
        if (productIdListVO.isSuccess() && !CollectionUtils.isEmpty(productIdListVO.getResultData())) {
            // // 设置机型权限
            List<String> productIdList = productIdListVO.getResultData();
            req.setProductIdList(productIdList);
        }

        return prodProductBaseService.getProductBaseList(req);
    }

	@ApiOperation(value = "添加产品基本信息", notes = "添加操作")
    @ApiResponses({
            @ApiResponse(code=400,message="请求参数没填好"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
    @PostMapping(value="addProductBase")
    @UserLoginToken
    public ResultVO<String> addProductBase(@RequestBody ProductBaseAddReqDTO dto)
            throws ProductException {
        //获取memberId
        String userId = UserContext.getUserId();
        if(org.apache.commons.lang.StringUtils.isEmpty(userId)){
            ResultVO resultVO = new ResultVO();
            resultVO.setResultMsg("userId can not be null");
            resultVO.setResultCode(ResultCodeEnum.ERROR.getCode());
            return resultVO;
        }
        List purchaseTypeList = dto.getPurchaseType();
        String purchaseTypeString = "";
        //list转string
        ProductBaseAddReq req = new ProductBaseAddReq();
        if (null != purchaseTypeList && purchaseTypeList.size() > 0) {
            //list转string
            purchaseTypeString = StringUtils.join(purchaseTypeList.toArray(), ",");
        }
        BeanUtils.copyProperties(dto, req);
        req.setCreateStaff(userId);
        req.setPurchaseType(purchaseTypeString);
        return prodProductBaseService.addProductBase(req);


        
    }

	@ApiOperation(value = "更新产品基本信息", notes = "更新操作")
    @ApiResponses({
            @ApiResponse(code=400,message="请求参数没填好"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
    @PutMapping(value="updateProductBase")
    @UserLoginToken
    public ResultVO<Integer> updateProductBase(@RequestBody ProductBaseUpdateReqDTO dto) throws ProductException {

        //获取memberId
        String userId = UserContext.getUserId();
        if(org.apache.commons.lang.StringUtils.isEmpty(userId)){
            ResultVO resultVO = new ResultVO();
            resultVO.setResultMsg("userId can not be null");
            resultVO.setResultCode(ResultCodeEnum.ERROR.getCode());
            return resultVO;
        }
        List purchaseTypeList = dto.getPurchaseType();
        ProductBaseUpdateReq req = new ProductBaseUpdateReq();
        String purchaseTypeString = "";
        if (null != purchaseTypeList && purchaseTypeList.size() > 0) {
            //list转string
            purchaseTypeString = StringUtils.join(purchaseTypeList.toArray(), ",");
        }
        BeanUtils.copyProperties(dto, req);
        req.setUpdateStaff(userId);
        req.setPurchaseType(purchaseTypeString);
        return prodProductBaseService.updateProductBase(req);



        
    }

	@ApiOperation(value = "删除产品基本信息", notes = "删除操作")
    @ApiImplicitParam(name = "productBaseId", value = "productBaseId", paramType = "query", required = true, dataType = "String")
    @ApiResponses({
            @ApiResponse(code=400,message="请求参数没填好"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
    @DeleteMapping(value="deleteProdProductBase")
    public ResultVO<Integer> deleteProdProductBase(@RequestParam(value = "productBaseId", required = true) String productBaseId) {
        ProdProductBaseDeleteReq req = new ProdProductBaseDeleteReq();
        req.setProductBaseId(productBaseId);
	    return prodProductBaseService.deleteProdProductBase(req);
    }

    @ApiOperation(value = "软删除产品基本信息", notes = "删除操作")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "productBaseId", value = "productBaseId", paramType = "query", required = true, dataType = "String")
    })
    @ApiResponses({
            @ApiResponse(code=400,message="请求参数没填好"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
    @DeleteMapping(value="softDelProdProductBase")
    public ResultVO<Integer> softDelProdProductBase(@RequestParam(value = "productBaseId", required = true) String productBaseId) {
        ProdProductBaseSoftDelReq req = new ProdProductBaseSoftDelReq();
        req.setProductBaseId(productBaseId);
	    return prodProductBaseService.softDelProdProductBase(req);
    }

    @ApiOperation(value = "查询产品基本信息", notes = "跟新操作")
    @ApiResponses({
            @ApiResponse(code=400,message="请求参数没填好"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
    @PostMapping(value="selectProductBase")
    public ResultVO<List<ProductBaseGetResp>> selectProductBase(@RequestBody ProductBaseGetReq req) {
        return prodProductBaseService.selectProductBase(req);
    }

    @ApiOperation(value = "产品详情查询", notes = "品牌详情查询")
    @ApiImplicitParam(name = "productBaseId", value = "productBaseId", paramType = "query", required = true, dataType = "String")
    @ApiResponses({
            @ApiResponse(code=400,message="请求参数没填好"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
    @GetMapping(value="getProductDetail")
    public ResultVO<ProductDetailResp> getProductDetail(@RequestParam(value = "productBaseId", required = true) String productBaseId)
    {
        ProductDetailGetByBaseIdReq req = new ProductDetailGetByBaseIdReq();
        req.setProductBaseId(productBaseId);
	    return prodProductBaseService.getProductDetail(req);
    }

    @ApiOperation(value = "根据活动查询产品基本信息查询", notes = "查询操作")
    @ApiResponses({
            @ApiResponse(code=400,message="请求参数没填好"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
    @PostMapping(value="queryProductBaseByActivity")
    @UserLoginToken
    public ResultVO<ProductBaseActivityQueryResp> queryProductBaseByActivity(@RequestBody ProductBaseActivityListReq req) {

        log.info("GoodsProductBaseB2BController.queryProductBase -->req={}",JSON.toJSONString(req));
        String merchantId = getMerchantId(req.getMerchantId());
        if (StringUtils.isEmpty(merchantId)) {
            log.error("GoodsProductBaseB2BController.queryProductBase,miss args -->merchantId={}", merchantId);
            return ResultVO.error("缺少商家ID");
        }

        ResultVO<List<String>> listResultVO = merchantRulesService.getProductAndBrandPermission(merchantId);
        if (!listResultVO.isSuccess()) {
            log.error("GoodsProductBaseB2BController.queryProductBase,getProductAndBrandPermission error-->listResultVO={}"
                    ,JSON.toJSONString(listResultVO));
            return ResultVO.error("获取商家经营权限失败");
        }
        final List<String> merchantBusiProductIds = listResultVO.getResultData();
        final List<String> activityProductIds = getActivityProductIds(req.getActivityIds());

        log.info("GoodsProductBaseB2BController.queryProductBase--> merchantBusiProductIds={}", JSON.toJSONString(merchantBusiProductIds));
        log.info("GoodsProductBaseB2BController.queryProductBase--> activityProductIds={}",JSON.toJSONString(activityProductIds));
        //如果有配置商家经营权限，取活动产品和商家经营权限产品的交集
        if (!CollectionUtils.isEmpty(merchantBusiProductIds)) {
            activityProductIds.retainAll(merchantBusiProductIds);
        }

        //如果没有查询到数据，返回一个空对象
        if (CollectionUtils.isEmpty(activityProductIds)) {
            log.warn("GoodsProductBaseB2BController.queryProductBase,activityProductIds is null -->req={}",JSON.toJSONString(req));
            ProductBaseActivityQueryResp resp = new ProductBaseActivityQueryResp();
            resp.setProductBaseGetResp(new Page<ProductBaseGetResp>());
            return ResultVO.success(new ProductBaseActivityQueryResp());
        }
        req.setProductIdList(activityProductIds);

        ResultVO<Page<ProductBaseGetResp>> resultVO = prodProductBaseService.getProductBaseList(req);
        if (!resultVO.isSuccess()) {
            log.error("GoodsProductBaseB2BController.queryProductBase -->resultVO={}",JSON.toJSONString(resultVO));
            return ResultVO.error(resultVO.getResultMsg());
        }

        ProductBaseActivityQueryResp resp = new ProductBaseActivityQueryResp();
        resp.setProductBaseGetResp(resultVO.getResultData());
        resp.setProductIds(activityProductIds);
        return ResultVO.success(resp);
    }


    /**
     * 获取商家ID,如果是管理员根据传入的商家ID，如果是商家则直接获取当前登录的商家
     * @param reqMerchantId
     * @return
     */
    private String getMerchantId(String reqMerchantId) {
        Boolean isAdminType = UserContext.isAdminType();

        log.info("GoodsProductBaseB2BController.getMerchantId-->isAdminType={}",isAdminType);
        if (UserContext.isMerchant()) {
            log.info("GoodsProductBaseB2BController.getMerchantId-->MerchantId={}",UserContext.getMerchantId());
            return UserContext.getMerchantId();
        }else if(isAdminType){
            // 管理员选产品如果传过来商家id则查看权限；没传的话查看全部
            return reqMerchantId;
        }

        return null;
    }


    /**
     * 查询商家允许经营的产品ID
     * @param merchantId
     * @return
     */
    private List<String> getMerchantProductIds(String merchantId) {
        ResultVO<List<String>> listResultVO = merchantRulesService.getBusinessModelPermission(merchantId);
        if (listResultVO.isSuccess()) {
            return listResultVO.getResultData();
        }

        return null;
    }

    /**
     * 根据活动ID集合获取允许参加的产品ID集合
     * @param activityIds
     * @return
     */
    private List<String> getActivityProductIds(List<String> activityIds) {
        ActivityProductListReq req = new ActivityProductListReq();
        req.setMarketingActivityIds(activityIds);
        ResultVO<List<ActivityProductDTO>> activityProductVOs  = activityProductService.queryActivityProducts(req);
        if (!activityProductVOs.isSuccess()) {
            log.error("GoodsProductBaseB2BController.getActivityIds-->!!queryActivityProducts error!!,activityProductVOs={}"
                    ,JSON.toJSONString(activityProductVOs));
            return null;
        }

        List<ActivityProductDTO> activityProductDTOs = activityProductVOs.getResultData();
        if (activityProductDTOs == null) {
            log.warn("GoodsProductBaseB2BController.getActivityIds-->!!queryActivityProducts is null!!,activityProductVOs={}"
                    , JSON.toJSONString(activityProductVOs));

            return null;
        }

        List<String> productIds = activityProductDTOs.stream().map(ActivityProductDTO::getProductId)
                .collect(Collectors.toList());

        return productIds;
    }

}
