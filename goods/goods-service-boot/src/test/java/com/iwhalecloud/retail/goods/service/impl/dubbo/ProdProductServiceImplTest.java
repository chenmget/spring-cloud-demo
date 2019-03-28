package com.iwhalecloud.retail.goods.service.impl.dubbo;

import com.iwhalecloud.retail.goods.GoodsServiceApplication;
import com.iwhalecloud.retail.goods.dto.ProdProductDTO;
import com.iwhalecloud.retail.goods.dto.ResultVO;
import com.iwhalecloud.retail.goods.service.dubbo.ProdProductService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = GoodsServiceApplication.class)
public class ProdProductServiceImplTest {

    @Autowired
    private ProdProductService prodProductService;

    @Test
    public void getProduct() {

        String productId = "1067695247957291009";
        ProdProductDTO prodProductDTO = prodProductService.getProduct(productId);
        System.out.println(prodProductDTO.getName());
    }

    @Test
    public void queryProductByGoodsId() {

        String goodsId = "1067698200642428929";
        ResultVO<List<ProdProductDTO>> listResultVO = prodProductService.queryProductByGoodsId(goodsId);
        System.out.println(listResultVO.isSuccess());
    }
}