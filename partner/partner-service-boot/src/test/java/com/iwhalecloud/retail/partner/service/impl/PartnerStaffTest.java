package com.iwhalecloud.retail.partner.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.partner.PartnerServiceApplication;
import com.iwhalecloud.retail.partner.dto.PartnerStaffDTO;
import com.iwhalecloud.retail.partner.dto.req.PartnerStaffAddReq;
import com.iwhalecloud.retail.partner.dto.req.PartnerStaffPageReq;
import com.iwhalecloud.retail.partner.dto.req.PartnerStaffUpdateReq;
import com.iwhalecloud.retail.partner.service.PartnerStaffService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest(classes = PartnerServiceApplication.class)
@RunWith(SpringRunner.class)
public class PartnerStaffTest {

    @Autowired
    private PartnerStaffService partnerStaffService;

    @Test
    public void getPartnerStaffList() throws Exception {
        PartnerStaffPageReq req = new PartnerStaffPageReq();
        req.setPageNo(1);
        req.setPageSize(10);
        req.setPartnerShopId("4311031055094");
//        req.setStaffCode("zho2");
        Page<PartnerStaffDTO> result = partnerStaffService.getPartnerStaffList(req);
        System.out.println("  结果：" + result.toString());
    }

    @Test
    public void getStaff() {
        PartnerStaffDTO partnerStaffDTO = partnerStaffService.getPartnerStaff("1057251607177863169");
        System.out.print("结果：" + partnerStaffDTO.toString());
    }

//    @Test
//    public void listStaffByPartnerId() {
//        List<PartnerStaffQryByPartnerIdResp> list = partnerStaffService.getStaffListByPartnerId("4301211024068");
//        System.out.print("结果：" + list.toString());
//    }

    @Test
    public void addStaff() {
        PartnerStaffAddReq req = new PartnerStaffAddReq();
        req.setStaffName("砖头");
        req.setPosition("6");
        req.setPhoneNo("15912345678");
        req.setPartnerShopId("4311031055094");
        PartnerStaffDTO partnerStaffDTO = partnerStaffService.addPartnerStaff(req);
        System.out.print("结果：" + partnerStaffDTO.toString());
    }

    @Test
    public void editStaff() {
        PartnerStaffUpdateReq req = new PartnerStaffUpdateReq();
        req.setStaffId("1067677695401377794");
        req.setStaffName("砖头");
        req.setPosition("店员");
        req.setPhoneNo("15912345678");
        req.setPartnerShopId("4311031055094");
        int result = partnerStaffService.editPartnerStaff(req);
        System.out.print("结果：" + result);
    }
}
