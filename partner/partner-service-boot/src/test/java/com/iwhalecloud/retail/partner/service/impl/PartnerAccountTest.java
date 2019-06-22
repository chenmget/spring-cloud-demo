package com.iwhalecloud.retail.partner.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.partner.PartnerServiceApplication;
import com.iwhalecloud.retail.partner.dto.PartnerAccountDTO;
import com.iwhalecloud.retail.partner.dto.req.PartnerAccountPageReq;
import com.iwhalecloud.retail.partner.service.PartnerAccountService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest(classes = PartnerServiceApplication.class)
@RunWith(SpringRunner.class)
public class PartnerAccountTest {
    @Autowired
    private PartnerAccountService partnerAccountService;

    @Test
    public void getPartnerAccountPage(){
        PartnerAccountPageReq req = new PartnerAccountPageReq();
        req.setPageNo(1);
        req.setPageSize(10);
        req.setPartnerId("4313811009422");
        Page<PartnerAccountDTO> list = partnerAccountService.qryPartnerAccountPageList(req);
        System.out.print("结果：" + list.toString());
    }

    @Test
    public void addPartnerAccount(){
        PartnerAccountDTO req = new PartnerAccountDTO();
        req.setAccount("微信号");
        req.setAccountType("1");
        req.setPartnerId("4313811009422");
        PartnerAccountDTO result = partnerAccountService.addPartnerAccount(req);
        System.out.print("结果：" + result.toString());
    }

    @Test
    public void updatePartnerAccount(){
        PartnerAccountDTO req = new PartnerAccountDTO();
        req.setAccount("微信号1");
        req.setAccountId("1067609164928262145");
        PartnerAccountDTO result = partnerAccountService.modifyPartnerAccount(req);
        System.out.print("结果：" + result.toString());
    }

    @Test
    public void removePartnerAccount(){
        int result = partnerAccountService.removePartnerAccount("1067609164928262145");
        System.out.print("结果：" + result);
    }
}
