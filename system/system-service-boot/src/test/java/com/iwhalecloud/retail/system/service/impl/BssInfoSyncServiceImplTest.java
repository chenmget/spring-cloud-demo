package com.iwhalecloud.retail.system.service.impl;

import com.iwhalecloud.retail.system.SystemServiceApplication;
import com.iwhalecloud.retail.system.service.BssInfoSyncService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SystemServiceApplication.class)
public class BssInfoSyncServiceImplTest {

    @Autowired
    private BssInfoSyncService bssInfoSyncService;

    @Test
    public void userInfoSync() {
        String jsonString = "{\n" +
                "    \"systemUser\":{\n" +
                "        \"sysUserDesc\":\"BSS3.0长电工号清理;陈前\",\n" +
                "        \"systemInfoId\":\"727001\",\n" +
                "        \"effDate\":\"2017-11-14 00:00:00\",\n" +
                "        \"expDate\":\"2040-12-31 00:00:00\",\n" +
                "        \"sysUserCode\":\"731CHNZQ01122\",\n" +
                "        \"statusDate\":\"2018-04-12 14:38:44\",\n" +
                "        \"password\":\"yohiegpSGKM4ikAarvCABA==\",\n" +
                "        \"pwdStatus\":\"1100\",\n" +
                "        \"userName\":\"陈前\",\n" +
                "        \"createDate\":\"2017-11-14 08:39:55\",\n" +
                "        \"regionId\":\"73101\",\n" +
                "        \"limitCount\":\"3\",\n" +
                "        \"updateStaff\":\"103907741\",\n" +
                "        \"staffId\":\"11075\",\n" +
                "        \"salesstaffCode\":\"Y43010137544\",\n" +
                "        \"pwdErrCnt\":\"0\",\n" +
                "        \"loginedNum\":\"287\",\n" +
                "        \"pwdNewtime\":\"2018-01-17 12:52:29\",\n" +
                "        \"updateDate\":\"2018-04-12 14:38:55\",\n" +
                "        \"sysUserId\":\"103907741\",\n" +
                "        \"userOrgId\":\"110002247702\",\n" +
                "        \"statusCd\":\"1000\",\n" +
                "        \"pwdSmsTel\":\"18975169056\",\n" +
                "        \"pwdEffectDays\":\"90\"\n" +
                "    },\n" +
                "    \"actType\":\"A\"\n" +
                "}";

        String result = bssInfoSyncService.userInfoSync(jsonString);
        log.info("BssInfoSyncServiceImplTest.userInfoSync result:{}", result);
    }
}