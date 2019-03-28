package com.iwhalecloud.retail.rights.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.promo.common.PromoConst;
import com.iwhalecloud.retail.promo.dto.MarketingActivityDTO;
import com.iwhalecloud.retail.promo.dto.PromotionDTO;
import com.iwhalecloud.retail.promo.dto.req.ActivityProductReq;
import com.iwhalecloud.retail.promo.dto.req.AuitMarketingActivityReq;
import com.iwhalecloud.retail.promo.dto.req.QueryMarketingActivityReq;
import com.iwhalecloud.retail.promo.dto.resp.PreSubsidyProductRespDTO;
import com.iwhalecloud.retail.promo.service.ActivityProductService;
import com.iwhalecloud.retail.promo.service.MarketingActivityService;
import com.iwhalecloud.retail.promo.service.PromotionService;
import com.iwhalecloud.retail.rights.common.ResultCodeEnum;
import com.iwhalecloud.retail.rights.common.RightsConst;
import com.iwhalecloud.retail.rights.consts.RightsStatusConsts;
import com.iwhalecloud.retail.rights.dto.request.*;
import com.iwhalecloud.retail.rights.dto.response.*;
import com.iwhalecloud.retail.rights.entity.CouponApplyObject;
import com.iwhalecloud.retail.rights.entity.MktResCoupon;
import com.iwhalecloud.retail.rights.manager.*;
import com.iwhalecloud.retail.rights.service.PreSubsidyCouponService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Component;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.util.Date;
import java.util.List;

/**
 * @author zhou.zc
 * @date 2019年02月21日
 * @Description:前置补贴优惠券
 */
@Slf4j
@Component("preSubsidyCouponService")
@Service
public class PreSubsidyCouponServiceImpl implements PreSubsidyCouponService {

    @Autowired
    private MktResCouponManager mktResCouponManager;

    @Autowired
    private CouponEffExpRuleManager couponEffExpRuleManager;

    @Autowired
    private CouponSupplyRuleManager couponSupplyRuleManager;

    @Autowired
    private CouponApplyObjectManager couponApplyObjectManager;

    @Autowired
    private CouponDiscountRuleManager couponDiscountRuleManager;

    @Reference
    private PromotionService promotionService;

    @Reference
    private ActivityProductService activityProductService;

    @Reference
    private MarketingActivityService marketingActivityService;

    @Autowired
    private DataSourceTransactionManager transactionManager;

    @Override
    public ResultVO addPreSubsidyCoupon(AddPreSubsidyCouponReqDTO req) {
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setName("PreSubsidyCouponServiceImpl_addPreSubsidyCoupon");
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        TransactionStatus status = transactionManager.getTransaction(def);
        QueryMarketingActivityReq queryMarketingActivityReq = new QueryMarketingActivityReq();
        queryMarketingActivityReq.setMarketingActivityId(req.getMarketingActivityId());
        ResultVO<MarketingActivityDTO> marketingActivityDTOResultVO = marketingActivityService.queryMarketingActivityById(queryMarketingActivityReq);
        if(ResultCodeEnum.ERROR.getCode().equals(marketingActivityDTOResultVO.getResultCode())||marketingActivityDTOResultVO.getResultData() == null){
            return ResultVO.error("查询活动信息异常");
        }
        try {
            log.info("AddPreSubsidyCouponReqDTO={}", JSON.toJSON(req));
            MktResCoupon mktResCoupon = new MktResCoupon();
            BeanUtils.copyProperties(req, mktResCoupon);
            mktResCoupon.setCreateDate(new Date());
            mktResCoupon.setUpdateDate(new Date());
            mktResCoupon.setCreateStaff(req.getCreateStaff());
            mktResCoupon.setUpdateStaff(req.getCreateStaff());
            mktResCoupon.setUseSysId(Long.parseLong("-1"));
            mktResCoupon.setMarketingActivityId(req.getMarketingActivityId());
            mktResCoupon.setStatusCd(RightsStatusConsts.RIGHTS_STATUS_UNOBTAIN);
            mktResCouponManager.addMktResCoupon(mktResCoupon);
            String mktResId = mktResCoupon.getMktResId();
            log.info("PreSubsidyCouponServiceImpl addPreSubsidyCoupon 优惠券信息新增成功");
            SaveCouponEffExpRuleReqDTO saveCouponEffExpRuleReqDTO = new SaveCouponEffExpRuleReqDTO();
            BeanUtils.copyProperties(req, saveCouponEffExpRuleReqDTO, new String[]{"remark"});
            saveCouponEffExpRuleReqDTO.setMktResId(mktResId);
            couponEffExpRuleManager.saveCouponEffExpRule(saveCouponEffExpRuleReqDTO);
            log.info("PreSubsidyCouponServiceImpl addPreSubsidyCoupon 优惠券生失效规则新增成功");
            SaveCouponSupplyRuleReqDTO saveCouponSupplyRuleReqDTO = new SaveCouponSupplyRuleReqDTO();
            BeanUtils.copyProperties(req, saveCouponSupplyRuleReqDTO, new String[]{"remark"});
            saveCouponSupplyRuleReqDTO.setMktResId(mktResId);
            saveCouponSupplyRuleReqDTO.setBeginTime(marketingActivityDTOResultVO.getResultData().getStartTime());
            saveCouponSupplyRuleReqDTO.setEndTime(marketingActivityDTOResultVO.getResultData().getEndTime());
            couponSupplyRuleManager.saveCouponSupplyRule(saveCouponSupplyRuleReqDTO);
            log.info("PreSubsidyCouponServiceImpl addPreSubsidyCoupon 优惠券领取规则新增成功");
            SaveCouponDiscountRuleReqDTO saveCouponDiscountRuleReqDTO = new SaveCouponDiscountRuleReqDTO();
            BeanUtils.copyProperties(req, saveCouponDiscountRuleReqDTO);
            saveCouponDiscountRuleReqDTO.setMktResId(mktResId);
            couponDiscountRuleManager.saveCouponDiscountRule(saveCouponDiscountRuleReqDTO);
            log.info("PreSubsidyCouponServiceImpl addPreSubsidyCoupon 优惠券抵扣规则新增成功");
            PromotionDTO promotionDTO = new PromotionDTO();
            BeanUtils.copyProperties(req, promotionDTO, new String[]{"remark"});
            promotionDTO.setPromotionEffectTime(req.getEffDate());
            promotionDTO.setPromotionOverdueTime(req.getExpDate());
            promotionDTO.setPromotionPrice(req.getDiscountValue().toString());
            promotionDTO.setPromotionType(PromoConst.PromotionType.PROMOTION_TYPE_CD_20.getCode());
            promotionDTO.setMktResId(mktResId);
            promotionDTO.setIsDeleted(PromoConst.IsDelete.IS_DELETE_CD_0.getCode());
            promotionService.addActPromotion(promotionDTO);
            transactionManager.commit(status);
        } catch (Exception e) {
            log.info("PreSubsidyCouponServiceImpl addPreSubsidyCoupon 配置优惠券失败", e);
            transactionManager.rollback(status);
            return ResultVO.error("配置优惠券失败");
        }
        return ResultVO.success();
    }

    @Override
    public ResultVO updatePreSubsidyCoupon(UpdatePreSubsidyCouponReqDTO req) {
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setName("PreSubsidyCouponServiceImpl_updatePreSubsidyCoupon");
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        TransactionStatus status = transactionManager.getTransaction(def);
        Integer temp = 0;
        Integer sum = 4;
        try {
            QueryMarketingActivityReq queryMarketingActivityReq = new QueryMarketingActivityReq();
            queryMarketingActivityReq.setMarketingActivityId(req.getMarketingActivityId());
            ResultVO<MarketingActivityDTO> marketingActivityDTOResultVO = marketingActivityService.queryMarketingActivityById(queryMarketingActivityReq);
            if(ResultCodeEnum.ERROR.getCode().equals(marketingActivityDTOResultVO.getResultCode())||marketingActivityDTOResultVO.getResultData() == null){
                return ResultVO.error("查询活动信息异常");
            }
            UpdateMktResCouponReqDTO updateMktResCouponReqDTO = new UpdateMktResCouponReqDTO();
            BeanUtils.copyProperties(req, updateMktResCouponReqDTO);
            updateMktResCouponReqDTO.setUpdateDate(new Date());
            temp += mktResCouponManager.updateMktResCoupon(updateMktResCouponReqDTO);
            UpdateCouponEffExpRuleReqDTO updateCouponEffExpRuleReqDTO = new UpdateCouponEffExpRuleReqDTO();
            BeanUtils.copyProperties(req, updateCouponEffExpRuleReqDTO, new String[]{"remark"});
            temp += couponEffExpRuleManager.updateCouponEffExpRule(updateCouponEffExpRuleReqDTO);
            UpdateCouponSupplyRuleReqDTO updateCouponSupplyRuleReqDTO = new UpdateCouponSupplyRuleReqDTO();
            BeanUtils.copyProperties(req, updateCouponSupplyRuleReqDTO, new String[]{"remark"});
            updateCouponSupplyRuleReqDTO.setBeginTime(marketingActivityDTOResultVO.getResultData().getStartTime());
            updateCouponSupplyRuleReqDTO.setEndTime(marketingActivityDTOResultVO.getResultData().getEndTime());
            temp += couponSupplyRuleManager.updateCouponSupplyRule(updateCouponSupplyRuleReqDTO);
            UpdateCouponDiscountRuleReqDTO updateCouponDiscountRuleReqDTO = new UpdateCouponDiscountRuleReqDTO();
            BeanUtils.copyProperties(req, updateCouponDiscountRuleReqDTO, new String[]{"remark"});
            temp += couponDiscountRuleManager.updateCouponDiscountRule(updateCouponDiscountRuleReqDTO);
            if (temp < sum) {
                throw new Exception("PreSubsidyCouponServiceImpl updatePreSubsidyCoupon 前置补贴优惠券更新失败,数据异常");
            }
            transactionManager.commit(status);
        } catch (Exception e) {
            log.info("PreSubsidyCouponServiceImpl updatePreSubsidyCoupon 前置补贴优惠券更新失败", e);
            transactionManager.rollback(status);
            return ResultVO.error("更新失败");
        }
        return ResultVO.success();
    }

    @Override
    public ResultVO<List<QueryPreSubsidyCouponResqDTO>> queryPreSubsidyCoupon(QueryPreSubsidyReqDTO queryPreSubsidyReqDTO) {
        List<PromotionDTO> promotionDTOS = promotionService.queryActPromotion(queryPreSubsidyReqDTO.getMarketingActivityId(), PromoConst.PromotionType.PROMOTION_TYPE_CD_20.getCode());
        List<QueryPreSubsidyCouponResqDTO> queryPreSubsidyCouponReqDTOS = Lists.newArrayList();
        if (promotionDTOS.size() <= 0) {
            return ResultVO.success(queryPreSubsidyCouponReqDTOS);
        }
        for (PromotionDTO promotionDTO : promotionDTOS) {
            QueryPreSubsidyCouponResqDTO queryPreSubsidyCouponResqDTO = new QueryPreSubsidyCouponResqDTO();
            String mktResId = promotionDTO.getMktResId();
            QueryMktResCouponRespDTO queryMktResCouponRespDTO = mktResCouponManager.queryMktResCoupon(mktResId);
            BeanUtils.copyProperties(queryMktResCouponRespDTO, queryPreSubsidyCouponResqDTO);
            CouponEffExpRuleRespDTO couponEffExpRuleRespDTO = couponEffExpRuleManager.queryCouponEffExpRuleOne(mktResId);
            BeanUtils.copyProperties(couponEffExpRuleRespDTO, queryPreSubsidyCouponResqDTO, new String[]{"remark"});
            CouponSupplyRuleRespDTO couponSupplyRuleRespDTO = couponSupplyRuleManager.queryCouponSupplyRuleOne(mktResId);
            BeanUtils.copyProperties(couponSupplyRuleRespDTO, queryPreSubsidyCouponResqDTO, new String[]{"remark"});
            CouponDiscountRuleRespDTO couponDiscountRuleRespDTO = couponDiscountRuleManager.queryCouponDiscountRuleOne(mktResId);
            BeanUtils.copyProperties(couponDiscountRuleRespDTO, queryPreSubsidyCouponResqDTO, new String[]{"mktResName", "remark"});
            queryPreSubsidyCouponReqDTOS.add(queryPreSubsidyCouponResqDTO);
        }
        return ResultVO.success(queryPreSubsidyCouponReqDTOS);
    }

    @Override
    public ResultVO deletePreSubsidyCoupon(QueryPreSubsidyReqDTO queryPreSubsidyReqDTO) {
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setName("PreSubsidyCouponServiceImpl_deletePreSubsidyCoupon");
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        TransactionStatus status = transactionManager.getTransaction(def);
        Integer temp = 0;
        Integer sum = 5;
        String mktResId = queryPreSubsidyReqDTO.getMktResId();
        CouponApplyObjectListReq couponApplyObjectListReq = new CouponApplyObjectListReq();
        couponApplyObjectListReq.setMktResId(mktResId);
        List<CouponApplyObject> couponApplyObjects = couponApplyObjectManager.listCouponApplyObject(couponApplyObjectListReq);
        if (couponApplyObjects.size() > 0) {
            return ResultVO.error("前置补贴优惠券删除失败,该优惠券已经配置产品");
        }
        try {
            temp += mktResCouponManager.deleteMktResCoupon(mktResId);
            temp += couponEffExpRuleManager.deleteCouponEffExpRule(mktResId);
            temp += couponSupplyRuleManager.deleteCouponSupplyRule(mktResId);
            temp += couponDiscountRuleManager.deleteCouponDiscountRule(mktResId);
            temp += promotionService.deleteActPromotion(queryPreSubsidyReqDTO.getMarketingActivityId(), mktResId);
            if (temp < sum) {
                throw new Exception("PreSubsidyCouponServiceImpl deletePreSubsidyCoupon 前置补贴优惠券删除失败,数据异常");
            }
            transactionManager.commit(status);
        } catch (Exception e) {
            log.info("PreSubsidyCouponServiceImpl deletePreSubsidyCoupon 前置补贴优惠券删除失败", e);
            transactionManager.rollback(status);
            return ResultVO.error("更新失败");
        }
        return ResultVO.success();
    }

    @Override
    public ResultVO addPreSubsidyProduct(AddPromotionProductReqDTO addPromotionProductReqDTO) {
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setName("PreSubsidyCouponServiceImpl_addPreSubsidyProduct");
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        TransactionStatus status = transactionManager.getTransaction(def);
        ResultVO<List<PreSubsidyProductRespDTO>> listResultVO = activityProductService.queryPreSubsidyProduct(addPromotionProductReqDTO.getMarketingActivityId());
        QueryMarketingActivityReq queryMarketingActivityReq = new QueryMarketingActivityReq();
        queryMarketingActivityReq.setMarketingActivityId(addPromotionProductReqDTO.getMarketingActivityId());
        ResultVO<MarketingActivityDTO> marketingActivityDTOResultVO = marketingActivityService.queryMarketingActivityById(queryMarketingActivityReq);
        try {
            String promotionType = marketingActivityDTOResultVO.getResultData().getPromotionTypeCode();
            if (listResultVO.getResultData().size() > 0) {
                if (PromoConst.PromotionType.PROMOTION_TYPE_CD_20.getCode().equals(promotionType)) {
                    for (PreSubsidyProductRespDTO preSubsidyProductResqDTO : listResultVO.getResultData()) {
                        CouponApplyObjectListReq couponApplyObjectListReq = new CouponApplyObjectListReq();
                        couponApplyObjectListReq.setObjIdList(Lists.newArrayList(preSubsidyProductResqDTO.getActivityProductResqDTO().getProductId()));
                        List<CouponApplyObject> couponApplyObjects = couponApplyObjectManager.listCouponApplyObject(couponApplyObjectListReq);
                        for (CouponApplyObject couponApplyObject : couponApplyObjects) {
                            couponApplyObjectManager.deleteCouponApplyObject(couponApplyObject.getMktResId(), preSubsidyProductResqDTO.getActivityProductResqDTO().getProductId());
                        }
                    }
                } else if (PromoConst.PromotionType.PROMOTION_TYPE_CD_10.getCode().equals(promotionType)) {
                    for (AddPreSubsidyProductReqDTO addPreSubsidyProductReqDTO : addPromotionProductReqDTO.getAddPreSubsidyProductReqDTOS()) {
                        ResultVO resultVO = activityProductService.checkProductDiscountAmount(addPreSubsidyProductReqDTO.getProductId(), addPreSubsidyProductReqDTO.getDiscountAmount());
                        if (ResultCodeEnum.ERROR.getCode().equals(resultVO.getResultCode())) {
                            return ResultVO.error(resultVO.getResultMsg());
                        }
                    }
                }
                activityProductService.deletePreSubsidyProduct(addPromotionProductReqDTO.getMarketingActivityId());
            }
            for (AddPreSubsidyProductReqDTO addPreSubsidyProductReqDTO : addPromotionProductReqDTO.getAddPreSubsidyProductReqDTOS()) {
                ActivityProductReq activityProductReq = new ActivityProductReq();
                BeanUtils.copyProperties(addPreSubsidyProductReqDTO, activityProductReq);
                activityProductReq.setCreator(addPromotionProductReqDTO.getUserId());
                activityProductReq.setMarketingActivityId(addPromotionProductReqDTO.getMarketingActivityId());
                if (PromoConst.PromotionType.PROMOTION_TYPE_CD_20.getCode().equals(promotionType)) {
                    List<String> mktResIds = addPreSubsidyProductReqDTO.getMktResIds();
                    if (mktResIds.size() > 0) {
                        for (String mktResId : mktResIds) {
                            CouponApplyObject couponApplyObject = new CouponApplyObject();
                            couponApplyObject.setMktResId(mktResId);
                            couponApplyObject.setObjId(addPreSubsidyProductReqDTO.getProductId());
                            couponApplyObject.setObjType(RightsConst.CouponApplyObjType.PRODUCT.getType());
                            couponApplyObject.setCreateStaff(addPromotionProductReqDTO.getUserId());
                            couponApplyObject.setUpdateStaff(addPromotionProductReqDTO.getUserId());
                            couponApplyObject.setStatusCd(RightsStatusConsts.RIGHTS_STATUS_UNOBTAIN);
                            couponApplyObjectManager.addCouponApplyObject(couponApplyObject);
                        }
                    }
                }
                activityProductService.addPreSubsidyProduct(activityProductReq);
            }
            AuitMarketingActivityReq auitMarketingActivityReq = new AuitMarketingActivityReq();
            BeanUtils.copyProperties(addPromotionProductReqDTO,auitMarketingActivityReq);
            auitMarketingActivityReq.setId(addPromotionProductReqDTO.getMarketingActivityId());
            auitMarketingActivityReq.setName(marketingActivityDTOResultVO.getResultData().getName());
            marketingActivityService.auitMarketingActivity(auitMarketingActivityReq);
            transactionManager.commit(status);
        } catch (Exception e) {
            log.info("PreSubsidyCouponServiceImpl addPreSubsidyProduct 前置补贴新增失败", e);
            transactionManager.rollback(status);
            activityProductService.deletePreSubsidyProduct(addPromotionProductReqDTO.getMarketingActivityId());
            for (PreSubsidyProductRespDTO preSubsidyProductResqDTO : listResultVO.getResultData()) {
                ActivityProductReq activityProductReq = new ActivityProductReq();
                BeanUtils.copyProperties(preSubsidyProductResqDTO.getActivityProductResqDTO(), activityProductReq);
                activityProductService.addPreSubsidyProduct(activityProductReq);
            }
            return ResultVO.error(e.getMessage());
        }
        return ResultVO.success();
    }

    @Override
    public ResultVO<List<PreSubsidyProductPromResqDTO>> queryPreSubsidyProduct(QueryPreSubsidyReqDTO queryPreSubsidyReqDTO) {
        QueryMarketingActivityReq queryMarketingActivityReq = new QueryMarketingActivityReq();
        queryMarketingActivityReq.setMarketingActivityId(queryPreSubsidyReqDTO.getMarketingActivityId());
        ResultVO<MarketingActivityDTO> marketingActivityDTOResultVO = marketingActivityService.queryMarketingActivityById(queryMarketingActivityReq);
        log.info("PreSubsidyCouponServiceImpl.queryPreSubsidyProduct marketingActivityService.queryMarketingActivityById marketingActivityDTOResultVO ={} ",JSON.toJSON(marketingActivityDTOResultVO));
        String promotionType = marketingActivityDTOResultVO.getResultData().getPromotionTypeCode();
        List<PreSubsidyProductPromResqDTO> preSubsidyProductPromResqDTOS = Lists.newArrayList();
        ResultVO<List<PreSubsidyProductRespDTO>> listResultVO = activityProductService.queryPreSubsidyProduct(queryPreSubsidyReqDTO.getMarketingActivityId());
        log.info("PreSubsidyCouponServiceImpl.queryPreSubsidyProduct activityProductService.queryPreSubsidyProduct listResultVO ={} ",JSON.toJSON(listResultVO));
        if (listResultVO.getResultData().size() <= 0) {
            return ResultVO.success(preSubsidyProductPromResqDTOS);
        }
        for (PreSubsidyProductRespDTO preSubsidyProductResqDTO : listResultVO.getResultData()) {
            PreSubsidyProductPromResqDTO preSubsidyProductPromResqDTO = new PreSubsidyProductPromResqDTO();
            BeanUtils.copyProperties(preSubsidyProductResqDTO, preSubsidyProductPromResqDTO);
            ActivityProductResq activityProductResq = new ActivityProductResq();
            BeanUtils.copyProperties(preSubsidyProductResqDTO.getActivityProductResqDTO(), activityProductResq);
            preSubsidyProductPromResqDTO.setActivityProductResq(activityProductResq);
            if (PromoConst.PromotionType.PROMOTION_TYPE_CD_20.getCode().equals(promotionType)) {
                String productId = preSubsidyProductResqDTO.getActivityProductResqDTO().getProductId();
                QueryProductCouponReq queryProductCouponReq = new QueryProductCouponReq();
                queryProductCouponReq.setProductId(productId);
                queryProductCouponReq.setMarketingActivityId(queryPreSubsidyReqDTO.getMarketingActivityId());
                queryProductCouponReq.setStatusCd(RightsStatusConsts.RIGHTS_STATUS_EXPIRE);
                queryProductCouponReq.setObjType(RightsConst.CouponApplyObjType.PRODUCT.getType());
                List<MktResCouponRespDTO> mktResCouponRespDTOList = mktResCouponManager.queryActivityCoupon(queryProductCouponReq);
                log.info("PreSubsidyCouponServiceImpl.queryPreSubsidyProduct mktResCouponManager.queryActivityCoupon mktResCouponRespDTOList={}",JSON.toJSON(mktResCouponRespDTOList));
                preSubsidyProductPromResqDTO.setMktResRegionRespDTOS(mktResCouponRespDTOList);
            }
            preSubsidyProductPromResqDTOS.add(preSubsidyProductPromResqDTO);
        }
        log.info("PreSubsidyCouponServiceImpl.queryPreSubsidyProduct preSubsidyProductPromResqDTOS={}",JSON.toJSON(preSubsidyProductPromResqDTOS));
        return ResultVO.success(preSubsidyProductPromResqDTOS);
    }
}
