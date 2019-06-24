package com.iwhalecloud.retail.warehouse.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.warehouse.WarehouseServiceApplication;
import com.iwhalecloud.retail.warehouse.common.ResourceConst;
import com.iwhalecloud.retail.warehouse.dto.request.ResourceInstCheckReq;
import com.iwhalecloud.retail.warehouse.dto.request.ResourceReqDetailQueryReq;
import com.iwhalecloud.retail.warehouse.dto.response.ResourceReqDetailPageResp;
import com.iwhalecloud.retail.warehouse.service.AdminResourceInstService;
import com.iwhalecloud.retail.warehouse.service.ResourceReqDetailService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = WarehouseServiceApplication.class)
@Slf4j
public class ResourceReqDetailServiceImplTest {



    @Autowired
    private ResourceReqDetailService resourceReqDetailService;

    @Autowired
    private AdminResourceInstService resourceInstService;


    @Test
    public void listResourceRequestPage(){
        ResourceReqDetailQueryReq req=new ResourceReqDetailQueryReq();
        req.setPageNo(1);
        req.setPageSize(10);
        req.setTypeId("201903142030001");
        req.setProductName("华为Mate20");
        ResultVO<Page<ResourceReqDetailPageResp>> vo=resourceReqDetailService.listResourceRequestDetailPage(req);
        System.out.println(vo);
    }

    @Test
    public void checkResourceReqDetail(){
        ResourceInstCheckReq req=new ResourceInstCheckReq();
        req.setCheckStatusCd(ResourceConst.DetailStatusCd.STATUS_CD_1004.getCode());
        List<String> list=new ArrayList<>();
        list.add("12333990");
        req.setMktResReqDetailIds(list);
        System.out.println(resourceInstService.batchAuditNbr(req));
    }



}