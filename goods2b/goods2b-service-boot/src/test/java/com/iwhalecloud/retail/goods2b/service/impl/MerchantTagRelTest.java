package com.iwhalecloud.retail.goods2b.service.impl;


import com.google.common.collect.Lists;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.goods2b.Goods2BServiceApplication;
import com.iwhalecloud.retail.goods2b.dto.req.MerchantTagRelDeleteReq;
import com.iwhalecloud.retail.goods2b.dto.req.MerchantTagRelListReq;
import com.iwhalecloud.retail.goods2b.dto.req.MerchantTagRelQueryReq;
import com.iwhalecloud.retail.goods2b.dto.req.MerchantTagRelSaveReq;
import com.iwhalecloud.retail.goods2b.service.dubbo.MerchantTagRelService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


@SpringBootTest(classes = Goods2BServiceApplication.class)
@RunWith(SpringRunner.class)
public class MerchantTagRelTest {

    @Autowired
    private MerchantTagRelService merchantTagRelService;

    @Test
    public void save(){
        MerchantTagRelSaveReq req = new MerchantTagRelSaveReq();
        req.setMerchantId("666");
        req.setTagIdList(Lists.newArrayList("2", "3"));
//        req.setTagId("111");
        ResultVO resultVO = merchantTagRelService.saveMerchantTagRel(req);
        System.out.print("结果行数：" + resultVO.toString());
    }

    @Test
    public void get(){
        MerchantTagRelQueryReq req = new MerchantTagRelQueryReq();
        req.setRelId("1080796709954633730");
        ResultVO resultVO = merchantTagRelService.getMerchantTagRelById(req);
        System.out.print("结果行数：" + resultVO.toString());
    }

    @Test
    public void delete(){
        MerchantTagRelDeleteReq req = new MerchantTagRelDeleteReq();
        req.setMerchantId("666");
//        req.setTagId("111");
//        req.setRelId("1080796709954633730");
        ResultVO resultVO = merchantTagRelService.deleteMerchantTagRel(req);
        System.out.print("结果行数：" + resultVO.toString());
    }

    @Test
    public void list(){
        MerchantTagRelListReq req = new MerchantTagRelListReq();
        req.setMerchantId("4331301049127");
//        req.setTagId("112");
        ResultVO resultVO = merchantTagRelService.listMerchantTagRel(req);
        System.out.print("结果行数：" + resultVO.toString());
    }

}
