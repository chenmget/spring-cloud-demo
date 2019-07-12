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
import com.iwhalecloud.retail.order2b.dto.resquest.purapply.PurApplyDeliveryReq;
import com.iwhalecloud.retail.order2b.dto.resquest.purapply.PurApplyExtReq;
import com.iwhalecloud.retail.order2b.dto.resquest.purapply.PurApplyReceivingReq;
import com.iwhalecloud.retail.order2b.dto.resquest.purapply.PurApplyReq;
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
    public ResultVO delivery(PurApplyDeliveryReq req) {
        Map<String,Integer> listMap = new HashMap<String,Integer>();//申请清单
        Map<String,Integer> deliveryMap = new HashMap<String,Integer>();//已发货
        Map<String,Integer> deliveryingMap = new HashMap<String,Integer>();//待发货
        List<String> mktResInstNbr = Lists.newArrayList();
        mktResInstNbr = req.getMktResInstNbr(); //串码列表
        if (mktResInstNbr==null || mktResInstNbr.size()<=0) {
            return ResultVO.error("串码不能为空！");
        }
        // 判断是否有重复的串码
       boolean isSame = isSameData(mktResInstNbr);
        if (isSame == false) {
            return ResultVO.error("串码有重复，请检查！");
        }
//      一次发货不能超过1000
        if(mktResInstNbr.size()>1000) {
            return ResultVO.error("一次发货数量不能超过1000！");
        }
        PurApply purApply = purApplyManager.getPurApplyByAppId(req.getApplyId());
        log.info("1.查询申请单信息根据appId={},purApply={}",req.getApplyId(),JSON.toJSONString(purApply));
        //供应商 商家id
        String merchantId = purApply.getMerchantId();
        // 申请者 商家ID
//        String applyMerchantId =purApply.getApplyMerchantId();
        StoreGetStoreIdReq storeIdReq  = new StoreGetStoreIdReq();
        storeIdReq.setMerchantId(merchantId);
        storeIdReq.setStoreSubType("1300");//终端类型
        String MktResStoreId= resouceStoreService.getStoreId(storeIdReq);//查询仓库id
        log.info("2.供应商商家ID-merchantId={},查出的MktResStoreId={}",merchantId,MktResStoreId);
        if (MktResStoreId==null) {
            return ResultVO.error("没有查到该供应商仓库");
        }
        //通过采购申请单查询采购申请单项
        List<PurApplyItem> purApplyItem = purApplyItemManager.getPurApplyItem(req.getApplyId());
        log.info("3.通过采购申请单查询采购申请单项purApplyItem ={}" , JSON.toJSONString(purApplyItem));
        listMap = listChangeMap(purApplyItem);
        log.info("4.申请单listMap={} " ,JSON.toJSONString(listMap));
        // 查询已发货的Map
        List<PurApplyItemResp> deliveryList = purApplyManager.getDeliveryInfoByAppId(req.getApplyId());
        deliveryMap = listChangeDeliveryMap(deliveryList);
        log.info("4.已发货deliveryMap={} " ,JSON.toJSONString(deliveryMap));
//        校验串码是否有效
        ResourceStoreIdResnbr resourceStoreIdResnbr = new ResourceStoreIdResnbr();
        resourceStoreIdResnbr.setMktResInstNbrs(mktResInstNbr);
        resourceStoreIdResnbr.setMktResStoreId(MktResStoreId);
//         List<String> currList = new ArrayList<String>();
        List<String> errorList = new ArrayList<String>();// 查集

        log.info("5.getMktResInstNbrForCheckInTrack 参数={}",JSON.toJSONString(resourceStoreIdResnbr));
        List<ResourceInstCheckResp> resourceInstList = supplierResourceInstService.getMktResInstNbrForCheckInTrack(resourceStoreIdResnbr);
        log.info("6.supplierResourceInstService 结果 ={}" , JSON.toJSONString(resourceInstList));
        if ( resourceInstList != null && resourceInstList.size()>0) {
            if ( mktResInstNbr.size() > resourceInstList.size()) {
                List<String>  currList = resourceInstList.stream().map(t->t.getMktResInstNbr()).collect(toList());
                errorList = mktResInstNbr.stream().filter(item -> !currList.contains(item)).collect(toList());
            }
        } else {
            return ResultVO.error("这些串码不可用！");
        }
        if (errorList!=null && errorList.size()>0) {
            log.info("5.错误串码errorList={} " ,JSON.toJSONString(errorList));
            return ResultVO.error("这些串码不可用"+ listToString(errorList,','));
        }

//     相同产品，数量
        Map<String,Integer> productMap = new HashMap<String,Integer>();
        List<String> proIds = new ArrayList<String>();
//      整理调用发货接口的数据
        Map<String,List<String>> tradeMap = new HashMap<String,List<String>>();
        Map<String,Object> m = listChangeTradeMap( resourceInstList );
        tradeMap = (Map<String,List<String>>)m.get("tradeMap");
        productMap = (Map<String,Integer>)m.get("productMap");
        proIds = (List<String>)m.get("proIds");
        log.info("6.整理调用发货接口的数据tradeMap={} " , JSON.toJSONString(tradeMap));
        List<ProductApplyInfoResp>  productInfoList = productService.getDeliveryInfo(proIds);

        // 把产品的数量填进去
        for (ProductApplyInfoResp productInfo : productInfoList) {
            String productId = productInfo.getProductId();
            Integer num = productMap.get(productId);
            productInfo.setNum(String.valueOf(num));
        }
//      机型不合适，串码提示
        Map<String,Object> mm = listChangeDeliveryingMap(productInfoList,tradeMap);
        Map<String,List<String>> errorMap = new HashMap<String,List<String>>();
        errorMap = (Map<String,List<String>>)mm.get("errorMap");
        deliveryingMap = (Map<String,Integer>)mm.get("deliveryingMap");
        log.info("7.正在发货deliveryingMap={} " , JSON.toJSONString(deliveryingMap));
        // 开始 校验 是否是 符合申请单的

        Integer errorFlag = 0;
        StringBuffer mgs= new StringBuffer();
        List<String> errorMktList = new ArrayList<String>();
        for (String key : deliveryingMap.keySet()) {
            if (listMap.get(key) == null) {
//                mgs=mgs+key+",";
                mgs.append(key+",");
                List<String> mkt =errorMap.get(key);
                if (mkt !=null) {
                    errorMktList.addAll(mkt);
                }
                errorFlag=1;
            }
        }
        log.info("8.串码机型不符合申请单规格，请检查！mgs={},errorMktList={}" ,mgs.toString(),JSON.toJSONString(errorMktList));
        if (errorFlag ==1) {
            return ResultVO.error("串码机型不符合申请单规格，请检查！"+ listToString(errorMktList,','));
        }
//       判断 发货的数量是否 有超过
        Integer numFlag = 0;
        Integer deliveryFlag = 0; // 完全发货，修改申请单状态
        Map<String,Integer> flagMap = applyDataNum(listMap,deliveryMap,deliveryingMap);
        numFlag = flagMap.get("numFlag");
        deliveryFlag = flagMap.get("deliveryFlag");
        if (numFlag ==1) {
            return ResultVO.error("发货数量已超过!");
        }

        // 开始整理 发货接口 数据
        TradeResourceInstReq tradeResourceInstReq = new TradeResourceInstReq();
        tradeResourceInstReq = getTradeResourceInstReq(tradeMap,purApply.getApplyCode(),purApply.getLanId(),merchantId);
        log.info("8开始调用串码出库.tradeOutResourceInst= tradeResourceInstReq = {}" , JSON.toJSONString(tradeResourceInstReq));
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
        // 新增采购申请单项明细
        List<PurApplyItemDetail> purApplyItemDetailList = Lists.newArrayList();

        purApplyItemDetailList = getPurApplyItemDetailList(tradeMap,purApplyItem,req);

        log.info("9.开始批量插入发货数据 purApplyItemDetailList={}" ,JSON.toJSONString(purApplyItemDetailList));


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
     * List 集合转换为String
     * @param list
     * @param separator
     * @return
     */

    public String listToString(List<String> list, char separator) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            sb.append(list.get(i)).append(separator);
        }
        return sb.toString().substring(0,sb.toString().length()-1);
    }

    /**
     *   整理调用接口的数据
     * @param tradeMap
     * @param lanId
     * @param merchantId
     * @return
     */
    TradeResourceInstReq getTradeResourceInstReq(Map<String,List<String>> tradeMap,String applyCode,String lanId,String merchantId) {
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
        tradeResourceInstReq.setOrderId(applyCode);
        tradeResourceInstReq.setLanId(lanId);//申请者地市ID
        tradeResourceInstReq.setSellerMerchantId(merchantId);//供应商商家ID
        tradeResourceInstReq.setTradeResourceInstItemList(tradeResourceInstItemItemList);
        return tradeResourceInstReq;
    }

    /**
     *   判断 发货的数量是否 有超过
     * @param listMap
     * @param deliveryMap
     * @param deliveryingMap
     * @return
     */
      Map<String,Integer> applyDataNum(Map<String,Integer> listMap,Map<String,Integer> deliveryMap,Map<String,Integer> deliveryingMap) {
          Map<String,Integer> m = new HashMap<String, Integer>();
          //       判断 发货的数量是否 有超过
          Integer numFlag = 0;
          Integer deliveryFlag = 0; // 完全发货，修改申请单状态

          for (String key : listMap.keySet()) {
              Integer totalNum =  listMap.get(key);
              Integer deliveryNum =  0; // 已发货
              if (deliveryMap.get(key) != null) {
                  deliveryNum =  deliveryMap.get(key);
              }
              Integer deliveryingNum = 0; // 发货中
              if (deliveryingMap.get(key) != null) {
                  deliveryingNum =  deliveryingMap.get(key);
              }
              if (totalNum < (deliveryNum+ deliveryingNum)) {
                  numFlag = 1;
                  break;
              }
              if (totalNum != (deliveryNum+ deliveryingNum)) {
                  deliveryFlag = 1;
              }

          }
          m.put("numFlag",numFlag);
          m.put("deliveryFlag",deliveryFlag);
          return m;
      }
    /**
     * 调用串码出库接口 的数据 转换为deliveryingMap
     * @param productInfoList
     * @param tradeMap
     * @return
     */
    Map<String,Object> listChangeDeliveryingMap(List<ProductApplyInfoResp>productInfoList,Map<String,List<String>> tradeMap) {
        Map<String,Object> m = new HashMap<String, Object>();
        Map<String,Integer> deliveryingMap = new HashMap<String,Integer>();
        //      机型不合适，串码提示
        Map<String,List<String>> errorMap = new HashMap<String,List<String>>();
        for ( ProductApplyInfoResp p : productInfoList) {
            String productId = p.getProductId();
            String baseId =  p.getProductBaseId();
            String memory = p.getMemory();
            String AttrValue1 = p.getAttrValue1();
            String purType = p.getPurchaseType();
            if (deliveryingMap.get(baseId+"_"+memory+"_"+AttrValue1+"_"+purType) ==null) {
                deliveryingMap.put(baseId+"_"+memory+"_"+AttrValue1+"_"+purType,Integer.valueOf(p.getNum()));
                List<String>errorMkt = tradeMap.get(productId);
                errorMap.put(baseId+"_"+memory+"_"+AttrValue1+"_"+purType,errorMkt);
            }else {
                Integer num = deliveryingMap.get(baseId+"_"+memory+"_"+AttrValue1+"_"+purType);
                Integer totalNum = Integer.valueOf(num)+Integer.valueOf( p.getNum() );
                deliveryingMap.put(baseId+"_"+memory+"_"+AttrValue1+"_"+purType,totalNum);
                List<String>errorMkt = tradeMap.get(productId);
                errorMap.put(baseId+"_"+memory+"_"+AttrValue1+"_"+purType,errorMkt);

            }
        }
        m.put("errorMap",errorMap);
        m.put("deliveryingMap",deliveryingMap);
        return m;
    }

    /**
     * 调用串码出库接口 的数据 转换为tradeMap
     * @param resourceInstList
     * @return
     */
     Map<String,Object> listChangeTradeMap ( List<ResourceInstCheckResp> resourceInstList) {
        //     相同产品，数量
         Map<String,Object> m = new HashMap<String, Object>();
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
                mktResInstNbrList.add(mktResIntNbr);
                tradeMap.put(proId,mktResInstNbrList);
            } else {
                List<String> mktResInstNbrList =  tradeMap.get(proId);
                mktResInstNbrList.add(mktResIntNbr);
                tradeMap.put(proId,mktResInstNbrList);
            }

        }
         m.put("tradeMap",tradeMap);
         m.put("productMap",productMap);
         m.put("proIds",proIds);
        return m;
    }

    /**
     * 已发货列表 转换为deliveryMap
     * @param deliveryList
     * @return
     */
    Map<String,Integer> listChangeDeliveryMap( List<PurApplyItemResp> deliveryList ) {
//        List<PurApplyItemResp> deliveryList = purApplyManager.getDeliveryInfoByAppId(req.getApplyId());
        Map<String,Integer> deliveryMap = new HashMap<String,Integer>();
        if (deliveryList!=null && deliveryList.size()>0) {
            for (PurApplyItemResp purApplyItemResp:deliveryList) {
                String productId = purApplyItemResp.getProductId();
                String productIdNum = purApplyItemResp.getNum();//已发货的数量
                // 这个地方 ，还可以优化，
                ProductApplyInfoResp productApplyInfoResp = productService.getProductApplyInfo(productId);
                String baseId =  productApplyInfoResp.getProductBaseId();
                String memory = productApplyInfoResp.getMemory();
                String AttrValue1 = productApplyInfoResp.getAttrValue1();
                String purType = productApplyInfoResp.getPurchaseType();
                if (deliveryMap.get(baseId+"_"+memory+"_"+AttrValue1+"_"+purType) ==null) {
                    deliveryMap.put(baseId+"_"+memory+"_"+AttrValue1+"_"+purType,Integer.valueOf(productIdNum));
                }else {
                    Integer num = deliveryMap.get(baseId+"_"+memory+"_"+AttrValue1+"_"+purType);
                    Integer totalNum = Integer.valueOf(num)+Integer.valueOf( productIdNum );
                    deliveryMap.put(baseId+"_"+memory+"_"+AttrValue1+"_"+purType,totalNum);

                }
            }
        }
        return deliveryMap;
    }
    /**
     * 申请单列表 转换为listMap
     * @param purApplyItem
     * @return
     */
    Map<String,Integer> listChangeMap( List<PurApplyItem> purApplyItem ) {
        Map<String,Integer> listMap = new HashMap<String,Integer>();
        for (PurApplyItem pItem:purApplyItem) {
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

        }
        return listMap;
    }
    /**
     * 判断是否有重复数据
     * @param mktResInstNbr
     * @return
     */
    boolean isSameData(List<String> mktResInstNbr) {
        List<String> theSameList = new ArrayList<String>();
        theSameList= mktResInstNbr;
        HashSet h = new HashSet(theSameList);
        theSameList.clear();
        theSameList.addAll(h);

        if (theSameList.size() != mktResInstNbr.size()) {
            return false;
        }
        return true;
    }
    /**
     * 发货列表数据
     *
     * @param tradeMap
     * @param purApplyItem
     * @param req
     * @return
     */
    List<PurApplyItemDetail> getPurApplyItemDetailList ( Map<String,List<String>> tradeMap,List<PurApplyItem>purApplyItem,PurApplyDeliveryReq req) {
        List<PurApplyItemDetail> purApplyItemDetailList = Lists.newArrayList();
        for (String key : tradeMap.keySet()) {
            int itemFlag =0;
            List<String> mktResInstNbrList  = tradeMap.get(key);
            PurApplyItem purApplyItemTemp = new PurApplyItem();
            for (PurApplyItem pI : purApplyItem) {
                String pId = pI.getProductId();
                if (key.equals(pId)) {
                    purApplyItemTemp = pI;
                    //标识 属于申请单的机型
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
        return purApplyItemDetailList;
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
    public ResultVO receiving(PurApplyReceivingReq req) {
        //串码入库
        //     List<PurApplyItemDetail> purApplyItemDetailList = purApplyItemDetailManager.getPurApplyItemDetail(req.getApplyId());
        List<PurApplyItemDetail> purApplyItemDetailList =   purApplyDeliveryManager.getDeliveryListByApplyID(req.getApplyId());
        log.info("1.查询待收货的串码列表purApplyItemDetailList={}", JSON.toJSONString(purApplyItemDetailList));
        if (purApplyItemDetailList==null || purApplyItemDetailList.size()==0) {
            return ResultVO.error("暂无可收货的串码");
        }

        PurApply pur = purApplyManager.getPurApplyByAppId(req.getApplyId());
        log.info("1.查询申请单信息根据appId={},purApply={}",req.getApplyId(),JSON.toJSONString(pur));

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
        log.info("2.reving 开始处理产品id和对应的串码分类，处理结果map={}",JSON.toJSONString(map));
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
        tradeResourceInstReq.setOrderId(pur.getApplyCode());
//      调用确认收货接口
        log.info("3.调用串码入库接口tradeResourceInstReq={}", JSON.toJSONString(tradeResourceInstReq));
        ResultVO resultVOIn = tradeResourceInstService.tradeInResourceInst(tradeResourceInstReq);
        log.info("4.调用串码入库接口结果resultVOIn={}"+ JSON.toJSONString(resultVOIn));
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
            log.info("5.更新确认收货成功数量 r= {}", r);

        }
        // 判断是否全部收货完,首先 获取条目表 记录 中的数量  和 详情记录的 已确认收货的数量 作比较 一致则表示完成收完
        // 通过采购申请单查询采购申请单项
//        List<PurApplyItemResp> deliveryList = purApplyManager.getDeliveryInfoByAppId(req.getApplyId());
        List<String> deliveryList =purApplyManager.countDelivery(req.getApplyId());//总发货数量
        List<PurApplyItem> purApplyItem = purApplyItemManager.getPurApplyItem(req.getApplyId());
        Integer total = 0;
        int flag = 0; //定义是否完全收货标识
        for (PurApplyItem PurApplyItemTemp : purApplyItem) {
            String num = PurApplyItemTemp.getPurNum();//数量
            total = total+Integer.valueOf(num);
        }
        Integer deliveryCount =  deliveryList.size();
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

