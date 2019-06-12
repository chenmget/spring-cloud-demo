package com.iwhalecloud.retail.report;

import com.alibaba.fastjson.JSON;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.report.dto.request.SummarySaleBySupplierPageReq;
import com.iwhalecloud.retail.report.service.SupplierOperatingDayService;
import lombok.extern.slf4j.Slf4j;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author wenlong.zhong
 * @date 2019/6/10
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ReportServiceApplication.class)
public class SupplierOperatingDayServiceTest {


    @Autowired
    private SupplierOperatingDayService supplierOperatingDayService;

    @org.junit.Test
    public void pageSummarySaleBySupplier(){
        SummarySaleBySupplierPageReq req = new SummarySaleBySupplierPageReq();
//        req.setRegionIdList(Lists.newArrayList("731","732","733","734","735"));
//        req.setSupplierId("11");
        req.setPageNo(1);
        req.setPageSize(3);
        ResultVO resultVO = supplierOperatingDayService.pageSummarySaleBySupplier(req);
        log.info("结果：{}", JSON.toJSONString(resultVO.getResultData()));
    }
}

