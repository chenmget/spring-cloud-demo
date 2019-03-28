package com.iwhalecloud.retail.partner.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.partner.PartnerServiceApplication;
import com.iwhalecloud.retail.partner.dto.PartnerDTO;
import com.iwhalecloud.retail.partner.dto.req.PartnerPageReq;
import com.iwhalecloud.retail.partner.dto.req.SupplierReq;
import com.iwhalecloud.retail.partner.service.PartnerService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@SpringBootTest(classes = PartnerServiceApplication.class)
@RunWith(SpringRunner.class)
public class PartnerTest {

    @Autowired
    private PartnerService partnerService;

    @Test
    public void getPartnerListByShopId(){
        List list = partnerService.getPartnerListByShopId("4304261017618");
        System.out.print("结果：" + list.toString());
    }

    @Test
    public void getPartnerList(){
        PartnerPageReq req = new PartnerPageReq();
        req.setPageNo(1);
        req.setPageSize(20);
        Page<PartnerDTO> list = partnerService.pagePartner(req);
        System.out.print("结果：" + list.toString());
    }

    @Test
    public void getPartnerBySupplierId(){
        SupplierReq req = new SupplierReq();
        req.setSupplierId("1");
        ResultVO<Page<PartnerDTO>> list = partnerService.querySupplyRel(req);
        System.out.print("结果：" + list.toString());
    }


    @Test
    public void getPartnerById(){
        PartnerDTO partnerDTO = partnerService.getPartnerById("4304261017620");
        System.out.print("结果：" + partnerDTO);
    }
}
