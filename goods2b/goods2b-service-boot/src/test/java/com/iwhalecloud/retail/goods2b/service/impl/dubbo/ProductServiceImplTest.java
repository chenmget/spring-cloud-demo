package com.iwhalecloud.retail.goods2b.service.impl.dubbo;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.goods2b.Goods2BServiceApplication;
import com.iwhalecloud.retail.goods2b.dto.req.ProductGetByIdReq;
import com.iwhalecloud.retail.goods2b.dto.req.ProductsPageReq;
import com.iwhalecloud.retail.goods2b.dto.resp.ProductApplyInfoResp;
import com.iwhalecloud.retail.goods2b.dto.resp.ProductPageResp;
import com.iwhalecloud.retail.goods2b.dto.resp.ProductResp;
import com.iwhalecloud.retail.goods2b.service.dubbo.ProductService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Goods2BServiceApplication.class)
public class ProductServiceImplTest {

    @Autowired
    private ProductService productService;

    @Test
    public void getProduct() {

        String productId = "1067695247957291009";
        ProductGetByIdReq req = new ProductGetByIdReq();
        req.setProductId(productId);
        ResultVO<ProductResp> resultVO =  productService.getProduct(req);
        System.out.println(JSON.toJSON(resultVO.getResultData()));
    }
    @Test
    public void test(){
        ProductsPageReq req = new ProductsPageReq();
        req.setCostLower("100000");
        req.setCostUpper("199900");
        ResultVO<Page<ProductPageResp>> pageResultVO = productService.selectPageProductAdmin(req);
        System.out.println(JSON.toJSON(pageResultVO.getResultData()));
    }
    @Test
    public void getProductApplyInfo(){
        String productId="100000065";
        ProductApplyInfoResp r= productService.getProductApplyInfo(productId);
        System.out.println(JSON.toJSON(r));
    }

}