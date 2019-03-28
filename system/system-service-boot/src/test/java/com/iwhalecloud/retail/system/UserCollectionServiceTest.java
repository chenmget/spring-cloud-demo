package com.iwhalecloud.retail.system;

import com.alibaba.fastjson.JSON;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.system.dto.UserCollectionDTO;
import com.iwhalecloud.retail.system.dto.request.UserCollectionJudgeReq;
import com.iwhalecloud.retail.system.dto.request.UserCollectionListReq;
import com.iwhalecloud.retail.system.service.UserCollectionService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Created by z on 2019/1/15.
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SystemServiceApplication.class)
public class UserCollectionServiceTest {

    @Resource
    private UserCollectionService userCollectionService;

    @Test
    public void testQueryUserCollection() {
        UserCollectionListReq req = new UserCollectionListReq();
        req.setUserId("1");
        req.setObjType("1");

        List<String> objIds = new ArrayList<>();
        objIds.add("B2CGoodsId2");
        objIds.add("B2CGoodsId1");
        req.setObjIds(objIds);
        ResultVO<List<UserCollectionDTO>> listResultVO =  userCollectionService.queryUserCollection(req);

        System.out.println(JSON.toJSONString(listResultVO));
    }

    @Test
    public void testBooCollection() {
        UserCollectionJudgeReq req = new UserCollectionJudgeReq();
        req.setUserId("1");
        req.setObjType("1");
        req.setObjId("B2CGoodsId12");

        ResultVO<Boolean> resultVO =  userCollectionService.booCollection(req);

        System.out.println(JSON.toJSONString(resultVO));
    }
}
