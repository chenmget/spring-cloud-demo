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
import com.iwhalecloud.retail.rights.common.RightsConst;
import com.iwhalecloud.retail.rights.consts.RightsStatusConsts;
import com.iwhalecloud.retail.rights.dto.request.*;
import com.iwhalecloud.retail.rights.dto.response.*;
import com.iwhalecloud.retail.rights.entity.*;
import com.iwhalecloud.retail.rights.manager.*;
import com.iwhalecloud.retail.rights.service.PreSubsidyCouponService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

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


    @Override
    @Transactional
    public ResultVO addPreSubsidyCoupon(AddPreSubsidyCouponReqDTO req) {
        log.info("PreSubsidyCouponServiceImpl.addPreSubsidyCoupon AddPreSubsidyCouponReqDTO={}", JSON.toJSON(req));
        QueryMarketingActivityReq queryMarketingActivityReq = new QueryMarketingActivityReq();
        queryMarketingActivityReq.setMarketingActivityId(req.getMarketingActivityId());
        ResultVO<MarketingActivityDTO> marketingActivityDTOResultVO = marketingActivityService.queryMarketingActivityById(queryMarketingActivityReq);
        if (!marketingActivityDTOResultVO.isSuccess() || marketingActivityDTOResultVO.getResultData() == null) {
            return ResultVO.error("查询活动信息异常");
        }

        MktResCoupon mktResCoupon = new MktResCoupon();
        BeanUtils.copyProperties(req, mktResCoupon);
        mktResCoupon.setCreateDate(new Date());
        mktResCoupon.setUpdateDate(new Date());
        mktResCoupon.setCreateStaff(req.getCreateStaff());
        mktResCoupon.setUpdateStaff(req.getCreateStaff());
        mktResCoupon.setShowAmount(req.getDiscountValue().longValue());
        mktResCoupon.setDiscountType(RightsConst.CouponDiscountType.FIXED_DISCOUNT.getType());
        mktResCoupon.setManageType(RightsConst.ManagerType.MANAGER_TYPE_3000.getType());
        mktResCoupon.setUseSysId(Long.parseLong("-1"));
        mktResCoupon.setStatusCd(RightsStatusConsts.RIGHTS_STATUS_UNOBTAIN);
        mktResCoupon.setCouponType(RightsConst.MktResCouponType.PLATFORM.getType());
        mktResCouponManager.addMktResCoupon(mktResCoupon);
        String mktResId = mktResCoupon.getMktResId();
        log.info("PreSubsidyCouponServiceImpl addPreSubsidyCoupon 优惠券信息新增成功");

        CouponEffExpRule couponEffExpRule = new CouponEffExpRule();
        couponEffExpRule.setMktResId(mktResId);
        couponEffExpRule.setEffDate(marketingActivityDTOResultVO.getResultData().getStartTime());
        couponEffExpRule.setExpDate(marketingActivityDTOResultVO.getResultData().getEndTime());
        couponEffExpRule.setCreateStaff(req.getCreateStaff());
        couponEffExpRuleManager.addCouponEffExpRule(couponEffExpRule);
        log.info("PreSubsidyCouponServiceImpl addPreSubsidyCoupon 优惠券生失效规则新增成功");

        CouponSupplyRule couponSupplyRule = new CouponSupplyRule();
        BeanUtils.copyProperties(req, couponSupplyRule);
        couponSupplyRule.setMktResId(mktResId);
        couponSupplyRule.setBeginTime(marketingActivityDTOResultVO.getResultData().getStartTime());
        couponSupplyRule.setEndTime(marketingActivityDTOResultVO.getResultData().getEndTime());
        couponSupplyRuleManager.addCouponSupplyRule(couponSupplyRule);
        log.info("PreSubsidyCouponServiceImpl addPreSubsidyCoupon 优惠券领取规则新增成功");

        CouponDiscountRule couponDiscountRule = new CouponDiscountRule();
        BeanUtils.copyProperties(req, couponDiscountRule);
        couponDiscountRule.setMktResId(mktResId);
        couponDiscountRule.setMinValue(Double.valueOf("0"));
        List<String> mixUseTarget = req.getMixUseTarget();
        String mixUseFlag = req.getMixUseFlag();
        //添加可混合使用券
        if (mixUseTarget.size() > 0 && RightsStatusConsts.MIXUSE_FLAG_YSE.equals(mixUseFlag)) {
            couponDiscountRule.setMixUseTarget(StringUtils.join(mixUseTarget, ',') + ',');
            //同步混用优惠券的记录
            addMixUseCoupon(mixUseTarget, mktResId);
        }
        couponDiscountRuleManager.addCouponDiscountRule(couponDiscountRule);
        log.info("PreSubsidyCouponServiceImpl addPreSubsidyCoupon 优惠券抵扣规则新增成功");

        PromotionDTO promotionDTO = new PromotionDTO();
        BeanUtils.copyProperties(req, promotionDTO);
        promotionDTO.setPromotionEffectTime(req.getEffDate());
        promotionDTO.setPromotionOverdueTime(req.getExpDate());
        promotionDTO.setPromotionPrice(req.getDiscountValue().toString());
        promotionDTO.setPromotionType(PromoConst.PromotionType.PROMOTION_TYPE_CD_20.getCode());
        promotionDTO.setMktResId(mktResId);
        promotionDTO.setIsDeleted(PromoConst.IsDelete.IS_DELETE_CD_0.getCode());
        promotionService.addActPromotion(promotionDTO);
        return ResultVO.success();
    }

    @Override
    @Transactional
    public ResultVO updatePreSubsidyCoupon(UpdatePreSubsidyCouponReqDTO req) {
        log.info("PreSubsidyCouponServiceImpl.updatePreSubsidyCoupon UpdatePreSubsidyCouponReqDTO={}", JSON.toJSON(req));
        QueryMarketingActivityReq queryMarketingActivityReq = new QueryMarketingActivityReq();
        queryMarketingActivityReq.setMarketingActivityId(req.getMarketingActivityId());
        ResultVO<MarketingActivityDTO> marketingActivityDTOResultVO = marketingActivityService.queryMarketingActivityById(queryMarketingActivityReq);
        log.info("PreSubsidyCouponServiceImpl.updatePreSubsidyCoupon marketingActivityService.queryMarketingActivityById marketingActivityDTOResultVO={}", JSON.toJSON(marketingActivityDTOResultVO));
        if (!marketingActivityDTOResultVO.isSuccess() || marketingActivityDTOResultVO.getResultData() == null) {
            return ResultVO.error("查询活动信息异常");
        }

        //更新优惠券基础信息
        MktResCoupon mktResCoupon = new MktResCoupon();
        BeanUtils.copyProperties(req, mktResCoupon);
        mktResCoupon.setShowAmount(req.getDiscountValue().longValue());
        mktResCouponManager.updateMktResCouponById(mktResCoupon);

        //更新优惠券领取规则
        CouponSupplyRule couponSupplyRule = new CouponSupplyRule();
        BeanUtils.copyProperties(req, couponSupplyRule);
        couponSupplyRule.setBeginTime(marketingActivityDTOResultVO.getResultData().getStartTime());
        couponSupplyRule.setEndTime(marketingActivityDTOResultVO.getResultData().getEndTime());
        couponSupplyRuleManager.updateCouponSupplyRuleById(couponSupplyRule);

        //更新优惠券抵扣规则
        CouponDiscountRule couponDiscountRule = new CouponDiscountRule();
        BeanUtils.copyProperties(req, couponDiscountRule);
        List<String> mixUseTarget = Lists.newArrayList();
        String mixUseFlag = req.getMixUseFlag();
        if (RightsStatusConsts.MIXUSE_FLAG_YSE.equals(mixUseFlag)) {
            mixUseTarget = req.getMixUseTarget();
            if (mixUseTarget.size() <= 0) {
                couponDiscountRule.setMixUseTarget("");
            }
            couponDiscountRule.setMixUseTarget(StringUtils.join(mixUseTarget, ',') + ',');
        }
        couponDiscountRuleManager.updateCouponDiscountRuleById(couponDiscountRule);

        //同步可混合优惠券
        addMixUseCoupon(mixUseTarget, req.getMktResId());

        return ResultVO.success();
    }

    @Override
    public ResultVO<List<QueryPreSubsidyCouponResqDTO>> queryPreSubsidyCoupon(QueryPreSubsidyReqDTO queryPreSubsidyReqDTO) {
        List<QueryPreSubsidyCouponResqDTO> queryPreSubsidyCouponReqDTOS = Lists.newArrayList();
        log.info("PreSubsidyCouponServiceImpl.queryPreSubsidyCoupon queryPreSubsidyReqDTO={}", JSON.toJSON(queryPreSubsidyReqDTO));
        List<MktResCoupon> mktResCoupons = mktResCouponManager.queryCouponByActId(queryPreSubsidyReqDTO.getMarketingActivityId());
        log.info("PreSubsidyCouponServiceImpl.queryPreSubsidyCoupon mktResCouponManager.queryCouponByActId mktResCoupons={}", JSON.toJSON(mktResCoupons));
        if (mktResCoupons.size() <= 0) {
            return ResultVO.success(queryPreSubsidyCouponReqDTOS);
        }
        queryPreSubsidyCouponReqDTOS = mktResCoupons.stream().map(this::getMktResCouponInfo).collect(Collectors.toList());
        return ResultVO.success(queryPreSubsidyCouponReqDTOS);
    }

    @Override
    @Transactional
    public ResultVO deletePreSubsidyCoupon(QueryPreSubsidyReqDTO queryPreSubsidyReqDTO) {
        log.info("PreSubsidyCouponServiceImpl.deletePreSubsidyCoupon queryPreSubsidyReqDTO={}", JSON.toJSON(queryPreSubsidyReqDTO));
        String mktResId = queryPreSubsidyReqDTO.getMktResId();
        CouponApplyObjectListReq couponApplyObjectListReq = new CouponApplyObjectListReq();
        couponApplyObjectListReq.setMktResId(mktResId);
        List<CouponApplyObject> couponApplyObjects = couponApplyObjectManager.listCouponApplyObject(couponApplyObjectListReq);
        log.info("PreSubsidyCouponServiceImpl.deletePreSubsidyCoupon couponApplyObjectManager.listCouponApplyObject couponApplyObjects={}", JSON.toJSON(couponApplyObjects));
        if (couponApplyObjects.size() > 0) {
            return ResultVO.error("前置补贴优惠券删除失败,该优惠券已经配置产品");
        }
        //删除优惠券基础信息
        mktResCouponManager.deleteMktResCoupon(mktResId);

        //删除优惠券领取规则
        couponSupplyRuleManager.deleteCouponSupplyRule(mktResId);

        //删除优惠券的抵扣规则
        couponDiscountRuleManager.deleteCouponDiscountRule(mktResId);

        //删除优惠券的混用记录
        couponDiscountRuleManager.deleteMixUseCoupon(mktResId + ",");

        //删除活动优惠记录
        promotionService.deleteActPromotion(queryPreSubsidyReqDTO.getMarketingActivityId(), mktResId);
        return ResultVO.success();
    }

    @Override
    @Transactional
    public ResultVO addPreSubsidyProduct(AddPromotionProductReqDTO addPromotionProductReqDTO) {
        log.info("PreSubsidyCouponServiceImpl.addPreSubsidyProduct addPromotionProductReqDTO={}", JSON.toJSON(addPromotionProductReqDTO));
        QueryMarketingActivityReq queryMarketingActivityReq = new QueryMarketingActivityReq();
        queryMarketingActivityReq.setMarketingActivityId(addPromotionProductReqDTO.getMarketingActivityId());
        ResultVO<MarketingActivityDTO> marketingActivityDTOResultVO = marketingActivityService.queryMarketingActivityById(queryMarketingActivityReq);
        log.info("PreSubsidyCouponServiceImpl.addPreSubsidyProduct marketingActivityService.queryMarketingActivityById marketingActivityDTOResultVO={}", JSON.toJSON(marketingActivityDTOResultVO));
        if (!marketingActivityDTOResultVO.isSuccess() || marketingActivityDTOResultVO.getResultData() == null) {
            return ResultVO.error("活动数据异常");
        }
        ResultVO<List<PreSubsidyProductRespDTO>> listResultVO = activityProductService.queryPreSubsidyProduct(addPromotionProductReqDTO.getMarketingActivityId());
        log.info("PreSubsidyCouponServiceImpl.addPreSubsidyProduct marketingActivityService.queryMarketingActivityById marketingActivityDTOResultVO={}", JSON.toJSON(marketingActivityDTOResultVO));
        if(!listResultVO.isSuccess()){
            return ResultVO.error(listResultVO.getResultMsg());
        }
        try {
            String promotionType = marketingActivityDTOResultVO.getResultData().getPromotionTypeCode();
            if (listResultVO.getResultData().size() > 0) {
                //优惠券类型活动先删除优惠券适用对象的数据
                if (PromoConst.PromotionType.PROMOTION_TYPE_CD_20.getCode().equals(promotionType)) {
                    List<MktResCoupon> mktResCoupons = mktResCouponManager.queryCouponByActId(addPromotionProductReqDTO.getMarketingActivityId());
                    log.info("PreSubsidyCouponServiceImpl.addPreSubsidyProduct mktResCouponManager.queryCouponByActId mktResCoupons={}", JSON.toJSON(mktResCoupons));
                    couponApplyObjectManager.deleteCouponApplyObjectByAct(mktResCoupons.stream().map(MktResCoupon::getMktResId).collect(Collectors.toList()));
                }
                if (PromoConst.PromotionType.PROMOTION_TYPE_CD_10.getCode().equals(promotionType)) {
                    for (AddPreSubsidyProductReqDTO addPreSubsidyProductReqDTO : addPromotionProductReqDTO.getAddPreSubsidyProductReqDTOS()) {
                        ResultVO resultVO = activityProductService.checkProductDiscountAmount(addPreSubsidyProductReqDTO.getProductId(), addPreSubsidyProductReqDTO.getDiscountAmount());
                        if (!resultVO.isSuccess()) {
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
                        List<CouponApplyObject> couponApplyObjects = mktResIds.stream().map((String string) -> {
                            CouponApplyObject couponApplyObject = new CouponApplyObject();
                            couponApplyObject.setMktResId(string);
                            couponApplyObject.setObjId(addPreSubsidyProductReqDTO.getProductId());
                            couponApplyObject.setObjType(RightsConst.CouponApplyObjType.PRODUCT.getType());
                            couponApplyObject.setCreateStaff(addPromotionProductReqDTO.getUserId());
                            couponApplyObject.setUpdateStaff(addPromotionProductReqDTO.getUserId());
                            couponApplyObject.setStatusCd(RightsStatusConsts.RIGHTS_STATUS_UNOBTAIN);
                            return couponApplyObject;
                        }).collect(Collectors.toList());
                        couponApplyObjectManager.saveBatch(couponApplyObjects);
                    }
                }
                activityProductService.addPreSubsidyProduct(activityProductReq);
            }
            AuitMarketingActivityReq auitMarketingActivityReq = new AuitMarketingActivityReq();
            BeanUtils.copyProperties(addPromotionProductReqDTO,auitMarketingActivityReq);
            auitMarketingActivityReq.setId(addPromotionProductReqDTO.getMarketingActivityId());
            auitMarketingActivityReq.setName(marketingActivityDTOResultVO.getResultData().getName());
            marketingActivityService.auitMarketingActivity(auitMarketingActivityReq);
        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            log.info("PreSubsidyCouponServiceImpl addPreSubsidyProduct 前置补贴新增失败", e);
            activityProductService.deletePreSubsidyProduct(addPromotionProductReqDTO.getMarketingActivityId());
            for (PreSubsidyProductRespDTO preSubsidyProductResqDTO : listResultVO.getResultData()) {
                ActivityProductReq activityProductReq = new ActivityProductReq();
                BeanUtils.copyProperties(preSubsidyProductResqDTO.getActivityProductResqDTO(), activityProductReq);
                activityProductService.addPreSubsidyProduct(activityProductReq);
            }
            return ResultVO.error("前置补贴新增失败");
        }
        return ResultVO.success();
    }

    @Override
    public ResultVO<List<PreSubsidyProductPromResqDTO>> queryPreSubsidyProduct(QueryPreSubsidyReqDTO queryPreSubsidyReqDTO) {
        log.info("PreSubsidyCouponServiceImpl.queryPreSubsidyProduct  queryPreSubsidyReqDTO ={}", JSON.toJSON(queryPreSubsidyReqDTO));
        QueryMarketingActivityReq queryMarketingActivityReq = new QueryMarketingActivityReq();
        queryMarketingActivityReq.setMarketingActivityId(queryPreSubsidyReqDTO.getMarketingActivityId());
        ResultVO<MarketingActivityDTO> marketingActivityDTOResultVO = marketingActivityService.queryMarketingActivityById(queryMarketingActivityReq);
        log.info("PreSubsidyCouponServiceImpl.queryPreSubsidyProduct marketingActivityService.queryMarketingActivityById marketingActivityDTOResultVO ={} ", JSON.toJSON(marketingActivityDTOResultVO));
        if (!marketingActivityDTOResultVO.isSuccess() || marketingActivityDTOResultVO.getResultData() == null) {
            return ResultVO.error("活动数据异常");
        }
        String promotionType = marketingActivityDTOResultVO.getResultData().getPromotionTypeCode();
        List<PreSubsidyProductPromResqDTO> preSubsidyProductPromResqDTOS = Lists.newArrayList();
        ResultVO<List<PreSubsidyProductRespDTO>> listResultVO = activityProductService.queryPreSubsidyProduct(queryPreSubsidyReqDTO.getMarketingActivityId());
        log.info("PreSubsidyCouponServiceImpl.queryPreSubsidyProduct activityProductService.queryPreSubsidyProduct listResultVO ={} ", JSON.toJSON(listResultVO));
        if (listResultVO.getResultData().size() <= 0) {
            return ResultVO.success(preSubsidyProductPromResqDTOS);
        }
        for (PreSubsidyProductRespDTO preSubsidyProductResqDTO : listResultVO.getResultData()) {
            //复制产品信息
            PreSubsidyProductPromResqDTO preSubsidyProductPromResqDTO = new PreSubsidyProductPromResqDTO();
            BeanUtils.copyProperties(preSubsidyProductResqDTO, preSubsidyProductPromResqDTO);
            //复制活动产品信息
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
                log.info("PreSubsidyCouponServiceImpl.queryPreSubsidyProduct mktResCouponManager.queryActivityCoupon mktResCouponRespDTOList={}", JSON.toJSON(mktResCouponRespDTOList));
                preSubsidyProductPromResqDTO.setMktResRegionRespDTOS(mktResCouponRespDTOList);
            }
            preSubsidyProductPromResqDTOS.add(preSubsidyProductPromResqDTO);
        }
        log.info("PreSubsidyCouponServiceImpl.queryPreSubsidyProduct preSubsidyProductPromResqDTOS={}", JSON.toJSON(preSubsidyProductPromResqDTOS));
        return ResultVO.success(preSubsidyProductPromResqDTOS);
    }

    /**
     * 混合使用优惠券同步数据
     *
     * @param mixUseTarget
     * @param mktResId
     */
    public void addMixUseCoupon(List<String> mixUseTarget, String mktResId) {
        log.info("PreSubsidyCouponServiceImpl.addMixUseCoupon mixUseTarget={},mktResId={}", mixUseTarget, mktResId);
        couponDiscountRuleManager.deleteMixUseCoupon(mktResId + ",");
        if (mixUseTarget.size() > 0) {
            couponDiscountRuleManager.addMixUseCoupon(mixUseTarget, mktResId+',');
        }
    }

    /**
     * 根据优惠券查询规格信息
     *
     * @param mktResCoupon
     * @return
     */
    private QueryPreSubsidyCouponResqDTO getMktResCouponInfo(MktResCoupon mktResCoupon) {
        log.info("PreSubsidyCouponServiceImpl.getMktResCouponInfo mktResCoupon={}", JSON.toJSON(mktResCoupon));
        QueryPreSubsidyCouponResqDTO queryPreSubsidyCouponResqDTO = new QueryPreSubsidyCouponResqDTO();
        BeanUtils.copyProperties(mktResCoupon, queryPreSubsidyCouponResqDTO);

        //复制领取规则信息
        CouponSupplyRule couponSupplyRule = couponSupplyRuleManager.selectOneCouponSupplyRule(mktResCoupon.getMktResId());
        log.info("PreSubsidyCouponServiceImpl.queryPreSubsidyCoupon couponSupplyRuleManager.selectOneCouponSupplyRule couponSupplyRule={}", JSON.toJSON(couponSupplyRule));
        BeanUtils.copyProperties(couponSupplyRule, queryPreSubsidyCouponResqDTO);

        //复制抵扣规则信息
        CouponDiscountRule couponDiscountRule = couponDiscountRuleManager.selectOneCouponDiscountRule(mktResCoupon.getMktResId());
        log.info("PreSubsidyCouponServiceImpl.queryPreSubsidyCoupon couponDiscountRuleManager.selectOneCouponDiscountRule couponDiscountRule={}", JSON.toJSON(couponDiscountRule));
        BeanUtils.copyProperties(couponDiscountRule, queryPreSubsidyCouponResqDTO, "mixUseTarget");
        String mixUseTarget = couponDiscountRule.getMixUseTarget();
        List<String> mixUseTargets = Lists.newArrayList();
        if (!StringUtils.isEmpty(mixUseTarget)) {
            mixUseTargets = Arrays.asList(mixUseTarget.split(","));
        }
        queryPreSubsidyCouponResqDTO.setMixUseTarget(mixUseTargets);

        //添加该券可选的混合范围
        QueryPreSubsidyReqDTO queryPreSubsidyReqDTO = new QueryPreSubsidyReqDTO();
        queryPreSubsidyReqDTO.setMarketingActivityId(mktResCoupon.getMarketingActivityId());
        queryPreSubsidyReqDTO.setMktResId(mktResCoupon.getMktResId());
        List<MktResCouponRespDTO> mktResCouponRespDTOList = mktResCouponManager.queryMixUseCoupon(queryPreSubsidyReqDTO);
        log.info("PreSubsidyCouponServiceImpl.getMktResCouponInfo mktResCouponManager.queryMixUseCoupon mktResCouponRespDTOList={}", JSON.toJSON(mktResCouponRespDTOList));
        queryPreSubsidyCouponResqDTO.setMktResCouponRespDTOS(mktResCouponRespDTOList);
        log.info("PreSubsidyCouponServiceImpl.getMktResCouponInfo QueryPreSubsidyCouponResqDTO={}", JSON.toJSON(queryPreSubsidyCouponResqDTO));
        return queryPreSubsidyCouponResqDTO;
    }

    @Override
    public ResultVO<List<MktResCouponRespDTO>> queryMixUseCoupon(QueryPreSubsidyReqDTO queryPreSubsidyReqDTO) {
        log.info("PreSubsidyCouponServiceImpl.queryMixUseCoupon queryPreSubsidyReqDTO={}", JSON.toJSON(queryPreSubsidyReqDTO));
        QueryMarketingActivityReq queryMarketingActivityReq = new QueryMarketingActivityReq();
        queryMarketingActivityReq.setMarketingActivityId(queryPreSubsidyReqDTO.getMarketingActivityId());
        ResultVO<MarketingActivityDTO> marketingActivityDTOResultVO = marketingActivityService.queryMarketingActivityById(queryMarketingActivityReq);
        log.info("PreSubsidyCouponServiceImpl.queryPreSubsidyProduct marketingActivityService.queryMarketingActivityById marketingActivityDTOResultVO ={} ", JSON.toJSON(marketingActivityDTOResultVO));
        if (!marketingActivityDTOResultVO.isSuccess() || marketingActivityDTOResultVO.getResultData() == null) {
            return ResultVO.error("活动数据异常");
        }
        return ResultVO.success(mktResCouponManager.queryMixUseCoupon(queryPreSubsidyReqDTO));
    }

    @Override
    public ResultVO updateActCouponType(QueryPreSubsidyReqDTO queryPreSubsidyReqDTO) {
        log.info("PreSubsidyCouponServiceImpl.updateActCouponType queryPreSubsidyReqDTO={}", JSON.toJSON(queryPreSubsidyReqDTO));
        return ResultVO.success(mktResCouponManager.updateActCouponType(queryPreSubsidyReqDTO.getMarketingActivityId(), queryPreSubsidyReqDTO.getCouponKind()));
    }

    @Override
    @Transactional
    public ResultVO updateCouponDate(String marketingActivityId) {
        log.info("PreSubsidyCouponServiceImpl.updateCouponDate marketingActivityId={}", marketingActivityId);
        QueryMarketingActivityReq queryMarketingActivityReq = new QueryMarketingActivityReq();
        queryMarketingActivityReq.setMarketingActivityId(marketingActivityId);
        ResultVO<MarketingActivityDTO> marketingActivityDTOResultVO = marketingActivityService.queryMarketingActivityById(queryMarketingActivityReq);
        log.info("PreSubsidyCouponServiceImpl.updateCouponDate marketingActivityService.queryMarketingActivityById marketingActivityDTOResultVO ={} ", JSON.toJSON(marketingActivityDTOResultVO));
        if (!marketingActivityDTOResultVO.isSuccess() || marketingActivityDTOResultVO.getResultData() == null) {
            return ResultVO.error("活动数据异常");
        }
        MarketingActivityDTO resultData = marketingActivityDTOResultVO.getResultData();
        List<MktResCoupon> mktResCoupons = mktResCouponManager.queryCouponByActId(marketingActivityId);
        log.info("PreSubsidyCouponServiceImpl.updateCouponDate mktResCouponManager.queryCouponByActId mktResCoupons ={} ", JSON.toJSON(mktResCoupons));
        List<String> mktResIds = mktResCoupons.stream().map(MktResCoupon::getMktResId).collect(Collectors.toList());
        //更新优惠券生失效时间
        couponEffExpRuleManager.updateCouponEffExpDate(mktResIds, resultData.getStartTime(), resultData.getEndTime());
        //更新优惠券领取时间
        couponSupplyRuleManager.updateCouponSupplyDate(mktResIds, resultData.getStartTime(), resultData.getEndTime());
        return ResultVO.success();
    }
}