package com.iwhalecloud.retail.system;

import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.system.common.SysOrgConst;
import com.iwhalecloud.retail.system.dto.request.OrganizationChildListReq;
import com.iwhalecloud.retail.system.dto.request.OrganizationListReq;
import com.iwhalecloud.retail.system.dto.response.OrganizationListResp;
import com.iwhalecloud.retail.system.dto.response.OrganizationRegionResp;
import com.iwhalecloud.retail.system.service.OrganizationService;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.util.Lists;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SystemServiceApplication.class)
public class OrganizationServerTest {

    @Autowired
    private OrganizationService organizationService;



    @org.junit.Test
    public void queryRegionOrganizationId(){
        ResultVO<List<OrganizationRegionResp>> resultVO = organizationService.queryRegionOrganizationId();
        System.out.print("结果：" + resultVO.getResultData().toString());
    }

    @org.junit.Test
    public void listOrganizationChild(){
        OrganizationChildListReq req = new OrganizationChildListReq();
        req.setOrgIdList(Lists.newArrayList("843073102060000", "84307300504000"));
        req.setOrgLevel(SysOrgConst.ORG_LEVEL.LEVEL_6.getCode());
        ResultVO<List<OrganizationListResp>> resultVO = organizationService.listOrganizationChild(req);
        System.out.print("结果：" + resultVO.getResultData().toString());
    }

    @org.junit.Test
    public void listOrganization(){
        OrganizationListReq req = new OrganizationListReq();
        req.setOrgIdList(Lists.newArrayList("843073102060000", "84307300504000"));
//        req.setOrgLevel(SysOrgConst.ORG_LEVEL.LEVEL_6.getCode());
        ResultVO<List<OrganizationListResp>> resultVO = organizationService.listOrganization(req);
        System.out.print("结果：" + resultVO.getResultData().toString());
    }


}
