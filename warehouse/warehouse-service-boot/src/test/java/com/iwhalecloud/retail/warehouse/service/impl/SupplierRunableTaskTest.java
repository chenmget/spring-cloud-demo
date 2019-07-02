package com.iwhalecloud.retail.warehouse.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.warehouse.WarehouseServiceApplication;
import com.iwhalecloud.retail.warehouse.dto.request.ResourceUploadTempListPageReq;
import com.iwhalecloud.retail.warehouse.dto.response.ResourceUploadTempListResp;
import com.iwhalecloud.retail.warehouse.runable.SupplierRunableTask;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = WarehouseServiceApplication.class)
@Slf4j
public class SupplierRunableTaskTest {

    @Autowired
    private SupplierRunableTask supplierRunableTask;



    @Test
    public void exceutorListResourceUploadTemp() {
        ResourceUploadTempListPageReq req = new ResourceUploadTempListPageReq();
        req.setMktResUploadBatch("23337440");
        Page<ResourceUploadTempListResp> resultVO = supplierRunableTask.exceutorListResourceUploadTemp(req);
        log.info("RetailerResourceInstServiceImplTest.addResourceInstByGreenChannel result:{}", JSON.toJSONString(resultVO));
    }
}