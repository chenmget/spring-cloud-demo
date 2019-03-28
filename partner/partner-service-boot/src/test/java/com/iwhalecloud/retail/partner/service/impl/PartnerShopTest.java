package com.iwhalecloud.retail.partner.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.partner.PartnerServiceApplication;
import com.iwhalecloud.retail.partner.dto.PartnerRelShopDTO;
import com.iwhalecloud.retail.partner.dto.PartnerShopDTO;
import com.iwhalecloud.retail.partner.dto.req.*;
import com.iwhalecloud.retail.partner.service.PartnerShopService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = PartnerServiceApplication.class)
public class PartnerShopTest {

    @Autowired
    private PartnerShopService partnerShopService;

    @Test
    public void updateShopState() {
        PartnerShopStateUpdateReq req = new PartnerShopStateUpdateReq();
        req.setPartnerShopId("4311031055094");
        req.setState("2");
        int result = partnerShopService.updatePartnerShopState(req);
        System.out.print("结果：" + result);
    }

    @Test
    public void updateShop() {
        PartnerShopUpdateReq req = new PartnerShopUpdateReq();
        req.setPartnerShopId("4311031055094");
        req.setState("1");
        int result = partnerShopService.updatePartnerShop(req);
        System.out.print("结果：" + result);
    }


    @Test
    public void listShopPartnerNearby() {
        PartnerShopListReq req = new PartnerShopListReq();
        req.setLat("23");
        req.setLng("34");
        Page<PartnerRelShopDTO> shopList = partnerShopService.pagePartnerNearby(req);
        System.out.print("结果：" + shopList);
    }

    @Test
    public void queryShopPage() {
        PartnerShopQueryPageReq req = new PartnerShopQueryPageReq();
        req.setPageNo(1);
        req.setPageSize(10);
        Page<PartnerShopDTO> shopList = partnerShopService.queryPartnerShopPage(req);
        System.out.print("结果：" + shopList.toString());
    }



    @Test
    public void queryShopList() {
        PartnerShopQueryListReq req = new PartnerShopQueryListReq();
        req.setName("长沙");
        List<PartnerShopDTO> shopList = partnerShopService.queryPartnerShopList(req);
        System.out.print("结果：" + shopList.toString());
    }

    @Test
    public void getShop() {
        PartnerShopDTO resp = partnerShopService.getPartnerShop("4311031055094");
        System.out.print("结果：" + resp);
    }
}