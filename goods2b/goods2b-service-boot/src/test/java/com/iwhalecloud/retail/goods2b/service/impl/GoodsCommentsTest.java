package com.iwhalecloud.retail.goods2b.service.impl;

import com.iwhalecloud.retail.goods2b.Goods2BServiceApplication;
import com.iwhalecloud.retail.goods2b.dto.req.AddCommentsReqDTO;
import com.iwhalecloud.retail.goods2b.service.dubbo.GoodsCommentsService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @Author My
 * @Date 2018/12/1
 **/
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Goods2BServiceApplication.class)
public class GoodsCommentsTest {
    @Autowired
    private GoodsCommentsService goodsCommentsService;
    @Test
    public void add(){
        AddCommentsReqDTO comments = new AddCommentsReqDTO();
        comments.setQuotas("sssaa");
        comments.setObjectId("2342");
        comments.setObjectType("4534");
        comments.setOrderId("2342");
        comments.setGrade(5L);
        goodsCommentsService.addComments(comments);
    }
}
