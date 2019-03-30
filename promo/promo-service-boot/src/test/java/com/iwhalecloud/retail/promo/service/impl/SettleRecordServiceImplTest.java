package com.iwhalecloud.retail.promo.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.iwhalecloud.retail.promo.PromoServiceApplication;
import com.iwhalecloud.retail.promo.dto.SettleRecordDTO;
import com.iwhalecloud.retail.promo.service.SettleRecordService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * Created by Administrator on 2019/3/29.
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PromoServiceApplication.class)
public class SettleRecordServiceImplTest {
    @Reference
    private SettleRecordService settleRecordService;
    @Test
    public void test() throws Exception{
        List<SettleRecordDTO> settleRecordDTOs = settleRecordService.getSettleRecord();
        Integer num = settleRecordService.batchAddSettleRecord(settleRecordDTOs);
        System.out.println(num);
    }
}
