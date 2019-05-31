package com.iwhalecloud.retail.order2b.dubbo;

import com.alibaba.dubbo.config.annotation.Reference;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.order2b.TestBase;
import com.iwhalecloud.retail.order2b.dto.response.purapply.PurApplyDeliveryResp;
import com.iwhalecloud.retail.order2b.dto.resquest.purapply.*;
import com.iwhalecloud.retail.order2b.service.PurApplyService;
import com.iwhalecloud.retail.order2b.service.PurchaseApplyService;
import org.junit.Test;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

public class PurApplyServiceImplTest  extends TestBase {


//    ProcureApplyReq(isSave=1, applyId=1651, applyCode=1558768652984, applyName=chenbin5, applyMerchantCode=4331301047393
//            , applyAddress=731, applyDepartment=null, applyContact=13004835189,
//                    supplierId=4331301016047, applyType=null, content=null, supplierCode=4331301016047, applyMerchantId=4331301047393, regionId=73105,
//                    relApplyId=null, statusCd=10, statusDate=2019-5-25 16:28:22, createStaff=1, createDate=2019-5-25 16:28:22, updateStaff=1,
//                    updateDate=2019-5-25 16:28:22, addrId=12308526, addProductReq=[AddProductReq(productName=null, brandName=null,
//                    unitType=null, specName=null, color=null, cost=null, snCount=11, priceInStore=111100, purchaseType=10, sn=null, applyItemId=null,
//                    applyId=null, statusCd=null, createStaff=null, createDate=null, updateStaff=null, updateDate=null, statusDate=null,
//                    isFixedLine=null, typeName=null, productId=100006163)], addFileReq=[])
    @Resource
    private PurApplyService purApplyService;
    @Resource
    private PurchaseApplyService purchaseApplyService;
    @Test
    public void tcProcureApply() {
//        System.out.println("1111"+purApplyService);
        String applyId="1694";
        ProcureApplyReq req = new ProcureApplyReq();
        req.setIsSave("2");
        req.setApplyId(applyId);
        req.setApplyCode("1558768652984");
        req.setApplyName("chenbin4455y53");
        req.setApplyMerchantCode("4331301047393");
        req.setApplyAddress("731");
        req.setApplyContact("13004835189");
        req.setSupplierId("4331301016047");
        req.setSupplierCode("4331301016047");
        req.setApplyMerchantId("4331301047393");
        req.setRegionId("73105");
        req.setStatusCd("20");
        req.setStatusDate("2019-5-27 17:09:22");
        req.setCreateStaff("1");
        req.setCreateDate("2019-5-25 17:09:22");
        req.setUpdateStaff("1");
        req.setApplyType("10");
        AddProductReq addProductReq =new AddProductReq();
        addProductReq.setSnCount("1");
        addProductReq.setPriceInStore("121111000");
        addProductReq.setPurchaseType("10");
        addProductReq.setProductId("100006163");
        List<AddProductReq> p = new ArrayList<>();
        p.add(addProductReq);
        req.setAddProductReq(p);

        List<AddProductReq> list = req.getAddProductReq();
        for(int i=0;i<list.size();i++){
            AddProductReq addProductReq1 = list.get(i);
            String itemId = purApplyService.hqSeqItemId();
            addProductReq1.setApplyItemId(itemId);
            addProductReq1.setApplyId(req.getApplyId());
            addProductReq1.setStatusCd("1000");
            addProductReq1.setCreateStaff("1");
            addProductReq1.setCreateDate("2019-5-25 17:09:22");
            addProductReq1.setUpdateStaff("1");
            addProductReq1.setUpdateDate("2019-5-25 17:09:22");
            addProductReq1.setStatusDate("2019-5-25 17:09:22");
            //写表PUR_APPLY_ITEM(采购申请单项)
            purApplyService.crPurApplyItem(addProductReq1);
        }

        purApplyService.tcProcureApply(req);


        //表里面没记录的话

//             MemMemberAddressReq memMeneberAddr = purApplyService.selectMemMeneberAddr(req);
//        System.out.println("11=======================================================11"+applyId);
//        memMeneberAddr.setApplyId(applyId);
//        memMeneberAddr.setCreateStaff("1");
//        memMeneberAddr.setCreateDate("2019-5-25 17:09:22");
//        memMeneberAddr.setUpdateStaff("1");
//        memMeneberAddr.setUpdateDate("2019-5-25 17:09:22");
//        purApplyService.insertPurApplyExt(memMeneberAddr);




    }

}