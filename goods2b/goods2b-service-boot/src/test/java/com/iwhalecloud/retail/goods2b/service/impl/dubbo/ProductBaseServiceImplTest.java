package com.iwhalecloud.retail.goods2b.service.impl.dubbo;

import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.goods2b.Goods2BServiceApplication;
import com.iwhalecloud.retail.goods2b.dto.req.OldProductBaseUpdateReq;
import com.iwhalecloud.retail.goods2b.dto.req.ProductBaseUpdateReq;
import com.iwhalecloud.retail.goods2b.dto.req.ProductUpdateReq;
import com.iwhalecloud.retail.goods2b.service.dubbo.ProductBaseService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Goods2BServiceApplication.class)
public class ProductBaseServiceImplTest {

    @Autowired
    private ProductBaseService productBaseService;

    @Test
    public void updateAvgApplyPrice() {
        ProductBaseUpdateReq req = new ProductBaseUpdateReq();
        req.setProductBaseId("100000150");
        ResultVO<Boolean> resultVO = productBaseService.updateAvgApplyPrice(req);
        System.out.println(resultVO.isSuccess());
    }

    @Test
    public void update() {
        ProductBaseUpdateReq req = new ProductBaseUpdateReq();
        OldProductBaseUpdateReq oldreq =new OldProductBaseUpdateReq();
        oldreq.setSallingPoint("小米卖点测试");
        oldreq.setProductName("小米卖点测试");
        List<ProductUpdateReq> oldproductUpdateReqs = new ArrayList<>();
        ProductUpdateReq oldupdatereq = new ProductUpdateReq();
        oldupdatereq.setProductId("12335343");
        oldupdatereq.setSn("1111111111111111111");
        oldupdatereq.setCost(12000D);
        oldproductUpdateReqs.add(oldupdatereq);
        oldreq.setProductUpdateReqs(oldproductUpdateReqs);
        req.setOldProductBaseUpdateReq(oldreq);

        req.setProductBaseId("12335342");
        req.setProductName("小米卖点测试123");
        req.setSallingPoint("小米卖点测试123");
        List<ProductUpdateReq> newproductUpdateReqs = new ArrayList<>();
        ProductUpdateReq newupdatereq = new ProductUpdateReq();
        newupdatereq.setProductId("12335343");
        newupdatereq.setSn("1111111111111111111");
        newupdatereq.setCost(13000D);
        newproductUpdateReqs.add(newupdatereq);
        req.setProductUpdateReqs(newproductUpdateReqs);

        ResultVO<Integer> resultVO = productBaseService.updateProductBase(req);
        System.out.println(resultVO.isSuccess());
    }
}