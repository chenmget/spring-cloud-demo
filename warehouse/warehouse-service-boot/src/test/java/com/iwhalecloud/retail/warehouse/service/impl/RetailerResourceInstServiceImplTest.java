package com.iwhalecloud.retail.warehouse.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.warehouse.WarehouseServiceApplication;
import com.iwhalecloud.retail.warehouse.common.ResourceConst;
import com.iwhalecloud.retail.warehouse.dto.request.*;
import com.iwhalecloud.retail.warehouse.dto.response.ResourceInstListPageResp;
import com.iwhalecloud.retail.warehouse.mapper.ResourceReqDetailMapper;
import com.iwhalecloud.retail.warehouse.service.AdminResourceInstService;
import com.iwhalecloud.retail.warehouse.service.RetailerResourceInstService;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.util.Lists;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = WarehouseServiceApplication.class)
@Slf4j
public class RetailerResourceInstServiceImplTest {

    @Reference
    private RetailerResourceInstService retailerResourceInstService;
    @Reference
    private AdminResourceInstService adminResourceInstService;

    @Autowired
    private ResourceReqDetailMapper resourceReqDetailMapper;

    @Test
    public void addResourceInstByGreenChannel() {
        String json = "{\"createStaff\":\"1082191485979451394\",\"ctCode\":{\"201901211609003\":\"9003\"},\"merchantId\":\"4300001063072\",\"mktResId\":\"1085440543258468353\",\"mktResInstNbrs\":[\"201901211609003\"],\"regionId\":\"0731\",\"sourceType\":\"2\",\"statusCd\":\"1302\"}";
        json = "{\"mktResId\":\"1089765781184110594\",\"regionId\":\"0731\",\"merchantId\":\"4300001063072\",\"mktResInstNbrs\":[\"1231111\"]}";

        json = "{\"mktResId\":\"1089765444700266498\",\"regionId\":\"0731\",\"merchantId\":\"4300001063072\",\"mktResInstNbrs\":[\"123111\",\"123222\"]}";
        Gson gson = new Gson();
        ResourceInstAddReq req =  gson.fromJson(json, new TypeToken<ResourceInstAddReq>(){}.getType());
        ResultVO resultVO = retailerResourceInstService.addResourceInstByGreenChannel(req);
        log.info("RetailerResourceInstServiceImplTest.addResourceInstByGreenChannel result:{}", JSON.toJSONString(resultVO));
    }

    @Test
    public void delResourceInst() {
        String json = "{\"merchantId\":\"4300001063072\",\"mktResInstNbrs\":[\"123\"],\"updateDate\":1551940288965}";
        Gson gson = new Gson();
        ResourceInstUpdateReq req =  gson.fromJson(json, new TypeToken<ResourceInstUpdateReq>(){}.getType());
        ResultVO resultVO = retailerResourceInstService.delResourceInst(req);
        log.info("RetailerResourceInstServiceImplTest.delResourceInst result:{}", JSON.toJSONString(resultVO));
    }

    @Test
    public void listResourceInst() {
        ResourceInstListPageReq req = new ResourceInstListPageReq();
        req.setMktResInstNbrs(Lists.newArrayList("A000008E25F8CA"));
        ResultVO<Page<ResourceInstListPageResp>> pageResultVO = retailerResourceInstService.listResourceInst(req);
        System.out.println("RetailerResourceInstServiceImplTest.listResourceInst "+pageResultVO);
    }
    /**
     * 省包调拨-查询串码
     */
    @Test
    public void getBatch() {
        String json = "{\"mktResInstNbrs\":[\"201901211426001\"],\"mktResStoreId\":\"32\"}";
        Gson gson = new Gson();
        ResourceInstBatchReq req =  gson.fromJson(json, new TypeToken<ResourceInstBatchReq>(){}.getType());
        req.setStatusCd(ResourceConst.STATUSCD.AVAILABLE.getCode());
        retailerResourceInstService.getBatch(req);
    }
    /**
     * 省包调拨-查询串码
     */
    @Test
    public void updateResourceInstByIds() {
        AdminResourceInstDelReq req = new AdminResourceInstDelReq();
        req.setUpdateStaff("99999");
        req.setMktResInstIdList(Lists.newArrayList("6999688484883835B"));
        req.setDestStoreId("102654034");
        req.setStatusCd(ResourceConst.STATUSCD.DELETED.getCode());
        req.setEventType(ResourceConst.EVENTTYPE.CANCEL.getCode());

        // 只有可用状态的串码才能删除
        List<String> checkStatusCd = Lists.newArrayList(
                ResourceConst.STATUSCD.DELETED.getCode(),
                ResourceConst.STATUSCD.AUDITING.getCode(),
                ResourceConst.STATUSCD.ALLOCATIONING.getCode(),
                ResourceConst.STATUSCD.RESTORAGEING.getCode(),
                ResourceConst.STATUSCD.RESTORAGED.getCode(),
                ResourceConst.STATUSCD.SALED.getCode());
        req.setCheckStatusCd(checkStatusCd);
        adminResourceInstService.updateResourceInstByIds(req);
    }

    @Test
    public void getProcessingNbrList() {
        List<String> list = Lists.newArrayList("201903192105","201903192108");
        List<String> nbrList = resourceReqDetailMapper.getProcessingNbrList(list);
        System.out.print(nbrList.toString());

    }
}