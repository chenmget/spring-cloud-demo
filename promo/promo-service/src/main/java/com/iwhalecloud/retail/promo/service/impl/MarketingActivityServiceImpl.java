package com.iwhalecloud.retail.promo.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.goods2b.dto.MerchantTagRelDTO;
import com.iwhalecloud.retail.goods2b.dto.req.MerchantTagRelListReq;
import com.iwhalecloud.retail.goods2b.dto.req.ProductGetByIdReq;
import com.iwhalecloud.retail.goods2b.dto.resp.ProductResp;
import com.iwhalecloud.retail.goods2b.service.dubbo.MerchantTagRelService;
import com.iwhalecloud.retail.goods2b.service.dubbo.ProductService;
import com.iwhalecloud.retail.order2b.dto.model.order.OrderDTO;
import com.iwhalecloud.retail.order2b.service.OrderSelectOpenService;
import com.iwhalecloud.retail.partner.dto.MerchantDTO;
import com.iwhalecloud.retail.partner.dto.req.MerchantListLanCityReq;
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
import com.iwhalecloud.retail.rights.service.*;
import com.iwhalecloud.retail.system.common.SysUserMessageConst;
import com.iwhalecloud.retail.system.dto.CommonRegionDTO;
import com.iwhalecloud.retail.system.dto.ConfigInfoDTO;
import com.iwhalecloud.retail.system.dto.SysUserMessageDTO;
import com.iwhalecloud.retail.system.dto.UserDTO;
import com.iwhalecloud.retail.system.dto.request.CommonRegionListReq;
import com.iwhalecloud.retail.system.dto.request.UserGetReq;
import com.iwhalecloud.retail.system.service.CommonRegionService;
import com.iwhalecloud.retail.system.service.ConfigInfoService;
import com.iwhalecloud.retail.system.service.SysUserMessageService;
import com.iwhalecloud.retail.system.service.UserService;
import com.iwhalecloud.retail.workflow.common.ResultCodeEnum;
import com.iwhalecloud.retail.workflow.common.WorkFlowConst;
import com.iwhalecloud.retail.workflow.dto.TaskDTO;
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
import java.io.Serializable;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Slf4j
@Component("marketingActivityService")
@Service
public class MarketingActivityServiceImpl implements MarketingActivityService {
    @Autowired
    private MarketingActivityManager marketingActivityManager;

    @Autowired
    private ActivityChangeManager activityChangeManager;

    @Autowired
    private ActivityChangeDetailManager activityChangeDetailManager;

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
    private HistoryPurchaseManager historyPurchaseManager;

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

    @Reference
    private OrderSelectOpenService orderSelectOpenService;

    @Reference
    private SysUserMessageService sysUserMessageService;

    @Reference
    private ConfigInfoService configInfoService;

    @Reference
    private UserService userService;

    @Autowired
    private Constant constant;

    @Reference
    private MktResCouponTaskService mktResCouponTaskService;

    @Reference
    private ProductService productService;
    @Reference
    private MerchantTagRelService merchantTagRelService;

    @Autowired
    private AccountBalanceTypeManager accountBalanceTypeManager;

    @Autowired
    private AccountBalanceRuleManager accountBalanceRuleManager;
    private List<MarketingReliefActivityQueryResp> marketingReliefActivityQueryRespList;


    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultVO<MarketingActivityAddResp> addMarketingActivity(MarketingActivityAddReq req) {
        log.info("MarketingActivityServiceImpl.addMarketingActivity req={}", JSON.toJSONString(req));
        MarketingActivity marketingActivity = new MarketingActivity();
        BeanUtils.copyProperties(req, marketingActivity);
        // 添加营销活动
        marketingActivityManager.addMarketingActivity(marketingActivity);
        String marketingActivityId = marketingActivity.getId();
        String marketingActivityCode = marketingActivity.getCode();
        MarketingActivityModify marketingActivityModify = new MarketingActivityModify();
        BeanUtils.copyProperties(req, marketingActivityModify);
        marketingActivityModify.setMarketingActivityId(marketingActivityId);
        marketingActivityManager.addMarketingActivityModify(req.getId(), marketingActivityModify);
        //营销活动变更内容Id
        String marketingActivityModifyId = marketingActivityModify.getId();
        List<ActivityScopeDTO> activityScopeDTOList = req.getActivityScopeList();
        List<ActivityScope> activityScopeList = Lists.newArrayList();
        activityScopeManager.deleteActivityScopeBatch(marketingActivityId);
        if (!CollectionUtils.isEmpty(activityScopeDTOList)) {
            for (ActivityScopeDTO activityScopeDTO : activityScopeDTOList) {
                ActivityScope activityScope = new ActivityScope();
                BeanUtils.copyProperties(activityScopeDTO, activityScope);
                activityScope.setMarketingActivityId(marketingActivityId);
                Date dt = new Date();
                activityScope.setGmtCreate(dt);
                activityScope.setGmtModified(dt);
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
                Date dt = new Date();
                activityParticipant.setGmtCreate(dt);
                activityParticipant.setGmtModified(dt);
                activityParticipant.setIsDeleted(PromoConst.IsDelete.IS_DELETE_CD_0.getCode());
                activityParticipantList.add(activityParticipant);
            }
            // 添加活动参与人员
            activityParticipantManager.addActivityParticipantBatch(activityParticipantList);
        }
        //返利活动
        if (PromoConst.ACTIVITYTYPE.REBATE.getCode().equals(req.getActivityType())) {

            //添加账户余额类型
            AccountBalanceType balanceType = new AccountBalanceType();
            balanceType.setBalanceTypeName(req.getName());
            balanceType.setActId(marketingActivityId);
            balanceType.setCreateStaff(req.getCreator());
            balanceType.setUpdateStaff(req.getModifier());
            balanceType.setRemark(req.getDescription());
            accountBalanceTypeManager.addAccountBalanceType(balanceType);
            String balanceTypeId = balanceType.getBalanceTypeId();
            log.info("MarketingActivityServiceImpl.addMarketingActivity balanceTypeId={}", JSON.toJSONString(balanceTypeId));
            //添加账户余额使用规则
            AccountBalanceRule balanceRule = new AccountBalanceRule();
            balanceRule.setBalanceTypeId(balanceTypeId);
            balanceRule.setObjId(req.getInitiator());
            balanceRule.setCreateStaff(req.getCreator());
            balanceRule.setUpdateStaff(req.getModifier());
            accountBalanceRuleManager.addAccountBalanceRule(balanceRule);
        }
        MarketingActivityAddResp marketingActivityAddResp = new MarketingActivityAddResp();
        marketingActivityAddResp.setId(marketingActivityId);
        marketingActivityAddResp.setMarketingActivityModifyId(marketingActivityModifyId);
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
        List<ActivityScopeDTO> activityScopeDTOList = activityScopeManager.queryActivityScopeByMktIdAndStatus(marketingActivityId, PromoConst.Status.Audited.getCode());
        log.info("MarketingActivityServiceImpl.queryMarketingActivity activityScopeList=" + activityScopeDTOList);

        if (!CollectionUtils.isEmpty(activityScopeDTOList)) {
            for (ActivityScopeDTO activityScopeDTO : activityScopeDTOList
            ) {
                List<String> list = new ArrayList<>();
                CommonRegionListReq commonRegionListReq = new CommonRegionListReq();
                list.add(activityScopeDTO.getLanId());
                commonRegionListReq.setRegionIdList(list);
                ResultVO<List<CommonRegionDTO>> lancommonRegionDTOList = commonRegionService.listCommonRegion(commonRegionListReq);
                if (!CollectionUtils.isEmpty(lancommonRegionDTOList.getResultData())) {
                    for (CommonRegionDTO commonRegionDTO : lancommonRegionDTOList.getResultData()
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
                ResultVO<List<CommonRegionDTO>> citycommonRegionDTOList = commonRegionService.listCommonRegion(commonRegionListReq);
                if (!CollectionUtils.isEmpty(citycommonRegionDTOList.getResultData())) {
                    for (CommonRegionDTO commonRegionDTO : citycommonRegionDTOList.getResultData()
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
        List<ActivityParticipantDTO> activityParticipantDTOList = activityParticipantManager.queryActivityParticipantByMktIdAndStatus(marketingActivityId, PromoConst.Status.Audited.getCode());
        log.info("MarketingActivityServiceImpl.queryMarketingActivity activityParticipantDTOList=" +
                activityParticipantDTOList);
        if (!CollectionUtils.isEmpty(activityParticipantDTOList)) {
            for (ActivityParticipantDTO activityParticipantDTO : activityParticipantDTOList
            ) {
                List<String> list = new ArrayList<>();
                CommonRegionListReq commonRegionListReq = new CommonRegionListReq();
                list.add(activityParticipantDTO.getLanId());
                commonRegionListReq.setRegionIdList(list);
                ResultVO<List<CommonRegionDTO>> lancommonRegionDTOList = commonRegionService.listCommonRegion(commonRegionListReq);
                if (!CollectionUtils.isEmpty(lancommonRegionDTOList.getResultData())) {
                    for (CommonRegionDTO commonRegionDTO : lancommonRegionDTOList.getResultData()
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
                ResultVO<List<CommonRegionDTO>> citycommonRegionDTOList = commonRegionService.listCommonRegion(commonRegionListReq);
                if (!CollectionUtils.isEmpty(citycommonRegionDTOList.getResultData())) {
                    for (CommonRegionDTO commonRegionDTO : citycommonRegionDTOList.getResultData()) {
                        activityParticipantDTO.setCityName(commonRegionDTO.getRegionName());
                        activityParticipantDTO.setKey(commonRegionDTO.getRegionId());
                        activityParticipantDTO.setTitle(commonRegionDTO.getRegionName());
                        ;
                        activityParticipantDTO.setRegionId(commonRegionDTO.getRegionId());
                    }
                }
                log.info("MarketingActivityServiceImpl.queryMarketingActivity citycommonRegionDTOList=" +
                        citycommonRegionDTOList.getResultData());
            }
            List<ActivityParticipantDTO> activityParticipantDTOLists = ReflectUtils.batchAssign(activityParticipantDTOList, ActivityParticipantDTO.class);
            resp.setActivityParticipantList(activityParticipantDTOLists);
            log.info("MarketingActivityServiceImpl.queryMarketingActivity resp.getActivityParticipantList=" +
                    resp.getActivityParticipantList());
        }
        // 根据活动编码查询参与活动产品
        List<ActivityProduct> activityGoodsList = activityProductManager.queryActivityProductByCondition(marketingActivityId);
        if (!CollectionUtils.isEmpty(activityGoodsList)) {
            List<ActivityProductDTO> activityProductDTOList = ReflectUtils.batchAssign(activityGoodsList, ActivityProductDTO.class);
            if (!CollectionUtils.isEmpty(activityProductDTOList)) {
                for (int i = 0; i < activityProductDTOList.size(); i++) {
                    ProductGetByIdReq productGetByIdReq = new ProductGetByIdReq();
                    productGetByIdReq.setProductId(activityProductDTOList.get(i).getProductId());
                    ResultVO<ProductResp> respResultVO = productService.getProduct(productGetByIdReq);
                    if (null != respResultVO.getResultData()) {
                        activityProductDTOList.get(i).setUnitName(respResultVO.getResultData().getUnitName());
                        activityProductDTOList.get(i).setSn(respResultVO.getResultData().getSn());
                    }
                }
            }
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
    public ResultVO<MarketingActivityDetailResp> queryMarketingActivityFor(String id) {
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
            for (ActivityScopeDTO activityScopeDTO : activityScopeDTOList
            ) {
                List<String> list = new ArrayList<>();
                CommonRegionListReq commonRegionListReq = new CommonRegionListReq();
                list.add(activityScopeDTO.getLanId());
                commonRegionListReq.setRegionIdList(list);
                ResultVO<List<CommonRegionDTO>> lancommonRegionDTOList = commonRegionService.listCommonRegion(commonRegionListReq);
                if (!CollectionUtils.isEmpty(lancommonRegionDTOList.getResultData())) {
                    for (CommonRegionDTO commonRegionDTO : lancommonRegionDTOList.getResultData()
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
                ResultVO<List<CommonRegionDTO>> citycommonRegionDTOList = commonRegionService.listCommonRegion(commonRegionListReq);
                if (!CollectionUtils.isEmpty(citycommonRegionDTOList.getResultData())) {
                    for (CommonRegionDTO commonRegionDTO : citycommonRegionDTOList.getResultData()
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
                List<String> list = new ArrayList<>();
                CommonRegionListReq commonRegionListReq = new CommonRegionListReq();
                list.add(activityParticipantDTO.getLanId());
                commonRegionListReq.setRegionIdList(list);
                ResultVO<List<CommonRegionDTO>> lancommonRegionDTOList = commonRegionService.listCommonRegion(commonRegionListReq);
                if (!CollectionUtils.isEmpty(lancommonRegionDTOList.getResultData())) {
                    for (CommonRegionDTO commonRegionDTO : lancommonRegionDTOList.getResultData()
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
                ResultVO<List<CommonRegionDTO>> citycommonRegionDTOList = commonRegionService.listCommonRegion(commonRegionListReq);
                if (!CollectionUtils.isEmpty(citycommonRegionDTOList.getResultData())) {
                    for (CommonRegionDTO commonRegionDTO : citycommonRegionDTOList.getResultData()) {
                        activityParticipantDTO.setCityName(commonRegionDTO.getRegionName());
                        activityParticipantDTO.setKey(commonRegionDTO.getRegionId());
                        activityParticipantDTO.setTitle(commonRegionDTO.getRegionName());
                        ;
                        activityParticipantDTO.setRegionId(commonRegionDTO.getRegionId());
                    }
                }
                log.info("MarketingActivityServiceImpl.queryMarketingActivity citycommonRegionDTOList=" +
                        citycommonRegionDTOList.getResultData());
            }
            List<ActivityParticipantDTO> activityParticipantDTOLists = ReflectUtils.batchAssign(activityParticipantDTOList, ActivityParticipantDTO.class);
            resp.setActivityParticipantList(activityParticipantDTOLists);
            log.info("MarketingActivityServiceImpl.queryMarketingActivity resp.getActivityParticipantList=" +
                    resp.getActivityParticipantList());
        }
        // 根据活动编码查询参与活动产品
        List<ActivityProduct> activityGoodsList = activityProductManager.queryActivityProductByCondition(marketingActivityId);
        if (!CollectionUtils.isEmpty(activityGoodsList)) {
            List<ActivityProductDTO> activityProductDTOList = ReflectUtils.batchAssign(activityGoodsList, ActivityProductDTO.class);
            if (!CollectionUtils.isEmpty(activityProductDTOList)) {
                for (int i = 0; i < activityProductDTOList.size(); i++) {
                    ProductGetByIdReq productGetByIdReq = new ProductGetByIdReq();
                    productGetByIdReq.setProductId(activityProductDTOList.get(i).getProductId());
                    ResultVO<ProductResp> respResultVO = productService.getProductInfo(productGetByIdReq);
                    if (null != respResultVO.getResultData()) {
                        activityProductDTOList.get(i).setUnitName(respResultVO.getResultData().getUnitName());
                        activityProductDTOList.get(i).setSn(respResultVO.getResultData().getSn());
                        activityProductDTOList.get(i).setTypeName(respResultVO.getResultData().getTypeName());//lws
                        activityProductDTOList.get(i).setProductName(respResultVO.getResultData().getProductName());//lws
                        activityProductDTOList.get(i).setColor(respResultVO.getResultData().getColor());//lws
                        activityProductDTOList.get(i).setMemory(respResultVO.getResultData().getMemory());//lws
                        activityProductDTOList.get(i).setUnitTypeName(respResultVO.getResultData().getUnitTypeName());//lws
                        activityProductDTOList.get(i).setBrandName(respResultVO.getResultData().getBrandName());//lws
                        activityProductDTOList.get(i).setUnitTypeName(respResultVO.getResultData().getUnitTypeName());//lws
                    }
                }
            }
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
    public ResultVO<List<MarketingActivityResp>> getMarketingCampaign(MarketingActivityReq req) {
        log.info("MarketingActivityServiceImpl.getMarketingCampaign req={}", JSON.toJSONString(req));
        return ResultVO.success(marketingActivityManager.getMarketingCampaign(req));
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
     * 查询B2B产品适用活动
     * 逻辑：先检查商家是否有可以参加的活动 ，可以参加  就返回 活动产品 关联对象（主要是获取活动价格）
     *
     * @param req 入参数
     * @return 活动产品 关联对象
     */
    @Override
    public ResultVO<ActivityProductDTO> getActivityProduct(MarketingActivityQueryByGoodsReq req) {
        log.info("MarketingActivityServiceImpl.getActivityProduct req={}", JSON.toJSONString(req));
        ResultVO<ActivityProductDTO> resp = ResultVO.success(null);
        ResultVO<List<MarketingGoodsActivityQueryResp>> resultVO = listGoodsMarketingActivitys(req);
        if (resultVO.isSuccess() && !CollectionUtils.isEmpty(resultVO.getResultData())) {
            // 取第一条
            MarketingGoodsActivityQueryResp marketingGoodsActivityQueryResp = resultVO.getResultData().get(0);
            // 取活动产品关联信息
            ActivityProductListReq activityProductListReq = new ActivityProductListReq();
            activityProductListReq.setProductId(req.getProductId());
            activityProductListReq.setMarketingActivityIds(Lists.newArrayList(marketingGoodsActivityQueryResp.getId()));
            ResultVO<List<ActivityProductDTO>> activityProductResultVO = activityProductService.queryActivityProducts(activityProductListReq);
            if (activityProductResultVO.isSuccess() && !CollectionUtils.isEmpty(activityProductResultVO.getResultData())) {
                // 取第一个
                ActivityProductDTO activityProductDTO = activityProductResultVO.getResultData().get(0);
                // 设置返回值
                resp.setResultData(activityProductDTO);
            }
        }
        log.info("MarketingActivityServiceImpl.getActivityProduct out={}", JSON.toJSONString(resp));
        return resp;
    }


    /**
     * 查询商品详情页面中的适用活动
     *
     * @param req 商品参数
     * @return
     */
    @Override
    public ResultVO<List<MarketingGoodsActivityQueryResp>> listGoodsMarketingActivitys(MarketingActivityQueryByGoodsReq req) {
        log.info("MarketingActivityServiceImpl.listGoodsMarketingActivitys req={}", JSON.toJSONString(req));
        List<MarketingGoodsActivityQueryResp> marketingGoodsActivityQueryRespList = Lists.newArrayList();
        // 产品鉴定
        List<ActivityProduct> activityProducts = activityProductManager.queryActivityProductByProductId(req.getProductId());
        if (!CollectionUtils.isEmpty(activityProducts)) {
            List<String> marketingActivityIdList = Lists.newArrayList();
            activityProducts.forEach(item -> {
                marketingActivityIdList.add(item.getMarketingActivityId());
            });
            // 查询活动列表
            List<MarketingActivity> marketingActivities = marketingActivityManager.listMarketingActivitysByCodes(marketingActivityIdList, req.getActivityType());
            if (!CollectionUtils.isEmpty(marketingActivities)) {
                List<MarketingActivity> marketingActivitieList = Lists.newArrayList();
                marketingActivities.forEach(item -> {
                    String activityScopeType = item.getActivityScopeType();
                    // 根据活动对象类型过滤活动
                    filterActivitys(req, marketingActivitieList, item, activityScopeType);
                });
                // 返回活动列表
                if (marketingActivitieList.size() > 0) {
                    marketingActivitieList.forEach(item -> {
                        if(PromoConst.ACTIVITYTYPE.PRESUBSIDY.getCode().equals(item.getActivityType())){
                            //如果是前置补贴营销活动，需将产品补贴金额替换到营销活动优惠描述中
                            convertPromotionDesc(req.getProductId(),item);
                        }
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
     * 将产品补贴金额替换到营销活动优惠描述中
     * @param productId
     * @param activity
     * @return
     */
    private MarketingActivity convertPromotionDesc(String productId,MarketingActivity activity){
        // 1.查看“活动的优惠规则描述”字段，如果为空，直接返回
        String promotionDesc = activity.getPromotionDesc();
        if(StringUtils.isEmpty(promotionDesc)){
            return activity;
        }
        // 2.根据产品id和活动id获取活动产品信息
        ActivityProductDTO activityProductDTO = null;
        ActivityProductListReq activityProductListReq = new ActivityProductListReq();
        activityProductListReq.setProductId(productId);
        activityProductListReq.setMarketingActivityIds(Lists.newArrayList(activity.getId()));
        ResultVO<List<ActivityProductDTO>> activityProductResultVO = activityProductService.queryActivityProducts(activityProductListReq);
        if (activityProductResultVO.isSuccess() && !CollectionUtils.isEmpty(activityProductResultVO.getResultData())) {
            // 取第一个
            activityProductDTO = activityProductResultVO.getResultData().get(0);
        }
        // 3.提取活动产品的优惠金额
        Long discountAmount = activityProductDTO.getDiscountAmount();
        // 4.替换营销活动中“活动优惠占位符${}”为产品的优惠活动金额。例：省级前置补贴${前置补贴金额}元
        // 匹配用的正则表达式
        String regex = "\\$\\{(.*?)\\}";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(promotionDesc);
        // 如果能匹配到就进行替换
        while (matcher.find()) {
            String tag = matcher.group();
            promotionDesc = promotionDesc.replace(tag,discountAmount+"");
        }
        activity.setPromotionDesc(promotionDesc);
        return activity;
    }

    /**
     * 根据活动ID查询活动详情
     *
     * @param activityId
     * @return
     */
    @Override
    public ResultVO<MarketingGoodsActivityQueryResp> getMarketingActivity(String activityId) {

        log.error("MarketingActivityServiceImpl.getMarketingGoodsActivity -->activityId={}", activityId);
        MarketingActivity marketingActivity = marketingActivityManager.getMarketingActivityById(activityId);
        if (marketingActivity == null) {
            log.error("MarketingActivityServiceImpl.getMarketingGoodsActivity  activity not exist-->activityId={}", activityId);
            return ResultVO.error("活动不存在");
        }
        MarketingGoodsActivityQueryResp marketingGoodsActivityQueryResp = new MarketingGoodsActivityQueryResp();
        BeanUtils.copyProperties(marketingActivity, marketingGoodsActivityQueryResp);
        log.error("MarketingActivityServiceImpl.getMarketingGoodsActivity -->marketingGoodsActivityQueryResp={}", JSON.toJSONString(marketingGoodsActivityQueryResp));
        return ResultVO.success(marketingGoodsActivityQueryResp);
    }

    /**
     * 查询营销活动信息关联的优惠信息
     *
     * @param req 商品参数
     * @return
     */
    @Override
    public ResultVO<List<MarketingAndPromotionResp>> listMarketingActivityAndPromotions(MarketingActivityQueryByGoodsReq req) {
        log.info("MarketingActivityServiceImpl.listMarketingActivityAndPromotions req{} ", req);
        ResultVO<List<MarketingGoodsActivityQueryResp>> resultVO = listGoodsMarketingActivitys(req);
        List<MarketingGoodsActivityQueryResp> marketingGoodsActivityQueryRespList = resultVO.getResultData();
        log.info("MarketingActivityServiceImpl.listMarketingActivityAndPromotions marketingGoodsActivityQueryRespList{} ",
                resultVO.getResultData());
        log.info("MarketingActivityServiceImpl.listMarketingActivityAndPromotions marketingGoodsActivityQueryRespList{} ",
                marketingGoodsActivityQueryRespList);
        List<MarketingAndPromotionResp> respList = new ArrayList<>();
        if (!CollectionUtils.isEmpty(marketingGoodsActivityQueryRespList)) {
            for (int i = 0; i < marketingGoodsActivityQueryRespList.size(); i++) {
                MarketingActivity marketingActivity = marketingActivityManager.getMarketingActivityById(marketingGoodsActivityQueryRespList.get(i).getId());
                List<String> marketingActivityIds = new ArrayList<>();
                marketingActivityIds.add(marketingActivity.getId());
                List<ActivityProduct> activityGoodsList = activityProductManager.queryActivityProductBymktIdProdId(marketingActivityIds, req.getProductId());
                if (!CollectionUtils.isEmpty(activityGoodsList)) {
                    for (int k = 0; k < activityGoodsList.size(); k++) {
                        MarketingAndPromotionResp marketingAndPromotionResp = new MarketingAndPromotionResp();
                        marketingAndPromotionResp.setPromotionTypeCode(marketingActivity.getPromotionTypeCode());
                        marketingAndPromotionResp.setActivityType(marketingActivity.getActivityType());
                        marketingAndPromotionResp.setName(marketingActivity.getName());
                        marketingAndPromotionResp.setId(marketingActivity.getId());
                        marketingAndPromotionResp.setPromotionPrice(String.valueOf(activityGoodsList.get(k).getDiscountAmount()));
                        marketingAndPromotionResp.setProductId(activityGoodsList.get(k).getProductId());
                        marketingAndPromotionResp.setNum(activityGoodsList.get(k).getNum());

                        respList.add(marketingAndPromotionResp);
                    }
                }
            }
        }
        log.info("MarketingActivityServiceImpl.listMarketingActivityAndPromotions resp{} ",
                respList);
        return ResultVO.success(respList);
    }


    /**
     * 根据活动对象类型过滤活动
     *
     * @param req                    查询总入参
     * @param marketingActivitieList 接收结果的List
     * @param item                   当前循环体内的活动对象
     * @param activityScopeType      活动类型
     */
    private void filterActivitys(MarketingActivityQueryByGoodsReq req, List<MarketingActivity> marketingActivitieList, MarketingActivity item, String activityScopeType) {
        if (PromoConst.ActivityScopeType.ACTIVITY_SCOPE_TYPE_10.getCode().equals(activityScopeType)) {
            // 过滤活动对象，即买家
            ResultVO<MerchantDTO> merchantDTO = merchantService.getMerchantById(req.getSupplierCode());
            MerchantDTO merchant = merchantDTO.getResultData();
            if (merchant != null) {
                // 过滤地市、区县
                ActivityScope activityScopeByLanId = activityScopeManager.queryActivityScopeByLandId(item.getId(), merchantDTO.getResultData().getLanId());
                // 过滤地市
                if (activityScopeByLanId != null) {
                    // 过滤活动对象，即买家，根据参与对象类型过滤活动
                    Boolean aBoolean = filterMerchant(req.getMerchantCode(), item, marketingActivitieList);
                    if (aBoolean) {
                        marketingActivitieList.add(item);
                    }
                } else {
                    // 过滤区县
                    ActivityScope activityScopeByCity = activityScopeManager.queryActivityScopeByCityId(item.getId(), merchantDTO.getResultData().getCity());
                    if (activityScopeByCity != null) {
                        // 过滤活动对象，即买家，根据参与对象类型过滤活动
                        Boolean aBoolean = filterMerchant(req.getMerchantCode(), item, marketingActivitieList);
                        if (aBoolean) {
                            marketingActivitieList.add(item);
                        }
                    }
                }

            }
        } else if (PromoConst.ActivityScopeType.ACTIVITY_SCOPE_TYPE_20.getCode().equals(activityScopeType)) {
            // 过滤供应商（卖家）编码
            ActivityScope activityScope = activityScopeManager.queryActivityScopeBySupplierCode(item.getId(), req.getSupplierCode());
            if (activityScope != null) {
                // 过滤活动对象，即买家，根据参与对象类型过滤活动
                Boolean aBoolean = filterMerchant(req.getMerchantCode(), item, marketingActivitieList);
                if (aBoolean) {
                    marketingActivitieList.add(item);
                }
            }
        }
    }

    /**
     * 根据参与对象类型过滤活动
     *
     * @param merchantCode
     * @param item
     * @param marketingActivitieList
     * @return
     */
    private Boolean filterMerchant(String merchantCode, MarketingActivity item, List<MarketingActivity> marketingActivitieList) {
        if (StringUtils.isBlank(merchantCode)) {
            // 未登录
            return true;
        }
        // 已登录
        // 过滤当前登录用户是否为创建者
        UserGetReq userGetReq = new UserGetReq();
        userGetReq.setRelCode(merchantCode);
        UserDTO user = userService.getUser(userGetReq);
        if (null != user) {
            String currentUserId = user.getUserId();
            String activityCreator = item.getCreator();
            if (activityCreator.equals(currentUserId)) {
                // 创建者是当前登录商家
                return false;
            }
        }
        // 创建者不为当前登录商家
        String activityParticipantType = item.getActivityParticipantType();
        if (PromoConst.ActivityParticipantType.ACTIVITY_PARTICIPANT_TYPE_10.getCode().equals(activityParticipantType)) {
            //过滤地市、区县
            ResultVO<MerchantDTO> merchantDTO = merchantService.getMerchantById(merchantCode);
            MerchantDTO merchant = merchantDTO.getResultData();
            if (merchant != null) {
                ActivityParticipant activityParticipantsByLandId = activityParticipantManager.queryActivityParticipantByLandId(item.getId(), merchantDTO.getResultData().getLanId());
                if (activityParticipantsByLandId != null) {
                    //过滤地市
                    marketingActivitieList.add(item);
                } else {
                    //过滤区县
                    ActivityParticipant activityParticipantsByCity = activityParticipantManager.queryActivityParticipantByCityId(item.getId(), merchantDTO.getResultData().getCity());
                    if (activityParticipantsByCity != null) {
                        marketingActivitieList.add(item);
                    }
                }
            }
        } else if (PromoConst.ActivityParticipantType.ACTIVITY_PARTICIPANT_TYPE_20.getCode().equals(activityParticipantType)) {
            //过滤商家（买家）编码
            ActivityParticipant activityParticipantList = activityParticipantManager.queryActivityParticipantByMerchantCode(item.getId(), merchantCode);
            if (activityParticipantList != null) {
                marketingActivitieList.add(item);
            }
        }else if (PromoConst.ActivityParticipantType.ACTIVITY_PARTICIPANT_TYPE_30.getCode().equals(activityParticipantType)) {
            //查看该商家是否是活动对象(要求满足全部过滤条件)
            boolean flag = isExistingInParticipantFilterValue(item, merchantCode);
            if (flag) {
                marketingActivitieList.add(item);
            }
        }

        return false;

    }

    /**
     * 判断商家merchantCode是否是活动activity参与对象
     * @param activity 活动
     * @param merchantCode 商家编码
     * @return
     */
    private boolean isExistingInParticipantFilterValue(MarketingActivity activity,String merchantCode){
        // 1.准备过滤条件数据
        // 查询过滤条件是否存在，不存在则直接返回false;
        List<ActivityParticipantDTO> activityParticipantDTOList  = activityParticipantManager.queryActivityParticipantByMktIdAndStatus(activity.getId(), PromoConst.Status.Audited.getCode());
        ActivityParticipantDTO  activityParticipantDTO = CollectionUtils.isEmpty(activityParticipantDTOList)?null:activityParticipantDTOList.get(0);
        if (activityParticipantDTO == null||activityParticipantDTO.getFilterValue()==null) {
            return false;
        }
        String filterValue = activityParticipantDTO.getFilterValue();

        // 2.准备商家数据
        // 校验商家是否存在，不存在则直接返回false;
        ResultVO<MerchantDTO> resultVO = merchantService.getMerchantByCode(merchantCode);
        MerchantDTO merchantDTO = resultVO.getResultData();
        if (merchantDTO == null) {
            return false;
        }
        // 3.准备标签数据
        // 查询商家标签信息
        MerchantTagRelListReq req = new MerchantTagRelListReq();
        req.setMerchantId(merchantDTO.getMerchantId());
        ResultVO<List<MerchantTagRelDTO>> tagListResultVO = merchantTagRelService.listMerchantTagRel(req);
        List<MerchantTagRelDTO> merchantTagRelDTOs = null;
        if(tagListResultVO!=null&&tagListResultVO.getResultData()!=null){
            merchantTagRelDTOs = tagListResultVO.getResultData();
        }
        //4.开始比较是否是活动参与对象
        boolean flag = true;
        try {
            // 解析过滤条件内容
            JSONObject filterValueJson = JSONObject.parseObject(filterValue);
            JSONArray cityArray = filterValueJson.getJSONArray("cityList");
            JSONArray countyArray = filterValueJson.getJSONArray("countyList");
            JSONArray tagArray = filterValueJson.getJSONArray("tagList");
            // 获取各级过滤条件ids
            List<String> cityIds =  getFieldListFromJSONArray(cityArray,"regionId");
            List<String> countyIds =  getFieldListFromJSONArray(countyArray,"regionId");
            List<String> tagIds =  getFieldListFromJSONArray(tagArray,"tagId");

            // 查看商家所在城市是否包含在城市条件里
            if(!CollectionUtils.isEmpty(cityIds)){
                flag = cityIds.contains(merchantDTO.getLanId());
            }
            // 查看商家所在区县是否包含在区县条件里
            if(!CollectionUtils.isEmpty(countyIds)){
                flag = countyIds.contains(merchantDTO.getCity());
            }
            // 查看商家标签是否包含在标签条件里
            if(!CollectionUtils.isEmpty(tagIds)&&!CollectionUtils.isEmpty(merchantTagRelDTOs)){
                //只要有一个商家标签包含在标签条件里就算包含
                flag = false;
                for (MerchantTagRelDTO merchantTagRelDTO : merchantTagRelDTOs) {
                    if(tagIds.contains(merchantTagRelDTO.getTagId())){
                        flag = true;
                        break;
                    }
                }
            }
        } catch (Exception e) {
            log.info("MarketingActivityServiceImpl.isActivityParticipant 处理异常，filterValue={}", filterValue);
            return false;
        }

        return flag;
    }

    /**
     * 从JsonArray里提取字段list
     * @param array JsonArray模型对象
     * @param field 要提取的字段
     * @return
     */
    private List<String> getFieldListFromJSONArray(JSONArray array, String field){
        List<String> list =  Lists.newArrayList();
        try {
            for (int i = 0; i < array.size(); i++) {
                JSONObject object = array.getJSONObject(i);
                String regionId = object.getString(field);
                list.add(regionId);
            }
        } catch (Exception ex) {
            log.info("MarketingActivityServiceImpl.getFieldListFromJSONArray 处理异常，array={}", array.toJSONString());
            return list;
        }
        return list;
    }

    /**
     * 查询商品详情页面中的适用减免
     *
     * @param req 商品参数
     * @return
     */
    @Override
    public ResultVO<List<MarketingReliefActivityQueryResp>> listGoodsMarketingReliefActivitys(MarketingActivityQueryByGoodsReq req) {
        log.info("MarketingActivityServiceImpl.listGoodsMarketingReliefActivitys req={}", JSON.toJSONString(req));
        List<MarketingReliefActivityQueryResp> marketingReliefActivityQueryRespList = Lists.newArrayList();
        ResultVO<List<MarketingGoodsActivityQueryResp>> listResultVO = this.listGoodsMarketingActivitys(req);
        List<MarketingGoodsActivityQueryResp> activityList = listResultVO.getResultData();
        VerifyProductPurchasesLimitReq verifyProductPurchasesLimitReq = new VerifyProductPurchasesLimitReq();
        if (!CollectionUtils.isEmpty(activityList)) {
            List<String> marketingActivityIdList = Lists.newArrayList();
            activityList.forEach(item -> {
                marketingActivityIdList.add(item.getId());
            });
            //查询商品适用减免
            List<ActivityProduct> productList = activityProductManager.queryActivityProductByActIdAndProductId(marketingActivityIdList, req.getProductId());
            if (!CollectionUtils.isEmpty(productList)) {
                productList.forEach(item -> {
                    if (null != item.getDiscountAmount()) {
                        if (PromoConst.ProductNumFlg.ProductNumFlg_1.getCode().equals(item.getNumLimitFlg())) {
                            verifyProductPurchasesLimitReq.setPurchaseCount(1);
                            verifyProductPurchasesLimitReq.setActivityId(item.getMarketingActivityId());
                            verifyProductPurchasesLimitReq.setProductId(item.getProductId());
                            Long buyCount = historyPurchaseManager.queryActProductPurchasedSum(verifyProductPurchasesLimitReq);
                            if (buyCount != null) {
                                if (item.getNum() < buyCount) {
                                    MarketingReliefActivityQueryResp marketingReliefActivityQueryResp = new MarketingReliefActivityQueryResp();
                                    marketingReliefActivityQueryResp.setMarketingActivityId(item.getMarketingActivityId());
                                    marketingReliefActivityQueryResp.setPromotionPrice(String.valueOf(item.getDiscountAmount()));
                                    marketingReliefActivityQueryRespList.add(marketingReliefActivityQueryResp);
                                }
                            }
                        }
                    }
                });
            }
        }
        log.info("MarketingActivityServiceImpl.listGoodsMarketingReliefActivitys resp={}", JSON.toJSONString(marketingReliefActivityQueryRespList));
        return ResultVO.success(marketingReliefActivityQueryRespList);
    }

    /**
     * 查询商品详情页面中的适用优惠券
     *
     * @param req 商品参数
     * @return
     */
    @Override
    public ResultVO<List<MarketingCouponActivityQueryResp>> listGoodsMarketingCouponActivitys(MarketingActivityQueryByGoodsReq req) {
        log.info("MarketingActivityServiceImpl.listGoodsMarketingCouponActivitys req={}", JSON.toJSONString(req));
        List<MarketingCouponActivityQueryResp> marketingCouponActivityQueryRespArrayList = Lists.newArrayList();
        ResultVO<List<MarketingGoodsActivityQueryResp>> listResultVO = this.listGoodsMarketingActivitys(req);
        List<MarketingGoodsActivityQueryResp> activityList = listResultVO.getResultData();
        if (!CollectionUtils.isEmpty(activityList)) {
            List<String> marketingActivityIdList = Lists.newArrayList();
            activityList.forEach(item -> {
                marketingActivityIdList.add(item.getId());
            });
            //查询商品适用卡券
            List<Promotion> promotionList = promotionManager.getPromotion(marketingActivityIdList, PromoConst.PromotionType.PROMOTION_TYPE_CD_20.getCode());
            if (!CollectionUtils.isEmpty(promotionList)) {
                List<String> mktIds = Lists.newArrayList();
                QueryCouponByProductAndActivityIdReq queryCouponByProductAndActivityIdReq = new QueryCouponByProductAndActivityIdReq();
                queryCouponByProductAndActivityIdReq.setProductId(req.getProductId());
                promotionList.forEach(item -> {
                    mktIds.add(item.getMktResId());
                });
                queryCouponByProductAndActivityIdReq.setMktResIds(mktIds);
                ResultVO<List<CouponApplyObjectRespDTO>> resultVO = couponApplyObjectService.queryCouponApplyObjectByCondition(queryCouponByProductAndActivityIdReq);
                List<CouponApplyObjectRespDTO> couponApplyObjectRespDTOS = resultVO.getResultData();
                if (!CollectionUtils.isEmpty(couponApplyObjectRespDTOS)) {
                    couponApplyObjectRespDTOS.forEach(item -> {
                        QueryCouponByIdReq queryCouponByIdReq = new QueryCouponByIdReq();
                        queryCouponByIdReq.setMktResId(item.getMktResId());
                        CouponRuleAndTypeQueryResp ruleAndTypeRespDTO = mktResCouponService.queryCouponRuleAndTypeById(queryCouponByIdReq);
                        if (ruleAndTypeRespDTO != null) {
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
     *
     * @param req
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultVO<Boolean> updateMarketingActivity(MarketingActivityAddReq req) {
        log.info("MarketingActivityServiceImpl.updateMarketingActivity req={}", JSON.toJSONString(req));
        MarketingActivity marketingActivity = new MarketingActivity();
        BeanUtils.copyProperties(req, marketingActivity);
        String marketingActivityId = marketingActivity.getId();
        if (StringUtils.isNotEmpty(marketingActivityId)) {
            //1. 先判断此数据的状态， 审核通过用新逻辑，不通过用原逻辑
            ResultVO<MarketingActivityDTO> result = marketingActivityManager.getMarketingActivityDtoById(marketingActivityId);
            MarketingActivityDTO marketingActivityDTO = result.getResultData();
            // STATUS_CD_20("20","审核通过")
            if (marketingActivityDTO != null && PromoConst.STATUSCD.STATUS_CD_20.getCode().equals(marketingActivityDTO.getStatus())) {
//                int num = 0;
//                List<MarketingActivityModify> list = marketingActivityManager.queryMarketingActivityModifySize(req.getId());
//                if (!CollectionUtils.isEmpty(list)){
//                    num = list.size();
//                }
//                MarketingActivityModify marketingActivityModify = new MarketingActivityModify();
//                req.setId("");
//                BeanUtils.copyProperties(req, marketingActivityModify);
//                marketingActivityModify.setMarketingActivityId(marketingActivityId);
//                marketingActivityModify.setVerNum(Long.valueOf(num+1));
//                int size =  marketingActivityManager.updateMarketingActivityModify(marketingActivityModify);
//                if (size > 0){
//                  return ResultVO.successMessage(constant.getUpdateSuccess());
//                } else {
//                  return ResultVO.error(constant.getUpdateFaile());
//                }
                //新变更审核逻辑-开始
                try {
                    ResultVO processResult = marketingActivityChangeAuitProcess(marketingActivityDTO, req);
                    if (!processResult.isSuccess()) {
                        return processResult;
                    }
                    return ResultVO.successMessage(constant.getUpdateSuccess());
                } catch (Exception e) {
                    log.info("MarketingActivityServiceImpl.updateMarketingActivity req={}", JSON.toJSONString(req));
                    log.error("MarketingActivityServiceImpl.updateMarketingActivity 变更营销活动通过审核失败", e);
                    return ResultVO.error(constant.getUpdateFaile());
                }
                //新变更审核逻辑-结束
            } else {
                ResultVO<MarketingActivityAddResp> response = addMarketingActivity(req);
                log.info("MarketingActivityServiceImpl.updateMarketingActivity resp={}", JSON.toJSONString(response));
                if (response.isSuccess() && null != response.getResultData()) {
                    return ResultVO.successMessage(constant.getUpdateSuccess());
                } else {
                    return ResultVO.error(constant.getUpdateFaile());
                }
            }
        } else {
            return ResultVO.error(constant.getNoMarketingActivityId());
        }
    }

    /**
     * 活动变更流程主方法，包含添加变更数据和启动审核流程
     *
     * @param originalActivity
     * @param changedActivity
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultVO marketingActivityChangeAuitProcess(MarketingActivityDTO originalActivity, MarketingActivityAddReq changedActivity) {
        if (PromoConst.ActivityIsModifying.YES.getCode().equals(originalActivity.getIsModifying())) {
            return ResultVO.error(constant.getModifying());
        }
        //1.插入变更信息，包含比较变更数据和原数据，获取变更项，插入变更表和变更明细表
        String changeId = addMarketingActivityChange(originalActivity, changedActivity);
        if (changeId == null) {
            return ResultVO.error(constant.getNoModify());
        }
        //2.将营销活动“修改标识”改为1(在审核修改中)
        marketingActivityManager.updateMarketingActivityToModifying(changedActivity.getId(), PromoConst.ActivityIsModifying.YES.getCode());
        //3.起变更审核流程
        ResultVO auditMktResultVO = activityChangeAuitStartProcess(changedActivity.getName(), changedActivity.getUserId(), changedActivity.getUserName(), changedActivity.getOrgId(), changedActivity.getSysPostName(), changedActivity.getId(), changeId);
        return auditMktResultVO;

    }

    /**
     * 增加营销活动变更信息
     *
     * @param originalActivity 原活动
     * @param changedActivity  变更后的活动
     * @return 变更信息id
     */
    private String addMarketingActivityChange(MarketingActivityDTO originalActivity, MarketingActivityAddReq changedActivity) {

        //获取原活动信息，与新活动信息比较获取变更明细信息
        List<ActivityChangeDetail> activityChangeDetails = calculateActivityChangeDetail(originalActivity, changedActivity);
        if (activityChangeDetails.size() == 0) {
            return null;
        }
        //通过查询该活动的最新变更信息,计算出新变更版本号，与变更明细共用
        Long num = 0L;
        List<ActivityChange> changes = activityChangeManager.queryActivityChangeByActivityId(changedActivity.getId());
        if (changes != null && changes.size() > 0) {
            num = changes.size() + 1L;
        }
        //从全局sequence获取id序号作为本实例的id
        String changeId = activityChangeManager.getPrimaryKey();
        String createStaff = changedActivity.getModifier();
        Date createDate = new Date();
        //封装活动变更信息模型
        ActivityChange activityChange = new ActivityChange();
        activityChange.setChangeId(changeId);
        activityChange.setVerNum(num);
        activityChange.setCreateDate(createDate);
        activityChange.setCreateStaff(createStaff);
        activityChange.setMarketingActivityId(changedActivity.getId());
        activityChange.setAuditState(PromoConst.AuditState.AuditState_2.getCode());

        //补充变更详情相关关联信息
        for (ActivityChangeDetail activityChangeDetail : activityChangeDetails) {
            activityChangeDetail.setChangeId(changeId);
            activityChangeDetail.setVerNum(Long.valueOf(num));
            activityChangeDetail.setCreateDate(createDate);
            activityChangeDetail.setCreateStaff(createStaff);
        }
        //保存活动变更信息
        activityChangeManager.insertActivityChange(activityChange);
        //保存活动变更明细信息
        activityChangeDetailManager.addActivityChangeDetailBatch(activityChangeDetails);
        return changeId;
    }

    /**
     * 计算营销活动变更详情信息
     *
     * @param originalActivity 原活动
     * @param changedActivity  变更后的活动
     * @return
     */
    private List<ActivityChangeDetail> calculateActivityChangeDetail(MarketingActivityDTO originalActivity, MarketingActivityAddReq changedActivity) {
        List<ActivityChangeDetail> activityChangeDetails = new ArrayList<>();

        // 1.计算活动名称、概述、描述、优惠描述信息,获取变更数据
        calculateActivityNameAndBriefAndDescriptionAndPromotionDescChangeDetail(originalActivity, changedActivity, activityChangeDetails);

        // 2.计算活动时间信息,获取变更数据
        calculateActivityTimeChangeDetail(originalActivity, changedActivity, activityChangeDetails);

        // 3.计算发货时间信息,获取变更数据
        calculateDeliverTimeChangeDetail(originalActivity, changedActivity, activityChangeDetails);

        // 4.计算支付定金信息,获取变更数据
        calculatePreTimeChangeDetail(originalActivity, changedActivity, activityChangeDetails);

        // 5.计算活动支付尾款信息,获取变更数据
        calculateImgChangeDetail(originalActivity, changedActivity, activityChangeDetails);

        // 6.计算活动图片信息,获取变更数据
        calculatePayTimeChangeDetail(originalActivity, changedActivity, activityChangeDetails);

        // 7.计算活动参与范围信息,获取变更数据
        calculateScopeChangeDetail(originalActivity, changedActivity, activityChangeDetails);

        // 8.计算活动参与对象信息,获取变更数据
        calculateParticipantChangeDetail(originalActivity, changedActivity, activityChangeDetails);
        return activityChangeDetails;
    }

    /**
     * 计算活动名称、概述、描述、优惠描述信息,获取变更数据
     * @param originalActivity  原活动信息
     * @param changedActivity   变更后的活动信息
     * @param activityChangeDetails 活动变更数据集合
     */
    private void calculateActivityNameAndBriefAndDescriptionAndPromotionDescChangeDetail(MarketingActivityDTO originalActivity, MarketingActivityAddReq changedActivity,List<ActivityChangeDetail> activityChangeDetails) {
        //1.比较活动基本信息
        //1.1 比较活动名称
        if(StringUtils.isNotEmpty(changedActivity.getName())&&!changedActivity.getName().equals(originalActivity.getName())){
            ActivityChangeDetail activityChangeDetail = new ActivityChangeDetail();
            activityChangeDetail.setOperType(PromoConst.OperType.MOD.getCode());
            activityChangeDetail.setTableName(MarketingActivity.TNAME);
            activityChangeDetail.setChangeField(MarketingActivity.FieldNames.name.getTableFieldName());
            activityChangeDetail.setChangeFieldName(MarketingActivity.FieldNames.name.getTableFieldComment());
            activityChangeDetail.setFieldType(PromoConst.FieldType.FieldType_1.getCode());
            activityChangeDetail.setOldValue(originalActivity.getName());
            activityChangeDetail.setNewValue(changedActivity.getName());
            activityChangeDetail.setKeyValue(originalActivity.getId());
            activityChangeDetails.add(activityChangeDetail);
        }

        //1.2 比较活动概述
        if(StringUtils.isNotEmpty(changedActivity.getBrief())&&!changedActivity.getBrief().equals(originalActivity.getBrief())) {
            ActivityChangeDetail activityChangeDetail = new ActivityChangeDetail();
            activityChangeDetail.setOperType(PromoConst.OperType.MOD.getCode());
            activityChangeDetail.setTableName(MarketingActivity.TNAME);
            activityChangeDetail.setChangeField(MarketingActivity.FieldNames.brief.getTableFieldName());
            activityChangeDetail.setChangeFieldName(MarketingActivity.FieldNames.brief.getTableFieldComment());
            activityChangeDetail.setFieldType(PromoConst.FieldType.FieldType_1.getCode());
            activityChangeDetail.setOldValue(originalActivity.getBrief());
            activityChangeDetail.setNewValue(changedActivity.getBrief());
            activityChangeDetail.setKeyValue(originalActivity.getId());
            activityChangeDetails.add(activityChangeDetail);
        }
        //1.3 比较活动描述
        if(StringUtils.isNotEmpty(changedActivity.getDescription())&&!changedActivity.getDescription().equals(originalActivity.getDescription())) {
            ActivityChangeDetail activityChangeDetail = new ActivityChangeDetail();
            activityChangeDetail.setOperType(PromoConst.OperType.MOD.getCode());
            activityChangeDetail.setTableName(MarketingActivity.TNAME);
            activityChangeDetail.setChangeField(MarketingActivity.FieldNames.description.getTableFieldName());
            activityChangeDetail.setChangeFieldName(MarketingActivity.FieldNames.description.getTableFieldComment());
            activityChangeDetail.setFieldType(PromoConst.FieldType.FieldType_1.getCode());
            activityChangeDetail.setOldValue(originalActivity.getDescription());
            activityChangeDetail.setNewValue(changedActivity.getDescription());
            activityChangeDetail.setKeyValue(originalActivity.getId());
            activityChangeDetails.add(activityChangeDetail);
        }
        //1.4 比较活动优惠规则描述
        if(StringUtils.isNotEmpty(changedActivity.getPromotionDesc())&&!changedActivity.getPromotionDesc().equals(originalActivity.getPromotionDesc())) {
            ActivityChangeDetail activityChangeDetail = new ActivityChangeDetail();
            activityChangeDetail.setOperType(PromoConst.OperType.MOD.getCode());
            activityChangeDetail.setTableName(MarketingActivity.TNAME);
            activityChangeDetail.setChangeField(MarketingActivity.FieldNames.promotionDesc.getTableFieldName());
            activityChangeDetail.setChangeFieldName(MarketingActivity.FieldNames.promotionDesc.getTableFieldComment());
            activityChangeDetail.setFieldType(PromoConst.FieldType.FieldType_1.getCode());
            activityChangeDetail.setOldValue(originalActivity.getDescription());
            activityChangeDetail.setNewValue(changedActivity.getDescription());
            activityChangeDetail.setKeyValue(originalActivity.getId());
            activityChangeDetails.add(activityChangeDetail);
        }
    }
    /**
     * 计算活动时间信息,获取变更数据
     * @param originalActivity  原活动信息
     * @param changedActivity   变更后的活动信息
     * @param activityChangeDetails 活动变更数据集合
     */
    private void calculateActivityTimeChangeDetail(MarketingActivityDTO originalActivity, MarketingActivityAddReq changedActivity,List<ActivityChangeDetail> activityChangeDetails) {
        // 2.比较活动时间（包括开始时间和结束时间）
        // 2.1 活动开始时间
        if ((changedActivity.getStartTime() != null && changedActivity.getStartTime().getTime() > 0) && !changedActivity.getStartTime().equals(originalActivity.getStartTime())) {
            ActivityChangeDetail activityChangeDetail = new ActivityChangeDetail();
            activityChangeDetail.setOperType(PromoConst.OperType.MOD.getCode());
            activityChangeDetail.setTableName(MarketingActivity.TNAME);
            activityChangeDetail.setChangeField(MarketingActivity.FieldNames.startTime.getTableFieldName());
            activityChangeDetail.setChangeFieldName(MarketingActivity.FieldNames.startTime.getTableFieldComment());
            activityChangeDetail.setFieldType(PromoConst.FieldType.FieldType_2.getCode());
            activityChangeDetail.setOldValue(dateToTimeStr(originalActivity.getStartTime()));
            activityChangeDetail.setNewValue(dateToTimeStr(changedActivity.getStartTime()));
            activityChangeDetail.setKeyValue(originalActivity.getId());
            activityChangeDetails.add(activityChangeDetail);
        }
        // 2.2 活动结束时间
        if ((changedActivity.getEndTime() != null && changedActivity.getEndTime().getTime() > 0) && !changedActivity.getEndTime().equals(originalActivity.getEndTime())) {
            ActivityChangeDetail activityChangeDetail = new ActivityChangeDetail();
            activityChangeDetail.setOperType(PromoConst.OperType.MOD.getCode());
            activityChangeDetail.setTableName(MarketingActivity.TNAME);
            activityChangeDetail.setChangeField(MarketingActivity.FieldNames.endTime.getTableFieldName());
            activityChangeDetail.setChangeFieldName(MarketingActivity.FieldNames.endTime.getTableFieldComment());
            activityChangeDetail.setFieldType(PromoConst.FieldType.FieldType_2.getCode());
            activityChangeDetail.setOldValue(dateToTimeStr(originalActivity.getEndTime()));
            activityChangeDetail.setNewValue(dateToTimeStr(changedActivity.getEndTime()));
            activityChangeDetail.setKeyValue(originalActivity.getId());
            activityChangeDetails.add(activityChangeDetail);
        }

    }


    /**
     * 计算发货时间信息,获取变更数据
     * @param originalActivity  原活动信息
     * @param changedActivity   变更后的活动信息
     * @param activityChangeDetails 活动变更数据集合
     */
    private void calculateDeliverTimeChangeDetail(MarketingActivityDTO originalActivity, MarketingActivityAddReq changedActivity,List<ActivityChangeDetail> activityChangeDetails) {
        // 3比较发货时间（包括开始时间和结束时间）
        // 3.1 活动发货开始时间
        if ((changedActivity.getDeliverStartTime() != null && changedActivity.getDeliverStartTime().getTime() > 0) && !changedActivity.getDeliverStartTime().equals(originalActivity.getDeliverStartTime())) {
            ActivityChangeDetail activityChangeDetail = new ActivityChangeDetail();
            activityChangeDetail.setOperType(PromoConst.OperType.MOD.getCode());
            activityChangeDetail.setTableName(MarketingActivity.TNAME);
            activityChangeDetail.setChangeField(MarketingActivity.FieldNames.deliverStartTime.getTableFieldName());
            activityChangeDetail.setChangeFieldName(MarketingActivity.FieldNames.deliverStartTime.getTableFieldComment());
            activityChangeDetail.setFieldType(PromoConst.FieldType.FieldType_2.getCode());
            activityChangeDetail.setOldValue(dateToTimeStr(originalActivity.getDeliverStartTime()));
            activityChangeDetail.setNewValue(dateToTimeStr(changedActivity.getDeliverStartTime()));
            activityChangeDetail.setKeyValue(originalActivity.getId());
            activityChangeDetails.add(activityChangeDetail);
        }
        // 3.2 活动发货结束时间
        if ((changedActivity.getDeliverEndTime() != null && changedActivity.getDeliverEndTime().getTime() > 0) && !changedActivity.getDeliverEndTime().equals(originalActivity.getDeliverEndTime())) {
            ActivityChangeDetail activityChangeDetail = new ActivityChangeDetail();
            activityChangeDetail.setOperType(PromoConst.OperType.MOD.getCode());
            activityChangeDetail.setTableName(MarketingActivity.TNAME);
            activityChangeDetail.setChangeField(MarketingActivity.FieldNames.deliverEndTime.getTableFieldName());
            activityChangeDetail.setChangeFieldName(MarketingActivity.FieldNames.deliverEndTime.getTableFieldComment());
            activityChangeDetail.setFieldType(PromoConst.FieldType.FieldType_2.getCode());
            activityChangeDetail.setOldValue(dateToTimeStr(originalActivity.getDeliverEndTime()));
            activityChangeDetail.setNewValue(dateToTimeStr(changedActivity.getDeliverEndTime()));
            activityChangeDetail.setKeyValue(originalActivity.getId());
            activityChangeDetails.add(activityChangeDetail);
        }

    }

    /**
     * 计算支付定金时间信息,获取变更数据
     * @param originalActivity  原活动信息
     * @param changedActivity   变更后的活动信息
     * @param activityChangeDetails 活动变更数据集合
     */
    private void calculatePreTimeChangeDetail(MarketingActivityDTO originalActivity, MarketingActivityAddReq changedActivity,List<ActivityChangeDetail> activityChangeDetails) {
        // 4比较支付定金时间（包括开始时间和结束时间）
        // 4.1 支付定金开始时间
        if ((changedActivity.getPreStartTime() != null && changedActivity.getPreStartTime().getTime() > 0) && !changedActivity.getPreStartTime().equals(originalActivity.getPreStartTime())) {
            ActivityChangeDetail activityChangeDetail = new ActivityChangeDetail();
            activityChangeDetail.setOperType(PromoConst.OperType.MOD.getCode());
            activityChangeDetail.setTableName(MarketingActivity.TNAME);
            activityChangeDetail.setChangeField(MarketingActivity.FieldNames.preStartTime.getTableFieldName());
            activityChangeDetail.setChangeFieldName(MarketingActivity.FieldNames.preStartTime.getTableFieldComment());
            activityChangeDetail.setFieldType(PromoConst.FieldType.FieldType_2.getCode());
            activityChangeDetail.setOldValue(dateToTimeStr(originalActivity.getPreStartTime()));
            activityChangeDetail.setNewValue(dateToTimeStr(changedActivity.getPreStartTime()));
            activityChangeDetail.setKeyValue(originalActivity.getId());
            activityChangeDetails.add(activityChangeDetail);
        }
        //1.4.2 支付定金结束时间
        if ((changedActivity.getPreEndTime() != null && changedActivity.getPreEndTime().getTime() > 0) && !changedActivity.getPreEndTime().equals(originalActivity.getPreEndTime())) {
            ActivityChangeDetail activityChangeDetail = new ActivityChangeDetail();
            activityChangeDetail.setOperType(PromoConst.OperType.MOD.getCode());
            activityChangeDetail.setTableName(MarketingActivity.TNAME);
            activityChangeDetail.setChangeField(MarketingActivity.FieldNames.preEndTime.getTableFieldName());
            activityChangeDetail.setChangeFieldName(MarketingActivity.FieldNames.preEndTime.getTableFieldComment());
            activityChangeDetail.setFieldType(PromoConst.FieldType.FieldType_2.getCode());
            activityChangeDetail.setOldValue(dateToTimeStr(originalActivity.getPreEndTime()));
            activityChangeDetail.setNewValue(dateToTimeStr(changedActivity.getPreEndTime()));
            activityChangeDetail.setKeyValue(originalActivity.getId());
            activityChangeDetails.add(activityChangeDetail);
        }
    }

    /**
     * 计算活动支付尾款信息,获取变更数据
     * @param originalActivity  原活动信息
     * @param changedActivity   变更后的活动信息
     * @param activityChangeDetails 活动变更数据集合
     */
    private void calculatePayTimeChangeDetail(MarketingActivityDTO originalActivity, MarketingActivityAddReq changedActivity,List<ActivityChangeDetail> activityChangeDetails) {
        // 5比较支付尾款时间（包括开始时间和结束时间）
        // 5.1 支付尾款开始时间
        if ((changedActivity.getTailPayStartTime() != null && changedActivity.getTailPayStartTime().getTime() > 0) && !changedActivity.getTailPayStartTime().equals(originalActivity.getTailPayStartTime())) {
            ActivityChangeDetail activityChangeDetail = new ActivityChangeDetail();
            activityChangeDetail.setOperType(PromoConst.OperType.MOD.getCode());
            activityChangeDetail.setTableName(MarketingActivity.TNAME);
            activityChangeDetail.setChangeField(MarketingActivity.FieldNames.tailPayStartTime.getTableFieldName());
            activityChangeDetail.setChangeFieldName(MarketingActivity.FieldNames.tailPayStartTime.getTableFieldComment());
            activityChangeDetail.setFieldType(PromoConst.FieldType.FieldType_2.getCode());
            activityChangeDetail.setOldValue(dateToTimeStr(originalActivity.getTailPayStartTime()));
            activityChangeDetail.setNewValue(dateToTimeStr(changedActivity.getTailPayStartTime()));
            activityChangeDetail.setKeyValue(originalActivity.getId());
            activityChangeDetails.add(activityChangeDetail);
        }
        //5.2 支付尾款结束时间
        if ((changedActivity.getTailPayEndTime() != null && changedActivity.getTailPayEndTime().getTime() > 0) && !changedActivity.getTailPayEndTime().equals(originalActivity.getTailPayEndTime())) {
            ActivityChangeDetail activityChangeDetail = new ActivityChangeDetail();
            activityChangeDetail.setOperType(PromoConst.OperType.MOD.getCode());
            activityChangeDetail.setTableName(MarketingActivity.TNAME);
            activityChangeDetail.setChangeField(MarketingActivity.FieldNames.tailPayEndTime.getTableFieldName());
            activityChangeDetail.setChangeFieldName(MarketingActivity.FieldNames.tailPayEndTime.getTableFieldComment());
            activityChangeDetail.setFieldType(PromoConst.FieldType.FieldType_2.getCode());
            activityChangeDetail.setOldValue(dateToTimeStr(originalActivity.getTailPayEndTime()));
            activityChangeDetail.setNewValue(dateToTimeStr(changedActivity.getTailPayEndTime()));
            activityChangeDetail.setKeyValue(originalActivity.getId());
            activityChangeDetails.add(activityChangeDetail);
        }

    }

    /**
     * 计算活动图片信息,获取变更数据
     * @param originalActivity  原活动信息
     * @param changedActivity   变更后的活动信息
     * @param activityChangeDetails 活动变更数据集合
     */
    private void calculateImgChangeDetail(MarketingActivityDTO originalActivity, MarketingActivityAddReq changedActivity,List<ActivityChangeDetail> activityChangeDetails) {
        // 6 比较活动图片
        // 6.1 比较活动顶部图片
        if (StringUtils.isNotEmpty(changedActivity.getTopImgUrl()) && !changedActivity.getTopImgUrl().equals(originalActivity.getTopImgUrl())) {
            ActivityChangeDetail activityChangeDetail = new ActivityChangeDetail();
            activityChangeDetail.setOperType(PromoConst.OperType.MOD.getCode());
            activityChangeDetail.setTableName(MarketingActivity.TNAME);
            activityChangeDetail.setChangeField(MarketingActivity.FieldNames.topImgUrl.getTableFieldName());
            activityChangeDetail.setChangeFieldName(MarketingActivity.FieldNames.topImgUrl.getTableFieldComment());
            activityChangeDetail.setFieldType(PromoConst.FieldType.FieldType_3.getCode());
            activityChangeDetail.setOldValue(originalActivity.getTopImgUrl());
            activityChangeDetail.setNewValue(changedActivity.getTopImgUrl());
            activityChangeDetail.setKeyValue(originalActivity.getId());
            activityChangeDetails.add(activityChangeDetail);
        }
        // 6.2 比较活动页面图片
        if (StringUtils.isNotEmpty(changedActivity.getPageImgUrl()) && !changedActivity.getPageImgUrl().equals(originalActivity.getPageImgUrl())) {
            ActivityChangeDetail activityChangeDetail = new ActivityChangeDetail();
            activityChangeDetail.setOperType(PromoConst.OperType.MOD.getCode());
            activityChangeDetail.setTableName(MarketingActivity.TNAME);
            activityChangeDetail.setChangeField(MarketingActivity.FieldNames.pageImgUrl.getTableFieldName());
            activityChangeDetail.setChangeFieldName(MarketingActivity.FieldNames.pageImgUrl.getTableFieldComment());
            activityChangeDetail.setFieldType(PromoConst.FieldType.FieldType_3.getCode());
            activityChangeDetail.setOldValue(originalActivity.getPageImgUrl());
            activityChangeDetail.setNewValue(changedActivity.getPageImgUrl());
            activityChangeDetail.setKeyValue(originalActivity.getId());
            activityChangeDetails.add(activityChangeDetail);
        }
    }

    /**
     * 计算活动参与范围信息,获取变更数据
     * @param originalActivity  原活动信息
     * @param changedActivity   变更后的活动信息
     * @param activityChangeDetails 活动变更数据集合
     */
    private void calculateScopeChangeDetail(MarketingActivityDTO originalActivity, MarketingActivityAddReq changedActivity,List<ActivityChangeDetail> activityChangeDetails) {
        // 7.比较活动范围信息
        //获取原活动范围数据和变更后是活动范围数据
        List<ActivityScopeDTO> originalActivityScopeDTOList = activityScopeManager.queryActivityScopeByMktIdAndStatus(originalActivity.getId(), PromoConst.Status.Audited.getCode());
        List<ActivityScopeDTO> changedActivityScopeDTOList = changedActivity.getActivityScopeList();
        if (!CollectionUtils.isEmpty(changedActivityScopeDTOList) && changedActivityScopeDTOList.size() > originalActivityScopeDTOList.size()) {
            List<String> originalActivityScopeLanIds = originalActivityScopeDTOList.stream().map(ActivityScopeDTO::getLanId).collect(Collectors.toList());
            List<String> originalActivityScopeCitys = originalActivityScopeDTOList.stream().map(ActivityScopeDTO::getCity).collect(Collectors.toList());
            List<String> originalActivitySupplierCodes = originalActivityScopeDTOList.stream().map(ActivityScopeDTO::getSupplierCode).collect(Collectors.toList());
            for (ActivityScopeDTO changedActivityScopeDTO : changedActivityScopeDTOList) {
                if (changedActivityScopeDTOList == null || "null".equals(changedActivityScopeDTO)) {
                    continue;
                }
                String lanId = changedActivityScopeDTO.getLanId() == null ? "" : changedActivityScopeDTO.getLanId();
                String city = changedActivityScopeDTO.getCity() == null ? "" : changedActivityScopeDTO.getCity();
                String supplierCode = changedActivityScopeDTO.getSupplierCode() == null ? "" : changedActivityScopeDTO.getSupplierCode();
                if (originalActivityScopeLanIds.contains(lanId) || originalActivityScopeCitys.contains(city)) {
                    continue;
                }
                if (originalActivitySupplierCodes.contains(supplierCode)) {
                    continue;
                }
                //如果该范围不在原范围里，准备进行添加，并写变更详情表
                //封装活动范围信息
                ActivityScope activityScope = new ActivityScope();
                BeanUtils.copyProperties(changedActivityScopeDTO, activityScope);
                activityScope.setMarketingActivityId(originalActivity.getId());
                Date dt = new Date();
                activityScope.setGmtCreate(dt);
                activityScope.setGmtModified(dt);
                activityScope.setIsDeleted(PromoConst.IsDelete.IS_DELETE_CD_0.getCode());
                activityScope.setStatus(PromoConst.Status.WaitAudit.getCode());
                activityScopeManager.insertActivityScope(activityScope);
                //封装变更明细信息
                ActivityChangeDetail activityChangeDetail = new ActivityChangeDetail();
                activityChangeDetail.setOperType(PromoConst.OperType.ADD.getCode());
                activityChangeDetail.setTableName(ActivityScope.TNAME);
                activityChangeDetail.setKeyValue(activityScope.getId());
                activityChangeDetail.setFieldType(changedActivity.getActivityScopeType());
                activityChangeDetails.add(activityChangeDetail);

            }
        }
    }

    /**
     * 计算活动参与对象信息,获取变更数据
     * @param originalActivity  原活动信息
     * @param changedActivity   变更后的活动信息
     * @param activityChangeDetails 活动变更数据集合
     */
    private void calculateParticipantChangeDetail(MarketingActivityDTO originalActivity, MarketingActivityAddReq changedActivity,List<ActivityChangeDetail> activityChangeDetails) {
        // 8.比较活动参与对象信息
        // 获取原活动参与对象数据和变更后是活动参与对象数据
        List<ActivityParticipantDTO> originalParticipantDTOList = activityParticipantManager.queryActivityParticipantByMktIdAndStatus(originalActivity.getId(), PromoConst.Status.Audited.getCode());
        List<ActivityParticipantDTO> changedParticipantDTOList = changedActivity.getActivityParticipantList();
        // 比较得出参与对象变更项
        List<ActivityParticipantDTO> newParticipantDTOList = new ArrayList<>();
        String participantType = changedActivity.getActivityParticipantType();
        // 8.1如果是"按条件过滤"的参与对象变更，用如下逻辑判断变更项
        if (!CollectionUtils.isEmpty(changedParticipantDTOList) && changedParticipantDTOList.size() == originalParticipantDTOList.size()&&PromoConst.ActivityParticipantType.ACTIVITY_PARTICIPANT_TYPE_30.getCode().equals(participantType)) {
            ActivityParticipantDTO changedParticipantDTO = changedParticipantDTOList.get(0);
            String originalParticipantFilterValue = originalParticipantDTOList.get(0).getFilterValue();
            String changeParticipantFilterValue = changedParticipantDTO.getFilterValue();
            if (!originalParticipantFilterValue.equals(changeParticipantFilterValue)) {
                changedParticipantDTO.setOperType(PromoConst.OperType.MOD.getCode());
                newParticipantDTOList.add(changedParticipantDTO);
            }
        }
        // 8.2如果是"按地市/按商家"的参与对象的新增，用如下逻辑判断新增项
        if (!CollectionUtils.isEmpty(changedParticipantDTOList) && changedParticipantDTOList.size() > originalParticipantDTOList.size()) {
            List<String> originalParticipantLanIds = originalParticipantDTOList.stream().map(ActivityParticipantDTO::getLanId).collect(Collectors.toList());
            List<String> originalParticipantCitys = originalParticipantDTOList.stream().map(ActivityParticipantDTO::getCity).collect(Collectors.toList());
            List<String> originalParticipantMerchantCodes = originalParticipantDTOList.stream().map(ActivityParticipantDTO::getMerchantCode).collect(Collectors.toList());
            for (ActivityParticipantDTO changedParticipantDTO : changedParticipantDTOList) {
                if (changedParticipantDTO == null || "null".equals(changedParticipantDTO)) {
                    continue;
                }
                String lanId = changedParticipantDTO.getLanId() == null ? "" : changedParticipantDTO.getLanId();
                String city = changedParticipantDTO.getCity() == null ? "" : changedParticipantDTO.getCity();
                String merchantCode = changedParticipantDTO.getMerchantCode() == null ? "" : changedParticipantDTO.getMerchantCode();
                if (originalParticipantLanIds.contains(lanId) || originalParticipantCitys.contains(city)) {
                    continue;
                }
                if (originalParticipantMerchantCodes.contains(merchantCode)) {
                    continue;
                }

                //添加到变更项列表
                changedParticipantDTO.setOperType(PromoConst.OperType.ADD.getCode());
                newParticipantDTOList.add(changedParticipantDTO);
            }

        }
        // 8.3进行参与对象变更项插入和变更记录新增
        for (ActivityParticipantDTO newParticipantDTO : newParticipantDTOList){
            //如果该范围不在原参与对象里，准备进行添加，并写变更详情表
            ActivityParticipant activityParticipant = new ActivityParticipant();
            BeanUtils.copyProperties(newParticipantDTO, activityParticipant);
            activityParticipant.setMarketingActivityId(originalActivity.getId());
            Date dt = new Date();
            activityParticipant.setGmtCreate(dt);
            activityParticipant.setGmtModified(dt);
            activityParticipant.setIsDeleted(PromoConst.IsDelete.IS_DELETE_CD_0.getCode());
            activityParticipant.setStatus(PromoConst.Status.WaitAudit.getCode());
            activityParticipantManager.insertActivityParticipant(activityParticipant);
            //封装变更明细信息
            ActivityChangeDetail activityChangeDetail = new ActivityChangeDetail();
            activityChangeDetail.setOperType(newParticipantDTO.getOperType());
            activityChangeDetail.setTableName(ActivityParticipant.TNAME);
            activityChangeDetail.setKeyValue(activityParticipant.getId());
            activityChangeDetail.setFieldType(changedActivity.getActivityParticipantType());
            activityChangeDetails.add(activityChangeDetail);
        }
    }

    /**
     * date 转换成 time字符
     *
     * @param date
     * @return
     */
    private String dateToTimeStr(Date date) {
        if (Objects.isNull(date)) {
            return null;
        }
        return String.valueOf(date.getTime());
    }


    /**
     * 营销活动变更审核启动流程
     *
     * @param activityName 流程名（这里使用活动名称）
     * @param userId       发起人id
     * @param userName     发起人用户名
     * @param orgName      发起人组织
     * @param sysPostName  发起人岗位名称
     * @param activityId   业务id
     * @param changeId     变更id
     * @return
     */
    private ResultVO activityChangeAuitStartProcess(String activityName, String userId, String userName, String orgName, String sysPostName, String activityId, String changeId) {
        log.info("MarketingActivityServiceImpl.activityChangeAuitStartProcess activityName={},userId={}, userName{}," +
                "orgName{}, sysPostName{}, activityId{}", activityName, userId, userName, orgName, sysPostName, activityId);
        ProcessStartReq processStartDTO = new ProcessStartReq();
        processStartDTO.setTitle(activityName);
        //创建流程者，参数需要提供
        processStartDTO.setApplyUserId(userId);
        processStartDTO.setApplyUserName(userName);
        processStartDTO.setExtends1(orgName + sysPostName);
        processStartDTO.setProcessId("3060101");
        processStartDTO.setFormId(activityId);
        processStartDTO.setParamsType(2);
        processStartDTO.setParamsValue(changeId);
        //TASK_SUB_TYPE_1142("1","1142","营销活动变更流程");
        processStartDTO.setTaskSubType(WorkFlowConst.TASK_SUB_TYPE.TASK_SUB_TYPE_1142.getTaskSubType());
        log.info("MarketingActivityServiceImpl.activityChangeAuitStartProcess req={}", JSON.toJSONString(processStartDTO));
        //开启流程
        return taskService.startProcess(processStartDTO);
    }

    /**
     * 营销活动审核启动流程
     *
     * @param userId,invoiceId
     * @return
     */
    @Override
    public ResultVO marketingActivityAuitStartProcess(String mktName, String userId, String userName, String orgName, String sysPostName, String marketId) {
        log.info("MarketingActivityServiceImpl.marketingActivityAuit userId={} userName{}" +
                        "lanId{} regionId{} marketId{}",
                userId, userName, orgName, sysPostName, marketId);
        ProcessStartReq processStartDTO = new ProcessStartReq();
        processStartDTO.setTitle(mktName);
        //创建流程者，参数需要提供
        processStartDTO.setApplyUserId(userId);
        processStartDTO.setApplyUserName(userName);
        processStartDTO.setExtends1(orgName + sysPostName);
        processStartDTO.setProcessId("4");
        processStartDTO.setFormId(marketId);
        //TASK_SUB_TYPE_1140 销售活动配置流程
        processStartDTO.setTaskSubType(WorkFlowConst.TASK_SUB_TYPE.TASK_SUB_TYPE_1140.getTaskSubType());
        ResultVO taskServiceRV = new ResultVO();
        try {
            //开启流程
            taskServiceRV = taskService.startProcess(processStartDTO);
            return ResultVO.success();
        } catch (Exception ex) {
            return ResultVO.error();
        } finally {
            log.info("MarketingActivityServiceImpl.marketingActivityAuit req={},resp={}",
                    JSON.toJSONString(processStartDTO), JSON.toJSONString(taskServiceRV));
        }
    }


    /**
     * 根据优惠ID集合查询 营销活动集合
     *
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
     *
     * @param activityId
     * @return
     */
    @Override
    public ResultVO<MarketingActivityInfoResp> queryMarketingActivityInfo(String activityId) {
        MarketingActivityInfoResp marketingActivityInfoResp = new MarketingActivityInfoResp();

        //营销活动基本信息
        MarketingActivityDetailResp marketingActivityDetailResp = queryMarketingActivity(activityId).getResultData();
        marketingActivityInfoResp.setMarketingActivityDetailResp(marketingActivityDetailResp);
        QueryPreSubsidyReqDTO queryPreSubsidyReqDTO = new QueryPreSubsidyReqDTO();
        queryPreSubsidyReqDTO.setMarketingActivityId(activityId);
        if (PromoConst.ACTIVITYTYPE.PRESUBSIDY.getCode().equals(marketingActivityDetailResp.getActivityType())) {
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
        } else if (PromoConst.ACTIVITYTYPE.BOOKING.getCode().equals(marketingActivityDetailResp.getActivityType())) {
            QueryMarketingActivityReq queryMarketingActivityReq = new QueryMarketingActivityReq();
            queryMarketingActivityReq.setMarketingActivityId(activityId);
            ResultVO<List<PreSubsidyProductRespDTO>> listResultVO = activityProductService.queryPreSaleProduct(queryMarketingActivityReq);
            marketingActivityInfoResp.setPreSaleProductInfo(listResultVO.getResultData());
        }
        return ResultVO.success(marketingActivityInfoResp);
    }

    /**
     * 根据营销活动ID查询营销活动、优惠券、参与产品详情
     *
     * @param activityId
     * @return
     */
    @Override
    public ResultVO<MarketingActivityInfoResp> queryMarketingActivityInfor(String activityId) {
        MarketingActivityInfoResp marketingActivityInfoResp = new MarketingActivityInfoResp();

        //营销活动基本信息
        MarketingActivityDetailResp marketingActivityDetailResp = queryMarketingActivityFor(activityId).getResultData();
        marketingActivityInfoResp.setMarketingActivityDetailResp(marketingActivityDetailResp);
        QueryPreSubsidyReqDTO queryPreSubsidyReqDTO = new QueryPreSubsidyReqDTO();
        queryPreSubsidyReqDTO.setMarketingActivityId(activityId);
        if (PromoConst.ACTIVITYTYPE.PRESUBSIDY.getCode().equals(marketingActivityDetailResp.getActivityType())) {
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
            //前置补贴活动
            List<com.iwhalecloud.retail.rights.dto.response.PreSubsidyProductPromResqDTO> productList =
                    preSubsidyCouponService.queryPreSubsidyProductInfo(queryPreSubsidyReqDTO).getResultData();
            if (productList != null && productList.size() > 0) {
                for (int i = 0; i < productList.size(); i++) {
                    PreSubsidyProductPromResqDTO preSubsidyProductPromResqDTO = new PreSubsidyProductPromResqDTO();
                    BeanUtils.copyProperties(productList.get(i), preSubsidyProductPromResqDTO);

                    // 取前置补贴活动价 zhong.wenlong
                    if (Objects.nonNull(productList.get(i))) {
                        preSubsidyProductPromResqDTO.setPrice(productList.get(i).getActivityProductResq().getPrice());
                        preSubsidyProductPromResqDTO.setProductPic(productList.get(i).getActivityProductResq().getProductPic());
                        preSubsidyProductPromResqDTO.setProductPicUseType(productList.get(i).getActivityProductResq().getProductPicUseType());
                    }

                    preSubsidyProductPromResqDTO.setNum(productList.get(i).getActivityProductResq().getNum());
                    preSubsidyProductPromResqDTO.setDiscountAmount(productList.get(i).getActivityProductResq().getDiscountAmount());
                    preSubsidyProductPromResqDTOS.add(preSubsidyProductPromResqDTO);
                }
            }
            marketingActivityInfoResp.setQueryPreSubsidyProductPromResqDTOList(preSubsidyProductPromResqDTOS);
        } else if (PromoConst.ACTIVITYTYPE.BOOKING.getCode().equals(marketingActivityDetailResp.getActivityType())) {
            QueryMarketingActivityReq queryMarketingActivityReq = new QueryMarketingActivityReq();
            queryMarketingActivityReq.setMarketingActivityId(activityId);
            //预售活动
            ResultVO<List<PreSubsidyProductRespDTO>> listResultVO = activityProductService.queryPreSaleProductInfo(queryMarketingActivityReq);
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
    public ResultVO<MarketingActivityDTO> queryMarketingActivityByIdtime(QueryMarketingActivityReq queryMarketingActivityReq) {
        MarketingActivityDTO marketingActivityDTO = new MarketingActivityDTO();
        MarketingActivity marketingActivity = marketingActivityManager.queryMarketingActivityTime(queryMarketingActivityReq.getMarketingActivityId());
        BeanUtils.copyProperties(marketingActivity, marketingActivityDTO);
        return ResultVO.success(marketingActivityDTO);
    }

    @Override
    public ResultVO updatePreSaleActivityRule(MarketingActivityAddReq marketingActivityAddReq) {
        MarketingActivity marketingActivity = new MarketingActivity();
        BeanUtils.copyProperties(marketingActivityAddReq, marketingActivity);
        marketingActivity.setPayType(StringUtils.isEmpty(marketingActivityAddReq.getPayType()) ? PromoConst.PayType.PAY_TYPE_1.getCode() : marketingActivityAddReq.getPayType());
        marketingActivityManager.updatePreSaleActivityRule(marketingActivity);
        return ResultVO.success();
    }

    @Override
    public ResultVO<AdvanceActivityProductInfoResp> getAdvanceActivityProductInfo(AdvanceActivityProductInfoReq advanceActivityProductInfoReq) {
        log.info("MarketingActivityServiceImpl.getAdvanceActivityProductInfo-->advanceActivityProductInfoReq={}", JSON.toJSONString(advanceActivityProductInfoReq));
        if (StringUtils.isEmpty(advanceActivityProductInfoReq.getProductId())
                || StringUtils.isEmpty(advanceActivityProductInfoReq.getMarketingActivityId())) {
            log.error("MarketingActivityServiceImpl.getAdvanceActivityProductInfo--> !!miss args!!,productId={},activityId={}"
                    , advanceActivityProductInfoReq.getProductId(), advanceActivityProductInfoReq.getMarketingActivityId());
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
        log.info("MarketingActivityServiceImpl.queryMarketingActivityQueryBySupplier-->invalid activties size={}", marketingActivities.size());

        //2、过滤出当前卖家能参与的活动
        marketingActivities.forEach(marketingActivitity -> {
            ActivityAuthModel activityAuthModel = getActivityAuthModel(req.getSupplierId(), marketingActivitity);
            boolean hasPermission = sellerActivityFilter.doFilter(activityAuthModel);
            if (hasPermission) {
                MarketingActivityQueryBySupplierResp resp = new MarketingActivityQueryBySupplierResp();
                BeanUtils.copyProperties(marketingActivitity, resp);
                activityQueryBySupplierResps.add(resp);
            }
        });

        return ResultVO.success(activityQueryBySupplierResps);
    }

    /**
     * 获取营销活动校验模型
     *
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
                    , JSON.toJSONString(merchantDTOResultVO));

            //设置商家信息
            activityAuthModel.setMerchantSeller(merchantDTOResultVO.getResultData());
            return activityAuthModel;
        } else {
            //无商家信息了
            log.error("MarketingActivityServiceImpl.getActivityAuthModel get merchantDTOResultVO !!error!!,merchantDTOResultVO={}-->"
                    , JSON.toJSONString(merchantDTOResultVO));
        }

        return activityAuthModel;
    }

    @Override
    public ResultVO<List<MarketingActivityListResp>> queryInvalidActivity(MarketingActivityListReq marketingActivityListReq) {
        return ResultVO.success(marketingActivityManager.queryInvalidActivity(marketingActivityListReq));
    }

    /**
     * 根据活动id查询所有商家id
     *
     * @param marketingActivityId
     * @return
     */
    public ResultVO<List<String>> queryActMerchant(String marketingActivityId) {
        log.info("MarketingActivityServiceImpl.queryActMerchant--> marketingActivityId={}", marketingActivityId);
        List<String> strings = Lists.newArrayList();
        MarketingActivity marketingActivity = marketingActivityManager.queryMarketingActivity(marketingActivityId);

        if (marketingActivity == null) {
            return ResultVO.error("活动不存在");
        }
        List<ActivityParticipant> activityParticipants = activityParticipantManager.queryActivityParticipantByCondition(marketingActivityId);
        log.info("MarketingActivityServiceImpl.queryActMerchant queryActivityParticipantByCondition --> activityParticipants={}", JSON.toJSON(activityParticipants));
        if (activityParticipants.size() <= 0) {
            return ResultVO.success(strings);
        }
        String activityParticipantType = marketingActivity.getActivityParticipantType();
        if (PromoConst.ActivityParticipantType.ACTIVITY_PARTICIPANT_TYPE_20.getCode().equals(activityParticipantType)) {
            /** 参与对象表当前merchant_code字段存的是merchant_id*/
            strings = activityParticipants.stream().map(ActivityParticipant::getMerchantCode).collect(Collectors.toList());
        } else if (PromoConst.ActivityParticipantType.ACTIVITY_PARTICIPANT_TYPE_10.getCode().equals(activityParticipantType)) {
            //获取活动参与对象的地区集合
            List<String> lanList = Lists.newArrayList();
            List<String> cityList = Lists.newArrayList();
            activityParticipants.stream().filter((ActivityParticipant activityParticipant) ->
                    StringUtils.isNotEmpty(activityParticipant.getLanId()) || StringUtils.isNotEmpty(activityParticipant.getLanId())
            ).forEach((ActivityParticipant activityParticipant) -> {
                if (StringUtils.isNotEmpty(activityParticipant.getCity())) {
                    cityList.add(activityParticipant.getCity());
                } else {
                    lanList.add(activityParticipant.getLanId());
                }
            });
            if (lanList.size() > 0 || cityList.size() > 0) {
                MerchantListLanCityReq merchantListLanCityReq = new MerchantListLanCityReq();
                merchantListLanCityReq.setCityList(cityList);
                merchantListLanCityReq.setLanList(lanList);
                ResultVO<List<String>> listResultVO = merchantService.listMerchantByLanCity(merchantListLanCityReq);
                log.info("MarketingActivityServiceImpl.queryActMerchant listMerchantByLanCity --> listResultVO={}", JSON.toJSON(listResultVO));
                if (!listResultVO.isSuccess()) {
                    return ResultVO.error("获取商家失败");
                }
                strings = listResultVO.getResultData();
            }
        } else if (PromoConst.ActivityParticipantType.ACTIVITY_PARTICIPANT_TYPE_10.getCode().equals(activityParticipantType)) {
            List<String> participants = Lists.newArrayList();
            for (ActivityParticipant activityParticipant : activityParticipants) {
                String city = activityParticipant.getCity();
                String lanId = activityParticipant.getLanId();
                if (StringUtils.isNotEmpty(city) || StringUtils.isNotEmpty(lanId)) {
                    participants.add(StringUtils.isNotEmpty(city) ? city : lanId);
                }
            }
            if (participants.size() > 0) {
                MerchantListLanCityReq merchantListReq = new MerchantListLanCityReq();
                merchantListReq.setCityList(participants);
                ResultVO<List<String>> listResultVO = merchantService.listMerchantByLanCity(merchantListReq);
                log.info("MarketingActivityServiceImpl.queryActMerchant listMerchantByLanCity --> listResultVO={}", JSON.toJSON(listResultVO));
                for (String merchantDTO : listResultVO.getResultData()) {
                    strings.add(merchantDTO);
                }
            }
        }
        log.info("MarketingActivityServiceImpl.queryActMerchant --> strings={}", JSON.toJSON(strings));
        return ResultVO.success(strings);
    }

    @Override
    public ResultVO autoPushActivityCoupon(String marketingActivityId) {
        ResultVO<List<String>> listResultVO = queryActMerchant(marketingActivityId);
        if (ResultCodeEnum.ERROR.getCode().equals(listResultVO.getResultCode())) {
            return listResultVO;
        }
        if (listResultVO.getResultData().size() <= 0) {
            return ResultVO.error("该活动没有参与的商家");
        }
        //异步写优惠券推送任务表
        AutoPushCouponReq autoPushCouponReq = new AutoPushCouponReq();
        autoPushCouponReq.setMarketingActivityId(marketingActivityId);
        autoPushCouponReq.setMerchantIds(listResultVO.getResultData());
        mktResCouponTaskService.addMktResCouponTask(autoPushCouponReq);
        return ResultVO.success();
    }

    public ResultVO turnActMarket(String userId, String userName, String mktId) {
        updateMktStatus(mktId, PromoConst.STATUSCD.STATUS_CD_10.getCode());
        NextRouteAndReceiveTaskReq nextRouteAndReceiveTaskReq = new NextRouteAndReceiveTaskReq();
        nextRouteAndReceiveTaskReq.setFormId(mktId);
        nextRouteAndReceiveTaskReq.setHandlerUserId(userId);
        nextRouteAndReceiveTaskReq.setHandlerMsg("营销活动驳回审核");

        return taskService.nextRouteAndReceiveTask(nextRouteAndReceiveTaskReq);
    }

    public Boolean updateMktStatus(String mktId, String vatInvoiceStatus) {
        return marketingActivityManager.updateMarketingActivityById(mktId, vatInvoiceStatus);
    }

    @Override
    public ResultVO<List<MarketingActivityDTO>> queryActivityByProductId(String productId, String activityType) {
        List<MarketingActivity> marketingActivityList = marketingActivityManager.queryActivityByProductId(productId, activityType);
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
        updateMktStatus(req.getId(), PromoConst.STATUSCD.STATUS_CD_10.getCode());
        //营销活动审核 1.新增的时候进行审核
        log.info("MarketingActivityServiceImpl.addMarketingActivity marketingActivityId{}");
        if (org.springframework.util.StringUtils.isEmpty(req.getId())) {
            try {
                ResultVO auditMktResultVO = marketingActivityAuitStartProcess(req.getName(), req.getUserId(), req.getUserName(), req.getOrgId(), req.getSysPostName(), req.getId());
            } catch (Exception e) {
                log.info("MarketingActivityServiceImpl.addMarketingActivity 新增营销活动审核失败");
                e.printStackTrace();
            }
        } else {
            ResultVO<MarketingActivityDetailResp> respResultVO = queryMarketingActivity(req.getId());
            MarketingActivityDetailResp marketingActivityDetailResp = respResultVO.getResultData();
            //修改的时候 如果是审核通过或者是审核中 不起流程
            if (PromoConst.STATUSCD.STATUS_CD_20.getCode().equals(marketingActivityDetailResp.getStatus())
                    || PromoConst.STATUSCD.STATUS_CD_10.getCode().equals(marketingActivityDetailResp.getStatus())) {
                try {
                    ResultVO auditMktResultVO = marketingActivityAuitStartProcess(req.getName(), req.getUserId(), req.getUserName(), req.getOrgId(), req.getSysPostName(), req.getId());
                } catch (Exception e) {
                    log.info("MarketingActivityServiceImpl.addMarketingActivity 修改营销活动通过审核失败");
                    e.printStackTrace();
                }
            } else {
                try {
                    ResultVO workFlowResultVO = turnActMarket(req.getUserId(), req.getUserName(), req.getId());
                } catch (Exception e) {
                    log.info("MarketingActivityServiceImpl.addMarketingActivity 修改营销活动驳回审核失败");
                    e.printStackTrace();
                }
            }
        }
    }

    private class ActivityContent implements Serializable {
        public ActivityContent(Date deliverEndItme, String title, String content) {
            this.deliverEndItme = deliverEndItme;
            this.title = title;
            this.content = content;
        }

        Date deliverEndItme;
        String title;
        String content;
    }

    /**
     * 对活动发货时间截止前未发货的订单预警
     *
     * @return
     */
    @Override
    public void notifyMerchantActivityOrderDelivery() {
        // 从数据字典取提前预警天数
        ConfigInfoDTO configInfoDTO = configInfoService.getConfigInfoById(SysUserMessageConst.DELIVER_END_TIME_NOTIFY_DAYS);
        String earlyWarningDays = configInfoDTO.getCfValue();
        // 查询发货时间临近的活动
        List<MarketingActivity> marketingActivityList = marketingActivityManager.queryActivityOrderDeliveryClose(earlyWarningDays);
        if (CollectionUtils.isEmpty(marketingActivityList)) {
            return;
        }

        // 活动ID和发货截止时间
        Map<String, ActivityContent> activityIdAndDeliverEndTimeMap = new HashMap();
        for (MarketingActivity marketingActivity : marketingActivityList) {
            activityIdAndDeliverEndTimeMap.put(marketingActivity.getId(), new ActivityContent(marketingActivity.getDeliverEndTime(), marketingActivity.getName(), marketingActivity.getDescription()));
        }

        // 根据活动ID查询活动购买记录
        List<HistoryPurchase> historyPurchaseList = historyPurchaseManager.queryHistoryPurchaseByMarketingActivityId(Lists.newArrayList(activityIdAndDeliverEndTimeMap.keySet()));
        if (CollectionUtils.isEmpty(historyPurchaseList)) {
            return;
        }

        // 查询未完成发货订单信息
        List<String> orderIdList;
        for (HistoryPurchase historyPurchase : historyPurchaseList) {
            orderIdList = Lists.newArrayList();
            orderIdList.add(historyPurchase.getOrderId());
            List<OrderDTO> orderDTOList = orderSelectOpenService.selectNotDeliveryOrderByIds(orderIdList);

            if (CollectionUtils.isEmpty(orderDTOList)) {
                continue;
            }

            // 根据ordeId查询任务信息获取任务Id
            for (OrderDTO orderDTO : orderDTOList) {
                List<TaskDTO> taskDTOs = taskService.getTaskByFormId(orderDTO.getOrderId()).getResultData();
                if (CollectionUtils.isEmpty(taskDTOs)) {
                    continue;
                }
                // 组装用户消息准备入库
                for (TaskDTO taskDTO : taskDTOs) {
                    SysUserMessageDTO sysUserMessageDTO = new SysUserMessageDTO();
                    sysUserMessageDTO.setTaskId(taskDTO.getTaskId());
                    sysUserMessageDTO.setEndTime(activityIdAndDeliverEndTimeMap.get(historyPurchase.getMarketingActivityId()).deliverEndItme);
                    sysUserMessageDTO.setTitle(activityIdAndDeliverEndTimeMap.get(historyPurchase.getMarketingActivityId()).title + SysUserMessageConst.NOTIFY_ACTIVITY_ORDER_DELIVERY_TITLE);
                    sysUserMessageDTO.setContent(activityIdAndDeliverEndTimeMap.get(historyPurchase.getMarketingActivityId()).content);
                    if (!Objects.isNull(orderDTO.getMerchantId())) {
                        MerchantDTO merchantDTO = merchantService.getMerchantInfoById(orderDTO.getMerchantId());
                        if (!Objects.isNull(merchantDTO)) {
                            sysUserMessageDTO.setUserId(merchantDTO.getUserId());
                        }
                    }
                    sysUserMessageService.addSysUserMessage(sysUserMessageDTO);
                }
            }
        }
    }

    @Override
    public ResultVO marketingActivityModifyAuitStartProcess(String mktName, String userId, String userName, String orgName, String sysPostName, String id) {
        log.info("MarketingActivityServiceImpl.marketingActivityAuit userId={} userName{}" +
                        "lanId{} regionId{} marketId{}",
                userId, userName, orgName, sysPostName, id);
        ProcessStartReq processStartDTO = new ProcessStartReq();
        processStartDTO.setTitle(mktName);
        //创建流程者，参数需要提供
        processStartDTO.setApplyUserId(userId);
        processStartDTO.setApplyUserName(userName);
        processStartDTO.setExtends1(orgName + sysPostName);
        processStartDTO.setProcessId("10");
        //营销活动变更内容Id
        processStartDTO.setFormId(id);
        //TASK_SUB_TYPE_1140 销售活动配置流程
        processStartDTO.setTaskSubType(WorkFlowConst.TASK_SUB_TYPE.TASK_SUB_TYPE_2060.getTaskSubType());
        ResultVO taskServiceRV = new ResultVO();
        try {
            //开启流程
            taskServiceRV = taskService.startProcess(processStartDTO);
            return ResultVO.success();
        } catch (Exception ex) {
            return ResultVO.error();
        } finally {
            log.info("MarketingActivityServiceImpl.marketingActivityAuit req={},resp={}",
                    JSON.toJSONString(processStartDTO), JSON.toJSONString(taskServiceRV));
        }
    }

    @Override
    public void auitMarketingActivityMoify(AuitMarketingActivityReq req) {
//        if (org.springframework.util.StringUtils.isEmpty(req.getMarketingActivityModiftyId())){
//            try{
//                List<MarketingActivityModify> modifies =marketingActivityManager.queryMarketingActivityModifySize(req.getId());
//                String id= null;
//                if (!CollectionUtils.isEmpty(modifies)){
//                    id = modifies.get(modifies.size()-1).getId();
//                }
//                //审核变更内容表最新数据
//                ResultVO auditMktResultVO = marketingActivityModifyAuitStartProcess(req.getName(),req.getUserId(),req.getUserName(),req.getOrgId(),req.getSysPostName(), id);
//            }catch (Exception e){
//                log.info("MarketingActivityServiceImpl.addMarketingActivity 新增营销活动审核失败");
//                e.printStackTrace();
//            }
//        }
        List<MarketingActivityModify> modifies = marketingActivityManager.queryMarketingActivityModifySize(req.getId());
        MarketingActivityModify marketingActivityModify = new MarketingActivityModify();
        String id = null;
        if (!CollectionUtils.isEmpty(modifies)) {
            marketingActivityModify = modifies.get(modifies.size() - 1);
        }
        ResultVO<MarketingActivityDetailResp> respResultVO = queryMarketingActivity(req.getId());
        MarketingActivityDetailResp marketingActivityDetailResp = respResultVO.getResultData();
        //修改的时候 如果是审核通过或者是审核中 不起流程
        if (PromoConst.STATUSCD.STATUS_CD_20.getCode().equals(marketingActivityModify.getStatus())
                || PromoConst.STATUSCD.STATUS_CD_10.getCode().equals(marketingActivityModify.getStatus())) {
            try {
                ResultVO auditMktResultVO = marketingActivityModifyAuitStartProcess(req.getName(), req.getUserId(), req.getUserName(), req.getOrgId(), req.getSysPostName(), id);
            } catch (Exception e) {
                log.info("MarketingActivityServiceImpl.auitMarketingActivityMoify 变更营销活动通过审核失败");
                e.printStackTrace();
            }
        } else {
            try {
                ResultVO workFlowResultVO = turnActMarket(req.getUserId(), req.getUserName(), id);
            } catch (Exception e) {
                log.info("MarketingActivityServiceImpl.auitMarketingActivityMoify 变更营销活动驳回审核失败");
                e.printStackTrace();
            }
        }
    }
}