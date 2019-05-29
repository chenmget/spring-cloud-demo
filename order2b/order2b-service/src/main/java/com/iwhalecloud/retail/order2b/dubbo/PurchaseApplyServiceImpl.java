package com.iwhalecloud.retail.order2b.dubbo;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.google.common.collect.Lists;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.order2b.consts.PurApplyConsts;
import com.iwhalecloud.retail.order2b.dto.resquest.purapply.PurApplyDeliveryReq;
import com.iwhalecloud.retail.order2b.dto.resquest.purapply.PurApplyExtReq;
import com.iwhalecloud.retail.order2b.dto.resquest.purapply.PurApplyReceivingReq;
import com.iwhalecloud.retail.order2b.dto.resquest.purapply.PurApplyReq;
import com.iwhalecloud.retail.order2b.entity.PurApplyItem;
import com.iwhalecloud.retail.order2b.entity.PurApplyItemDetail;
import com.iwhalecloud.retail.order2b.manager.PurApplyDeliveryManager;
import com.iwhalecloud.retail.order2b.manager.PurApplyExtManager;
import com.iwhalecloud.retail.order2b.manager.PurApplyItemDetailManager;
import com.iwhalecloud.retail.order2b.manager.PurApplyItemManager;
import com.iwhalecloud.retail.order2b.service.PurchaseApplyService;
import com.iwhalecloud.retail.warehouse.dto.request.ResourceInstAddReq;
import com.iwhalecloud.retail.warehouse.service.SupplierResourceInstService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

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
    private PurApplyItemDetailManager purApplyItemDetailManager;

    @Autowired
    private PurApplyExtManager purApplyExtManager;

    @Reference
    private SupplierResourceInstService supplierResourceInstService;

    /**
     * 采购单发货
     *
     * @param req
     * @return
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public ResultVO delivery(PurApplyDeliveryReq req) {
        //生成batchId
//        String batchId = UUID.randomUUID().toString().replace("-", "");
        String batchId = purApplyDeliveryManager.getSeqApplyItemDetailBatchId();
        req.setBatchId(batchId);
        //插入采购申请单发货记录
        int i = purApplyDeliveryManager.insertPurApplyDelivery(req);
        log.info("PurchaseApplyServiceImpl.delivery insertPurApplyDeliveryResp = {}", i);
        if (i < 1) {
            return ResultVO.error("新增采购发货记录失败");
        }
        //处理前端录入的串码数据
        String productIdAndMktResInstNbr = req.getProductIdAndMktResInstNbr();
        List<String> result = Arrays.asList(productIdAndMktResInstNbr.split(";"));
        List<String> productIdList = Lists.newArrayList();
        List<String> mktResInstNbrList = Lists.newArrayList();
        for (int l = 0; l < result.size(); l++) {
            if (l % 2 == 0) {
                productIdList.add(result.get(l));
            } else {
                mktResInstNbrList.add(result.get(l));
            }
        }
        req.setProductIdList(productIdList);
        req.setMktResInstNbrList(mktResInstNbrList);
        //通过采购申请单查询采购申请单项
        List<PurApplyItem> purApplyItem = purApplyItemManager.getPurApplyItem(req.getApplyId());

        //新增采购申请单项明细
        List<PurApplyItemDetail> purApplyItemDetailList = Lists.newArrayList();
        for (int m = 0; m < purApplyItem.size(); m++) {
            for (int n = 0; n < mktResInstNbrList.size(); n++) {
                PurApplyItemDetail purApplyItemDetail = new PurApplyItemDetail();
                BeanUtils.copyProperties(req, purApplyItemDetail);
                purApplyItemDetail.setMktResInstNbr(mktResInstNbrList.get(n));
                purApplyItemDetail.setProductId(purApplyItem.get(m).getProductId());
                purApplyItemDetail.setApplyItemId(purApplyItem.get(m).getApplyItemId());
                purApplyItemDetailList.add(purApplyItemDetail);
            }
        }
        boolean saveFlag = purApplyItemDetailManager.saveBatch(purApplyItemDetailList);
        log.info("PurApplyServiceImpl.delivery saveBatchResp = {}", saveFlag);
        //更新采购申请单状态
        PurApplyReq purApplyReq = new PurApplyReq();
        purApplyReq.setApplyId(req.getApplyId());
        purApplyReq.setStatusCd(PurApplyConsts.PUR_APPLY_STATUS_RECEIVED);
        int k = purApplyDeliveryManager.updatePurApplyStatus(purApplyReq);
        log.info("PurchaseApplyServiceImpl.delivery updatePurApplyStatusResp = {}", k);
        if (k < 1) {
            return ResultVO.error("更新采购申请单状态失败");
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
        for (PurApplyItemDetail purApplyItemDetail : purApplyItemDetailList) {
            ResourceInstAddReq resourceInstAddReq = new ResourceInstAddReq();
            BeanUtils.copyProperties(purApplyItemDetail, resourceInstAddReq);
            resourceInstAddReq.setMktResId(purApplyItemDetail.getProductId());
            resourceInstAddReq.setMktResInstType(req.getMktResInstType());
            resourceInstAddReq.setMerchantId(req.getMerchantId());
            resourceInstAddReq.setStatusCd(req.getStatusCd());
            resourceInstAddReq.setStorageType(req.getStorageType());
            resourceInstAddReq.setSourceType(req.getSourceType());
            resourceInstAddReq.setCreateStaff(req.getCreateStaff());
            ResultVO resultVO = supplierResourceInstService.addResourceInstByAdmin(resourceInstAddReq);
            if(!resultVO.isSuccess()){
                return ResultVO.error(resultVO.getResultMsg());
            }
        }

        //更新采购申请单状态
        PurApplyReq purApplyReq = new PurApplyReq();
        purApplyReq.setApplyId(req.getApplyId());
        purApplyReq.setStatusCd(PurApplyConsts.PUR_APPLY_STATUS_FINISHED);
        int i = purApplyDeliveryManager.updatePurApplyStatus(purApplyReq);
        log.info("PurchaseApplyServiceImpl.receiving updatePurApplyStatusResp = {}", i);
        if (i < 1) {
            return ResultVO.error("更新采购申请单状态失败");
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

