package com.iwhalecloud.retail.order2b.dubbo;

import com.alibaba.dubbo.config.annotation.Reference;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.goods2b.dto.resp.ProductApplyInfoResp;
import com.iwhalecloud.retail.goods2b.dto.resp.ProductInfoResp;
import com.iwhalecloud.retail.goods2b.service.dubbo.ProductService;
import com.iwhalecloud.retail.order2b.consts.PurApplyConsts;
import com.iwhalecloud.retail.order2b.dto.response.purapply.PurApplyDeliveryResp;
import com.iwhalecloud.retail.order2b.dto.response.purapply.PurApplyItemAndProductBaseInfoResp;
import com.iwhalecloud.retail.order2b.dto.response.purapply.PurApplyItemResp;
import com.iwhalecloud.retail.order2b.dto.resquest.purapply.*;
import com.iwhalecloud.retail.order2b.entity.PurApply;
import com.iwhalecloud.retail.order2b.entity.PurApplyItem;
import com.iwhalecloud.retail.order2b.entity.PurApplyItemDetail;
import com.iwhalecloud.retail.order2b.manager.*;
import com.iwhalecloud.retail.order2b.service.PurchaseApplyService;
import com.iwhalecloud.retail.warehouse.dto.request.ResourceStoreIdResnbr;
import com.iwhalecloud.retail.warehouse.dto.request.StoreGetStoreIdReq;
import com.iwhalecloud.retail.warehouse.dto.request.TradeResourceInstItem;
import com.iwhalecloud.retail.warehouse.dto.request.TradeResourceInstReq;
import com.iwhalecloud.retail.warehouse.dto.response.ResourceInstCheckResp;
import com.iwhalecloud.retail.warehouse.service.ResouceStoreService;
import com.iwhalecloud.retail.warehouse.service.SupplierResourceInstService;
import com.iwhalecloud.retail.warehouse.service.TradeResourceInstService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.*;

import static java.util.stream.Collectors.toList;

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
    public ResultVO deliveryEdit(PurApplyDeliveryReq req) {// 最开始的
        List<String> mktResInstNbr = Lists.newArrayList();
        mktResInstNbr = req.getMktResInstNbr(); //串码列表
        int total = mktResInstNbr.size();//串码总数
        if (mktResInstNbr==null || mktResInstNbr.size()<=0) {
            return ResultVO.error("串码不能为空！");
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
        //通过采购申请单查询采购申请单项
        List<PurApplyItem> purApplyItem = purApplyItemManager.getPurApplyItem(req.getApplyId());
        log.info("3.通过采购申请单查询采购申请单项purApplyItem =" + JSON.toJSONString(purApplyItem));
        // 存 key 产品id  - value 串码列表
        Map<String,List<String>> map = new HashMap<String,List<String>>();
        // 判断采购类型是否一致
        Integer typeflag = 0;
        for (int i=0;i<mktResInstNbr.size();i++) {
            //开始验证串码的有效性
            String mktResInstNbrCheck = mktResInstNbr.get(i);
//            ResultVO<Page<ResourceInstListPageResp>> queryMktResInstNbr= supplierResourceInstService.getResourceInstList(resource);
            ResourceStoreIdResnbr resourceStoreIdResnbr = new ResourceStoreIdResnbr();
            resourceStoreIdResnbr.setMktResInstNbr(mktResInstNbrCheck);
            resourceStoreIdResnbr.setMktResStoreId(MktResStoreId);
            log.info("4.resourceStoreIdResnbr{} =" + JSON.toJSONString(resourceStoreIdResnbr));
            ResourceInstCheckResp resourceInstCheckResp = supplierResourceInstService.getMktResInstNbrForCheck(resourceStoreIdResnbr);
//            long count = queryMktResInstNbr.getResultData().getTotal();
            if (resourceInstCheckResp==null) {
                return ResultVO.error("该仓库查不到该串码"+mktResInstNbr);
            } else {
                // 判断串码是否有效
                String statusCd = resourceInstCheckResp.getStatusCd();
                if (!"1202".equals(statusCd)) {
                    String msg = isValidMktResInstNbr(statusCd,mktResInstNbrCheck);
                    ResultVO.error(msg);
                }

                String purchaseType = resourceInstCheckResp.getPurchaseType();// 产品采购类型
                if (purchaseType==null) {
                    purchaseType="0";
                }

                //开始出理串码和产品id 归类，
//                List<ResourceInstListPageResp> resultData = queryMktResInstNbr.getResultData().getRecords();
//                ResourceInstListPageResp resourceInstListPageResp = resultData.get(0);
                String productId = resourceInstCheckResp.getMktResId();//产品ID

                // 判断采购类型是否一致
                // Integer typeflag = 0;
                for (PurApplyItem pItem:purApplyItem) {
                    String proId = pItem.getProductId();
                    if (productId.equals(proId)) {
                        String purType = pItem.getPurType();
                        if (purType==null) {
                            purType="0";
                        }
                        if(!purType.equals(purchaseType)) {
                            typeflag=1;
                            break;
                        }

                    }

                }
                if (typeflag==1) {
                    break;
                }
                if (map.get(productId)==null) {
                    List<String> mktResInstNbrList = new ArrayList<String>();
                    mktResInstNbrList.add(mktResInstNbrCheck);
                    map.put(productId,mktResInstNbrList);
                } else {
                    List<String> mktResInstNbrList = map.get(productId);
                    mktResInstNbrList.add(mktResInstNbrCheck);
                    map.put(productId,mktResInstNbrList);
                }
            }
        }
        if (typeflag==1) {
            return ResultVO.error("产品的采购类型不一样，请检查！");
        }
        log.info("5.产品对应串码map =" + JSON.toJSONString(map));
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



//        判断是否有申请单外的串码类型
        String othersProductId="";

        for (String key : map.keySet()) {
            Integer otherFlag=0;
            for (PurApplyItem PurApplyItemTemp : purApplyItem) {
                String productId = PurApplyItemTemp.getProductId();
                if (key.equals(productId)) {
                    otherFlag=1;

                    break;
                }
            }
            if (otherFlag==0) {
                othersProductId=othersProductId+key+",";
                log.info("判断是否有申请单外的串码类型=key="+key+" othersProductId= "+othersProductId);
            }
        }

        if (othersProductId.length()>0) {
            return ResultVO.error(othersProductId+"这些机型不在申请单中,请检查！");
        }
        //串码出库前 -- 开始验证串码发货数量是否超过申请单数量，并验证产品是否属于申请单的产品
            Integer flagCount=0;//判断是否发货数量大于申请数量
            Integer flag=0;// 判断 是否完全发货
        for (PurApplyItem PurApplyItemTemp : purApplyItem) {
            String num = PurApplyItemTemp.getPurNum();//数量
            PurApplyItemReq PurApplyItemReq = new PurApplyItemReq();
            PurApplyItemReq.setApplyItem(PurApplyItemTemp.getApplyItemId());
            PurApplyItemReq.setProductId(PurApplyItemTemp.getProductId());
            log.info("6._串码出库前 -- 开始验证串码发货数量是否超过申请单数量"+PurApplyItemTemp.getProductId()+" countPurApplyItemDetail =" +JSON.toJSONString(PurApplyItemReq));
            List<String> mktList =purApplyManager.countPurApplyItemDetail(PurApplyItemReq);//查询发货的条数
             Integer count = mktList.size();
            log.info("7._串码出库前 -- 开始验证串码发货数量是否超过申请单数量"+PurApplyItemTemp.getProductId()+" countPurApplyItemDetail = count ="+count+" = "+JSON.toJSONString(PurApplyItemReq));
            List<String> mktForProductId = map.get(PurApplyItemTemp.getProductId());
            Integer countNow=0;
            if (mktForProductId!=null) {
                countNow = mktForProductId.size();
            }

            Integer sumCount = count+countNow;
            log.info("8._串码出库前 -- 开始验证串码发货数量是否超过申请单数量sumCount="+sumCount+" count="+count+" num="+num+" countNow="+countNow);

            if (Integer.valueOf(num)<sumCount) {
                flagCount=1;//发货数量与条数数量不符合，标识还未完全发货
                break;
            }else{
                if (Integer.valueOf(num)!=sumCount)
                flag=1;
            }
        }
        if (flagCount==1) {
            return ResultVO.error("发货数量超过申请单的数量，请确认！");
        }

       // 开始调用串码出库
       // ResultVO deliveryOutResourceInst(DeliveryResourceInstReq req);
        TradeResourceInstReq tradeResourceInstReq = new TradeResourceInstReq();
        tradeResourceInstReq.setOrderId(req.getApplyId());
        tradeResourceInstReq.setLanId(lanId);//申请者地市ID
        tradeResourceInstReq.setSellerMerchantId(merchantId);//供应商商家ID
        tradeResourceInstReq.setTradeResourceInstItemList(tradeResourceInstItemItemList);
        log.info("9开始调用串码出库.tradeOutResourceInst= tradeResourceInstReq =" + JSON.toJSONString(tradeResourceInstReq));
//        ResultVO outResult = supplierResourceInstService.deliveryOutResourceInst(deliveryResourceInstReq);
        ResultVO outResult = tradeResourceInstService.tradeOutResourceInst(tradeResourceInstReq);

        log.info("10.调用串码出库结果outResult="+JSON.toJSONString(outResult));
        if (!outResult.isSuccess()) {
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
        //新增采购申请单项明细
        List<PurApplyItemDetail> purApplyItemDetailList = Lists.newArrayList();
        for (PurApplyItem pI : purApplyItem) {
            String pId = pI.getProductId();
            List<String>mktResInstNbrList = map.get(pId);
            if (mktResInstNbrList !=null && mktResInstNbrList.size()>0) {
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
        }

        boolean saveFlag = purApplyItemDetailManager.saveBatch(purApplyItemDetailList);
        log.info("PurApplyServiceImpl.delivery saveBatchResp = {}", saveFlag);

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
        } else {
//          分批发货
            if (req.getStatusCd()!=PurApplyConsts.PUR_APPLY_STATUS_DELIVERYING) {
                PurApplyReq purApplyReq = new PurApplyReq();
                purApplyReq.setApplyId(req.getApplyId());
                purApplyReq.setStatusCd(PurApplyConsts.PUR_APPLY_STATUS_DELIVERYING);
                int k = purApplyDeliveryManager.updatePurApplyStatus(purApplyReq);
                log.info("PurchaseApplyServiceImpl.delivery updatePurApplyStatusResp = {}", k);
                if (k < 1) {
                    return ResultVO.error("更新采购申请单状态失败");
                }
            }

        }
        return ResultVO.success();
    }



    /**
     * 采购单发货
     *
     * @param req
     * @return
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public ResultVO delivery(PurApplyDeliveryReq req) {
        Map<String,Integer> listMap = new HashMap<String,Integer>();//申请清单

        Map<String,Integer> deliveryMap = new HashMap<String,Integer>();//已发货

        Map<String,Integer> deliveryingMap = new HashMap<String,Integer>();//待发货

        // Map<String,List<String>> mktResInstNbrMap = new HashMap<String,List<String>>();//
        List<String> mktResInstNbr = Lists.newArrayList();
        mktResInstNbr = req.getMktResInstNbr(); //串码列表
        int total = mktResInstNbr.size();//串码总数
        if (mktResInstNbr==null || mktResInstNbr.size()<=0) {
            return ResultVO.error("串码不能为空！");
        }
        // 判断是否有重复的串码
        List<String> theSameList = new ArrayList<String>();
        theSameList= mktResInstNbr;
        HashSet h = new HashSet(theSameList);
        theSameList.clear();
        theSameList.addAll(h);

        if (theSameList.size() != mktResInstNbr.size()) {
            return ResultVO.error("串码有重复，请检查！");
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
        //通过采购申请单查询采购申请单项
        List<PurApplyItem> purApplyItem = purApplyItemManager.getPurApplyItem(req.getApplyId());
        log.info("3.通过采购申请单查询采购申请单项purApplyItem =" + JSON.toJSONString(purApplyItem));
        List<PurApplyItemAndProductBaseInfoResp> purApplyItemProductBaseInfoList = new ArrayList<PurApplyItemAndProductBaseInfoResp>();
        for (PurApplyItem pItem:purApplyItem) {
            PurApplyItemAndProductBaseInfoResp purApplyItemAndProductBaseInfoResp = new PurApplyItemAndProductBaseInfoResp();
           BeanUtils.copyProperties(pItem,purApplyItemAndProductBaseInfoResp);
            String productId=pItem.getProductId();
            // 这个地方 ，还可以优化，
            ProductApplyInfoResp productApplyInfoResp = productService.getProductApplyInfo(productId);
            String baseId =  productApplyInfoResp.getProductBaseId();
            String memory = productApplyInfoResp.getMemory();
            String AttrValue1 = productApplyInfoResp.getAttrValue1();
            String purType = pItem.getPurType();
            if (listMap.get(baseId+"_"+memory+"_"+AttrValue1+"_"+purType) ==null) {
                listMap.put(baseId+"_"+memory+"_"+AttrValue1+"_"+purType,Integer.valueOf(pItem.getPurNum()));
            }else {
                Integer num = listMap.get(baseId+"_"+memory+"_"+AttrValue1+"_"+purType);
                Integer totalNum = num+Integer.valueOf( pItem.getPurNum());
                listMap.put(baseId+"_"+memory+"_"+AttrValue1+"_"+purType,totalNum);

            }

//            BeanUtils.copyProperties(productApplyInfoResp,purApplyItemAndProductBaseInfoResp);
//            purApplyItemProductBaseInfoList.add(purApplyItemAndProductBaseInfoResp);
        }
        log.info("4.申请单listMap={} " + JSON.toJSONString(listMap));
        // 查询已发货的Map
//        List<PurApplyItemDetail> deliveryList = purApplyItemDetailManager.getPurApplyItemDetail(req.getApplyId());
//        List<String> productIds = deliveryList.stream().map(t->t.getProductId()).collect(Collectors.toList());
//        List<ProductApplyInfoResp> deliveryInfo = productService.getDeliveryInfo(productIds);
        List<PurApplyItemResp> deliveryList = purApplyManager.getDeliveryInfoByAppId(req.getApplyId());
        if (deliveryList!=null && deliveryList.size()>0) {
            for (PurApplyItemResp purApplyItemResp:deliveryList) {
                String productId = purApplyItemResp.getProductId();
                String productIdNum = purApplyItemResp.getNum();//已发货的数量
                // 这个地方 ，还可以优化，
                ProductApplyInfoResp productApplyInfoResp = productService.getProductApplyInfo(productId);
                String baseId =  productApplyInfoResp.getProductBaseId();
                String memory = productApplyInfoResp.getMemory();
                String AttrValue1 = productApplyInfoResp.getAttrValue1();
                String purType = productApplyInfoResp.getPurType();
                if (deliveryMap.get(baseId+"_"+memory+"_"+AttrValue1+"_"+purType) ==null) {
                    deliveryMap.put(baseId+"_"+memory+"_"+AttrValue1+"_"+purType,Integer.valueOf(productIdNum));
                }else {
                    Integer num = deliveryMap.get(baseId+"_"+memory+"_"+AttrValue1+"_"+purType);
                    Integer totalNum = Integer.valueOf(num)+Integer.valueOf( productIdNum );
                    deliveryMap.put(baseId+"_"+memory+"_"+AttrValue1+"_"+purType,totalNum);

                }
            }


        }
        log.info("4.已发货deliveryMap={} " + JSON.toJSONString(deliveryMap));
//        校验串码是否有效
        ResourceStoreIdResnbr resourceStoreIdResnbr = new ResourceStoreIdResnbr();
        resourceStoreIdResnbr.setMktResInstNbrs(mktResInstNbr);
        resourceStoreIdResnbr.setMktResStoreId(MktResStoreId);
//         List<String> currList = new ArrayList<String>();
        List<String> errorList = new ArrayList<String>();// 查集

        log.info("5.getMktResInstNbrForCheckInTrack 参数" + JSON.toJSONString(resourceStoreIdResnbr));
        List<ResourceInstCheckResp> resourceInstList = supplierResourceInstService.getMktResInstNbrForCheckInTrack(resourceStoreIdResnbr);
        log.info("6.supplierResourceInstService 结果 " + JSON.toJSONString(resourceInstList));
       if ( resourceInstList != null && resourceInstList.size()>0) {

            if ( mktResInstNbr.size() > resourceInstList.size()) {
                List<String>  currList = resourceInstList.stream().map(t->t.getMktResInstNbr()).collect(toList());
                errorList = mktResInstNbr.stream().filter(item -> !currList.contains(item)).collect(toList());

            }
       } else {
           return ResultVO.error("该商家仓库找不到这些串码");
       }
       if (errorList!=null && errorList.size()>0) {
           return ResultVO.error("这些串码不可用"+ JSON.toJSONString(errorList));
       }
        log.info("5.错误串码errorList={} " + JSON.toJSONString(errorList));
//     相同产品，数量
        Map<String,Integer> productMap = new HashMap<String,Integer>();
//      整理调用发货接口的数据
        Map<String,List<String>> tradeMap = new HashMap<String,List<String>>();

       List<String> proIds = new ArrayList<String>();
       for( ResourceInstCheckResp resourceInstCheckResp:resourceInstList) {
           String proId = resourceInstCheckResp.getMktResId();
           String mktResIntNbr = resourceInstCheckResp.getMktResInstNbr();

           if (productMap.get(proId) == null) {
               productMap.put(proId,1);
               proIds.add(proId);
           } else {
               Integer n =  productMap.get(proId);
               productMap.put(proId,n+1);
           }
           if (tradeMap.get(proId) == null) {
               List<String> mktResInstNbrList = new ArrayList<String>();
               mktResInstNbrList.add(proId);
               tradeMap.put(proId,mktResInstNbrList);
           } else {
               List<String> mktResInstNbrList =  tradeMap.get(proId);
               mktResInstNbrList.add(proId);
               tradeMap.put(proId,mktResInstNbrList);
           }

       }
       log.info("6.整理调用发货接口的数据tradeMap={} " + JSON.toJSONString(errorList));
       List<ProductApplyInfoResp>  productInfoList = productService.getDeliveryInfo(proIds);

       // 把产品的数量填进去
        for (ProductApplyInfoResp productInfo : productInfoList) {
            String productId = productInfo.getProductId();
            Integer num = productMap.get(productId);
            productInfo.setNum(String.valueOf(num));
        }

       for ( ProductApplyInfoResp p : productInfoList) {
           String baseId =  p.getProductBaseId();
           String memory = p.getMemory();
           String AttrValue1 = p.getAttrValue1();
           String purType = p.getPurType();
           if (deliveryingMap.get(baseId+"_"+memory+"_"+AttrValue1+"_"+purType) ==null) {
               deliveryingMap.put(baseId+"_"+memory+"_"+AttrValue1+"_"+purType,Integer.valueOf(p.getNum()));
           }else {
               Integer num = deliveryingMap.get(baseId+"_"+memory+"_"+AttrValue1+"_"+purType);
               Integer totalNum = Integer.valueOf(num)+Integer.valueOf( p.getNum() );
               deliveryingMap.put(baseId+"_"+memory+"_"+AttrValue1+"_"+purType,totalNum);

           }
       }
        log.info("7.正在发货deliveryingMap={} " + JSON.toJSONString(errorList));
       // 开始 校验 是否是 符合申请单的

        Integer errorFlag = 0;
       String mgs="";
        for (String key : deliveryingMap.keySet()) {
            if (listMap.get(key) == null) {
                mgs=mgs+key+",";
                errorFlag=1;
            }
        }
        if (errorFlag ==1) {
            return ResultVO.error("不符合申请单规格，请检查！"+mgs);
        }

//       判断 发货的数量是否 有超过
        Integer numFlag = 0;
        Integer deliveryFlag = 0; // 完全发货，修改申请单状态
        for (String key : listMap.keySet()) {
            Integer totalNum =  listMap.get(key);
            Integer deliveryNum =  deliveryMap.get(key); // 已发货
            Integer deliveryingNum =  deliveryMap.get(key); // 发货中
            if (totalNum < (deliveryNum+ deliveryingNum)) {
                numFlag = 1;
                break;
            }
            if (totalNum != (deliveryNum+ deliveryingNum)) {
                deliveryFlag = 1;
            }

        }
        if (numFlag ==1) {
            return ResultVO.error("发货数量已超过!");
        }

        // 开始整理 发货接口 数据
        List<TradeResourceInstItem> tradeResourceInstItemItemList = new ArrayList<TradeResourceInstItem>();
        for (String key : tradeMap.keySet()) {
            List<String> temp  = tradeMap.get(key);
            TradeResourceInstItem tradeResourceInstItemTemp = new TradeResourceInstItem();
            tradeResourceInstItemTemp.setProductId(key);
            tradeResourceInstItemTemp.setMktResInstNbrs(temp);
            tradeResourceInstItemItemList.add(tradeResourceInstItemTemp);
        }

        // 开始调用串码出库
        // ResultVO deliveryOutResourceInst(DeliveryResourceInstReq req);
        TradeResourceInstReq tradeResourceInstReq = new TradeResourceInstReq();
        tradeResourceInstReq.setOrderId(req.getApplyId());
        tradeResourceInstReq.setLanId(lanId);//申请者地市ID
        tradeResourceInstReq.setSellerMerchantId(merchantId);//供应商商家ID
        tradeResourceInstReq.setTradeResourceInstItemList(tradeResourceInstItemItemList);
        log.info("8开始调用串码出库.tradeOutResourceInst= tradeResourceInstReq =" + JSON.toJSONString(tradeResourceInstReq));
//        ResultVO outResult = supplierResourceInstService.deliveryOutResourceInst(deliveryResourceInstReq);
        ResultVO outResult = tradeResourceInstService.tradeOutResourceInst(tradeResourceInstReq);

        log.info("9.调用串码出库结果outResult="+JSON.toJSONString(outResult));
        if (!outResult.isSuccess()) {
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
        //新增采购申请单项明细
        List<PurApplyItemDetail> purApplyItemDetailList = Lists.newArrayList();
        for (String key : tradeMap.keySet()) {
            int itemFlag =0;
            List<String> mktResInstNbrList  = tradeMap.get(key);
            PurApplyItem purApplyItemTemp = new PurApplyItem();
            for (PurApplyItem pI : purApplyItem) {
                String pId = pI.getProductId();
                if (key.equals(pId)) {
                    purApplyItemTemp = pI;
                    itemFlag =1;
                    break;
                }
            }
            if (itemFlag ==1) {
                if (mktResInstNbrList !=null && mktResInstNbrList.size()>0) {
                    for (String mktResInstNbrTemp: mktResInstNbrList) {
                        PurApplyItemDetail purApplyItemDetail = new PurApplyItemDetail();
                        BeanUtils.copyProperties(req, purApplyItemDetail);
                        purApplyItemDetail.setMktResInstNbr(mktResInstNbrTemp);
                        purApplyItemDetail.setProductId(key);
                        purApplyItemDetail.setStatusCd(PurApplyConsts.PUR_APPLY_STATUS_DELIVERY);//待收货
                        purApplyItemDetail.setApplyItemId(purApplyItemTemp.getApplyItemId());
                        purApplyItemDetailList.add(purApplyItemDetail);
                    }
                }
            } else {
                if (mktResInstNbrList !=null && mktResInstNbrList.size()>0) {
                    for (String mktResInstNbrTemp: mktResInstNbrList) {
                        PurApplyItemDetail purApplyItemDetail = new PurApplyItemDetail();
                        BeanUtils.copyProperties(req, purApplyItemDetail);
                        purApplyItemDetail.setMktResInstNbr(mktResInstNbrTemp);
                        purApplyItemDetail.setProductId(key);
                        purApplyItemDetail.setStatusCd(PurApplyConsts.PUR_APPLY_STATUS_DELIVERY);//待收货
                        //purApplyItemDetail.setApplyItemId(purApplyItemTemp.getApplyItemId());
                        purApplyItemDetailList.add(purApplyItemDetail);
                    }
                }
            }
        }
        log.info("9.开始批量插入发货数据 purApplyItemDetailList=" + JSON.toJSONString(purApplyItemDetailList));


        boolean saveFlag = purApplyItemDetailManager.saveBatch(purApplyItemDetailList);
        log.info("PurApplyServiceImpl.delivery saveBatchResp = {}", saveFlag);

        if (deliveryFlag==0) {//完全发货，修改采购单状态
            //更新采购申请单状态
            PurApplyReq purApplyReq = new PurApplyReq();
            purApplyReq.setApplyId(req.getApplyId());
            purApplyReq.setStatusCd(PurApplyConsts.PUR_APPLY_STATUS_RECEIVED);
            int k = purApplyDeliveryManager.updatePurApplyStatus(purApplyReq);
            log.info("PurchaseApplyServiceImpl.delivery updatePurApplyStatusResp = {}", k);
            if (k < 1) {
                return ResultVO.error("更新采购申请单状态失败");
            }
        } else {
//          分批发货
            if (req.getStatusCd()!=PurApplyConsts.PUR_APPLY_STATUS_DELIVERYING) {
                PurApplyReq purApplyReq = new PurApplyReq();
                purApplyReq.setApplyId(req.getApplyId());
                purApplyReq.setStatusCd(PurApplyConsts.PUR_APPLY_STATUS_DELIVERYING);
                int k = purApplyDeliveryManager.updatePurApplyStatus(purApplyReq);
                log.info("PurchaseApplyServiceImpl.delivery updatePurApplyStatusResp = {}", k);
                if (k < 1) {
                    return ResultVO.error("更新采购申请单状态失败");
                }
            }

        }
        return ResultVO.success();
    }
    /**
     * 是否是申请单的同一个机型
     *
     * @param purApplyItemProductBaseInfoList
     * @param productApplyInfoResp
     * @return
     */
    boolean isSameProductBaseId( List<PurApplyItemAndProductBaseInfoResp> purApplyItemProductBaseInfoList,ProductApplyInfoResp productApplyInfoResp) {

        boolean flag= false;
        String productBaseId = productApplyInfoResp.getProductBaseId();
        String bMemory = productApplyInfoResp.getMemory();
        if (bMemory == null) {
            bMemory = "0";
        }
        String purchaseType = productApplyInfoResp.getPurchaseType();
        if (purchaseType == null) {
            purchaseType = "0";
        }
        String productBaseIdAndBmemoryAndPurchaseType = productBaseId + bMemory + purchaseType;
        for ( PurApplyItemAndProductBaseInfoResp p:purApplyItemProductBaseInfoList) {
            String pbId = p.getProductBaseId();
            String memory = p.getMemory();
            String purType = p.getPurType();

            if (memory == null) {
                memory = "0";
            }
            if (purType == null) {
                purType = "0";
            }
            String ProductBaseIdAndMemoryAndPurType = pbId+memory+purType;
            if (ProductBaseIdAndMemoryAndPurType.equals(productBaseIdAndBmemoryAndPurchaseType)) {
                flag = true;
                break;
            }
        }
        return flag;
    }

    /**
     * 无效串码类型返回
     *
     * @param statusCd
     * @param mktResInstNbr
     * @return
     */
    String isValidMktResInstNbr(String statusCd,String mktResInstNbr) {
        if (statusCd.equals("1301")) {
            return "该串码"+mktResInstNbr+",待审核";
        } else if (statusCd.equals("1210")) {
            return "该串码"+mktResInstNbr+",调拨中";
        } else if (statusCd.equals("1211")) {
            return "该串码"+mktResInstNbr+",调拨中";
        } else if (statusCd.equals("1305")) {
            return "该串码"+mktResInstNbr+",退库中";
        } else if (statusCd.equals("1306")) {
            return "该串码"+mktResInstNbr+",换货中";
        } else if (statusCd.equals("1205")) {
            return "该串码"+mktResInstNbr+",退换货已冻结";
        } else if (statusCd.equals("1203")) {
            return "该串码"+mktResInstNbr+",已销售";
        } else if (statusCd.equals("1110")) {
            return "该串码"+mktResInstNbr+",已作废";
        }else {
            return "该串码"+mktResInstNbr+",不可用";
        }
    }

    /**
     * 采购单确认收货
     *
     * @param req
     * @return
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public ResultVO receivingEdit(PurApplyReceivingReq req) {// 最开始的版本
        //串码入库
        //     List<PurApplyItemDetail> purApplyItemDetailList = purApplyItemDetailManager.getPurApplyItemDetail(req.getApplyId());
        List<PurApplyItemDetail> purApplyItemDetailList =   purApplyDeliveryManager.getDeliveryListByApplyID(req.getApplyId());
        log.info("1.查询待收货的串码列表"+ JSON.toJSONString(purApplyItemDetailList));
        if (purApplyItemDetailList==null || purApplyItemDetailList.size()==0) {
            return ResultVO.error("暂无可收货的串码");
        }

        Map<String,List<String>> map  = new HashMap<String,List<String>>();
        List<String> allMktResInstNbrList = new ArrayList<String>();
        for (PurApplyItemDetail purApplyItemDetail : purApplyItemDetailList) {
            String productId  =purApplyItemDetail.getProductId();
            String mktResInstNbr = purApplyItemDetail.getMktResInstNbr();
            allMktResInstNbrList.add(mktResInstNbr);
            if (map.get(productId)==null) {
                List<String> mktResInstNbrList = new ArrayList<String>();
                mktResInstNbrList.add(mktResInstNbr);
                map.put(productId,mktResInstNbrList);
            } else {
                List<String> mktResInstNbrList = map.get(productId);
                mktResInstNbrList.add(mktResInstNbr);
                map.put(productId,mktResInstNbrList);
            }
        }
        log.info("2.reving 开始处理产品id和对应的串码分类，处理结果="+ JSON.toJSONString(map));
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
        log.info("3.调用串码入库接口"+ JSON.toJSONString(tradeResourceInstReq));
        ResultVO resultVOIn = tradeResourceInstService.tradeInResourceInst(tradeResourceInstReq);
        log.info("4.调用串码入库接口结果"+ JSON.toJSONString(resultVOIn));
        if (!resultVOIn.isSuccess()){
            return ResultVO.error(resultVOIn.getResultMsg());
        }
//


//    串码入库成功之后 更新确认收货
        if (allMktResInstNbrList!=null) {
            Date date = new Date();
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String updateDate = formatter.format(date);
            String updateUserId=req.getCreateStaff();
           Integer r = purApplyManager.updatePurApplyItemDetailStatusCd(allMktResInstNbrList,updateDate,updateUserId);
            log.info("5.更新确认收货成功数量"+ r);

        }
        // 判断是否全部收货完,首先 获取条目表 记录 中的数量  和 详情记录的 已确认收货的数量 作比较 一致则表示完成收完
        // 通过采购申请单查询采购申请单项
        List<PurApplyItem> purApplyItem = purApplyItemManager.getPurApplyItem(req.getApplyId());
        int flag = 0; //定义是否完全收货标识
        for (PurApplyItem PurApplyItemTemp : purApplyItem) {
            String num = PurApplyItemTemp.getPurNum();//数量
            PurApplyItemReq PurApplyItemReq = new PurApplyItemReq();
            PurApplyItemReq.setApplyItem(PurApplyItemTemp.getApplyItemId());
            PurApplyItemReq.setProductId(PurApplyItemTemp.getProductId());
            int count =purApplyManager.countPurApplyItemDetailReving(PurApplyItemReq);//查询发货的条数
            log.info("5._"+PurApplyItemTemp.getProductId()+" countPurApplyItemDetail num="+num+"= count ="+count+" = "+JSON.toJSONString(PurApplyItemReq));
            if (Integer.valueOf(num)!=count) {
                flag=1;//发货数量与条数数量不符合，标识还未完全收货
                break;
            }
        }

        //更新采购申请单状态
        if (flag==0) {
            PurApplyReq purApplyReq = new PurApplyReq();
            purApplyReq.setApplyId(req.getApplyId());
            purApplyReq.setStatusCd(PurApplyConsts.PUR_APPLY_STATUS_FINISHED);
            int i = purApplyDeliveryManager.updatePurApplyStatus(purApplyReq);
            log.info("5.PurchaseApplyServiceImpl.receiving updatePurApplyStatusResp = {}", i);
            if (i < 1) {
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
        //     List<PurApplyItemDetail> purApplyItemDetailList = purApplyItemDetailManager.getPurApplyItemDetail(req.getApplyId());
        List<PurApplyItemDetail> purApplyItemDetailList =   purApplyDeliveryManager.getDeliveryListByApplyID(req.getApplyId());
        log.info("1.查询待收货的串码列表"+ JSON.toJSONString(purApplyItemDetailList));
        if (purApplyItemDetailList==null || purApplyItemDetailList.size()==0) {
            return ResultVO.error("暂无可收货的串码");
        }

        // 整理调用串码收货数据
        Map<String,List<String>> map  = new HashMap<String,List<String>>();
        List<String> allMktResInstNbrList = new ArrayList<String>();
        for (PurApplyItemDetail purApplyItemDetail : purApplyItemDetailList) {
            String productId  =purApplyItemDetail.getProductId();
            String mktResInstNbr = purApplyItemDetail.getMktResInstNbr();
            allMktResInstNbrList.add(mktResInstNbr);
            if (map.get(productId)==null) {
                List<String> mktResInstNbrList = new ArrayList<String>();
                mktResInstNbrList.add(mktResInstNbr);
                map.put(productId,mktResInstNbrList);
            } else {
                List<String> mktResInstNbrList = map.get(productId);
                mktResInstNbrList.add(mktResInstNbr);
                map.put(productId,mktResInstNbrList);
            }
        }
        log.info("2.reving 开始处理产品id和对应的串码分类，处理结果="+ JSON.toJSONString(map));
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
        log.info("3.调用串码入库接口"+ JSON.toJSONString(tradeResourceInstReq));
        ResultVO resultVOIn = tradeResourceInstService.tradeInResourceInst(tradeResourceInstReq);
        log.info("4.调用串码入库接口结果"+ JSON.toJSONString(resultVOIn));
        if (!resultVOIn.isSuccess()){
            return ResultVO.error(resultVOIn.getResultMsg());
        }
//


//    串码入库成功之后 更新确认收货
        if (allMktResInstNbrList!=null) {
            Date date = new Date();
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String updateDate = formatter.format(date);
            String updateUserId=req.getCreateStaff();
            Integer r = purApplyManager.updatePurApplyItemDetailStatusCd(allMktResInstNbrList,updateDate,updateUserId);
            log.info("5.更新确认收货成功数量"+ r);

        }
        // 判断是否全部收货完,首先 获取条目表 记录 中的数量  和 详情记录的 已确认收货的数量 作比较 一致则表示完成收完
        // 通过采购申请单查询采购申请单项
        List<PurApplyItemResp> deliveryList = purApplyManager.getDeliveryInfoByAppId(req.getApplyId());
        List<PurApplyItem> purApplyItem = purApplyItemManager.getPurApplyItem(req.getApplyId());
        Integer total = 0;
        int flag = 0; //定义是否完全收货标识
        for (PurApplyItem PurApplyItemTemp : purApplyItem) {
            String num = PurApplyItemTemp.getPurNum();//数量
            total = total+Integer.valueOf(num);
        }
        Integer deliveryCount = deliveryList.size();
        log.info("6.tatal ="+ total +" deliveryCount="+deliveryCount);
        //更新采购申请单状态
        if (total==deliveryCount) {
            PurApplyReq purApplyReq = new PurApplyReq();
            purApplyReq.setApplyId(req.getApplyId());
            purApplyReq.setStatusCd(PurApplyConsts.PUR_APPLY_STATUS_FINISHED);
            int i = purApplyDeliveryManager.updatePurApplyStatus(purApplyReq);
            log.info("5.PurchaseApplyServiceImpl.receiving updatePurApplyStatusResp = {}", i);
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

        log.info("getDeliveryInfoByApplyID req{}"+JSON.toJSONString(list));
        List<PurApplyDeliveryResp> deliveryInfo = list.getRecords();
        if (deliveryInfo==null || deliveryInfo.size()==0)  {
            return ResultVO.success(list);
        }
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
        for (int i=0;i<k;i++) {
            PurApplyDeliveryResp purApplyDeliveryResp= deliveryInfo.get(i);
            String productId =purApplyDeliveryResp.getProductId();
            for (ProductInfoResp productInfoResp:proTemp) {
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

    @Override
    public Integer updatePurApplyItemDetailStatusCd(List<String> list,String updateDate,String updateUserId) {
        return purApplyManager.updatePurApplyItemDetailStatusCd(list,updateDate,updateUserId);
    }

    @Override
    public List<PurApplyItemDetail> getDeliveryListByApplyID(String applyId) {
//        List<PurApplyItemDetail> purApplyItemDetailList =

        return purApplyDeliveryManager.getDeliveryListByApplyID(applyId);
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

