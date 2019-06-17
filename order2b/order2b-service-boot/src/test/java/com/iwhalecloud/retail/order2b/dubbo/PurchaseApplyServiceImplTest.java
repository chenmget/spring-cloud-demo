package com.iwhalecloud.retail.order2b.dubbo;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.order2b.TestBase;
import com.iwhalecloud.retail.order2b.dto.response.purapply.PurApplyDeliveryResp;
import com.iwhalecloud.retail.order2b.dto.resquest.purapply.PurApplyReceivingReq;
import com.iwhalecloud.retail.order2b.dto.resquest.purapply.PurApplyReq;
import com.iwhalecloud.retail.order2b.service.PurchaseApplyService;
import com.iwhalecloud.retail.warehouse.common.ResourceConst;
import org.junit.Test;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

public class PurchaseApplyServiceImplTest extends TestBase {


//    ProcureApplyReq(isSave=1, applyId=1651, applyCode=1558768652984, applyName=chenbin5, applyMerchantCode=4331301047393
//            , applyAddress=731, applyDepartment=null, applyContact=13004835189,
//                    supplierId=4331301016047, applyType=null, content=null, supplierCode=4331301016047, applyMerchantId=4331301047393, regionId=73105,
//                    relApplyId=null, statusCd=10, statusDate=2019-5-25 16:28:22, createStaff=1, createDate=2019-5-25 16:28:22, updateStaff=1,
//                    updateDate=2019-5-25 16:28:22, addrId=12308526, addProductReq=[AddProductReq(productName=null, brandName=null,
//                    unitType=null, specName=null, color=null, cost=null, snCount=11, priceInStore=111100, purchaseType=10, sn=null, applyItemId=null,
//                    applyId=null, statusCd=null, createStaff=null, createDate=null, updateStaff=null, updateDate=null, statusDate=null,
//                    isFixedLine=null, typeName=null, productId=100006163)], addFileReq=[])

    @Resource
    private PurchaseApplyService purchaseApplyService;


    @Test
    public void getDeliveryInfoByApplyID(){
        PurApplyReq req = new PurApplyReq();
        req.setApplyId("1723");
        ResultVO<Page<PurApplyDeliveryResp>> listResultVO = purchaseApplyService.getDeliveryInfoByApplyID(req);
        Page<PurApplyDeliveryResp> l = listResultVO.getResultData();
        List<PurApplyDeliveryResp> r = l.getRecords();
        for(PurApplyDeliveryResp p:r) {
            System.out.println(p.getBatchId()+p.getUnitName()+p.getMktResInstNbr()+p.getCreateDate());
        }
    }


    @Test
    public void reving() {
        PurApplyReceivingReq req = new PurApplyReceivingReq();
        req.setApplyId("1736");
        String userId = "1";
        req.setCreateStaff(userId);
        req.setStatusCd(ResourceConst.STATUSCD.AVAILABLE.getCode());
        req.setEventType(ResourceConst.EVENTTYPE.PUT_STORAGE.getCode());
        req.setSourceType(ResourceConst.SOURCE_TYPE.MERCHANT.getCode());
        req.setStorageType(ResourceConst.STORAGETYPE.SUPPLIER_INPUT.getCode());
        req.setMktResInstType(ResourceConst.MKTResInstType.TRANSACTION.getCode());
        req.setMerchantId("4331301049118");
        ResultVO resultVO = purchaseApplyService.receiving(req);
        System.out.println(resultVO.isSuccess()+"===="+resultVO.getResultMsg());

    }
    @Test
    public void updateStatuscd() {
        List<String> l = new ArrayList<String>();
        l.add("p20p2019061303");
        Integer i = purchaseApplyService.updatePurApplyItemDetailStatusCd(l);
        System.out.println(i);
    }
}