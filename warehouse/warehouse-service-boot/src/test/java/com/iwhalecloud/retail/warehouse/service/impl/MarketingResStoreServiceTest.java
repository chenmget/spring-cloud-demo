package com.iwhalecloud.retail.warehouse.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.warehouse.WarehouseServiceApplication;
import com.iwhalecloud.retail.warehouse.dto.request.markres.SynMarkResStoreReq;
import com.iwhalecloud.retail.warehouse.dto.request.markresswap.*;
import com.iwhalecloud.retail.warehouse.dto.response.markresswap.QryMktInstInfoByConditionItemSwapResp;
import com.iwhalecloud.retail.warehouse.dto.response.markresswap.QryStoreMktInstInfoItemSwapResp;
import com.iwhalecloud.retail.warehouse.dto.response.markresswap.StoreInventoryQuantityItemSwapResp;
import com.iwhalecloud.retail.warehouse.dto.response.markresswap.base.QueryMarkResQueryResultsSwapResp;
import com.iwhalecloud.retail.warehouse.service.MarketingResStoreService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */

/**
 * @author 吴良勇
 * @date 2019/3/4 16:52
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = WarehouseServiceApplication.class)
@Slf4j
public class MarketingResStoreServiceTest {
    @Reference
    private MarketingResStoreService marketingResStoreService;

    @Test
    public void syncTerminal() {
        SyncTerminalSwapReq req = new SyncTerminalSwapReq();
        List<SyncTerminalItemSwapReq> mktResList = new ArrayList<SyncTerminalItemSwapReq>();

        SyncTerminalItemSwapReq item = new SyncTerminalItemSwapReq();
        item.setBarCode("A000009BB1FA20");
        item.setCitySupplyId("DBS9000010");
        item.setCitySupplyName("陈焕新@衡阳县新正街城东专营店");
        item.setDirectPrice("1");
        item.setProductCode("2869304");
        item.setPurchaseType("");
        item.setStoreId("102654034");
        item.setLanId("734");
        item.setProvSupplyId("53234");
        item.setProvSupplyName("湖南京辰电子科技有限公司");

        mktResList.add(item);
        req.setMktResList(mktResList);

        ResultVO resultVO = marketingResStoreService.syncTerminal(req);
        System.out.println(resultVO);//OK


    }

    @Test
    public void ebuyTerminal() {
        EBuyTerminalSwapReq req = new EBuyTerminalSwapReq();
        List<EBuyTerminalItemSwapReq> mktResList = new ArrayList<EBuyTerminalItemSwapReq>();
        EBuyTerminalItemSwapReq item1 = new EBuyTerminalItemSwapReq();

        item1.setBarCode("BC54FC5B30EAQ");//BC54FC5B30EAa,BC54FC5B30EAb
        item1.setLanId("734");
        item1.setMktId("15599");
        item1.setPurchaseType("2");
        item1.setSalesPrice("5");
        item1.setStoreId("203658057");
        item1.setSupplyCode("DBS0042908");
        item1.setSupplyName("湖南华龙伟业通信技术有限公司_长沙地包");

        mktResList.add(item1);
        EBuyTerminalItemSwapReq item2 = new EBuyTerminalItemSwapReq();

        item2.setBarCode("BC54FC5B30EAK");//BC54FC5B30EAa,BC54FC5B30EAb
        item2.setLanId("734");
        item2.setMktId("15599");
        item2.setPurchaseType("2");
        item2.setSalesPrice("5");
        item2.setStoreId("203658057");
        item2.setSupplyCode("DBS0042908");
        item2.setSupplyName("湖南华龙伟业通信技术有限公司_长沙地包");

        mktResList.add(item2);
        req.setMktResList(mktResList);


        ResultVO resultVO = marketingResStoreService.ebuyTerminal(req);
        System.out.println(resultVO);//OK
    }

    @Test
    public void synMktInstStatus() {
        SynMktInstStatusSwapReq req = new SynMktInstStatusSwapReq();
        //req.setLanId("734"); // 731
        req.setLanId("731");
        req.setBarCode("20191111019909");
       // req.setBarCode("BC54FC5B30EAB,BC54FC5B30EAa,A000009BB1FA14,A000009BB1FA15,A000009BB1FA11"); //20191111019909
        ResultVO resultVO = marketingResStoreService.synMktInstStatus(req);
        System.out.println(resultVO);//error,营销系统暂时有问题无法调试
    }

    @Test
    public void qryStoreMktInstInfo() {
        QryStoreMktInstInfoSwapReq req = new QryStoreMktInstInfoSwapReq();
        req.setBarCode("BC54FC5B30EAQ");
        req.setStoreId("203658057");//102654034
        req.setPageIndex("1");
        req.setPageSize("10");

        System.out.println(marketingResStoreService);
        ResultVO<QueryMarkResQueryResultsSwapResp<QryStoreMktInstInfoItemSwapResp>> result = marketingResStoreService.qryStoreMktInstInfo(req);
        System.out.println(result);//OK

    }

    @Test
    public void qryMktInstInfoByCondition() {
        QryMktInstInfoByConditionSwapReq req = new QryMktInstInfoByConditionSwapReq();
        req.setInstoreBeginTime("1984-04-01");
        req.setInstoreEndTime(null);
        req.setMktResId("1783295");
        req.setBarCode("A000008E25F8CA");

        req.setStoreId("101336024");//,101336024

        req.setPageIndex("1");
        req.setPageSize("20");

        ResultVO<QueryMarkResQueryResultsSwapResp<QryMktInstInfoByConditionItemSwapResp>> result = marketingResStoreService.qryMktInstInfoByCondition(req);
        System.out.println(result);//ok

        System.out.println("--------------------------------------------------------");
        System.out.println("仓库id    商家标识    商家名称    串码");
        List<QryMktInstInfoByConditionItemSwapResp> list = result.getResultData().getQueryInfo();
        for (QryMktInstInfoByConditionItemSwapResp item : list) {
            System.out.println(item.getStoreId()+"    "+item.getProviderCode()+"    "+item.getProviderName()+"    "+item.getBarCode());
        }
        System.out.println("--------------------------------------------------------");
    }

    @Test
    public void storeInventoryQuantity() {

        StoreInventoryQuantitySwapReq req = new StoreInventoryQuantitySwapReq();
        req.setStoreId("203658057");
        ResultVO<QueryMarkResQueryResultsSwapResp<StoreInventoryQuantityItemSwapResp>> result = marketingResStoreService.storeInventoryQuantity(req);
        System.out.println(result);//ok
    }
    public void synMarkResStore(){
        SynMarkResStoreReq req = new SynMarkResStoreReq();

        marketingResStoreService.synMarkResStore(req);
    }
}
