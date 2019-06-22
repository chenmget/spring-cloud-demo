package com.iwhalecloud.retail.partner.service.impl;


import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.partner.PartnerServiceApplication;
import com.iwhalecloud.retail.partner.dto.req.ManufacturerGetReq;
import com.iwhalecloud.retail.partner.dto.req.ManufacturerPageReq;
import com.iwhalecloud.retail.partner.dto.req.ManufacturerSaveReq;
import com.iwhalecloud.retail.partner.dto.req.ManufacturerUpdateReq;
import com.iwhalecloud.retail.partner.service.ManufacturerService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest(classes = PartnerServiceApplication.class)
@RunWith(SpringRunner.class)
public class ManufacturerTest {

    @Autowired
    private ManufacturerService manufacturerService;

    @Test
    public void getById() {
        ResultVO resultVO = manufacturerService.getManufacturerById("1077173824606191618");
        System.out.print("结果：" + resultVO.toString());
    }


    @Test
    public void getByCode() {
        ManufacturerGetReq req = new ManufacturerGetReq();
        req.setManufacturerCode("12345");
        ResultVO resultVO = manufacturerService.getManufacturer(req);
        System.out.print("结果：" + resultVO.toString());
    }

    @Test
    public void add() {
        ManufacturerSaveReq req = new ManufacturerSaveReq();
        req.setManufacturerCode("12345");
        req.setManufacturerLevel("1");
        req.setManufacturerName("厂商测试2");
        req.setStatus("1");
        req.setUserId("1");

        ResultVO resultVO = manufacturerService.saveManufacturer(req);
        System.out.print("结果：" + resultVO.toString());
    }

    @Test
    public void update() {
        ManufacturerUpdateReq req = new ManufacturerUpdateReq();
        req.setManufacturerId("1077173824606191618");
        req.setManufacturerCode("123452");
        req.setManufacturerLevel("12");
        req.setManufacturerName("厂商测试21");
        req.setStatus("12");
        req.setUserId("12");

        ResultVO resultVO = manufacturerService.updateManufacturer(req);
        System.out.print("结果：" + resultVO);
    }

    @Test
    public void page() {
        ManufacturerPageReq req = new ManufacturerPageReq();
        req.setManufacturerCode("1234");
        req.setManufacturerName("21");

        ResultVO resultVO= manufacturerService.pageManufacturer(req);
        System.out.print("结果：" + resultVO.toString());
    }

}
