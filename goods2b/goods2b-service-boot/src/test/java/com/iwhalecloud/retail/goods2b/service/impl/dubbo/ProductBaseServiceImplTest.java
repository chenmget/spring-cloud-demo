package com.iwhalecloud.retail.goods2b.service.impl.dubbo;

import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.goods2b.Goods2BServiceApplication;
import com.iwhalecloud.retail.goods2b.dto.req.ProductBaseUpdateReq;
import com.iwhalecloud.retail.goods2b.service.dubbo.ProductBaseService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

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
}