package com.iwhalecloud.retail.partner.service.impl;

import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.partner.PartnerServiceApplication;
import com.iwhalecloud.retail.partner.common.PartnerConst;
import com.iwhalecloud.retail.partner.dto.req.*;
import com.iwhalecloud.retail.partner.service.BusinessEntityService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest(classes = PartnerServiceApplication.class)
@RunWith(SpringRunner.class)
public class BusinessEntityTest {

    @Autowired
    private BusinessEntityService businessEntityService;

    @Test
    public void get(){
        BusinessEntityGetReq req = new BusinessEntityGetReq();
        req.setBusinessEntityCode("1234");
        ResultVO businessEntityDTO = businessEntityService.getBusinessEntity(req);
        System.out.print("结果：" + businessEntityDTO.toString());
    }

    @Test
    public void add(){
        BusinessEntitySaveReq req = new BusinessEntitySaveReq();
        req.setBusinessEntityCode("11113");
        req.setBusinessEntityLevel("1");
        req.setBusinessEntityName("经营主体测试");
        req.setBusinessEntityShortName("经营主体测试");
        req.setParentBusinessEntityCode("1");
        req.setStatus("1");
        ResultVO businessEntityDTO = businessEntityService.saveBusinessEntity(req);
        System.out.print("结果：" + businessEntityDTO.toString());
    }

    @Test
    public void update(){
        BusinessEntityUpdateReq req = new BusinessEntityUpdateReq();
        req.setBusinessEntityId("1077157890474708994");
        req.setBusinessEntityCode("123451");
        req.setBusinessEntityLevel("11");
        req.setBusinessEntityName("经营主体测试21");
        req.setBusinessEntityShortName("经营主体测试21");
        req.setParentBusinessEntityCode("11");

        ResultVO result = businessEntityService.updateBusinessEntity(req);
        System.out.print("结果：" + result);
    }

    @Test
    public void page(){
        BusinessEntityPageReq req = new BusinessEntityPageReq();
        req.setStatus(PartnerConst.CommonState.VALID.getCode());
//        req.setBusinessEntityCode("123451");
//        req.setBusinessEntityName("经营主体测试21");

        ResultVO result = businessEntityService.pageBusinessEntity(req);
        System.out.print("结果：" + result.toString());
    }

    @Test
    public void list(){
        BusinessEntityListReq req = new BusinessEntityListReq();
//        req.setBusinessEntityCode("123451");
//        req.setBusinessEntityName("经营主体测试21");

        ResultVO result = businessEntityService.listBusinessEntity(req);
        System.out.print("结果：" + result.toString());
    }

}
