package com.iwhalecloud.retail.promo.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.partner.dto.MerchantDTO;
import com.iwhalecloud.retail.partner.dto.req.MerchantListReq;
import com.iwhalecloud.retail.partner.service.MerchantService;
import com.iwhalecloud.retail.promo.common.PromoConst;
import com.iwhalecloud.retail.promo.constant.Constant;
import com.iwhalecloud.retail.promo.dto.*;
import com.iwhalecloud.retail.promo.dto.req.*;
import com.iwhalecloud.retail.promo.dto.resp.*;
import com.iwhalecloud.retail.promo.entity.*;
import com.iwhalecloud.retail.promo.filter.activity.SellerActivityFilter;
import com.iwhalecloud.retail.promo.filter.activity.model.ActivityAuthModel;
import com.iwhalecloud.retail.promo.manager.*;
import com.iwhalecloud.retail.promo.service.ActivityProductService;
import com.iwhalecloud.retail.promo.service.MarketingActivityService;
import com.iwhalecloud.retail.promo.utils.ReflectUtils;
import com.iwhalecloud.retail.rights.dto.request.AutoPushCouponReq;
import com.iwhalecloud.retail.rights.dto.request.QueryCouponByIdReq;
import com.iwhalecloud.retail.rights.dto.request.QueryCouponByProductAndActivityIdReq;
import com.iwhalecloud.retail.rights.dto.request.QueryPreSubsidyReqDTO;
import com.iwhalecloud.retail.rights.dto.response.CouponApplyObjectRespDTO;
import com.iwhalecloud.retail.rights.dto.response.CouponRuleAndTypeQueryResp;
import com.iwhalecloud.retail.rights.service.CouponApplyObjectService;
import com.iwhalecloud.retail.rights.service.CouponInstService;
import com.iwhalecloud.retail.rights.service.MktResCouponService;
import com.iwhalecloud.retail.rights.service.PreSubsidyCouponService;
import com.iwhalecloud.retail.system.dto.CommonRegionDTO;
import com.iwhalecloud.retail.system.dto.request.CommonRegionListReq;
import com.iwhalecloud.retail.system.service.CommonRegionService;
import com.iwhalecloud.retail.workflow.common.ResultCodeEnum;
import com.iwhalecloud.retail.workflow.common.WorkFlowConst;
import com.iwhalecloud.retail.workflow.dto.req.NextRouteAndReceiveTaskReq;
import com.iwhalecloud.retail.workflow.dto.req.ProcessStartReq;
import com.iwhalecloud.retail.workflow.service.TaskService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import javax.naming.Context;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component("marketingActivityService")
@Service
public class MarketingActivityServiceImpl implements MarketingActivityService {
    @Autowired
    private MarketingActivityManager marketingActivityManager;

    @Autowired
    private ActivityScopeManager activityScopeManager;

    @Autowired
    private ActivityParticipantManager activityParticipantManager;

    @Autowired
    private ActivityProductManager activityProductManager;

    @Autowired
    private ActivityGoodsManager activityGoodsManager;

    @Autowired
    private PromotionManager promotionManager;

    @Autowired
    private ActivityRuleManager activityRuleManager;

    @Reference
    private MktResCouponService mktResCouponService;

    @Reference
    private PreSubsidyCouponService preSubsidyCouponService;

    @Autowired
    private ActivityProductService activityProductService;

    @Reference
    private TaskService taskService;

    @Reference
    private CommonRegionService commonRegionService;

    @Reference
    private MerchantService merchantService;

    @Resource
    private SellerActivityFilter sellerActivityFilter;

    @Reference
    private CouponInstService couponInstService;

    @Reference
    private CouponApplyObjectService couponApplyObjectService;

    @Autowired
    private Constant constant;

    private List<MarketingReliefActivityQueryResp> marketingReliefActivityQueryRespList;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultVO<MarketingActivityAddResp> addMarketingActivity(MarketingActivityAddReq req){
        log.info("MarketingActivityServiceImpl.addMarketingActivity req={}", JSON.toJSONString(req));
        MarketingActivity marketingActivity = new MarketingActivity();
        BeanUtils.copyProperties(req, marketingActivity);
        // 添加营销活动
        marketingActivityManager.addMarketingActivity(marketingActivity);
        String marketingActivityId = marketingActivity.getId();
        String marketingActivityCode = marketingActivity.getCode();

        List<ActivityScopeDTO> activityScopeDTOList = req.getActivityScopeList();
        List<ActivityScope> activityScopeList = Lists.newArrayList();
        activityScopeManager.deleteActivityScopeBatch(marketingActivityId);
        if (!CollectionUtils.isEmpty(activityScopeDTOList)) {
            for (ActivityScopeDTO activityScopeDTO : activityScopeDTOList) {
                ActivityScope activityScope = new ActivityScope();
                BeanUtils.copyProperties(activityScopeDTO, activityScope);
                activityScope.setMarketingActivityId(marketingActivityId);
                activityScope.setIsDeleted(PromoConst.IsDelete.IS_DELETE_CD_0.getCode());
                activityScopeList.add(activityScope);
            }
            // 添加参与活动范围
            activityScopeManager.addActivityScopeBatch(activityScopeList);
        }


        List<ActivityParticipantDTO> activityParticipantDTOList = req.getActivityParticipantList();
        List<ActivityParticipant> activityParticipantList = Lists.newArrayList();
        activityParticipantManager.deleteActivityParticipantBatch(marketingActivityId);
        if (!CollectionUtils.isEmpty(activityParticipantDTOList)) {
            for (ActivityParticipantDTO activityParticipantDTO : activityParticipantDTOList) {
                ActivityParticipant activityParticipant = new ActivityParticipant();
                BeanUtils.copyProperties(activityParticipantDTO, activityParticipant);
                activityParticipant.setMarketingActivityId(marketingActivityId);
                activityParticipant.setIsDeleted(PromoConst.IsDelete.IS_DELETE_CD_0.getCode());
                activityParticipantList.add(activityParticipant);
            }
            // 添加活动参与人员
            activityParticipantManager.addActivityParticipantBatch(activityParticipantList);
        }
        MarketingActivityAddResp marketingActivityAddResp = new MarketingActivityAddResp();
        marketingActivityAddResp.setId(marketingActivityId);
        return ResultVO.success(marketingActivityAddResp);
    }

    @Override
    public ResultVO<MarketingActivityDetailResp> queryMarketingActivity(String id) {
        log.info("MarketingActivityServiceImpl.queryMarketingActivity id={}", id);
        ResultVO<MarketingActivityDTO> resultVO = marketingActivityManager.getMarketingActivityDtoById(id);
        MarketingActivityDTO marketingActivityDTO = resultVO.getResultData();
        log.info("MarketingActivityServiceImpl.queryMarketingActivity marketingActivityDTO{}", marketingActivityDTO);
        if (marketingActivityDTO == null) {
            return ResultVO.error(constant.getNoMarketingActivity());
        }
        MarketingActivityDetailResp resp = new MarketingActivityDetailResp();
        BeanUtils.copyProperties(marketingActivityDTO, resp);
        String marketingActivityCode = marketingActivityDTO.getCode();
        String marketingActivityId = marketingActivityDTO.getId();
        List<ActivityScopeDTO> activityScopeDTOList = activityScopeManager.queryActivityScopeByMktId(marketingActivityId);
        log.info("MarketingActivityServiceImpl.queryMarketingActivity activityScopeList=" + activityScopeDTOList);

        if (!CollectionUtils.isEmpty(activityScopeDTOList)) {
            for (ActivityScopeDTO activityScopeDTO:activityScopeDTOList
                 ) {
               List<String> list =new ArrayList<>();
                CommonRegionListReq commonRegionListReq = new CommonRegionListReq();
                list.add(activityScopeDTO.getLanId());
                commonRegionListReq.setRegionIdList(list);
                ResultVO<List<CommonRegionDTO>> lancommonRegionDTOList=commonRegionService.listCommonRegion(commonRegionListReq);
                if (!CollectionUtils.isEmpty(lancommonRegionDTOList.getResultData())){
                    for (CommonRegionDTO commonRegionDTO:lancommonRegionDTOList.getResultData()
                         ) {
                        activityScopeDTO.setLanName(commonRegionDTO.getRegionName());
                        activityScopeDTO.setKey(commonRegionDTO.getRegionId());
                        activityScopeDTO.setTitle(commonRegionDTO.getRegionName());
                        activityScopeDTO.setRegionId(commonRegionDTO.getRegionId());
                    }
                }
                log.info("MarketingActivityServiceImpl.queryMarketingActivity commonRegionDTOList=" +
                        lancommonRegionDTOList.getResultData());
                list.clear();
                commonRegionListReq.getRegionIdList().clear();
                list.add(activityScopeDTO.getCity());
                commonRegionListReq.setRegionIdList(list);
                log.info("MarketingActivityServiceImpl.queryMarketingActivity list commonRegionListReq=" +
                        list, commonRegionListReq.getRegionIdList());
                ResultVO<List<CommonRegionDTO>> citycommonRegionDTOList=commonRegionService.listCommonRegion(commonRegionListReq);
                if (!CollectionUtils.isEmpty(citycommonRegionDTOList.getResultData())){
                    for (CommonRegionDTO commonRegionDTO:citycommonRegionDTOList.getResultData()
                            ) {
                        activityScopeDTO.setCityName(commonRegionDTO.getRegionName());
                        activityScopeDTO.setKey(commonRegionDTO.getRegionId());
                        activityScopeDTO.setTitle(commonRegionDTO.getRegionName());
                        activityScopeDTO.setRegionId(commonRegionDTO.getRegionId());
                    }
                }
            }
            List<ActivityScopeDTO> activityScopeDTOLists = ReflectUtils.batchAssign(activityScopeDTOList, ActivityScopeDTO.class);
            resp.setActivityScopeList(activityScopeDTOLists);
            log.info("MarketingActivityServiceImpl.queryMarketingActivity resp.getActivityScopeList=" +
                    resp.getActivityScopeList());
        }
        List<ActivityParticipantDTO> activityParticipantDTOList = activityParticipantManager.queryActivityParticipantByMktId(marketingActivityId);
        log.info("MarketingActivityServiceImpl.queryMarketingActivity activityParticipantDTOList=" +
                activityParticipantDTOList);
        if (!CollectionUtils.isEmpty(activityParticipantDTOList)) {
            for (ActivityParticipantDTO activityParticipantDTO : activityParticipantDTOList
                 ) {
                List<String> list =new ArrayList<>();
                CommonRegionListReq commonRegionListReq = new CommonRegionListReq();
                list.add(activityParticipantDTO.getLanId());
                commonRegionListReq.setRegionIdList(list);
                ResultVO<List<CommonRegionDTO>> lancommonRegionDTOList=commonRegionService.listCommonRegion(commonRegionListReq);
                if (!CollectionUtils.isEmpty(lancommonRegionDTOList.getResultData())){
                    for (CommonRegionDTO commonRegionDTO:lancommonRegionDTOList.getResultData()
                            ) {
                        activityParticipantDTO.setLanName(commonRegionDTO.getRegionName());
                        activityParticipantDTO.setKey(commonRegionDTO.getRegionId());
                        activityParticipantDTO.setTitle(commonRegionDTO.getRegionName());
                        activityParticipantDTO.setRegionId(commonRegionDTO.getRegionId());
                    }
                }
                log.info("MarketingActivityServiceImpl.queryMarketingActivity lancommonRegionDTOList=" +
                        lancommonRegionDTOList.getResultData());
                list.clear();
                commonRegionListReq.getRegionIdList().clear();
                list.add(activityParticipantDTO.getCity());
                commonRegionListReq.setRegionIdList(list);
                log.info("MarketingActivityServiceImpl.queryMarketingActivity list commonRegionListReq=" +
                        list, commonRegionListReq.getRegionIdList());
                ResultVO<List<CommonRegionDTO>> citycommonRegionDTOList=commonRegionService.listCommonRegion(commonRegionListReq);
                if (!CollectionUtils.isEmpty(citycommonRegionDTOList.getResultData())){
                    for (CommonRegionDTO commonRegionDTO:citycommonRegionDTOList.getResultData()) {
                        activityParticipantDTO.setCityName(commonRegionDTO.getRegionName());
                        activityParticipantDTO.setKey(commonRegionDTO.getRegionId());
                        activityParticipantDTO.setTitle(commonRegionDTO.getRegionName());;
                        activityParticipantDTO.setRegionId(commonRegionDTO.getRegionId());
                    }
                }
                log.info("MarketingActivityServiceImpl.queryMarketingActivity citycommonRegionDTOList=" +
                        citycommonRegionDTOList.getResultData());
            }
            List<ActivityParticipantDTO> activityParticipantDTOLists = ReflectUtils.batchAssign(activityParticipantDTOList,ActivityParticipantDTO.class );
            resp.setActivityParticipantList(activityParticipantDTOLists);
            log.info("MarketingActivityServiceImpl.queryMarketingActivity resp.getActivityParticipantList=" +
                    resp.getActivityParticipantList());
        }
        // 根据活动编码查询参与活动产品
        List<ActivityProduct> activityGoodsList = activityProductManager.queryActivityProductByCondition(marketingActivityId);
        if (!CollectionUtils.isEmpty(activityGoodsList)) {
            List<ActivityProductDTO> activityProductDTOList = ReflectUtils.batchAssign(activityGoodsList, ActivityProductDTO.class);
            resp.setActivityProductList(activityProductDTOList);
        }
        // 根据活动编码查询活动规则
        List<ActivityRule> activityRuleList = activityRuleManager.queryActivityRuleByCondition(marketingActivityId);
        if (!CollectionUtils.isEmpty(activityRuleList)) {
            List<ActivityRuleDTO> activityRuleDTOList = ReflectUtils.batchAssign(activityRuleList, ActivityRuleDTO.class);
            resp.setActivityRuleDTOList(activityRuleDTOList);
        }
        // 根据活动编码查询优惠信息
        List<Promotion> promotionList = promotionManager.queryPromotionByCondition(marketingActivityId);
        if (!CollectionUtils.isEmpty(promotionList)) {
            List<PromotionDTO> promotionDTOList = ReflectUtils.batchAssign(promotionList, PromotionDTO.class);
            resp.setPromotionDTOList(promotionDTOList);
        }
        return ResultVO.success(resp);
    }

    @Override
    public ResultVO<Page<MarketingActivityListResp>> listMarketingActivity(MarketingActivityListReq req) {
        log.info("MarketingActivityServiceImpl.listMarketingActivity req={}", JSON.toJSONString(req));
        return ResultVO.success(marketingActivityManager.listMarketingActivity(req));
    }

    @Override
    public ResultVO<Boolean> cancleMarketingActivity(CancelMarketingActivityStatusReq req) {
        log.info("MarketingActivityServiceImpl.endMarketingActivity id={}", req.getActivityId());
        return ResultVO.success(marketingActivityManager.deleteMarketingActivityById(req.getActivityId(), PromoConst.STATUSCD.STATUS_CD_PLUS_1.getCode()));
    }

    @Override
    public ResultVO<Boolean> endMarketingActivity(EndMarketingActivityStatusReq req) {
        log.info("MarketingActivityServiceImpl.endMarketingActivity id={}", req.getActivityId());
        return ResultVO.success(marketingActivityManager.updateMarketingActivityById(req.getActivityId(), PromoConst.STATUSCD.STATUS_CD_30.getCode()));
    }

    @Override
    public ResultVO<Boolean> startMarketingActivity(StartMarketingActivityStatusReq req) {
        log.info("MarketingActivityServiceImpl.startMarketingActivity id={}", req.getActivityId());
        return ResultVO.success(marketingActivityManager.updateMarketingActivityById(req.getActivityId(), PromoConst.STATUSCD.STATUS_CD_1.getCode()));
    }

    /**
     * 查询商品详情页面中的适用活动
     * @param req 商品参数
     * @return
     */
    @Override
    public ResultVO<List<MarketingGoodsActivityQueryResp>> listGoodsMarketingActivitys(MarketingActivityQueryByGoodsReq req) {
        log.info("MarketingActivityServiceImpl.listGoodsMarketingActivitys req={}", JSON.toJSONString(req));
        List<MarketingGoodsActivityQueryResp> marketingGoodsActivityQueryRespList = Lists.newArrayList();
        //产品鉴定
        List<ActivityProduct> activityProducts = activityProductManager.queryActivityProductByProductId(req.getProductId());
        if(!CollectionUtils.isEmpty(activityProducts)) {
            List<String> marketingActivityIdList = Lists.newArrayList();
            activityProducts.forEach(item -> {
                marketingActivityIdList.add(item.getMarketingActivityId());
            });
            //查询活动列表
            List<MarketingActivity> marketingActivities = marketingActivityManager.listMarketingActivitysByCodes(marketingActivityIdList, req.getActivityType());
            if (!CollectionUtils.isEmpty(marketingActivities)) {
                List<MarketingActivity> marketingActivitieList = Lists.newArrayList();
                marketingActivities.forEach(item -> {
                    String activityScopeType = item.getActivityScopeType();
                    //根据活动对象类型过滤活动
                    filterActivitys(req, marketingActivitieList, item, activityScopeType);
                });
                //返回活动列表
                if(marketingActivitieList.size() > 0){
                    marketingActivitieList.forEach(item -> {
                        MarketingGoodsActivityQueryResp marketingGoodsActivityQueryResp = new MarketingGoodsActivityQueryResp();
                        BeanUtils.copyProperties(item, marketingGoodsActivityQueryResp);
                        marketingGoodsActivityQueryRespList.add(marketingGoodsActivityQueryResp);
                    });
                }
            }
        }
        log.info("MarketingActivityServiceImpl.listGoodsMarketingActivitys resp={}", JSON.toJSONString(marketingGoodsActivityQueryRespList));
        return ResultVO.success(marketingGoodsActivityQueryRespList);
    }

    /**
     * 根据活动ID查询活动详情
     * @param activityId
     * @return
     */
    @Override
    public ResultVO<MarketingGoodsActivityQueryResp> getMarketingActivity(String activityId) {

        log.error("MarketingActivityServiceImpl.getMarketingGoodsActivity -->activityId={}",activityId);
        MarketingActivity marketingActivity = marketingActivityManager.getMarketingActivityById(activityId);
        if (marketingActivity == null) {
            log.error("MarketingActivityServiceImpl.getMarketingGoodsActivity  activity not exist-->activityId={}",activityId);
            return ResultVO.error("活动不存在");
        }
        MarketingGoodsActivityQueryResp marketingGoodsActivityQueryResp = new MarketingGoodsActivityQueryResp();
        BeanUtils.copyProperties(marketingActivity, marketingGoodsActivityQueryResp);
        log.error("MarketingActivityServiceImpl.getMarketingGoodsActivity -->marketingGoodsActivityQueryResp={}",JSON.toJSONString(marketingGoodsActivityQueryResp));
        return ResultVO.success(marketingGoodsActivityQueryResp);
    }

    /**
     * 查询营销活动信息关联的优惠信息
     * @param req 商品参数
     * @return
     */
    @Override
     public ResultVO<List<MarketingAndPromotionResp>> listMarketingActivityAndPromotions(MarketingActivityQueryByGoodsReq req){
         log.info("MarketingActivityServiceImpl.listMarketingActivityAndPromotions req{} ",req);
         ResultVO<List<MarketingGoodsActivityQueryResp>> resultVO = listGoodsMarketingActivitys(req);
         List<MarketingGoodsActivityQueryResp> marketingGoodsActivityQueryRespList = resultVO.getResultData();
        log.info("MarketingActivityServiceImpl.listMarketingActivityAndPromotions marketingGoodsActivityQueryRespList{} ",
                resultVO.getResultData());
         log.info("MarketingActivityServiceImpl.listMarketingActivityAndPromotions marketingGoodsActivityQueryRespList{} ",
                 marketingGoodsActivityQueryRespList);
         List<MarketingAndPromotionResp> respList = new ArrayList<>();
         if (!CollectionUtils.isEmpty(marketingGoodsActivityQueryRespList)){
             for (int i = 0;i<marketingGoodsActivityQueryRespList.size();i++){
                 MarketingActivity marketingActivity = marketingActivityManager.getMarketingActivityById(marketingGoodsActivityQueryRespList.get(i).getId());
                 List<String> marketingActivityIds = new ArrayList<>();
                 marketingActivityIds.add(marketingActivity.getId());
                 List<ActivityProduct> activityGoodsList = activityProductManager.queryActivityProductBymktIdProdId(marketingActivityIds,req.getProductId());
                 if (!CollectionUtils.isEmpty(activityGoodsList)) {
                     for (int k=0; k<activityGoodsList.size();k++){
                         MarketingAndPromotionResp marketingAndPromotionResp = new MarketingAndPromotionResp();
                         marketingAndPromotionResp.setPromotionTypeCode(marketingActivity.getPromotionTypeCode());
                         marketingAndPromotionResp.setActivityType(marketingActivity.getActivityType());
                         marketingAndPromotionResp.setName(marketingActivity.getName());
                         marketingAndPromotionResp.setId(marketingActivity.getId());
                         marketingAndPromotionResp.setPromotionPrice(String.valueOf(activityGoodsList.get(k).getDiscountAmount()));
                         marketingAndPromotionResp.setProductId(activityGoodsList.get(k).getProductId());
                         respList.add(marketingAndPromotionResp);
                     }
                 }
             }
         }
         log.info("MarketingActivityServiceImpl.listMarketingActivityAndPromotions resp{} ",
                 respList);
         return   ResultVO.success(respList);
     }



    /**
     * 根据活动对象类型过滤活动
     * @param req 查询总入参
     * @param marketingActivitieList 接收结果的List
     * @param item  当前循环体内的活动对象
     * @param activityScopeType 活动类型
     */
    private void filterActivitys(MarketingActivityQueryByGoodsReq req, List<MarketingActivity> marketingActivitieList, MarketingActivity item, String activityScopeType) {
        if(PromoConst.ActivityScopeType.ACTIVITY_SCOPE_TYPE_10.getCode().equals(activityScopeType)){
            //过滤活动对象，即买家
            ResultVO<MerchantDTO> merchantDTO = merchantService.getMerchantById(req.getSupplierCode());
            MerchantDTO merchant = merchantDTO.getResultData();
            if(merchant != null){
                //过滤地市、区县
                ActivityScope activityScopeByLanId = activityScopeManager.queryActivityScopeByLandId(item.getId(), merchantDTO.getResultData().getLanId());
                //过滤地市
                if(activityScopeByLanId != null){
                    //过滤活动对象，即买家，根据参与对象类型过滤活动
                    Boolean aBoolean = filterMerchant(req.getMerchantCode(), item, marketingActivitieList);
                    if (aBoolean){
                        marketingActivitieList.add(item);
                    }
                } else {
                    //过滤区县
                    ActivityScope activityScopeByCity = activityScopeManager.queryActivityScopeByCityId(item.getId(), merchantDTO.getResultData().getCity());
                    if(activityScopeByCity != null){
                        //过滤活动对象，即买家，根据参与对象类型过滤活动
                        Boolean aBoolean = filterMerchant(req.getMerchantCode(), item, marketingActivitieList);
                        if (aBoolean){
                            marketingActivitieList.add(item);
                        }
                    }
                }

            }
        } else if (PromoConst.ActivityScopeType.ACTIVITY_SCOPE_TYPE_20.getCode().equals(activityScopeType)){
            //过滤供应商（卖家）编码
            ActivityScope activityScope = activityScopeManager.queryActivityScopeBySupplierCode(item.getId(), req.getSupplierCode());
            if(activityScope != null){
                //过滤活动对象，即买家，根据参与对象类型过滤活动
                Boolean aBoolean = filterMerchant(req.getMerchantCode(), item, marketingActivitieList);
                if (aBoolean){
                    marketingActivitieList.add(item);
                }
            }
        }
    }

    /**
     * 根据参与对象类型过滤活动
     * @param merchantCode
     * @param item
     * @param marketingActivitieList
     * @return
     */
    private Boolean filterMerchant(String merchantCode, MarketingActivity item, List<MarketingActivity> marketingActivitieList){
        if(StringUtils.isBlank(merchantCode)){
            //未登录
            return true;
        }
        //已登录
        String activityParticipantType = item.getActivityParticipantType();
        if(PromoConst.ActivityParticipantType.ACTIVITY_PARTICIPANT_TYPE_10.getCode().equals(activityParticipantType)){
            //过滤地市、区县
            ResultVO<MerchantDTO> merchantDTO = merchantService.getMerchantById(merchantCode);
            MerchantDTO merchant = merchantDTO.getResultData();
            if(merchant != null){
                ActivityParticipant activityParticipantsByLandId = activityParticipantManager.queryActivityParticipantByLandId(item.getId(), merchantDTO.getResultData().getLanId());
                if(activityParticipantsByLandId != null){
                    //过滤地市
                    marketingActivitieList.add(item);
                } else {
                    //过滤区县
                    ActivityParticipant activityParticipantsByCity = activityParticipantManager.queryActivityParticipantByCityId(item.getId(), merchantDTO.getResultData().getCity());
                    if(activityParticipantsByCity != null){
                        marketingActivitieList.add(item);
                    }
                }
            }
        } else if (PromoConst.ActivityParticipantType.ACTIVITY_PARTICIPANT_TYPE_20.getCode().equals(activityParticipantType)){
            //过滤商家（买家）编码
            ActivityParticipant activityParticipantList = activityParticipantManager.queryActivityParticipantByMerchantCode(item.getId(), merchantCode);
            if(activityParticipantList != null){
                marketingActivitieList.add(item);
            }
        }
        return false;

    }

    /**
     * 查询商品详情页面中的适用减免
     * @param req 商品参数
     * @return
     */
    @Override
    public ResultVO<List<MarketingReliefActivityQueryResp>> listGoodsMarketingReliefActivitys(MarketingActivityQueryByGoodsReq req) {
        log.info("MarketingActivityServiceImpl.listGoodsMarketingReliefActivitys req={}", JSON.toJSONString(req));
        List<MarketingReliefActivityQueryResp> marketingReliefActivityQueryRespList = Lists.newArrayList();
        ResultVO<List<MarketingGoodsActivityQueryResp>> listResultVO = this.listGoodsMarketingActivitys(req);
        List<MarketingGoodsActivityQueryResp> activityList = listResultVO.getResultData();
        if(!CollectionUtils.isEmpty(activityList)){
            List<String> marketingActivityIdList = Lists.newArrayList();
            activityList.forEach(item -> {
                marketingActivityIdList.add(item.getId());
            });
            //查询商品适用减免
            List<ActivityProduct> productList = activityProductManager.queryActivityProductByActIdAndProductId(marketingActivityIdList, req.getProductId());
            if(!CollectionUtils.isEmpty(productList)){
                productList.forEach(item -> {
                    if(null != item.getDiscountAmount()){
                        MarketingReliefActivityQueryResp marketingReliefActivityQueryResp = new MarketingReliefActivityQueryResp();
                        marketingReliefActivityQueryResp.setMarketingActivityId(item.getMarketingActivityId());
                        marketingReliefActivityQueryResp.setPromotionPrice(String.valueOf(item.getDiscountAmount()));
                        marketingReliefActivityQueryRespList.add(marketingReliefActivityQueryResp);
                    }
                });
            }
        }
        log.info("MarketingActivityServiceImpl.listGoodsMarketingReliefActivitys resp={}", JSON.toJSONString(marketingReliefActivityQueryRespList));
        return ResultVO.success(marketingReliefActivityQueryRespList);
    }

    /**
     * 查询商品详情页面中的适用优惠券
     * @param req 商品参数
     * @return
     */
    @Override
    public ResultVO<List<MarketingCouponActivityQueryResp>> listGoodsMarketingCouponActivitys(MarketingActivityQueryByGoodsReq req) {
        log.info("MarketingActivityServiceImpl.listGoodsMarketingCouponActivitys req={}", JSON.toJSONString(req));
        List<MarketingCouponActivityQueryResp> marketingCouponActivityQueryRespArrayList = Lists.newArrayList();
        ResultVO<List<MarketingGoodsActivityQueryResp>> listResultVO = this.listGoodsMarketingActivitys(req);
        List<MarketingGoodsActivityQueryResp> activityList = listResultVO.getResultData();
        if(!CollectionUtils.isEmpty(activityList)){
            List<String> marketingActivityIdList = Lists.newArrayList();
            activityList.forEach(item -> {
                marketingActivityIdList.add(item.getId());
            });
            //查询商品适用卡券
            List<Promotion> promotionList = promotionManager.getPromotion(marketingActivityIdList, PromoConst.PromotionType.PROMOTION_TYPE_CD_20.getCode());
            if(!CollectionUtils.isEmpty(promotionList)){
                List<String> mktIds = Lists.newArrayList();
                QueryCouponByProductAndActivityIdReq queryCouponByProductAndActivityIdReq = new QueryCouponByProductAndActivityIdReq();
                queryCouponByProductAndActivityIdReq.setProductId(req.getProductId());
                promotionList.forEach(item -> {
                    mktIds.add(item.getMktResId());
                });
                queryCouponByProductAndActivityIdReq.setMktResIds(mktIds);
                ResultVO<List<CouponApplyObjectRespDTO>> resultVO = couponApplyObjectService.queryCouponApplyObjectByCondition(queryCouponByProductAndActivityIdReq);
                List<CouponApplyObjectRespDTO> couponApplyObjectRespDTOS = resultVO.getResultData();
                if(!CollectionUtils.isEmpty(couponApplyObjectRespDTOS)){
                    couponApplyObjectRespDTOS.forEach(item -> {
                        QueryCouponByIdReq queryCouponByIdReq = new QueryCouponByIdReq();
                        queryCouponByIdReq.setMktResId(item.getMktResId());
                        CouponRuleAndTypeQueryResp ruleAndTypeRespDTO = mktResCouponService.queryCouponRuleAndTypeById(queryCouponByIdReq);
                        if(ruleAndTypeRespDTO != null){
                            Promotion promotoObj = promotionManager.getPromotoObj(item.getMktResId(), PromoConst.PromotionType.PROMOTION_TYPE_CD_20.getCode());
                            MarketingCouponActivityQueryResp mcResp = new MarketingCouponActivityQueryResp();
                            mcResp.setMktResId(item.getMktResId());
                            mcResp.setMktResName(promotoObj.getMktResName());
                            mcResp.setDiscountValue(ruleAndTypeRespDTO.getDiscountValue());
                            mcResp.setRuleAmount(ruleAndTypeRespDTO.getRuleAmount());
                            mcResp.setMktResTypeName(ruleAndTypeRespDTO.getMktResTypeName());
                            mcResp.setPromotionEffectTime(promotoObj.getPromotionEffectTime());
                            mcResp.setPromotionOverdueTime(promotoObj.getPromotionOverdueTime());
                            marketingCouponActivityQueryRespArrayList.add(mcResp);
                        }
                    });
                }
            }
        }
        log.info("MarketingActivityServiceImpl.listGoodsMarketingCouponActivitys req={}", JSON.toJSONString(marketingCouponActivityQueryRespArrayList));
        return ResultVO.success(marketingCouponActivityQueryRespArrayList);
    }

    /**
     * 营销活动更新
     * @param req
     * @return
     */
    @Override
    public ResultVO<Boolean> updateMarketingActivity(MarketingActivityAddReq req) {
        log.info("MarketingActivityServiceImpl.updateMarketingActivity req={}", JSON.toJSONString(req));
        MarketingActivity marketingActivity=new MarketingActivity();
        BeanUtils.copyProperties(req, marketingActivity);
        String marketingActivityId = marketingActivity.getId();
        if (StringUtils.isNotEmpty(marketingActivityId)){
            ResultVO<MarketingActivityAddResp> response = addMarketingActivity(req);
            log.info("MarketingActivityServiceImpl.updateMarketingActivity resp={}", JSON.toJSONString(response));
            if(response.isSuccess() && null != response.getResultData()){

                return ResultVO.successMessage(constant.getUpdateSuccess());
            }else{
                return ResultVO.error(constant.getUpdateFaile());
            }
        } else {
            return ResultVO.error(constant.getNoMarketingActivityId());
        }
    }

    /**
     * 营销活动审核
     *
     * @param userId,invoiceId
     * @return
     */
    @Override
    public ResultVO marketingActivityAuitStartProcess(String mktName,String userId,String userName,String orgName,String sysPostName,String marketId) {
        log.info("MarketingActivityServiceImpl.marketingActivityAuit userId={} userName{}" +
                        "lanId{} regionId{} marketId{}",
                userId,userName,orgName,sysPostName,marketId);
                ProcessStartReq processStartDTO = new ProcessStartReq();
                processStartDTO.setTitle(mktName);
                //创建流程者，参数需要提供
                processStartDTO.setApplyUserId(userId);
                processStartDTO.setApplyUserName(userName);
                processStartDTO.setExtends1(orgName+sysPostName);
                processStartDTO.setProcessId("4");
                processStartDTO.setFormId(marketId);
                //TASK_SUB_TYPE_1140 销售活动配置流程
                processStartDTO.setTaskSubType(WorkFlowConst.TASK_SUB_TYPE.TASK_SUB_TYPE_1140.getTaskSubType());
                ResultVO taskServiceRV = new ResultVO();
                try{
                    //开启流程
                    taskServiceRV = taskService.startProcess(processStartDTO);
                    return ResultVO.success();
                }catch (Exception ex){
                    return ResultVO.error();
                }finally {
                    log.info("MarketingActivityServiceImpl.marketingActivityAuit req={},resp={}",
                            JSON.toJSONString(processStartDTO), JSON.toJSONString(taskServiceRV));
                }
    }


    /**
     * 根据优惠ID集合查询 营销活动集合
     * @param mktResIds 优惠ID集合
     * @return
     */
    @Override
    public ResultVO<List<PromotionWithMarketingActivityDTO>> getMarketingActivitiesByMktResIds(List<String> mktResIds) {
        List<PromotionWithMarketingActivityDTO> respList = Lists.newArrayList();

        List<Promotion> promotionList = promotionManager.getPromotionsByMktResIds(mktResIds);
        List<String> activityIdList = Lists.newArrayList();
        if (!CollectionUtils.isEmpty(promotionList)) {
            // 获取活动ID集合
            promotionList.forEach(promotion -> {
                PromotionWithMarketingActivityDTO dto = new PromotionWithMarketingActivityDTO();
                BeanUtils.copyProperties(promotion, dto);
                respList.add(dto);
                activityIdList.add(promotion.getMarketingActivityId());
            });
        }
        //  获取活动信息集合
        List<MarketingActivity> activities = marketingActivityManager.listMarketingActivitiesByIds(activityIdList);
        if (!CollectionUtils.isEmpty(activities)) {
            activities.forEach(activity -> {
                respList.forEach(dto -> {
                    // 回塞活动信息
                    if (StringUtils.equals(activity.getId(), dto.getMarketingActivityId())) {
                        // 不能用BeanUtils.copyProperties（） 两个对象里面都有个 ID字段
                        dto.setCode(activity.getCode());
                        dto.setName(activity.getName());
                        dto.setBrief(activity.getBrief());
                        dto.setActivityType(activity.getActivityType());
                        dto.setActivityTypeName(activity.getActivityTypeName());
                    }
                });
            });
        }
        return ResultVO.success(respList);
    }

    /**
     * 根据营销活动ID查询营销活动、优惠券、参与产品详情
     * @param activityId
     * @return
     */
    @Override
    public ResultVO<MarketingActivityInfoResp> queryMarketingActivityInfo(String activityId){
        MarketingActivityInfoResp marketingActivityInfoResp = new MarketingActivityInfoResp();

        //营销活动基本信息
        MarketingActivityDetailResp marketingActivityDetailResp = queryMarketingActivity(activityId).getResultData();
        marketingActivityInfoResp.setMarketingActivityDetailResp(marketingActivityDetailResp);
        QueryPreSubsidyReqDTO queryPreSubsidyReqDTO = new QueryPreSubsidyReqDTO();
        queryPreSubsidyReqDTO.setMarketingActivityId(activityId);
        if(PromoConst.ACTIVITYTYPE.PRESUBSIDY.getCode().equals(marketingActivityDetailResp.getActivityType())) {
            //营销活动优惠券列表信息
            List<QueryPreSubsidyCouponResqDTO> queryPreSubsidyCouponResqDTOList = new ArrayList<>();
            List couponList = preSubsidyCouponService.queryPreSubsidyCoupon(queryPreSubsidyReqDTO).getResultData();
            if (couponList != null && couponList.size() > 0) {
                for (int i = 0; i < couponList.size(); i++) {
                    QueryPreSubsidyCouponResqDTO queryPreSubsidyCouponResqDTO = new QueryPreSubsidyCouponResqDTO();
                    BeanUtils.copyProperties(couponList.get(i), queryPreSubsidyCouponResqDTO);
                    queryPreSubsidyCouponResqDTOList.add(queryPreSubsidyCouponResqDTO);
                }
            }
            marketingActivityInfoResp.setQueryPreSubsidyCouponResqDTOList(queryPreSubsidyCouponResqDTOList);

            //营销活动产品列表信息
            List<PreSubsidyProductPromResqDTO> preSubsidyProductPromResqDTOS = new ArrayList<>();
            List<com.iwhalecloud.retail.rights.dto.response.PreSubsidyProductPromResqDTO> productList = 
                    preSubsidyCouponService.queryPreSubsidyProduct(queryPreSubsidyReqDTO).getResultData();
            if (productList != null && productList.size() > 0) {
                for (int i = 0; i < productList.size(); i++) {
                    PreSubsidyProductPromResqDTO preSubsidyProductPromResqDTO = new PreSubsidyProductPromResqDTO();
                    BeanUtils.copyProperties(productList.get(i), preSubsidyProductPromResqDTO);
                    preSubsidyProductPromResqDTO.setNum(productList.get(i).getActivityProductResq().getNum());
                    preSubsidyProductPromResqDTO.setDiscountAmount(productList.get(i).getActivityProductResq().getDiscountAmount());
                    preSubsidyProductPromResqDTOS.add(preSubsidyProductPromResqDTO);
                }
            }
            marketingActivityInfoResp.setQueryPreSubsidyProductPromResqDTOList(preSubsidyProductPromResqDTOS);
        }else if(PromoConst.ACTIVITYTYPE.BOOKING.getCode().equals(marketingActivityDetailResp.getActivityType())){
            QueryMarketingActivityReq queryMarketingActivityReq =new QueryMarketingActivityReq();
            queryMarketingActivityReq.setMarketingActivityId(activityId);
            ResultVO<List<PreSubsidyProductRespDTO>> listResultVO = activityProductService.queryPreSaleProduct(queryMarketingActivityReq);
            marketingActivityInfoResp.setPreSaleProductInfo(listResultVO.getResultData());
        }
        return ResultVO.success(marketingActivityInfoResp);
    }

    @Override
    public ResultVO<MarketingActivityDTO> queryMarketingActivityById(QueryMarketingActivityReq queryMarketingActivityReq) {
        MarketingActivityDTO marketingActivityDTO = new MarketingActivityDTO();
        MarketingActivity marketingActivity = marketingActivityManager.queryMarketingActivity(queryMarketingActivityReq.getMarketingActivityId());
        BeanUtils.copyProperties(marketingActivity, marketingActivityDTO);
        return ResultVO.success(marketingActivityDTO);
    }

    @Override
    public ResultVO updatePreSaleActivityRule(MarketingActivityAddReq marketingActivityAddReq) {
        MarketingActivity marketingActivity = new MarketingActivity();
        BeanUtils.copyProperties(marketingActivityAddReq,marketingActivity);
        marketingActivity.setPayType(StringUtils.isEmpty(marketingActivityAddReq.getPayType())? PromoConst.PayType.PAY_TYPE_1.getCode() : marketingActivityAddReq.getPayType());
        marketingActivityManager.updatePreSaleActivityRule(marketingActivity);
        return ResultVO.success();
    }

    @Override
    public ResultVO<AdvanceActivityProductInfoResp> getAdvanceActivityProductInfo(AdvanceActivityProductInfoReq advanceActivityProductInfoReq) {
        log.info("MarketingActivityServiceImpl.getAdvanceActivityProductInfo-->advanceActivityProductInfoReq={}",JSON.toJSONString(advanceActivityProductInfoReq));
        if (StringUtils.isEmpty(advanceActivityProductInfoReq.getProductId())
                || StringUtils.isEmpty(advanceActivityProductInfoReq.getMarketingActivityId())) {
            log.error("MarketingActivityServiceImpl.getAdvanceActivityProductInfo--> !!miss args!!,productId={},activityId={}"
                    ,advanceActivityProductInfoReq.getProductId(),advanceActivityProductInfoReq.getMarketingActivityId());
            return ResultVO.error("产品ID和活动ID为必填项");
        }
        AdvanceActivityProductInfoResp advanceActivityProductInfo = marketingActivityManager.getAdvanceActivityProductInfo(advanceActivityProductInfoReq);
        log.info("MarketingActivityServiceImpl.getAdvanceActivityProductInfo-->advanceActivityProductInfo={}", JSON.toJSONString(advanceActivityProductInfo));

        return ResultVO.success(advanceActivityProductInfo);
    }

    @Override
    public ResultVO<List<MarketingActivityQueryBySupplierResp>> queryMarketingActivityQueryBySupplier(MarketingActivityQueryBySupplierReq req) {

        log.info("MarketingActivityServiceImpl.queryMarketingActivityQueryBySupplier-->req={}" + JSON.toJSONString(req));
        List<MarketingActivityQueryBySupplierResp> activityQueryBySupplierResps = new ArrayList<>();

        //1、查询出当前有效的活动
        List<MarketingActivity> marketingActivities = marketingActivityManager.queryInvlidMarketingActivity(req.getActivityType());

        if (CollectionUtils.isEmpty(marketingActivities)) {
            log.info("MarketingActivityServiceImpl.queryMarketingActivityQueryBySupplier-->invalid activties is empty");
            return ResultVO.success(activityQueryBySupplierResps);
        }
        log.info("MarketingActivityServiceImpl.queryMarketingActivityQueryBySupplier-->invalid activties size={}",marketingActivities.size());

        //2、过滤出当前卖家能参与的活动
        marketingActivities.forEach(marketingActivitity -> {
            ActivityAuthModel activityAuthModel = getActivityAuthModel(req.getSupplierId(), marketingActivitity);
            boolean hasPermission = sellerActivityFilter.doFilter(activityAuthModel);
            if (hasPermission) {
                MarketingActivityQueryBySupplierResp resp = new MarketingActivityQueryBySupplierResp();
                BeanUtils.copyProperties(marketingActivitity,resp);
                activityQueryBySupplierResps.add(resp);
            }
        });

        return ResultVO.success(activityQueryBySupplierResps);
    }

    /**
     * 获取营销活动校验模型
     * @param merchantId
     * @param marketingActivitity
     * @return
     */
    private ActivityAuthModel getActivityAuthModel(String merchantId, MarketingActivity marketingActivitity) {
        ActivityAuthModel activityAuthModel = new ActivityAuthModel();
        activityAuthModel.setMarketingActivity(marketingActivitity);
        ResultVO<MerchantDTO> merchantDTOResultVO = merchantService.getMerchantById(merchantId);
        if (merchantDTOResultVO.isSuccess()) {

            log.info("MarketingActivityServiceImpl.getActivityAuthModel get merchantDTOResultVO,merchantDTOResultVO={}-->"
                    ,JSON.toJSONString(merchantDTOResultVO));

            //设置商家信息
            activityAuthModel.setMerchantSeller(merchantDTOResultVO.getResultData());
            return activityAuthModel;
        } else {
            //无商家信息了
            log.error("MarketingActivityServiceImpl.getActivityAuthModel get merchantDTOResultVO !!error!!,merchantDTOResultVO={}-->"
                    ,JSON.toJSONString(merchantDTOResultVO));
        }

        return activityAuthModel;
    }

    @Override
    public ResultVO<List<MarketingActivityListResp>> queryInvalidActivity(MarketingActivityListReq marketingActivityListReq) {
        return ResultVO.success(marketingActivityManager.queryInvalidActivity(marketingActivityListReq));
    }

    /**
     * 根据活动id查询所有商家id
     * @param marketingActivityId
     * @return
     */
    public ResultVO<List<String>> queryActMerchant(String marketingActivityId) {
        log.info("MarketingActivityServiceImpl.queryActMerchant--> marketingActivityId={}",marketingActivityId);
        List<String> strings = Lists.newArrayList();
        MarketingActivity marketingActivity = marketingActivityManager.queryMarketingActivity(marketingActivityId);
        if(marketingActivity == null) {
            return ResultVO.error("活动不存在");
        }
        List<ActivityParticipant> activityParticipants = activityParticipantManager.queryActivityParticipantByCondition(marketingActivityId);
        log.info("MarketingActivityServiceImpl.queryActMerchant queryActivityParticipantByCondition --> activityParticipants={}",JSON.toJSON(activityParticipants));
        if(activityParticipants.size()<=0){
            return ResultVO.success(strings);
        }
        String activityParticipantType = marketingActivity.getActivityParticipantType();
        if(PromoConst.ActivityParticipantType.ACTIVITY_PARTICIPANT_TYPE_20.getCode().equals(activityParticipantType)){
            for(ActivityParticipant activityParticipant : activityParticipants){
                /** 参与对象表当前merchant_code字段存的是merchant_id*/
                if(StringUtils.isNotEmpty(activityParticipant.getMerchantCode())){
                    strings.add(activityParticipant.getMerchantCode());
                }
            }
        }else if(PromoConst.ActivityParticipantType.ACTIVITY_PARTICIPANT_TYPE_10.getCode().equals(activityParticipantType)){
              List<String> participants = Lists.newArrayList();
              for(ActivityParticipant activityParticipant : activityParticipants){
                  String city = activityParticipant.getCity();
                  String lanId = activityParticipant.getLanId();
                  if(StringUtils.isNotEmpty(city) || StringUtils.isNotEmpty(lanId)){
                      participants.add(StringUtils.isNotEmpty(city)? city : lanId);
                  }
              }
              if(participants.size()>0){
                  MerchantListReq merchantListReq = new MerchantListReq();
                  merchantListReq.setCityList(participants);
                  ResultVO<List<MerchantDTO>> listResultVO = merchantService.listMerchantByLanCity(merchantListReq);
                  log.info("MarketingActivityServiceImpl.queryActMerchant listMerchantByLanCity --> listResultVO={}",JSON.toJSON(listResultVO));
                  for(MerchantDTO merchantDTO :listResultVO.getResultData()){
                      strings.add(merchantDTO.getMerchantId());
                  }
              }
        }
        log.info("MarketingActivityServiceImpl.queryActMerchant --> strings={}",JSON.toJSON(strings));
        return ResultVO.success(strings);
    }

    @Override
    public ResultVO autoPushActivityCoupon(String marketingActivityId) {
        ResultVO<List<String>> listResultVO = queryActMerchant(marketingActivityId);
        if(ResultCodeEnum.ERROR.getCode().equals(listResultVO.getResultCode())){
            return listResultVO;
        }
        if(listResultVO.getResultData().size()<=0){
            return ResultVO.error("该活动没有参与的商家");
        }
        AutoPushCouponReq autoPushCouponReq = new AutoPushCouponReq();
        autoPushCouponReq.setMarketingActivityId(marketingActivityId);
        autoPushCouponReq.setMerchantIds(listResultVO.getResultData());
        couponInstService.autoPushCoupon(autoPushCouponReq);
        return ResultVO.success("成功推送!");
    }

    public ResultVO turnActMarket(String userId,String userName,String mktId){
        updateMktStatus(mktId,PromoConst.STATUSCD.STATUS_CD_10.getCode());
        NextRouteAndReceiveTaskReq nextRouteAndReceiveTaskReq = new NextRouteAndReceiveTaskReq();
        nextRouteAndReceiveTaskReq.setFormId(mktId);
        nextRouteAndReceiveTaskReq.setHandlerUserId(userId);
        nextRouteAndReceiveTaskReq.setHandlerMsg("营销活动驳回审核");

        return taskService.nextRouteAndReceiveTask(nextRouteAndReceiveTaskReq);
    }
    public Boolean updateMktStatus(String mktId,String vatInvoiceStatus){
        return marketingActivityManager.updateMarketingActivityById(mktId,vatInvoiceStatus);
    }

    @Override
    public ResultVO<List<MarketingActivityDTO>> queryActivityByProductId(String productId,String activityType){
        List<MarketingActivity> marketingActivityList = marketingActivityManager.queryActivityByProductId(productId,activityType);
        List<MarketingActivityDTO> marketingActivityDTOList = Lists.newArrayList();
        for (MarketingActivity marketingActivity : marketingActivityList) {
            MarketingActivityDTO marketingActivityDTO = new MarketingActivityDTO();
            BeanUtils.copyProperties(marketingActivity, marketingActivityDTO);
            marketingActivityDTOList.add(marketingActivityDTO);
        }
        return ResultVO.success(marketingActivityDTOList);
    }

    @Override
    public void auitMarketingActivity(AuitMarketingActivityReq req) {
        //修改状态
        updateMktStatus(req.getId(),PromoConst.STATUSCD.STATUS_CD_10.getCode());
        //营销活动审核 1.新增的时候进行审核
        log.info("MarketingActivityServiceImpl.addMarketingActivity marketingActivityId{}");
        if (org.springframework.util.StringUtils.isEmpty(req.getId())){
            try{
                ResultVO auditMktResultVO = marketingActivityAuitStartProcess(req.getName(),req.getUserId(),req.getUserName(),req.getOrgId(),req.getSysPostName(), req.getId());
            }catch (Exception e){
                log.info("MarketingActivityServiceImpl.addMarketingActivity 新增营销活动审核失败");
                e.printStackTrace();
            }
        }else {
            ResultVO<MarketingActivityDetailResp> respResultVO = queryMarketingActivity(req.getId());
            MarketingActivityDetailResp marketingActivityDetailResp = respResultVO.getResultData();
            //修改的时候 如果是审核通过或者是审核中 不起流程
            if(PromoConst.STATUSCD.STATUS_CD_20.getCode().equals(marketingActivityDetailResp.getStatus())
                    || PromoConst.STATUSCD.STATUS_CD_10.getCode().equals(marketingActivityDetailResp.getStatus())){
                try{
                    ResultVO auditMktResultVO = marketingActivityAuitStartProcess(req.getName(),req.getUserId(),req.getUserName(),req.getOrgId(),req.getSysPostName(), req.getId());
                }catch (Exception e){
                    log.info("MarketingActivityServiceImpl.addMarketingActivity 修改营销活动通过审核失败");
                    e.printStackTrace();
                }
            }else {
                try{
                    ResultVO workFlowResultVO = turnActMarket(req.getUserId(),req.getUserName(), req.getId());
                }catch (Exception e){
                    log.info("MarketingActivityServiceImpl.addMarketingActivity 修改营销活动驳回审核失败");
                    e.printStackTrace();
                }
            }
        }
    }
}