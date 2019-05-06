package com.iwhalecloud.retail.warehouse.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.warehouse.WarehouseServiceApplication;
import com.iwhalecloud.retail.warehouse.common.ResourceConst;
import com.iwhalecloud.retail.warehouse.dto.request.ResourceInstAddReq;
import com.iwhalecloud.retail.warehouse.dto.request.ResourceUploadTempListPageReq;
import com.iwhalecloud.retail.warehouse.dto.response.ResourceUploadTempListResp;
import com.iwhalecloud.retail.warehouse.entity.ResouceUploadTemp;
import com.iwhalecloud.retail.warehouse.manager.ResourceUploadTempManager;
import com.iwhalecloud.retail.warehouse.service.MerchantResourceInstService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 */

/**
 * @author 吴良勇
 * @date 2019/3/4 16:52
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = WarehouseServiceApplication.class)
@Slf4j
public class ResourceUploadTempServiceTest {
    @Autowired
    private ResourceUploadTempManager resourceUploadTempManager;

    @Autowired
    private MerchantResourceInstService merchantResourceInstService;

    @Test
    public void saveBatch() {
        Date now = new Date();
        List<ResouceUploadTemp> instList = new ArrayList<ResouceUploadTemp>(100);
        for (Integer i = 0; i < 100; i++) {
            ResouceUploadTemp inst = new ResouceUploadTemp();
            inst.setMktResUploadBatch("10000");
            inst.setMktResInstNbr("100"+i);
            inst.setResult(ResourceConst.CONSTANT_NO);
            inst.setUploadDate(now);
            inst.setCreateDate(now);
            inst.setCreateStaff("11111");
            instList.add(inst);
        }
        resourceUploadTempManager.saveBatch(instList);


    }


    @Test
    public void listResourceUploadTemp() {
        ResourceUploadTempListPageReq req = new ResourceUploadTempListPageReq();
        req.setMktResUploadBatch("10000");
        req.setPageNo(0);
        req.setPageSize(100);
        Page<ResourceUploadTempListResp> resultVO = resourceUploadTempManager.listResourceUploadTemp(req);
        List<ResourceUploadTempListResp> list = resultVO.getRecords();
        System.out.println("result="+list.toString());
    }

    @Test
    public void addResourceInst() {
        ResourceInstAddReq req = new ResourceInstAddReq();
        req.setMerchantId("4331301049127");
        req.setDestStoreId("10134425");
        req.setCreateStaff("11111");
        req.setEventType(ResourceConst.EVENTTYPE.PUT_STORAGE.getCode());
        req.setLanId("731");
        req.setMktResId("100000454");
        req.setMktResInstNbrs(Lists.newArrayList("1234","1235","1236","1237","1238","1239","1240","1241",
                "1242","1243","1244","1245","1246","1247","1248","1249","1250",
                "1251","1252","1253","1254","1255","1256","1257","1258","1259",
                "1260","1261","1262","1263","1264","1265","1266","1267","1268","1269"));
        ResultVO resultVO = merchantResourceInstService.addResourceInst(req);
        System.out.println("result="+resultVO.toString());
    }


}
