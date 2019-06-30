package com.iwhalecloud.retail.order2b.dubbo;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.order2b.TestBase;
import com.iwhalecloud.retail.order2b.dto.response.purapply.*;
import com.iwhalecloud.retail.order2b.dto.resquest.purapply.*;
import com.iwhalecloud.retail.order2b.entity.PurApplyItemDetail;
import com.iwhalecloud.retail.order2b.manager.PurApplyManager;
import com.iwhalecloud.retail.order2b.service.PurApplyService;
import com.iwhalecloud.retail.order2b.service.PurchaseApplyService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
    @Autowired
    private PurApplyManager purApplyManager;
    @Resource
    private PurchaseApplyService purchaseApplyService;


    @Test
    public void tcProcureApply() {
////        System.out.println("1111"+purApplyService);
//
////        1770	1559635944794	测试测试测试			10000701	DBS0025054	4331301047393	4331301047393	1345665656	731	73101		21	200012813991	2019-06-04 16:13:29	200012813991	2019-06-04 16:13:29	2019-06-04 16:13:29
//       String isSave="3";
//        String applyId="1770";
//        ProcureApplyReq req = new ProcureApplyReq();
//        req.setIsSave(isSave);
//        req.setApplyId(applyId);
//        req.setApplyCode("1559635944794");
//        req.setApplyName("chenb9y53chenbin1");
//        req.setApplyMerchantCode("4331301047393");
//        req.setApplyAddress("731");
//        req.setApplyContact("13004835189");
//        req.setSupplierId("4331301016047");
//        req.setSupplierCode("4331301016047");
//        req.setApplyMerchantId("4331301047393");
//        req.setRegionId("73105");
//        req.setStatusCd("20");
//        req.setStatusDate("2019-06-04 16:13:29");
//        req.setCreateStaff("1");
//        req.setCreateDate("2019-06-04 16:13:29");
//        req.setUpdateStaff("1");
//        req.setApplyType("10");
//        AddProductReq addProductReq1 =new AddProductReq();
//        addProductReq1.setSnCount("1");
//        addProductReq1.setPriceInStore("9121111000");
//        addProductReq1.setPurchaseType("10");
//        addProductReq1.setProductId("100006163");
//        List<AddProductReq> p = new ArrayList<>();
//        p.add(addProductReq1);
//        req.setAddProductReq(p);
//
//
//
//
//
//
//

        ProcureApplyReq req = new ProcureApplyReq();
        String json ="{\"isSave\":\"3\",\"applyId\":\"1965\",\"applyCode\":\"1561172958603\",\"regionId\":\"73001\",\"applyMerchantId\":null,\"addrId\":\"12333929\",\"applyAddress\":\"730\",\"applyContact\":\"13004835189\",\"applyName\":\"chenbin12345678\",\"supplierId\":\"10000696\",\"addFileReq\":[],\"addProductReq\":[{\"productId\":\"100002873\",\"snCount\":\"2\",\"priceInStore\":\"33200\",\"purchaseType\":\"2\",\"parentTypeId\":null}],\"content\":\"改成集采哈哈哈，，你打我呀\"}";
        Gson gson = new Gson();
         req = gson.fromJson(json, new TypeToken<ProcureApplyReq>(){}.getType());
        String userId = "22796";
        String userName = "王利";
        req.setHandleUserId(userId);
        req.setHandleUserName(userName);
        String isSave = req.getIsSave();
        String statusCd = null ;
        if("1".equals(isSave)){//保存
            statusCd = "10";
        }else if("2".equals(isSave)){//提交
            statusCd = "20";
        }
        if(req.getAddrId()==null) {
            ResultVO.error("请选择收货地址！");
        }

        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = formatter.format(date);
        String updateStaff =userId;
        String updateDate = dateString;
        String statusDate = dateString;
        //情况一，默认是保存,状态就是10，待提交
        //情况二，如果是提交，状态就是20，待审核(分表里面是否有记录)
        String applyId = req.getApplyId();
        String createStaff = userId;

        //获取供应商ID和申请商家ID
        String supplierId = req.getSupplierId();
        String applyMerchantId = req.getApplyMerchantId();//申请商家ID
        String supplierCode = purApplyService.getMerchantCode(supplierId);
        String applyMerchantCode = purApplyService.getMerchantCode(applyMerchantId);

        String createDate = dateString;//创建时间

        req.setStatusCd(statusCd);
        req.setCreateStaff(createStaff);
        req.setCreateDate(createDate);
        req.setUpdateStaff(updateStaff);
        req.setUpdateDate(updateDate);
        req.setStatusDate(statusDate);
        req.setSupplierCode(supplierCode);
        req.setApplyMerchantCode(applyMerchantCode);
//        String userId = userId
//        String userName = u
//        req.setHandleUserId(userId);
//        req.setHandleUserName(userName);
        int isHaveSave = purApplyService.isHaveSave(applyId);
        if(isHaveSave != 0){//表里面有记录的话,申请单的字段就update,添加产品跟附件的就先delete再insert

            purApplyService.updatePurApply(req);//联系电话，项目名称，供应商code可以修改

            purApplyService.delApplyItem(req);

            purApplyService.delApplyFile(req);

            purApplyService.delPurApplyExt(req);

        }else{

            purApplyService.insertTcProcureApply(req);


        }

        List<AddProductReq> list = req.getAddProductReq();
        for(int i=0;i<list.size();i++){
            AddProductReq addProductReq = list.get(i);
            String itemId = purApplyService.hqSeqItemId();
            addProductReq.setApplyItemId(itemId);
            addProductReq.setApplyId(req.getApplyId());
            addProductReq.setStatusCd("1000");
            addProductReq.setCreateStaff(createStaff);
            addProductReq.setCreateDate(createDate);
            addProductReq.setUpdateStaff(updateStaff);
            addProductReq.setUpdateDate(updateDate);
            addProductReq.setStatusDate(statusDate);
            //写表PUR_APPLY_ITEM(采购申请单项)
            purApplyService.crPurApplyItem(addProductReq);

        }


        //表里面没记录的话

        MemMemberAddressReq memMeneberAddr = purApplyService.selectMemMeneberAddr(req);
        if(memMeneberAddr==null) {
            ResultVO.error("找不到该地址！");
        }
        memMeneberAddr.setApplyId(applyId);
        memMeneberAddr.setCreateStaff(createStaff);
        memMeneberAddr.setCreateDate(createDate);
        memMeneberAddr.setUpdateStaff(updateStaff);
        memMeneberAddr.setUpdateDate(updateDate);
        purApplyService.insertPurApplyExt(memMeneberAddr);

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
                addFileReq.setCreateStaff(createStaff);
                addFileReq.setCreateDate(createDate);
                addFileReq.setUpdateStaff(updateStaff);
                addFileReq.setUpdateDate(updateDate);
                purApplyService.crPurApplyFile(addFileReq);
            }
        }
        if(!"1".equals(isSave)) {// 只要不是保存都要起流程
            purApplyService.tcProcureApply(req);
        }
        System.out.println(ResultVO.success());
       // return ResultVO.success();

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
    @Test
    public void ckApplyData2() {
        PurApplyReq req = new PurApplyReq();
        req.setApplyId("1878");
        List<AddProductReq> procureApplyReq2 = purApplyService.ckApplyData2(req);
        System.out.println(JSON.toJSONString(procureApplyReq2));
    }
    @Test
    public void updatePurTypeByApplyId() {
//        PurApplyReq req = new PurApplyReq();
//        req.setApplyId("1878");
//        List<AddProductReq> procureApplyReq2 = purApplyService.ckApplyData2(req);
//        System.out.println(JSON.toJSONString(procureApplyReq2));
        ProcureApplyReq req = new ProcureApplyReq();
        List<AddProductReq> procureApplyReq =new ArrayList<AddProductReq>();
        AddProductReq ad =new AddProductReq();
        ad.setPurchaseType("1");
        ad.setApplyItemId("6272");
        procureApplyReq.add(ad);
        AddProductReq ad1 =new AddProductReq();
        ad1.setPurchaseType("1");
        ad1.setApplyItemId("6273");
        procureApplyReq.add(ad1);
        req.setAddProductReq(procureApplyReq);
        ResultVO v= purApplyService.updatePurTypeByApplyId(req);
        System.out.println(JSON.toJSONString(v));
    }
    @Test
    public void applySearchReport(){
        String json = "{\"pageNo\":1,\"pageSize\":10,\"applyCode\":\"\",\"applyName\":\"\",\"lanIdList\":[],\"merchantName\":\"\",\"productName\":\"\",\"unitType\":\"\",\"color\":\"\",\"memory\":\"\",\"statusCd\":\"\"}";
//        PurApplyReq req = new PurApplyReq();
        Gson gson = new Gson();
        PurApplyReportReq req = gson.fromJson(json, new TypeToken<PurApplyReportReq>(){}.getType());
//        log.info("cgSearchApply参数   req={}"+JSON.toJSONString(req));
        req.setLanId("731");
//        String userId = UserContext.getUserId();
//		String userId = "100028487";
//        PriCityManagerResp login = purApplyService.getLoginInfo(userId);
//        Integer userFounder = UserContext.getUser().getUserFounder();
        //传过来的APPLY_TYPE看

//        String lanId = login.getLanId();

//        log.info("1查询采购申请单报表*******************lanId = "+lanId +" **************userFounder = "+userFounder);
//        if(userFounder!=null) {
//            if(9==userFounder){//地市管理员
//                log.info("2查询采购申请单报表*******************lanId = "+lanId +" **************userFounder = "+userFounder);
//                req.setLanId(lanId);
//            }
//        }
//        Boolean isMerchant= UserContext.isMerchant();
//        if(isMerchant==true) {
//            req.setMerchantId(UserContext.getMerchantId());
//            log.info("查询采购申请单报表*******************isMerchant = "+isMerchant +" **************UserContext.getMerchantId() = "+UserContext.getMerchantId());
//        }
//
//        log.info("查询采购申请单报表入参*******************lanId = "+req.getLanId() );
        ResultVO resultVO =   purApplyService.applySearchReport(req);
        System.out.println(JSON.toJSONString(resultVO));
//        return ResultVO.success(purApplyResp);
    }

    @Test
    public void getDeliveryListByApplyID() {
        String applyId="1997";
        List<PurApplyItemDetail> l = purchaseApplyService.getDeliveryListByApplyID(applyId);
        System.out.println("lllll==="+JSON.toJSONString(l));
        PurApplyReq r = new PurApplyReq();
        r.setApplyId(applyId);
        ResultVO<Page<PurApplyDeliveryResp>> resultVO = purchaseApplyService.getDeliveryInfoByApplyID(r);
        System.out.println("lllll==="+JSON.toJSONString(resultVO));

    }

}