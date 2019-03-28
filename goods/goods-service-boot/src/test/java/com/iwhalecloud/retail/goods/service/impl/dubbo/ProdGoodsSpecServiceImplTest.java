package com.iwhalecloud.retail.goods.service.impl.dubbo;

import com.iwhalecloud.retail.goods.GoodsServiceApplication;
import com.iwhalecloud.retail.goods.dto.ResultVO;
import com.iwhalecloud.retail.goods.dto.resp.ProdGoodsSpecListResp;
import com.iwhalecloud.retail.goods.service.dubbo.ProdGoodsSpecService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = GoodsServiceApplication.class)
public class ProdGoodsSpecServiceImplTest {

    @Autowired
    private ProdGoodsSpecService prodGoodsSpecService;

    @Test
    public void listProdGoodsSpec() {
        String goodsId = "1068064486270455810";
        ResultVO<ProdGoodsSpecListResp> respResultVO = prodGoodsSpecService.listProdGoodsSpec(goodsId);
        System.out.println(respResultVO.isSuccess());
    }
}