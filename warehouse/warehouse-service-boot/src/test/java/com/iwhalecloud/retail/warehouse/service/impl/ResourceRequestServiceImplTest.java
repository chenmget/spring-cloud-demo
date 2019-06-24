package com.iwhalecloud.retail.warehouse.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import com.iwhalecloud.retail.dto.ResultCodeEnum;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.warehouse.WarehouseServiceApplication;
import com.iwhalecloud.retail.warehouse.common.ResourceConst;
import com.iwhalecloud.retail.warehouse.dto.ResourceReqDetailDTO;
import com.iwhalecloud.retail.warehouse.dto.request.*;
import com.iwhalecloud.retail.warehouse.dto.response.ResourceRequestQueryResp;
import com.iwhalecloud.retail.warehouse.dto.response.ResourceRequestResp;
import com.iwhalecloud.retail.warehouse.manager.ResourceReqDetailManager;
import com.iwhalecloud.retail.warehouse.service.ResourceRequestService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
@RunWith(SpringRunner.class)
@SpringBootTest(classes = WarehouseServiceApplication.class)
@Slf4j
public class ResourceRequestServiceImplTest {

    @Autowired
    private ResourceRequestService requestService;
    @Autowired
    private ResourceReqDetailManager resourceReqDetailManager;
    @Test
    public void insertResourceRequest() {
        ResourceRequestAddReq addReq = new ResourceRequestAddReq();
        addReq.setReqType(ResourceConst.REQTYPE.ALLOCATE_APPLYFOR.getCode());
        addReq.setCreateStaff("Test");
        addReq.setDestStoreId("Test");
        addReq.setMktResStoreId("Test");
        addReq.setLanId("Test");
        addReq.setRegionId("Test");
        addReq.setContent("Test");
        ResourceRequestAddReq.ResourceRequestInst instDTO = new ResourceRequestAddReq.ResourceRequestInst();
        instDTO.setMktResInstId("Test");
        instDTO.setMktResInstNbr("Test");
        instDTO.setMktResId("Test");
        List<ResourceRequestAddReq.ResourceRequestInst> list = Lists.newLinkedList();
        list.add(instDTO);
        addReq.setInstList(list);
        ResultVO<String> result = requestService.insertResourceRequest(addReq);
        Assert.assertEquals(result.getResultCode(),ResultCodeEnum.SUCCESS.getCode());
    }

    @Test
    public void listResourceRequest() {
        ResourceRequestQueryReq queryReq = new ResourceRequestQueryReq();
        queryReq.setStatusCd(ResourceConst.MKTRESSTATE.NEW_CREATE.getCode());
        queryReq.setProductList(Lists.newArrayList("1077895352720969728"));
        queryReq.setInstList(Lists.newArrayList("20191111019938"));
        ResultVO<Page<ResourceRequestQueryResp>> page = requestService.listResourceRequest(queryReq);
        Assert.assertEquals(page.getResultCode(),ResultCodeEnum.SUCCESS.getCode());
    }

    @Test
    public void updateResourceRequestState() {
        ResourceRequestUpdateReq req = new ResourceRequestUpdateReq();
        req.setMktResReqId("1083926616565141506");
        req.setStatusCd(ResourceConst.MKTRESSTATE.PROCESSING.getCode());
        ResultVO<Boolean> result = requestService.updateResourceRequestState(req);
        Assert.assertEquals(result.getResultCode(),ResultCodeEnum.SUCCESS.getCode());
    }
    @Test
    public void queryResourceRequestDetail(){
        ResourceRequestItemQueryReq req = new ResourceRequestItemQueryReq();
        req.setMktResReqId("1087274333727121410");
        ResultVO<ResourceRequestResp> resp = requestService.queryResourceRequestDetail(req);
        Assert.assertEquals(resp.getResultCode(),ResultCodeEnum.SUCCESS.getCode());
    }
    @Test
    public void listDetail(){
        ResourceReqDetailQueryReq req = new ResourceReqDetailQueryReq();
        req.setMktResReqId("10131118");
        List<ResourceReqDetailDTO> resp = resourceReqDetailManager.listDetail(req);
        Assert.assertEquals(resp,ResultCodeEnum.SUCCESS.getCode());
    }
}