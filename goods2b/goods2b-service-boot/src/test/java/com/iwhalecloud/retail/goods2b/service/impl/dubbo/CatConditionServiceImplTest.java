package com.iwhalecloud.retail.goods2b.service.impl.dubbo;

import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.goods2b.Goods2BServiceApplication;
import com.iwhalecloud.retail.goods2b.dto.req.CatConditionSaveReq;
import com.iwhalecloud.retail.goods2b.service.dubbo.CatConditionService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


/**
 * @author wenlong.zhong
 * @date 2019/6/11
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Goods2BServiceApplication.class)
public class CatConditionServiceImplTest {

    @Autowired
    private CatConditionService catConditionService;

    @Test
    public void save() {
        CatConditionSaveReq req = new CatConditionSaveReq();
        req.setCatId("1111");
        req.setRelType("1");
        req.setRelObjId("1111");
        req.setRelObjValue("test");
        ResultVO resultVO = catConditionService.saveCatCondition(req);
        System.out.println(resultVO.getResultData());
    }
}
