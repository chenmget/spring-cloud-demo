package com.iwhalecloud.retail.order.dbservice.impl;

import com.iwhalecloud.retail.order.OrderServiceApplication;
import com.iwhalecloud.retail.order.dto.response.CartListResp;
import com.iwhalecloud.retail.order.dto.resquest.AddCartReqDTO;
import com.iwhalecloud.retail.order.dto.resquest.DeleteCartReqDTO;
import com.iwhalecloud.retail.order.dto.resquest.ListCartReqDTO;
import com.iwhalecloud.retail.order.dto.resquest.UpdateCartReqDTO;
import com.iwhalecloud.retail.order.service.CartService;
import org.assertj.core.util.Lists;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * @Author My
 * @Date 2018/11/29
 **/
@RunWith(SpringRunner.class)
@SpringBootTest(classes = OrderServiceApplication.class)
public class CartServiceTest {
   @Autowired
    private CartService cartService;
    @Test
    public void addCart(){
        AddCartReqDTO req = new AddCartReqDTO();
        req.setPartnerId("1067695247957291009");
        req.setNum(22L);
        req.setMemberLvId("0");
        req.setMemberId("222222");
        int num = cartService.addCart(req);

    }
    @Test
    public void deleteCart(){
        DeleteCartReqDTO deleteCart = new DeleteCartReqDTO();
        List<String> ids = Lists.newArrayList();
        ids.add("11111");
        deleteCart.setCartIds(ids);
        cartService.deleteCart(deleteCart);
    }
    @Test
    public void update(){
        UpdateCartReqDTO dto = new UpdateCartReqDTO();
        dto.setNum(111);
        dto.setCartId("11111");
        dto.setPartnerId("1067695247957291009");
        cartService.updateCart(dto);
    }
    @Test
    public void selectCart(){
        ListCartReqDTO req = new ListCartReqDTO();
        req.setPartnerId("1067695247957291009");
        req.setMemberId("222222");
        req.setItemType(0);
        CartListResp resp =cartService.listCart(req);
        System.out.println(resp.getGoodsItemList());
    }


}
