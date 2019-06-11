package com.iwhalecloud.retail.goods2b.service.impl.dubbo;

import com.alibaba.fastjson.JSON;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.goods2b.Goods2BServiceApplication;
import com.iwhalecloud.retail.goods2b.dto.req.CatAddReq;
import com.iwhalecloud.retail.goods2b.service.dubbo.CatService;
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
public class CatServiceImplTest {

    @Autowired
    private CatService catService;

    @Test
    public void addCatComplex() {
        CatAddReq req = new CatAddReq();
        ResultVO resultVO = catService.addProdCat(req);
        log.info("resultVO: {}", JSON.toJSONString(resultVO));
    }
}
