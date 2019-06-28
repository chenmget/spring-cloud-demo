package com.iwhalecloud.retail.goods2b.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.goods2b.Goods2BServiceApplication;
import com.iwhalecloud.retail.goods2b.common.ProductConst;
import com.iwhalecloud.retail.goods2b.dto.req.*;
import com.iwhalecloud.retail.goods2b.dto.resp.ProductForResourceResp;
import com.iwhalecloud.retail.goods2b.dto.resp.ProductInfoResp;
import com.iwhalecloud.retail.goods2b.dto.resp.ProductPageResp;
import com.iwhalecloud.retail.goods2b.dto.resp.ProductResp;
import com.iwhalecloud.retail.goods2b.service.dubbo.ProductService;
import com.iwhalecloud.retail.goods2b.utils.ZopClientUtil;
import com.iwhalecloud.retail.partner.service.MerchantRulesService;
import com.ztesoft.zop.common.message.ResponseResult;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Z
 * @date 2018/11/28
 */

@SpringBootTest(classes = Goods2BServiceApplication.class)
@RunWith(SpringRunner.class)
public class ProductServiceTest {

    @Resource
    private ProductService productService;
    @Resource
    private MerchantRulesService merchantRulesService;

    @Test
    public void listProductId() {
        ProductListReq req = new ProductListReq();
//        req.setStatus(ProductConst.StatusType.EFFECTIVE.getCode());
//        req.setIsDeleted(ProductConst.IsDelete.NO.getCode());
        req.setAttrValue10(ProductConst.attrValue10.EFFECTIVE.getCode());
        ResultVO<List<String>> resultVO = productService.listProductId(req);
        System.out.println("resultVO=" + JSON.toJSON(resultVO));
    }

    @Test
    public void getProductTest() {
        ProductGetByIdReq req = new ProductGetByIdReq();
        req.setProductId("1067695247957291009");
        ResultVO<ProductResp> resultVO = productService.getProduct(req);
        System.out.println("resultVO=" + JSON.toJSON(resultVO));
        Assert.assertNotNull(resultVO);
    }

    @Test
    public void addProductTags() {
        ProductTagsAddReq req = new ProductTagsAddReq();
        req.setProductId("1077895352720969730");
        req.setTagName("手机测试标签20190228");
        req.setCreateStaff("张三");
        ResultVO<Boolean> resultVO = productService.addProductTags(req);
        System.out.println("resultVO=" + JSON.toJSON(resultVO));
    }

    @Test
    public void addProduct(){
        ProductAddReq req = new ProductAddReq();
//        req.setSn("1000");
        req.setUnitName("锤子火爆款 坚果T1 灰色 16G");
        req.setCreateStaff("张三");
        String productId = productService.addProduct(req);
        System.out.println("resultVO=" + JSON.toJSON(productId));
    }

    @Test
    public void selectPageProductAdmin() {
        ProductsPageReq req = new ProductsPageReq();
//        req.setSn("1000");
//        req.setUnitName("锤子火爆款 坚果T1 灰色 16G");
//        req.setCreateStaff("张三");
        ResultVO<Page<ProductPageResp>> resultVO = productService.selectPageProductAdmin(req);
        System.out.println("resultVO=" + JSON.toJSON(resultVO));
    }

    @Test
    public void getProductForResource() {
        ProductGetByIdReq req = new ProductGetByIdReq();
        req.setProductId("100000065");
        ResultVO<ProductForResourceResp> resultVO = productService.getProductForResource(req);
        System.out.println("resultVO=" + JSON.toJSON(resultVO));
    }

    public void testupdate(){
        ProductAuditStateUpdateReq req = new ProductAuditStateUpdateReq();
        List<String> list = new ArrayList<>();
        list.add("100000092");
        list.add("100000065");
        req.setAttrValue10(ProductConst.attrValue10.SUCCESS.getCode());
        req.setProductIds(list);
        productService.updateAttrValue10(req);
    }

    @Test
    public void getProductInfoByIds() {
        List<String> list = new ArrayList<String>();
        list.add("100001019");
        list.add("100001014");
       // req.setProductId("100000065");
        List<ProductInfoResp> list1 = productService.getProductInfoByIds(list);
        for (ProductInfoResp p:list1) {
            System.out.println("resultVO=" + p.getCorporationPrice()+p.getProductId()+p.getUnitName());
        }

    }

    @Test
    public void callRest() {
        String zopSecret = "ODA1NzM5Zjg1ZDVmNDBiNGVjYzVkNzVmOGJmZTRlYmM=";
        String zopUrl = "http://134.176.102.33:8081/api/rest";
        String method = "ord.operres.OrdInventoryChange";
        Map request = new HashMap<>();
        request.put("deviceId","1234");
        request.put("userName","测试");
        request.put("code","ITMS_ADD");
        request.put("params","city_code=731# warehouse=12#source=1#factory=厂家");
        String version = "1.0";
        ResponseResult responseResult = ZopClientUtil.callRest(zopSecret, zopUrl, method, version, request);
        String resCode = "00000";
        if (resCode.equals(responseResult.getRes_code())) {
            Object result = responseResult.getResult();
            String returnStr = String.valueOf(result);
        }else{
            System.out.print("能开请求失败：method：" + method + "，zopUrl:" + zopUrl + "，resCode:"
                    + responseResult.getRes_code() + "，msg:" + responseResult.getRes_message());
        }

    }
    @Test
    public void getP() {
        String json="{\"pageNo\":1,\"pageSize\":10,\"typeId\":\"201903142030001\",\"statusList\":[3]}";
        Gson gson = new Gson();
        ProductsPageReq req  = gson.fromJson(json, new TypeToken<ProductsPageReq>(){}.getType());
//        ProductsPageReq req=new ProductsPageReq();

        ResultVO<Page<ProductPageResp>>  r=  productService.selectPageProductAdmin(req);
        System.out.println(JSON.toJSONString(r));
    }
    @Test
    public  void chackprocduct() {
        String json="{\"pageNo\":1,\"pageSize\":10,\"typeId\":\"201903142030001\",\"manufacturerId\":\"10000696\",\"statusList\":[3]}";
        Gson gson = new Gson();
        ProductsPageReq req  = gson.fromJson(json, new TypeToken<ProductsPageReq>(){}.getType());
        String merchantId = req.getManufacturerId();
        // 供应商、零售商
        ResultVO<List<String>> productIdListVO = merchantRulesService.getProductAndBrandPermission(merchantId);
       // log.info("GoodsProductB2BController.selectPageProductAdmin.getProductAndBrandPermission req={}, merchantId={}", merchantId, JSON.toJSONString(productIdListVO));
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
        //log.info("GoodsProductB2BController.selectPageProductAdmin req={}, resp={}", JSON.toJSONString(req), JSON.toJSONString(list));
    }

}
