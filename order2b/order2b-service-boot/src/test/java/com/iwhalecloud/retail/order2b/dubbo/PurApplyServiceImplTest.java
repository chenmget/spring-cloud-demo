package com.iwhalecloud.retail.order2b.dubbo;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.iwhalecloud.retail.order2b.TestBase;
import com.iwhalecloud.retail.order2b.dto.resquest.purapply.AddFileReq;
import com.iwhalecloud.retail.order2b.dto.resquest.purapply.AddProductReq;
import com.iwhalecloud.retail.order2b.dto.resquest.purapply.ProcureApplyReq;
import com.iwhalecloud.retail.order2b.dto.resquest.purapply.PurApplyReq;
import com.iwhalecloud.retail.order2b.service.PurApplyService;
import com.iwhalecloud.retail.workflow.dto.req.RouteNextReq;
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

    @Test
    public void tcProcureApply() {
//        System.out.println("1111"+purApplyService);

//        1770	1559635944794	测试测试测试			10000701	DBS0025054	4331301047393	4331301047393	1345665656	731	73101		21	200012813991	2019-06-04 16:13:29	200012813991	2019-06-04 16:13:29	2019-06-04 16:13:29
       String isSave="3";
        String applyId="1770";
        ProcureApplyReq req = new ProcureApplyReq();
        req.setIsSave(isSave);
        req.setApplyId(applyId);
        req.setApplyCode("1559635944794");
        req.setApplyName("chenb9y53chenbin1");
        req.setApplyMerchantCode("4331301047393");
        req.setApplyAddress("731");
        req.setApplyContact("13004835189");
        req.setSupplierId("4331301016047");
        req.setSupplierCode("4331301016047");
        req.setApplyMerchantId("4331301047393");
        req.setRegionId("73105");
        req.setStatusCd("20");
        req.setStatusDate("2019-06-04 16:13:29");
        req.setCreateStaff("1");
        req.setCreateDate("2019-06-04 16:13:29");
        req.setUpdateStaff("1");
        req.setApplyType("10");
        AddProductReq addProductReq1 =new AddProductReq();
        addProductReq1.setSnCount("1");
        addProductReq1.setPriceInStore("9121111000");
        addProductReq1.setPurchaseType("10");
        addProductReq1.setProductId("100006163");
        List<AddProductReq> p = new ArrayList<>();
        p.add(addProductReq1);
        req.setAddProductReq(p);







        String userId = "200012813991";
        String userName = "zte管理员";
        req.setHandleUserId(userId);
        req.setHandleUserName(userName);
        int isHaveSave = purApplyService.isHaveSave(applyId);
        if(isHaveSave != 0){//表里面有记录的话,申请单的字段就update,添加产品跟附件的就先delete再insert

            purApplyService.updatePurApply(req);//联系电话，项目名称，供应商code可以修改

            purApplyService.delApplyItem(req);

            purApplyService.delApplyFile(req);

            purApplyService.delPurApplyExt(req);

            List<AddProductReq> list = req.getAddProductReq();
            for(int i=0;i<list.size();i++){
                AddProductReq addProductReq = list.get(i);
                String itemId = purApplyService.hqSeqItemId();
                addProductReq.setApplyItemId(itemId);
                addProductReq.setApplyId(req.getApplyId());
                addProductReq.setStatusCd("1000");
                addProductReq.setCreateStaff("200012813991");
                addProductReq.setCreateDate("2019-06-04 16:13:29");
                addProductReq.setUpdateStaff("200012813991");
                addProductReq.setUpdateDate("2019-06-04 16:13:29");
                addProductReq.setStatusDate("2019-06-04 16:13:29");
                //写表PUR_APPLY_ITEM(采购申请单项)
                purApplyService.crPurApplyItem(addProductReq);
            }
            if("3".equals(isSave)){//编辑
                purApplyService.tcProcureApply(req);
            }

        }else{
            List<AddProductReq> list = req.getAddProductReq();
            for(int i=0;i<list.size();i++){
                AddProductReq addProductReq = list.get(i);
                String itemId = purApplyService.hqSeqItemId();
                addProductReq.setApplyItemId(itemId);
                addProductReq.setApplyId(req.getApplyId());
                addProductReq.setStatusCd("1000");
                addProductReq.setCreateStaff("200012813991");
                addProductReq.setCreateDate("2019-06-04 16:13:29");
                addProductReq.setUpdateStaff("200012813991");
                addProductReq.setUpdateDate("2019-06-04 16:13:29");
                addProductReq.setStatusDate("2019-06-04 16:13:29");
                //写表PUR_APPLY_ITEM(采购申请单项)
                purApplyService.crPurApplyItem(addProductReq);

            }

            purApplyService.tcProcureApply(req);
        }

        //表里面没记录的话

//        MemMemberAddressReq memMeneberAddr = purApplyService.selectMemMeneberAddr(req);
//        memMeneberAddr.setApplyId(applyId);
//        memMeneberAddr.setCreateStaff("200012813991");
//        memMeneberAddr.setCreateDate("2019-06-04 16:13:29");
//        memMeneberAddr.setUpdateStaff("200012813991");
//        memMeneberAddr.setUpdateDate("2019-06-04 16:13:29");
//        purApplyService.insertPurApplyExt(memMeneberAddr);

//		List<AddProductReq> list = req.getAddProductReq();
//		for(int i=0;i<list.size();i++){
//			AddProductReq addProductReq = list.get(i);
//			String itemId = purApplyService.hqSeqItemId();
//			addProductReq.setApplyItemId(itemId);
//			addProductReq.setApplyId(req.getApplyId());
//			addProductReq.setStatusCd("1000");
//			addProductReq.setCreateStaff(createStaff);
//			addProductReq.setCreateDate(createDate);
//			addProductReq.setUpdateStaff(updateStaff);
//			addProductReq.setUpdateDate(updateDate);
//			addProductReq.setStatusDate(statusDate);
//			//写表PUR_APPLY_ITEM(采购申请单项)
//			purApplyService.crPurApplyItem(addProductReq);
//		}

        List<AddFileReq> fileList = req.getAddFileReq();
        if(fileList != null){
            for(int i=0;i<fileList.size();i++){
                AddFileReq addFileReq = fileList.get(i);
                String fileId = purApplyService.hqSeqFileId();
                addFileReq.setFileId(fileId);
                addFileReq.setApplyId(req.getApplyId());
                addFileReq.setCreateStaff("200012813991");
                addFileReq.setCreateDate("2019-06-04 16:13:29");
                addFileReq.setUpdateStaff("200012813991");
                addFileReq.setUpdateDate("2019-06-04 16:13:29");
                purApplyService.crPurApplyFile(addFileReq);
            }
        }



    }
    @Test
    public void purApplysearch() {
        String can="{\"applyCode\":\"\",\"startDate\":\"\",\"endDate\":\"\",\"applyName\":\"\",\"pageNo\":1,\"pageSize\":10,\"sourceFrom\":\"\"}";
        Gson gson = new Gson();
        PurApplyReq req = gson.fromJson(can, new TypeToken<PurApplyReq>(){}.getType());
        req.setLanId("730");
//        req.setMerchantId("555");
        purApplyService.cgSearchApply(req);
    }


}