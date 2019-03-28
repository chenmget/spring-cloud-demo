package com.iwhalecloud.retail.goods2b.service.impl.dubbo;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.goods2b.Goods2BServiceApplication;
import com.iwhalecloud.retail.goods2b.dto.req.CatComplexAddReq;
import com.iwhalecloud.retail.goods2b.dto.req.CatComplexDeleteReq;
import com.iwhalecloud.retail.goods2b.dto.req.CatComplexEditReq;
import com.iwhalecloud.retail.goods2b.dto.req.CatComplexQueryReq;
import com.iwhalecloud.retail.goods2b.dto.resp.CatComplexQueryResp;
import com.iwhalecloud.retail.goods2b.service.dubbo.CatComplexService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.List;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = Goods2BServiceApplication.class)
public class CatComplexServiceImplTest {

    @Autowired
    private CatComplexService catComplexService;

    @Test
    public void addCatComplex() {
        CatComplexAddReq req = new CatComplexAddReq();
        req.setCatId("1078913932417318914");
        req.setTargetType("2");
        req.setCreateDate(new Date());
        req.setTargetId("1077376919157895190");
        req.setTargetName("测试商品20190228");
        req.setTargetOrder(4L);
        ResultVO resultVO = catComplexService.addCatComplex(req);
        System.out.println(resultVO.getResultData());
    }

    @Test
    public void updateCatComplexSortOrder() {
        List<CatComplexAddReq> reqList = Lists.newArrayList();
        CatComplexAddReq req1 = new CatComplexAddReq();
        req1.setCatId("1078913932417318914");
        req1.setTargetType("2");
        req1.setCreateDate(new Date());
        req1.setTargetId("1077375563869319169");
        req1.setTargetName("手机");
        req1.setTargetOrder(4L);
        reqList.add(req1);
        CatComplexAddReq req2 = new CatComplexAddReq();
        req2.setCatId("1078913932417318914");
        req2.setTargetType("2");
        req2.setCreateDate(new Date());
        req2.setTargetId("1077376919157895170");
        req2.setTargetName("电脑");
        req2.setTargetOrder(3L);
        reqList.add(req2);
        CatComplexAddReq req3 = new CatComplexAddReq();
        req3.setCatId("1078913932417318914");
        req3.setTargetType("2");
        req3.setCreateDate(new Date());
        req3.setTargetId("1077376919157895171");
        req3.setTargetName("测试商品");
        req3.setTargetOrder(2L);
        reqList.add(req3);
        CatComplexAddReq req4 = new CatComplexAddReq();
        req4.setCatId("1078913932417318914");
        req4.setTargetType("2");
        req4.setCreateDate(new Date());
        req4.setTargetId("1077376919157895190");
        req4.setTargetName("测试商品20190228");
        req4.setTargetOrder(1L);
        reqList.add(req4);
        CatComplexEditReq catComplexEditReq = new CatComplexEditReq();
        catComplexEditReq.setCatComplexEditReq(reqList);
        ResultVO resultVO = catComplexService.updateCatComplexSortOrder(catComplexEditReq);
        System.out.println(resultVO.getResultData());
    }

    @Test
    public void deleteCatComplex() {
        CatComplexDeleteReq req = new CatComplexDeleteReq();
        List<String> ids = Lists.newArrayList();
        ids.add("1101019568404971522");
        req.setIds(ids);
        ResultVO resultVO = catComplexService.deleteCatComplex(req);
        System.out.println(resultVO.getResultData());
    }

    @Test
    public void queryCatComplexForPage() {
        CatComplexQueryReq req = new CatComplexQueryReq();
        req.setCatId("1078913932417318914");
        req.setTargetType("2");
        req.setPageNo(1);
        req.setPageSize(10);
        ResultVO<Page<CatComplexQueryResp>> resultVO = catComplexService.queryCatComplexForPage(req);
        System.out.println(resultVO.getResultData().getRecords());
    }

}