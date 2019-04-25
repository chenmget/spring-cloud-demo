package com.iwhalecloud.retail.goods2b.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.goods2b.Goods2BServiceApplication;
import com.iwhalecloud.retail.goods2b.dto.req.ProductAddReq;
import com.iwhalecloud.retail.goods2b.dto.req.ProductGetByIdReq;
import com.iwhalecloud.retail.goods2b.dto.req.ProductTagsAddReq;
import com.iwhalecloud.retail.goods2b.dto.req.ProductsPageReq;
import com.iwhalecloud.retail.goods2b.dto.resp.ProductForResourceResp;
import com.iwhalecloud.retail.goods2b.dto.resp.ProductPageResp;
import com.iwhalecloud.retail.goods2b.dto.resp.ProductResp;
import com.iwhalecloud.retail.goods2b.service.dubbo.ProductService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * @author Z
 * @date 2018/11/28
 */

@SpringBootTest(classes = Goods2BServiceApplication.class)
@RunWith(SpringRunner.class)
public class ProductServiceTest {

    @Resource
    private ProductService productService;

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
        ResultVO<Integer> resultVO = productService.addProduct(req);
        System.out.println("resultVO=" + JSON.toJSON(resultVO));
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
}
