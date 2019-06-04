package com.iwhalecloud.retail.warehouse.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.warehouse.WarehouseServiceApplication;
import com.iwhalecloud.retail.warehouse.dto.ResourceInstStoreDTO;
import com.iwhalecloud.retail.warehouse.dto.request.GetProductQuantityByMerchantReq;
import com.iwhalecloud.retail.warehouse.dto.request.InventoryWaringReq;
import com.iwhalecloud.retail.warehouse.dto.request.ProductQuantityItem;
import com.iwhalecloud.retail.warehouse.dto.request.UpdateStockReq;
import com.iwhalecloud.retail.warehouse.dto.response.InventoryWarningResp;
import com.iwhalecloud.retail.warehouse.entity.MktResItmsSyncRec;
import com.iwhalecloud.retail.warehouse.mapper.MktResItmsSyncRecMapper;
import com.iwhalecloud.retail.warehouse.service.ResourceInstStoreService;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.util.Lists;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = WarehouseServiceApplication.class)
@Slf4j
public class ResourceInstStoreServiceImplTest {

    @Resource
    private MktResItmsSyncRecMapper mktResItmsSyncRecMapper;
    @Reference
    private ResourceInstStoreService resourceInstStoreService;

    @Autowired
    private ResourceInstStoreServiceImpl resourceInstStoreServiceImpl;
    @Test
    public void getProductQuantityByMerchant() {
        GetProductQuantityByMerchantReq req = new GetProductQuantityByMerchantReq();
        req.setMerchantId("1");
        List<ProductQuantityItem> itemList = new ArrayList<>();
        ProductQuantityItem item = new ProductQuantityItem();
        item.setNum(100L);
        item.setProductId("1");
        itemList.add(item);
        req.setItemList(itemList);
        String json = "{\"itemList\":[{\"num\":2,\"productId\":\"1085133312128376834\"}],\"merchantId\":\"4301811025392\"}";
        Gson gson = new Gson();
        req  = gson.fromJson(json, new TypeToken<GetProductQuantityByMerchantReq>(){}.getType());
        resourceInstStoreService.getProductQuantityByMerchant(req);
    }

    @Test
    public void updateStock() {
        UpdateStockReq req = new UpdateStockReq();
        List<ProductQuantityItem> list = new ArrayList<>();
        ProductQuantityItem item = new ProductQuantityItem();
        item.setNum(10L);
        item.setProductId("1");
        list.add(item);
        req.setItemList(list);
        req.setMerchantId("1");
        resourceInstStoreService.updateStock(req);
    }

    @Test
    public void getProductQuantityByMerchant1() {
    }

    @Test
    public void updateResourceInstStore(){
        ResourceInstStoreDTO req = new ResourceInstStoreDTO();
        req.setMerchantId("4301811022885");
        req.setMktResId("1101089045995012097");
        req.setMktResStoreId("31");
        req.setQuantity(1L);
        req.setQuantityAddFlag(false);
        req.setStatusCd("1302");
        resourceInstStoreService.updateResourceInstStore(req);
    }

    @Test
    public void queryInventoryWarningTest(){
        List<InventoryWaringReq> req = Lists.newArrayList();
        InventoryWaringReq req1 = new InventoryWaringReq();
        req1.setProductId("100000114");
        req1.setMerchantId("4301811025392");
        req.add(req1);
        InventoryWaringReq req2 = new InventoryWaringReq();
        req2.setProductId("100000861");
        req2.setMerchantId("4301811025392");
        req.add(req2);
        InventoryWaringReq req3 = new InventoryWaringReq();
        req3.setProductId("10000586");
        req3.setMerchantId("4301811025392");
        req.add(req3);
        ResultVO<List<InventoryWarningResp>> resultVO = resourceInstStoreService.queryInventoryWarning(req);
        log.info("ResourceInstStoreServiceImplTest.queryInventoryWarningTest resp={}", JSON.toJSON(resultVO.getResultData()));
    }

    @Test
    public void getQuantityByMerchantId(){
        ResultVO<Integer> amount = resourceInstStoreService.getQuantityByMerchantId("4300001063072");
        System.out.print("amount==============="+ amount.getResultData());
    }
    @Test
    public void batchUpdateTest(){
        List<MktResItmsSyncRec> mktResItmsSyncRecRep = Lists.newArrayList();
        MktResItmsSyncRec mti1 = new MktResItmsSyncRec();
        MktResItmsSyncRec mti2 = new MktResItmsSyncRec();
        MktResItmsSyncRec mti3 = new MktResItmsSyncRec();
        mti1.setMktResInstNbr("201903192116");
        mti1.setSyncFileName("/home/itsm_y/itmsfile/data/back/Terminal/delete/731ITMS20190529002.txt");
        mti2.setSyncFileName("/home/itsm_y/itmsfile/data/back/Terminal/modify/735ITMS20190529002.txt");
        mti2.setMktResInstNbr("1223444444445");
        mti1.setStatusCd("1");
        mti2.setStatusCd("1");
        mti3.setMktResInstNbr("201903192116");
        mti3.setSyncFileName("/home/itsm_y/itmsfile/data/back/Terminal/delete/731ITMS20190529002.txt");
        mti3.setStatusCd("1");
        mktResItmsSyncRecRep.add(mti1);
        mktResItmsSyncRecRep.add(mti2);
        mktResItmsSyncRecRep.add(mti3);
        for(int i=0;i<mktResItmsSyncRecRep.size();i++){
            System.out.println("***********************"+mktResItmsSyncRecMapper.updateMRIyFileName(mktResItmsSyncRecRep.get(i)));
        }
    }
    @Test
    public void syncMktToITMSBack(){
//        System.out.println(mktResItmsSyncRecMapper.getSeqBysendDir("/home/itsm_y/itmsfile/data/back/IPTV/add/730"));
        resourceInstStoreServiceImpl.syncMktToITMS();
//        resourceInstStoreService.syncMktToITMS();
    }

}