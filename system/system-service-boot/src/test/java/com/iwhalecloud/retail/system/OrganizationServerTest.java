package com.iwhalecloud.retail.system;

import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.system.dto.response.OrganizationRegionResp;
import com.iwhalecloud.retail.system.service.OrganizationService;
import lombok.extern.slf4j.Slf4j;
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



}
