package com.iwhalecloud.retail.order2b.service.impl;

import com.iwhalecloud.retail.order2b.TestBase;
import com.iwhalecloud.retail.order2b.busiservice.BuilderCartService;
import com.iwhalecloud.retail.order2b.dto.resquest.cart.AddCartReq;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class CartServiceTest extends TestBase {

    @Autowired
    private BuilderCartService builderCartService;

    @Test
    public void test(){
//        product_id = '1098427109759229953'
//        AND user_id = '1082191485979451394'
//        AND goods_id = '1098756635756294145'
//        AND supplier_id = '4301811022885'
        AddCartReq addCartReqDTO=new AddCartReq();
        addCartReqDTO.setSupplierId("4301811022885");
        addCartReqDTO.setGoodsId("1098756635756294145");
        addCartReqDTO.setNum(1l);
        addCartReqDTO.setProductId("1098427109759229953");
        addCartReqDTO.setUserId("1082191485979451394");
        builderCartService.goodsRulesCheck(addCartReqDTO);
    }
}
