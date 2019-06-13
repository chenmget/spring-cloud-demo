package com.iwhalecloud.retail.order2b.dubbo;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.goods2b.dto.resp.ProductInfoResp;
import com.iwhalecloud.retail.goods2b.service.dubbo.ProductService;
import com.iwhalecloud.retail.order2b.consts.PurApplyConsts;
import com.iwhalecloud.retail.order2b.dto.response.purapply.PurApplyDeliveryResp;
import com.iwhalecloud.retail.order2b.dto.resquest.purapply.*;
import com.iwhalecloud.retail.order2b.entity.PurApply;
import com.iwhalecloud.retail.order2b.entity.PurApplyItem;
import com.iwhalecloud.retail.order2b.entity.PurApplyItemDetail;
import com.iwhalecloud.retail.order2b.manager.*;
import com.iwhalecloud.retail.order2b.service.PurchaseApplyService;
import com.iwhalecloud.retail.warehouse.dto.request.*;
import com.iwhalecloud.retail.warehouse.dto.response.ResourceInstCheckResp;
import com.iwhalecloud.retail.warehouse.dto.response.ResourceInstListPageResp;
import com.iwhalecloud.retail.warehouse.service.ResouceStoreService;
import com.iwhalecloud.retail.warehouse.service.SupplierResourceInstService;
import com.iwhalecloud.retail.warehouse.service.TradeResourceInstService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * @auther lin.wenhui@iwhalecloud.com
 * @date 2019/4/26 15:57
 * @description 采购管理
 */

@Service
@Slf4j
public class PurchaseApplyServiceImpl implements PurchaseApplyService {

    @Autowired
    private PurApplyDeliveryManager purApplyDeliveryManager;

    @Autowired
    private PurApplyItemManager purApplyItemManager;

    @Autowired
    private PurApplyManager purApplyManager;

    @Autowired
    private PurApplyItemDetailManager purApplyItemDetailManager;

    @Autowired
    private PurApplyExtManager purApplyExtManager;

    @Reference
    private SupplierResourceInstService supplierResourceInstService;
    @Reference
    TradeResourceInstService tradeResourceInstService;

    @Reference
    private ProductService productService;
    @Reference
    private ResouceStoreService resouceStoreService;

    /**
     * 采购单发货
     *
     * @param req
     * @return
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public ResultVO delivery(PurApplyDeliveryReq req) {
        List<String> mktResInstNbr = Lists.newArrayList();
        mktResInstNbr = req.getMktResInstNbr(); //串码列表
        int total = mktResInstNbr.size();//串码总数
        if(mktResInstNbr==null || mktResInstNbr.size()<=0) {
            return ResultVO.error("没有收到串码记录");
        }
        //select * from mkt_res_store where store_type = '1100' and lan_id = '731';
        PurApply purApply = purApplyManager.getPurApplyByAppId(req.getApplyId());
        String lanId = purApply.getLanId();
        log.info("1.查询申请单信息根据appId="+req.getApplyId()+" purApply= "+ JSON.toJSONString(purApply));
        //供应商 商家id
        String merchantId = purApply.getMerchantId();
        // 申请者 商家ID
//        String applyMerchantId =purApply.getApplyMerchantId();
        StoreGetStoreIdReq storeIdReq  = new StoreGetStoreIdReq();
        storeIdReq.setMerchantId(merchantId);
        storeIdReq.setStoreSubType("1300");//终端类型
        String MktResStoreId= resouceStoreService.getStoreId(storeIdReq);//查询仓库id
        log.info("2.供应商商家ID-merchantId="+merchantId+" 查出的MktResStoreId="+MktResStoreId);
        if (MktResStoreId==null) {
            return ResultVO.error("没有查到该供应商仓库");
        }
        // 存 key 产品id  - value 串码列表
        Map<String,List<String>> map = new HashMap<String,List<String>>();
        for (int i=0;i<mktResInstNbr.size();i++) {
            //开始验证串码的有效性
            String mktResInstNbrCheck = mktResInstNbr.get(i);


//        mktResInstNbr
            //生成batchId
//        String batchId = UUID.randomUUID().toString().replace("-", "");
//            List<String> mktResStoreIds = new ArrayList<String>();
            //查一下仓库
//            mktResStoreIds.add(MktResStoreId);
           // String mktResInstNbrCheck="";
//            ResourceInstListPageReq resource = new ResourceInstListPageReq();
//            resource.setMktResInstNbr(mktResInstNbrCheck);
//            resource.setMktResStoreIds(mktResStoreIds);

//            ResultVO<Page<ResourceInstListPageResp>> queryMktResInstNbr= supplierResourceInstService.getResourceInstList(resource);
            ResourceStoreIdResnbr resourceStoreIdResnbr = new ResourceStoreIdResnbr();
            resourceStoreIdResnbr.setMktResInstNbr(mktResInstNbrCheck);
            resourceStoreIdResnbr.setMktResStoreId(MktResStoreId);
            log.info("3.resourceStoreIdResnbr{} =" + JSON.toJSONString(resourceStoreIdResnbr));
            ResourceInstCheckResp resourceInstCheckResp = supplierResourceInstService.getMktResInstNbrForCheck(resourceStoreIdResnbr);
//            long count = queryMktResInstNbr.getResultData().getTotal();
            if (resourceInstCheckResp==null) {
                return ResultVO.error("该仓库查不到该串码"+mktResInstNbr);
            }else {
                //开始出理串码和产品id 归类，
//                List<ResourceInstListPageResp> resultData = queryMktResInstNbr.getResultData().getRecords();
//                ResourceInstListPageResp resourceInstListPageResp = resultData.get(0);
                String productId = resourceInstCheckResp.getMktResId();//产品ID
                if(map.get(productId)==null) {
                    List<String> mktResInstNbrList = new ArrayList<String>();
                    mktResInstNbrList.add(mktResInstNbrCheck);
                    map.put(productId,mktResInstNbrList);
                }else {
                    List<String> mktResInstNbrList = map.get(productId);
                    mktResInstNbrList.add(mktResInstNbrCheck);
                    map.put(productId,mktResInstNbrList);
                }
            }
        }
        log.info("4.产品对应串码map =" + JSON.toJSONString(map));
        //判断一下 ，产品id 对应的串码数 相加 是否等于总的串码数
//        TradeResourceInstItem tradeResourceInstItem = new TradeResourceInstItem();
        List<TradeResourceInstItem> tradeResourceInstItemItemList = new ArrayList<TradeResourceInstItem>();
        int totalTemp = 0;
        for (String key : map.keySet()) {
            List<String> temp  = map.get(key);
            totalTemp = totalTemp+temp.size();
            TradeResourceInstItem tradeResourceInstItemTemp = new TradeResourceInstItem();
            tradeResourceInstItemTemp.setProductId(key);
            tradeResourceInstItemTemp.setMktResInstNbrs(temp);
            tradeResourceInstItemItemList.add(tradeResourceInstItemTemp);
        }
        if (total != totalTemp) {
            return ResultVO.error("产品对应的串码数和总的串码数不相等");
        }
       // 开始调用串码出库
       // ResultVO deliveryOutResourceInst(DeliveryResourceInstReq req);
        TradeResourceInstReq tradeResourceInstReq = new TradeResourceInstReq();
        tradeResourceInstReq.setOrderId(req.getApplyId());
        tradeResourceInstReq.setLanId(lanId);//申请者地市ID
        tradeResourceInstReq.setSellerMerchantId(merchantId);//供应商商家ID
        tradeResourceInstReq.setTradeResourceInstItemList(tradeResourceInstItemItemList);
        log.info("5开始调用串码出库.tradeOutResourceInst= tradeResourceInstReq =" + JSON.toJSONString(tradeResourceInstReq));
//        ResultVO outResult = supplierResourceInstService.deliveryOutResourceInst(deliveryResourceInstReq);
        ResultVO outResult = tradeResourceInstService.tradeOutResourceInst(tradeResourceInstReq);

        log.info("6.调用串码出库结果outResult="+JSON.toJSONString(outResult));
        if(!outResult.isSuccess()) {
            return ResultVO.error(outResult.getResultMsg());
        }

//     开始操作申请单发货 相关业务逻辑表
        String batchId = purApplyDeliveryManager.getSeqApplyItemDetailBatchId();
        req.setBatchId(batchId);
        //插入采购申请单发货记录
        int i = purApplyDeliveryManager.insertPurApplyDelivery(req);
        log.info("PurchaseApplyServiceImpl.delivery insertPurApplyDeliveryResp = {}", i);
        if (i < 1) {
            return ResultVO.error("新增采购发货记录失败");
        }
        //处理前端录入的串码数据
        //String productIdAndMktResInstNbr = req.getProductIdAndMktResInstNbr();
      // List<String> result = Arrays.asList(productIdAndMktResInstNbr.split(";"));
       // List<String> productIdList = Lists.newArrayList();
//        List<String> mktResInstNbr = Lists.newArrayList();
//        mktResInstNbr = req.getMktResInstNbr();
//        if(mktResInstNbr==null || mktResInstNbr.size()<=0) {
//            return ResultVO.error("没有收到串码记录");
//        }
//        for (int l = 0; l < result.size(); l++) {
//            if (l % 2 == 0) {
//                productIdList.add(result.get(l));
//            } else {
//                mktResInstNbrList.add(result.get(l));
//            }
//        }
//        req.setProductIdList(productIdList);
//        req.setMktResInstNbrList(mktResInstNbrList);
        //通过采购申请单查询采购申请单项
//        List<PurApplyItem> purApplyItem = purApplyItemManager.getPurApplyItem(req.getApplyId());
//        for (PurApplyItem pI : purApplyItem) {
//            String pId = pI.getProductId();
//            String p = pI.getPurNum();
//        }
        //通过采购申请单查询采购申请单项
        List<PurApplyItem> purApplyItem = purApplyItemManager.getPurApplyItem(req.getApplyId());
        //新增采购申请单项明细
        List<PurApplyItemDetail> purApplyItemDetailList = Lists.newArrayList();
        for (PurApplyItem pI : purApplyItem) {
            String pId = pI.getProductId();
            List<String>mktResInstNbrList = map.get(pId);
            for (String mktResInstNbrTemp: mktResInstNbrList) {
                PurApplyItemDetail purApplyItemDetail = new PurApplyItemDetail();
                BeanUtils.copyProperties(req, purApplyItemDetail);
                purApplyItemDetail.setMktResInstNbr(mktResInstNbrTemp);
                purApplyItemDetail.setProductId(pId);
                purApplyItemDetail.setStatusCd(PurApplyConsts.PUR_APPLY_STATUS_DELIVERY);//待收货
                purApplyItemDetail.setApplyItemId(pI.getApplyItemId());
                purApplyItemDetailList.add(purApplyItemDetail);
            }
        }
//        for (int m = 0; m < purApplyItem.size(); m++) {
//            String pId = purApplyItem.getProductId();
//            for (int n = 0; n < mktResInstNbr.size(); n++) {
//                PurApplyItemDetail purApplyItemDetail = new PurApplyItemDetail();
//                BeanUtils.copyProperties(req, purApplyItemDetail);
//                purApplyItemDetail.setMktResInstNbr(mktResInstNbr.get(n));
//                purApplyItemDetail.setProductId(purApplyItem.get(m).getProductId());
//                purApplyItemDetail.setApplyItemId(purApplyItem.get(m).getApplyItemId());
//                purApplyItemDetailList.add(purApplyItemDetail);
//            }
//        }
        boolean saveFlag = purApplyItemDetailManager.saveBatch(purApplyItemDetailList);
        log.info("PurApplyServiceImpl.delivery saveBatchResp = {}", saveFlag);


        //判断是否全部发货完
        // 1查一下 发货明细记录 有几条数据 是否和 条目表的数量一致
        int flag = 0; //定义是否完全发货标识
        for (PurApplyItem PurApplyItemTemp : purApplyItem) {
            String num = PurApplyItemTemp.getPurNum();//数量
            PurApplyItemReq PurApplyItemReq = new PurApplyItemReq();
            PurApplyItemReq.setApplyItem(PurApplyItemTemp.getApplyItemId());
            PurApplyItemReq.setProductId(PurApplyItemTemp.getProductId());
            int count =purApplyManager.countPurApplyItemDetail(PurApplyItemReq);//查询发货的条数
            log.info("7._"+PurApplyItemTemp.getProductId()+" countPurApplyItemDetail = count ="+count+" = "+JSON.toJSONString(PurApplyItemReq));
            if (Integer.valueOf(num)!=count) {
                flag=1;//发货数量与条数数量不符合，标识还未完全发货
                break;
            }
        }

        if (flag==0) {//完全发货，修改采购单状态
            //更新采购申请单状态
            PurApplyReq purApplyReq = new PurApplyReq();
            purApplyReq.setApplyId(req.getApplyId());
            purApplyReq.setStatusCd(PurApplyConsts.PUR_APPLY_STATUS_RECEIVED);
            int k = purApplyDeliveryManager.updatePurApplyStatus(purApplyReq);
            log.info("PurchaseApplyServiceImpl.delivery updatePurApplyStatusResp = {}", k);
            if (k < 1) {
                return ResultVO.error("更新采购申请单状态失败");
            }
        }
        return ResultVO.success();
    }

    /**
     * 采购单确认收货
     *
     * @param req
     * @return
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public ResultVO receiving(PurApplyReceivingReq req) {
        //串码入库
        List<PurApplyItemDetail> purApplyItemDetailList = purApplyItemDetailManager.getPurApplyItemDetail(req.getApplyId());
        log.info("1.查询待收货的串码列表"+ JSON.toJSONString(purApplyItemDetailList));
        if(purApplyItemDetailList==null || purApplyItemDetailList.size()==0) {
            return ResultVO.error("暂无可收货的串码");
        }
//        StoreGetStoreIdReq storeIdReq  = new StoreGetStoreIdReq();
//        storeIdReq.setMerchantId(req.getMerchantId());//供应商 商家ID
//        storeIdReq.setStoreSubType("1300");//终端类型
//        String MktResStoreId= resouceStoreService.getStoreId(storeIdReq);//查询供应商仓库id
//        log.info("PurApplyReceivingReq req"+ JSON.toJSONString(req)+" MktResStoreId="+MktResStoreId);

        //
//        for (PurApplyItemDetail purApplyItemDetail : purApplyItemDetailList) {
//            List<String> MktResInstNbrsList = new ArrayList<String>();
//            ResourceInstAddReq resourceInstAddReq = new ResourceInstAddReq();
//            MktResInstNbrsList.add(purApplyItemDetail.getMktResInstNbr());
//            BeanUtils.copyProperties(purApplyItemDetail, resourceInstAddReq);
//            resourceInstAddReq.setMktResId(purApplyItemDetail.getProductId());
//            resourceInstAddReq.setMktResInstType(req.getMktResInstType());
//            resourceInstAddReq.setMerchantId(req.getMerchantId());
//            resourceInstAddReq.setStatusCd(req.getStatusCd());
//            resourceInstAddReq.setStorageType(req.getStorageType());
//            resourceInstAddReq.setSourceType(req.getSourceType());
//            resourceInstAddReq.setCreateStaff(req.getCreateStaff());
//            resourceInstAddReq.setMktResStoreId(MktResStoreId);//仓库id
//            resourceInstAddReq.setMktResInstNbrs(MktResInstNbrsList);//
//            ResultVO resultVO = supplierResourceInstService.addResourceInstByAdmin(resourceInstAddReq);
//            if(!resultVO.isSuccess()){
//                return ResultVO.error(resultVO.getResultMsg());
//            }
//        }

        Map<String,List<String>> map  = new HashMap<String,List<String>>();
        List<String> allMktResInstNbrList = new ArrayList<String>();
        for (PurApplyItemDetail purApplyItemDetail : purApplyItemDetailList) {
            String productId  =purApplyItemDetail.getProductId();
            String mktResInstNbr = purApplyItemDetail.getMktResInstNbr();
            allMktResInstNbrList.add(mktResInstNbr);
            if(map.get(productId)==null) {
                List<String> mktResInstNbrList = new ArrayList<String>();
                mktResInstNbrList.add(mktResInstNbr);
                map.put(productId,mktResInstNbrList);
            }else {
                List<String> mktResInstNbrList = map.get(productId);
                mktResInstNbrList.add(mktResInstNbr);
                map.put(productId,mktResInstNbrList);
            }
        }

        List<TradeResourceInstItem> tradeResourceInstItemItemList = new ArrayList<TradeResourceInstItem>();
        for (String key : map.keySet()) {
            List<String> temp  = map.get(key);
            TradeResourceInstItem tradeResourceInstItemTemp = new TradeResourceInstItem();
            tradeResourceInstItemTemp.setProductId(key);
            tradeResourceInstItemTemp.setMktResInstNbrs(temp);
            tradeResourceInstItemItemList.add(tradeResourceInstItemTemp);
        }

        TradeResourceInstReq tradeResourceInstReq = new TradeResourceInstReq();
        tradeResourceInstReq.setTradeResourceInstItemList(tradeResourceInstItemItemList);
        tradeResourceInstReq.setLanId(req.getLanId());
        tradeResourceInstReq.setSellerMerchantId(req.getMerchantId());
        tradeResourceInstReq.setOrderId(req.getApplyId());
//      调用确认收货接口
        ResultVO resultVOIn = tradeResourceInstService.tradeInResourceInst(tradeResourceInstReq);
        if(!resultVOIn.isSuccess()){
            return ResultVO.error(resultVOIn.getResultMsg());
        }
//


//    串码入库成功之后 更新确认收货
        if (allMktResInstNbrList!=null) {
           int r = purApplyManager.updatePurApplyItemDetailStatusCd(allMktResInstNbrList);
        }
        //判断是否全部收货完,首先 获取条目表 记录 中的数量  和 详情记录的 已确认收货的数量 作比较 一致则表示完成收完
        //通过采购申请单查询采购申请单项
        List<PurApplyItem> purApplyItem = purApplyItemManager.getPurApplyItem(req.getApplyId());
        int flag = 0; //定义是否完全发货标识
        for (PurApplyItem PurApplyItemTemp : purApplyItem) {
            String num = PurApplyItemTemp.getPurNum();//数量
            PurApplyItemReq PurApplyItemReq = new PurApplyItemReq();
            PurApplyItemReq.setApplyItem(PurApplyItemTemp.getApplyItemId());
            PurApplyItemReq.setProductId(PurApplyItemTemp.getProductId());
            int count =purApplyManager.countPurApplyItemDetailReving(PurApplyItemReq);//查询发货的条数
            log.info("7._"+PurApplyItemTemp.getProductId()+" countPurApplyItemDetail = count ="+count+" = "+JSON.toJSONString(PurApplyItemReq));
            if (Integer.valueOf(num)!=count) {
                flag=1;//发货数量与条数数量不符合，标识还未完全发货
                break;
            }
        }

        //更新采购申请单状态
        if (flag!=1) {
            PurApplyReq purApplyReq = new PurApplyReq();
            purApplyReq.setApplyId(req.getApplyId());
            purApplyReq.setStatusCd(PurApplyConsts.PUR_APPLY_STATUS_FINISHED);
            int i = purApplyDeliveryManager.updatePurApplyStatus(purApplyReq);
            log.info("PurchaseApplyServiceImpl.receiving updatePurApplyStatusResp = {}", i);
            if (i < 1) {
                return ResultVO.error("更新采购申请单状态失败");
            }
        }
        return ResultVO.success();
    }

    @Override
    public ResultVO updatePurApplyStatus(PurApplyReq req) {
        int i = purApplyDeliveryManager.updatePurApplyStatus(req);
        log.info("PurchaseApplyServiceImpl.updatePurApplyStatus Resp = {}", i);
        if (i < 1) {
            return ResultVO.error("更新采购申请单状态失败");
        }
        return ResultVO.success();
    }

    @Override
    public ResultVO addPurApplyExtInfo(PurApplyExtReq req) {
        //插入采购申请单扩展表
        int i = purApplyExtManager.addPurApplyExtInfo(req);
        log.info("PurchaseApplyServiceImpl.addPurApplyExtInfo Resp = {}", i);
        if (i < 1) {
            return ResultVO.error("新增采购申请单扩展失败");
        }
        return ResultVO.success();
    }

    @Override
    public ResultVO updatePurApplyExtInfo(PurApplyExtReq req) {
        //更新采购申请单扩展表
        int i = purApplyExtManager.updatePurApplyExtInfo(req);
        log.info("PurchaseApplyServiceImpl.addPurApplyExtInfo Resp = {}", i);
        if (i < 1) {
            return ResultVO.error("更新采购申请单扩展失败");
        }
        return ResultVO.success();
    }

    @Override
    public ResultVO<Page<PurApplyDeliveryResp>>  getDeliveryInfoByApplyID(PurApplyReq req) {

        Page<PurApplyDeliveryResp> list = purApplyDeliveryManager.getDeliveryInfoByApplyID(req);
        List<PurApplyDeliveryResp> deliveryInfo = list.getRecords();
        List<String> prodIds = new ArrayList<String>();
        for(PurApplyDeliveryResp purApplyDeliveryResp:deliveryInfo) {
            String productId =purApplyDeliveryResp.getProductId();
            if (productId!=null) {
                prodIds.add(productId);
            }
        }

       List<ProductInfoResp>  proTemp=productService.getProductInfoByIds(prodIds);
        //获取产品名称 设置到list的结果集中
        int k = deliveryInfo.size();
        for(int i=0;i<k;i++) {
            PurApplyDeliveryResp purApplyDeliveryResp= deliveryInfo.get(i);
            String productId =purApplyDeliveryResp.getProductId();
            for(ProductInfoResp productInfoResp:proTemp) {
                String productIdTemp = productInfoResp.getProductId();
                if (productIdTemp.equals(productId)) {
                    purApplyDeliveryResp.setUnitName(productInfoResp.getUnitName());
                    deliveryInfo.set(i,purApplyDeliveryResp);
                    break;
                }
            }
        }
        list.setRecords(deliveryInfo);
        return ResultVO.success(list);
    }

    public static void main(String[] args) {

//		1001;2001;
//		1001;2002;
//		1001;2003;
        String a = "1001;2001;1001;2002;1001;2003;";
        List<String> result = Arrays.asList(a.split(";"));
        List<String> productIdList = Lists.newArrayList();
        List<String> mktResInstNbrList = Lists.newArrayList();
        for (int i = 0; i < result.size(); i++) {
            if (i % 2 == 0) {
                productIdList.add(result.get(i));
            } else {
                mktResInstNbrList.add(result.get(i));
            }
        }

        System.out.println("productIdList:" + productIdList);
        System.out.println("mktResInstNbrList:" + mktResInstNbrList);
    }
}

