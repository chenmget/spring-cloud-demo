package com.iwhalecloud.retail.goods2b.service.impl;

import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.goods2b.Goods2BServiceApplication;
import com.iwhalecloud.retail.goods2b.dto.req.CatComplexQueryReq;
import com.iwhalecloud.retail.goods2b.dto.resp.CategoryRecommendQuery;
import com.iwhalecloud.retail.goods2b.service.dubbo.CatComplexService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * @author Z
 * @date 2018/12/3
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Goods2BServiceApplication.class)
public class CatComplexServiceImplTest {

    @Autowired
    private CatComplexService catComplexService;
    @Test
    public void test(){
        CatComplexQueryReq catComplexQueryReq = new CatComplexQueryReq();
        String catid = "1074939285852594178";
        catComplexQueryReq.setCatId(catid);
        ResultVO<List<CategoryRecommendQuery>> resultVO =  catComplexService.queryCategoryRecommend(catComplexQueryReq);
        System.out.println(resultVO);
    }
}


