package com.iwhalecloud.retail.warehouse.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.warehouse.WarehouseServiceApplication;
import com.iwhalecloud.retail.warehouse.common.MarketingResConst;
import com.iwhalecloud.retail.warehouse.dto.request.markres.SynMarkResStoreItemReq;
import com.iwhalecloud.retail.warehouse.dto.request.markres.SynMarkResStoreReq;
import com.iwhalecloud.retail.warehouse.service.MktResStoreTempService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Map;

/**
 * @author 吴良勇
 * @date 2019/3/9 18:14
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = WarehouseServiceApplication.class)
@Slf4j
public class MktResStoreTempServiceTest {
    @Reference
    private MktResStoreTempService mktResStoreTempService;
    @Test
    public void synMarkResStoreForObj(){
        for(int i=0;i<1;i++){
            SynMarkResStoreReq req = new SynMarkResStoreReq();
            req.setActType(MarketingResConst.ACT_TYPE_UPDATE);
            SynMarkResStoreItemReq itemReq = new SynMarkResStoreItemReq();
            itemReq.setMktResStoreId("123"+i);
            itemReq.setMktResStoreName("12"+i);
            itemReq.setCheckDate("2019-01-03 12:11:12");
            itemReq.setCreateDate("2019-01-03 12:11:12");
            itemReq.setParStoreId("1232");
            itemReq.setOrgId("110000432050");
            itemReq.setStaffId("12311");
            itemReq.setRegionId("111");
            itemReq.setChannelId("2");
            itemReq.setStoreType("3");
            itemReq.setStatusCd("01");
            itemReq.setStatusDate("2019-01-03 12:11:12");
            itemReq.setRemark("lytest");
            itemReq.setMktResStoreNbr("abcddddd");
            itemReq.setRStorageCode1("code1");
            itemReq.setRStorageCode2("code2");
            itemReq.setLanId("117");
            itemReq.setCOperId("321");
            itemReq.setAddress("fdlksjfffffffffff");
            itemReq.setVbatchcode("123");
            itemReq.setRcType("123");
            itemReq.setFamilyId("99");
            itemReq.setCreateStaff("111");
            itemReq.setDirectSupply("1");
            itemReq.setProvider("123");
            itemReq.setProviderName("福建");
            itemReq.setRecStoreId("321");
            itemReq.setRecType("111");
            itemReq.setRecDay("333");
            itemReq.setStoreSubType("1");
            itemReq.setStoreGrade("1");
            itemReq.setEffDate("2010-01-03 12:11:12");
            itemReq.setExpDate("2019-01-03 12:11:12");
            itemReq.setUpdateStaff("1");
            itemReq.setUpdateDate("2019-01-03 12:11:12");
            req.setRequest(itemReq);
            ResultVO resultVO = mktResStoreTempService.synMarkResStoreForObj(req);
            System.out.println(resultVO);
        }

    }
    @Test
    public void synMarkResStore(){
        String json = "{\n" +
                " \"request\": {\n" +
                "  \"statusDate\": \"2019-01-03 12:11:12\",\n" +
                "  \"updateDate\": \"2019-01-03 12:11:12\",\n" +
                "  \"parStoreId\": \"1232\",\n" +
                "  \"remark\": \"lytest\",\n" +
                "  \"lanId\": \"117\",\n" +
                "  \"expDate\": \"2019-01-03 12:11:12\",\n" +
                "  \"orgId\": \"1\",\n" +
                "  \"recType\": \"111\",\n" +
                "  \"effDate\": \"2010-01-03 12:11:12\",\n" +
                "  \"mktResStoreId\": \"12310\",\n" +
                "  \"provider\": \"123\",\n" +
                "  \"storeGrade\": \"1\",\n" +
                "  \"storeSubType\": \"1\",\n" +
                "  \"recStoreId\": \"321\",\n" +
                "  \"channelId\": \"2\",\n" +
                "  \"providerName\": \"福建\",\n" +
                "  \"createDate\": \"2019-01-03 12:11:12\",\n" +
                "  \"storeType\": \"3\",\n" +
                "  \"address\": \"fdlksjfffffffffff\",\n" +
                "  \"mktResStoreName\": \"120\",\n" +
                "  \"rcType\": \"123\",\n" +
                "  \"rStorageCode2\": \"code2\",\n" +
                "  \"recDay\": \"333\",\n" +
                "  \"statusCd\": \"01\",\n" +
                "  \"checkDate\": \"2019-01-03 12:11:12\",\n" +
                "  \"directSupply\": \"1\",\n" +
                "  \"rStorageCode1\": \"code1\",\n" +
                "  \"createStaff\": \"111\",\n" +
                "  \"familyId\": \"99\",\n" +
                "  \"regionId\": \"111\",\n" +
                "  \"vbatchcode\": \"123\",\n" +
                "  \"mktResStoreNbr\": \"abcddddd\",\n" +
                "  \"cOperId\": \"321\",\n" +
                "  \"staffId\": \"12311\",\n" +
                "  \"updateStaff\": \"1\"\n" +
                " },\n" +
                " \"actType\": \"A\"\n" +
                "}";
        Map resultVO = mktResStoreTempService.synMarkResStore(json);
        System.out.println(resultVO);
    }
    @Test
    public void synTempToMktResStore(){
        ResultVO resultVO = mktResStoreTempService.synTempToMktResStore();
        System.out.println(resultVO);
    }

}
