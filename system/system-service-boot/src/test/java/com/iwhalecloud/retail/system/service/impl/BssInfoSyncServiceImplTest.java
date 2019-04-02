package com.iwhalecloud.retail.system.service.impl;

import com.iwhalecloud.retail.system.SystemServiceApplication;
import com.iwhalecloud.retail.system.service.BssInfoSyncAbilityService;
import com.iwhalecloud.retail.system.service.BssInfoSyncService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Map;


@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SystemServiceApplication.class)
public class BssInfoSyncServiceImplTest {

    @Autowired
    private BssInfoSyncService bssInfoSyncService;

    @Autowired
    private BssInfoSyncAbilityService bssInfoSyncAbilityService;

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

    @Test
    public void orgInfoSync() {
        String jsonString = "{\n" +
            "    \"contractRoot\":{\n" +
            "        \"svcCont\":{\n" +
            "            \"noticeUser\":{\n" +
            "                \"portalNoticeUsers\":\"32101,32001\",\n" +
            "                \"portalNoticeType\":\"1200\"\n" +
            "            },\n" +
            "            \"authenticationInfo\":{\n" +
            "                \"sysUserId\":\"30000004617\",\n" +
            "                \"sysUserPostId\":\"20001\"\n" +
            "            },\n" +
            "            \"requestObject\":{\n" +
            "                \"orgType\":\"1100\",\n" +
            "                \"pathCode\":\"1000000020.843000000000000.843073100000000.843073102040000.843073102040025.110026180961.110026181074\",\n" +
            "                \"orgSubtype\":\"1800\",\n" +
            "                \"bizManagerCode\":\"J43010295424\",\n" +
            "                \"operatorUnitCode\":\"\",\n" +
            "                \"statusDate\":\"2019-02-26 16:46:05\",\n" +
            "                \"unionOrgCode\":\"8438236746\",\n" +
            "                \"lanId\":\"731\",\n" +
            "                \"actType\":\"ADD\",\n" +
            "                \"orgId\":\"110026181074\",\n" +
            "                \"salesorgCode\":\"4301021888714\",\n" +
            "                \"orgCode\":\"110026181074\",\n" +
            "                \"createDate\":\"2019-02-26 16:46:05\",\n" +
            "                \"villageFlag\":\"1000\",\n" +
            "                \"regionId\":\"73101\",\n" +
            "                \"updateStaff\":\"100095696\",\n" +
            "                \"parentOrgId\":\"110026180961\",\n" +
            "                \"divorgFlag\":\"0\",\n" +
            "                \"orgName\":\"ÐÇ·å@³¤É³ÊÐ¶«ÌÁÐÇ·å³ÇÊÐ»¨Ô°¶ÀÁ¢µê\",\n" +
            "                \"createStaff\":\"100095696\",\n" +
            "                \"updateDate\":\"2019-02-26 16:46:05\",\n" +
            "                \"orgIndex\":\"0\",\n" +
            "                \"saleBoxCode\":\"4301021889045\",\n" +
            "                \"statusCd\":\"1000\",\n" +
            "                \"partyId\":\"110026181074\",\n" +
            "                \"attrList\":[\n" +
            "                    {\n" +
            "                        \"attrNbr\":\"orgId\",\n" +
            "                        \"attrName\":\"×éÖ¯ID\",\n" +
            "                        \"attrValue\":\"110026181074\",\n" +
            "                        \"actType\":\"ADD\"\n" +
            "                    },\n" +
            "                    {\n" +
            "                        \"attrNbr\":\"orgClass\",\n" +
            "                        \"attrName\":\"×éÖ¯·ÖÀà\",\n" +
            "                        \"attrValue\":\"007\",\n" +
            "                        \"actType\":\"ADD\"\n" +
            "                    },\n" +
            "                    {\n" +
            "                        \"attrNbr\":\"channelAttr\",\n" +
            "                        \"attrName\":\"ÇþµÀÊôÐÔ\",\n" +
            "                        \"attrValue\":\"5002\",\n" +
            "                        \"actType\":\"ADD\"\n" +
            "                    },\n" +
            "                    {\n" +
            "                        \"attrNbr\":\"departType\",\n" +
            "                        \"attrName\":\"²¿ÃÅÀàÐÍ\",\n" +
            "                        \"attrValue\":\"01\",\n" +
            "                        \"actType\":\"ADD\"\n" +
            "                    },\n" +
            "                    {\n" +
            "                        \"attrNbr\":\"col1Name\",\n" +
            "                        \"attrName\":\"\",\n" +
            "                        \"attrValue\":\"\",\n" +
            "                        \"actType\":\"ADD\"\n" +
            "                    },\n" +
            "                    {\n" +
            "                        \"attrNbr\":\"col1\",\n" +
            "                        \"attrName\":\"\",\n" +
            "                        \"attrValue\":\"\",\n" +
            "                        \"actType\":\"ADD\"\n" +
            "                    },\n" +
            "                    {\n" +
            "                        \"attrNbr\":\"col2Name\",\n" +
            "                        \"attrName\":\"\",\n" +
            "                        \"attrValue\":\"\",\n" +
            "                        \"actType\":\"ADD\"\n" +
            "                    },\n" +
            "                    {\n" +
            "                        \"attrNbr\":\"col2\",\n" +
            "                        \"attrName\":\"\",\n" +
            "                        \"attrValue\":\"\",\n" +
            "                        \"actType\":\"ADD\"\n" +
            "                    },\n" +
            "                    {\n" +
            "                        \"attrNbr\":\"col3Name\",\n" +
            "                        \"attrName\":\"\",\n" +
            "                        \"attrValue\":\"\",\n" +
            "                        \"actType\":\"ADD\"\n" +
            "                    },\n" +
            "                    {\n" +
            "                        \"attrNbr\":\"col3\",\n" +
            "                        \"attrName\":\"\",\n" +
            "                        \"attrValue\":\"\",\n" +
            "                        \"actType\":\"ADD\"\n" +
            "                    },\n" +
            "                    {\n" +
            "                        \"attrNbr\":\"col4Name\",\n" +
            "                        \"attrName\":\"\",\n" +
            "                        \"attrValue\":\"\",\n" +
            "                        \"actType\":\"ADD\"\n" +
            "                    },\n" +
            "                    {\n" +
            "                        \"attrNbr\":\"col4\",\n" +
            "                        \"attrName\":\"¶ÔÓ¦·ÖÆÚ¸¶×Ê½ðÆ½Ì¨ÉÌ1(ÆÕÍ¨)\",\n" +
            "                        \"attrValue\":\"\",\n" +
            "                        \"actType\":\"ADD\"\n" +
            "                    },\n" +
            "                    {\n" +
            "                        \"attrNbr\":\"col5Name\",\n" +
            "                        \"attrName\":\"\",\n" +
            "                        \"attrValue\":\"\",\n" +
            "                        \"actType\":\"ADD\"\n" +
            "                    },\n" +
            "                    {\n" +
            "                        \"attrNbr\":\"col5\",\n" +
            "                        \"attrName\":\"¶ÔÓ¦·ÖÆÚ¸¶×Ê½ðÆ½Ì¨ÉÌ2(VIVO)\",\n" +
            "                        \"attrValue\":\"\",\n" +
            "                        \"actType\":\"ADD\"\n" +
            "                    },\n" +
            "                    {\n" +
            "                        \"attrNbr\":\"col6Name\",\n" +
            "                        \"attrName\":\"\",\n" +
            "                        \"attrValue\":\"\",\n" +
            "                        \"actType\":\"ADD\"\n" +
            "                    },\n" +
            "                    {\n" +
            "                        \"attrNbr\":\"col6\",\n" +
            "                        \"attrName\":\"¶ÔÓ¦·ÖÆÚ¸¶×Ê½ðÆ½Ì¨ÉÌ3(OPPO)\",\n" +
            "                        \"attrValue\":\"\",\n" +
            "                        \"actType\":\"ADD\"\n" +
            "                    },\n" +
            "                    {\n" +
            "                        \"attrNbr\":\"col7Name\",\n" +
            "                        \"attrName\":\"ÏúÊÛµãÃû³Æ\",\n" +
            "                        \"attrValue\":\"\",\n" +
            "                        \"actType\":\"ADD\"\n" +
            "                    },\n" +
            "                    {\n" +
            "                        \"attrNbr\":\"col7\",\n" +
            "                        \"attrName\":\"\",\n" +
            "                        \"attrValue\":\"\",\n" +
            "                        \"actType\":\"ADD\"\n" +
            "                    },\n" +
            "                    {\n" +
            "                        \"attrNbr\":\"col8Name\",\n" +
            "                        \"attrName\":\"\",\n" +
            "                        \"attrValue\":\"\",\n" +
            "                        \"actType\":\"ADD\"\n" +
            "                    },\n" +
            "                    {\n" +
            "                        \"attrNbr\":\"col8\",\n" +
            "                        \"attrName\":\"\",\n" +
            "                        \"attrValue\":\"\",\n" +
            "                        \"actType\":\"ADD\"\n" +
            "                    },\n" +
            "                    {\n" +
            "                        \"attrNbr\":\"col9Name\",\n" +
            "                        \"attrName\":\"\",\n" +
            "                        \"attrValue\":\"\",\n" +
            "                        \"actType\":\"ADD\"\n" +
            "                    },\n" +
            "                    {\n" +
            "                        \"attrNbr\":\"col9\",\n" +
            "                        \"attrName\":\"\",\n" +
            "                        \"attrValue\":\"\",\n" +
            "                        \"actType\":\"ADD\"\n" +
            "                    },\n" +
            "                    {\n" +
            "                        \"attrNbr\":\"col10Name\",\n" +
            "                        \"attrName\":\"\",\n" +
            "                        \"attrValue\":\"\",\n" +
            "                        \"actType\":\"ADD\"\n" +
            "                    },\n" +
            "                    {\n" +
            "                        \"attrNbr\":\"col10\",\n" +
            "                        \"attrName\":\"\",\n" +
            "                        \"attrValue\":\"\",\n" +
            "                        \"actType\":\"ADD\"\n" +
            "                    },\n" +
            "                    {\n" +
            "                        \"attrNbr\":\"col11Name\",\n" +
            "                        \"attrName\":\"\",\n" +
            "                        \"attrValue\":\"\",\n" +
            "                        \"actType\":\"ADD\"\n" +
            "                    },\n" +
            "                    {\n" +
            "                        \"attrNbr\":\"col11\",\n" +
            "                        \"attrName\":\"\",\n" +
            "                        \"attrValue\":\"\",\n" +
            "                        \"actType\":\"ADD\"\n" +
            "                    },\n" +
            "                    {\n" +
            "                        \"attrNbr\":\"col12Name\",\n" +
            "                        \"attrName\":\"\",\n" +
            "                        \"attrValue\":\"\",\n" +
            "                        \"actType\":\"ADD\"\n" +
            "                    },\n" +
            "                    {\n" +
            "                        \"attrNbr\":\"col12\",\n" +
            "                        \"attrName\":\"\",\n" +
            "                        \"attrValue\":\"110000\",\n" +
            "                        \"actType\":\"ADD\"\n" +
            "                    },\n" +
            "                    {\n" +
            "                        \"attrNbr\":\"col13Name\",\n" +
            "                        \"attrName\":\"\",\n" +
            "                        \"attrValue\":\"\",\n" +
            "                        \"actType\":\"ADD\"\n" +
            "                    },\n" +
            "                    {\n" +
            "                        \"attrNbr\":\"col13\",\n" +
            "                        \"attrName\":\"×¨ÊôÊôÐÔ(¼¯ÍÅ)\",\n" +
            "                        \"attrValue\":\"\",\n" +
            "                        \"actType\":\"ADD\"\n" +
            "                    },\n" +
            "                    {\n" +
            "                        \"attrNbr\":\"col14Name\",\n" +
            "                        \"attrName\":\"\",\n" +
            "                        \"attrValue\":\"\",\n" +
            "                        \"actType\":\"ADD\"\n" +
            "                    },\n" +
            "                    {\n" +
            "                        \"attrNbr\":\"col14\",\n" +
            "                        \"attrName\":\"ÊÇ·ñÊ¹ÓÃ¾«Æ·ÇþµÀÖÕ¶ËÏúÊÛÏµÍ³\",\n" +
            "                        \"attrValue\":\"\",\n" +
            "                        \"actType\":\"ADD\"\n" +
            "                    },\n" +
            "                    {\n" +
            "                        \"attrNbr\":\"col15Name\",\n" +
            "                        \"attrName\":\"\",\n" +
            "                        \"attrValue\":\"\",\n" +
            "                        \"actType\":\"ADD\"\n" +
            "                    },\n" +
            "                    {\n" +
            "                        \"attrNbr\":\"col15\",\n" +
            "                        \"attrName\":\"¿Í»§±àÂë\",\n" +
            "                        \"attrValue\":\"7143016432\",\n" +
            "                        \"actType\":\"ADD\"\n" +
            "                    },\n" +
            "                    {\n" +
            "                        \"attrNbr\":\"orgContent\",\n" +
            "                        \"attrName\":\"×éÖ¯ÄÚÈÝ\",\n" +
            "                        \"attrValue\":\"\",\n" +
            "                        \"actType\":\"ADD\"\n" +
            "                    },\n" +
            "                    {\n" +
            "                        \"attrNbr\":\"addressId\",\n" +
            "                        \"attrName\":\"µØÖ·ID\",\n" +
            "                        \"attrValue\":\"\",\n" +
            "                        \"actType\":\"ADD\"\n" +
            "                    },\n" +
            "                    {\n" +
            "                        \"attrNbr\":\"orgTypeId\",\n" +
            "                        \"attrName\":\"×éÖ¯ÀàÐÍ\",\n" +
            "                        \"attrValue\":\"8\",\n" +
            "                        \"actType\":\"ADD\"\n" +
            "                    },\n" +
            "                    {\n" +
            "                        \"attrNbr\":\"pathName\",\n" +
            "                        \"attrName\":\"Â·¾¶Ãû³Æ\",\n" +
            "                        \"attrValue\":\"\",\n" +
            "                        \"actType\":\"ADD\"\n" +
            "                    },\n" +
            "                    {\n" +
            "                        \"attrNbr\":\"channelType\",\n" +
            "                        \"attrName\":\"CRMÇþµÀÀàÐÍ\",\n" +
            "                        \"attrValue\":\"50\",\n" +
            "                        \"actType\":\"ADD\"\n" +
            "                    },\n" +
            "                    {\n" +
            "                        \"attrNbr\":\"commonRegionId\",\n" +
            "                        \"attrName\":\"ÍøµãµØÀíÇøÓò\",\n" +
            "                        \"attrValue\":\"\",\n" +
            "                        \"actType\":\"ADD\"\n" +
            "                    },\n" +
            "                    {\n" +
            "                        \"attrNbr\":\"channelFirstClassify\",\n" +
            "                        \"attrName\":\"ÏúÊÛµãÒ»¼¶·ÖÀà\",\n" +
            "                        \"attrValue\":\"110000\",\n" +
            "                        \"actType\":\"ADD\"\n" +
            "                    },\n" +
            "                    {\n" +
            "                        \"attrNbr\":\"channelLevel\",\n" +
            "                        \"attrName\":\"Íøµã¼¶±ð\",\n" +
            "                        \"attrValue\":\"\",\n" +
            "                        \"actType\":\"ADD\"\n" +
            "                    },\n" +
            "                    {\n" +
            "                        \"attrNbr\":\"goodsChannelLogo\",\n" +
            "                        \"attrName\":\"¾«Æ·ÇþµÀ±êÊ¶goods_channel_logo\",\n" +
            "                        \"attrValue\":\"\",\n" +
            "                        \"actType\":\"ADD\"\n" +
            "                    },\n" +
            "                    {\n" +
            "                        \"attrNbr\":\"gysCode\",\n" +
            "                        \"attrName\":\"¹©Ó¦ÉÌ±àÂë\",\n" +
            "                        \"attrValue\":\"G000394176\",\n" +
            "                        \"actType\":\"ADD\"\n" +
            "                    },\n" +
            "                    {\n" +
            "                        \"attrNbr\":\"contactPhone\",\n" +
            "                        \"attrName\":\"µêÖÐÉÌÁªÏµºÅÂë\",\n" +
            "                        \"attrValue\":\"\",\n" +
            "                        \"actType\":\"ADD\"\n" +
            "                    },\n" +
            "                    {\n" +
            "                        \"attrNbr\":\"fairMonthRent\",\n" +
            "                        \"attrName\":\"µêÖÐÉÌÔÂ¹«ÔÊ×â½ð\",\n" +
            "                        \"attrValue\":\"0\",\n" +
            "                        \"actType\":\"ADD\"\n" +
            "                    }\n" +
            "                ],\n" +
            "                \"channel\":{\n" +
            "                    \"channelName\":\"ÐÇ·å@³¤É³ÊÐ¶«ÌÁÐÇ·å³ÇÊÐ»¨Ô°¶ÀÁ¢µê\",\n" +
            "                    \"updateStaff\":\"100095696\",\n" +
            "                    \"salesThirdType\":\"\",\n" +
            "                    \"statusDate\":\"2019-02-26 16:46:05\",\n" +
            "                    \"createStaff\":\"100095696\",\n" +
            "                    \"updateDate\":\"2019-02-26 16:46:06\",\n" +
            "                    \"actType\":\"ADD\",\n" +
            "                    \"orgId\":\"110026181074\",\n" +
            "                    \"channelDesc\":\"ÐÇ·å@³¤É³ÊÐ¶«ÌÁÐÇ·å³ÇÊÐ»¨Ô°¶ÀÁ¢µê\",\n" +
            "                    \"salesSecondType\":\"\",\n" +
            "                    \"channelNbr\":\"110026181074\",\n" +
            "                    \"channelClass\":\"20\",\n" +
            "                    \"salesFirstType\":\"110000\",\n" +
            "                    \"statusCd\":\"1000\",\n" +
            "                    \"partyId\":\"110026181074\",\n" +
            "                    \"createDate\":\"2019-02-26 16:46:06\",\n" +
            "                    \"regionId\":\"73101\"\n" +
            "                },\n" +
            "                \"orgLevel\":\"6\"\n" +
            "            }\n" +
            "        },\n" +
            "        \"tcpCont\":{\n" +
            "            \"sign\":\"518690cbf7d5c285e545dc8261e5200f\",\n" +
            "            \"transactionId\":\"6002030005201902260001659605\",\n" +
            "            \"svcCode\":\"7010010008\",\n" +
            "            \"reqTime\":\"20190226165104\",\n" +
            "            \"appKey\":\"6002030005\",\n" +
            "            \"dstSysId\":955,\n" +
            "            \"version\":\"1.0\"\n" +
            "        }\n" +
            "    }\n" +
            "}";

        Map<String, Object> map = bssInfoSyncAbilityService.syncOrgInfo(jsonString);
        log.info("BssInfoSyncServiceImplTest.userInfoSync result:{}", map.get("result"));
    }

}