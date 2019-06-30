package com.iwhalecloud.retail.warehouse.util;

import com.iwhalecloud.retail.warehouse.WarehouseServiceApplication;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.stereotype.Service;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * @Description: WarehouseCacheUtilsTest
 * @author: Z
 * @date: 2019/6/28 10:11
 */

@RunWith(SpringRunner.class)
@SpringBootTest(classes = WarehouseServiceApplication.class)
@Slf4j
@EnableCaching
public class WarehouseCacheUtilsTest {

    @Resource
    private WarehouseCacheUtils warehouseCacheUtils;

    @Test
    public void testCache(){
        warehouseCacheUtils.put("RES_NBR_DEALING", "22");

        String value = warehouseCacheUtils.get("RES_NBR_DEALING");
        System.out.println("value >>" + value);

        warehouseCacheUtils.evict("RES_NBR_DEALING");
        value = warehouseCacheUtils.get("RES_NBR_DEALING");
        System.out.println("evict value >>" + value);
    }
}
