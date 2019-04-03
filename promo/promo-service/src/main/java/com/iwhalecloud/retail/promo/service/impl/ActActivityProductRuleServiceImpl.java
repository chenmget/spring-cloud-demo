package com.iwhalecloud.retail.promo.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.promo.common.PromoConst;
import com.iwhalecloud.retail.promo.constant.Constant;
import com.iwhalecloud.retail.promo.dto.ActActivityProductRuleDTO;
import com.iwhalecloud.retail.promo.dto.ActivityProductDTO;
import com.iwhalecloud.retail.promo.dto.ActivityRuleDTO;
import com.iwhalecloud.retail.promo.dto.req.*;
import com.iwhalecloud.retail.promo.dto.resp.ActActivityProductRuleServiceResp;
import com.iwhalecloud.retail.promo.dto.resp.ReBateActivityListResp;
import com.iwhalecloud.retail.promo.entity.ActActivityProductRule;
import com.iwhalecloud.retail.promo.entity.ActivityProduct;
import com.iwhalecloud.retail.promo.entity.ActivityRule;
import com.iwhalecloud.retail.promo.entity.MarketingActivity;
import com.iwhalecloud.retail.promo.manager.ActivityProductManager;
import com.iwhalecloud.retail.promo.manager.ActivityRuleManager;
import com.iwhalecloud.retail.promo.manager.MarketingActivityManager;
import com.iwhalecloud.retail.promo.service.MarketingActivityService;
import com.iwhalecloud.retail.promo.utils.ReflectUtils;
import com.iwhalecloud.retail.workflow.service.TaskService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import com.alibaba.dubbo.config.annotation.Service;
import com.iwhalecloud.retail.promo.manager.ActActivityProductRuleManager;
import com.iwhalecloud.retail.promo.service.ActActivityProductRuleService;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;


@Service
@Component("actActivityProductRuleService")
@Slf4j
public class ActActivityProductRuleServiceImpl implements ActActivityProductRuleService {

    @Autowired
    private ActActivityProductRuleManager actActivityProductRuleManager;

    @Autowired
    private ActivityRuleManager activityRuleManager;

    @Autowired
    private ActivityProductManager activityProductManager;

    @Reference
    private TaskService taskService;

    @Reference
    private MarketingActivityService marketingActivityService;

    @Autowired
    private MarketingActivityManager marketingActivityManager;

    @Autowired
    private Constant constant;

    @Override
    public ResultVO deleteReBateProductRuleActivity(ActivityProductReq activityProductReq) {
        log.info("ActActivityProductRuleServiceImpl.deleteReBateProductRuleActivity activityProductReq={}", JSON.toJSON(activityProductReq));
        if (null == activityProductReq){
            return  ResultVO.error("ActivityProductServiceImpl.deleteReBateProductActivity activityProductReq is null");
        }
        ResultVO ActivityProductRuleResultVO = actActivityProductRuleManager.deleteReBateProductRuleActivity(activityProductReq);
        if (ActivityProductRuleResultVO.isSuccess()){
            return  ResultVO.error(constant.getUpdateSuccess());
        }else {
            return  ResultVO.error(constant.getUpdateFaile());
        }
    }

    @Override
    public ResultVO<ActActivityProductRuleServiceResp> queryActActivityProductRuleServiceResp(String marketingActivityId,String prodId) {
        log.info("ActActivityProductRuleServiceImpl.queryActActivityProductRuleServiceResp marketingActivityId={}", marketingActivityId);
        if (StringUtils.isEmpty(marketingActivityId)){
            return ResultVO.error("ActActivityProductRuleServiceImpl.queryActActivityProductRuleServiceResp marketingActivityId is null");
        }
        ActActivityProductRuleServiceResp resp = new ActActivityProductRuleServiceResp();
        // 根据活动Id查询活动规则
        List<ActivityRule> activityRuleList = activityRuleManager.queryActivityRuleByCondition(marketingActivityId);
        if (!CollectionUtils.isEmpty(activityRuleList)) {
            List<ActivityRuleDTO> activityRuleDTOList = ReflectUtils.batchAssign(activityRuleList, ActivityRuleDTO.class);
            resp.setActivityRuleDTOList(activityRuleDTOList);
        }
        // 根据活动Id查询参与活动产品
        List<String> marketingActivityIds = Lists.newArrayList();
        marketingActivityIds.add(marketingActivityId);
        ActivityProductListReq activityProductListReq = new ActivityProductListReq();
        activityProductListReq.setProductId(prodId);
        activityProductListReq.setMarketingActivityIds(marketingActivityIds);
        List<ActivityProduct> activityGoodsList = activityProductManager.queryActivityProductByCondition(activityProductListReq);
        if (!CollectionUtils.isEmpty(activityGoodsList)) {
            List<ActivityProductDTO> activityProductDTOList = ReflectUtils.batchAssign(activityGoodsList, ActivityProductDTO.class);
            resp.setActivityProductDTOS(activityProductDTOList);
        }
        //查询活动规则对象
        List<ActActivityProductRule> actActivityProductRules = actActivityProductRuleManager.queryActActivityProductRuleDTO(marketingActivityId,prodId);
        if (!CollectionUtils.isEmpty(actActivityProductRules)) {
            List<ActActivityProductRuleDTO> actActivityProductRuleDTOS = ReflectUtils.batchAssign(actActivityProductRules, ActActivityProductRuleDTO.class);
            resp.setActActivityProductRuleDTOS(actActivityProductRuleDTOS);
        }
        log.info("ActActivityProductRuleServiceImpl.queryActActivityProductRuleServiceResp resp={}", JSON.toJSONString(resp));
        return ResultVO.success(resp);
    }
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultVO addReBateProduct(ActReBateProductReq actReBateProductReq) {
        log.info("ActivityProductServiceImpl.addReBateProduct actReBateProductReq={}",JSON.toJSON(actReBateProductReq));
        if(actReBateProductReq.getActivityProductReqs().size()<=0){
            return ResultVO.error("返利产品配置信息为空");
        }
        String marketingActivityId= actReBateProductReq.getMarketingActivityId();
        log.info("ActivityProductServiceImpl.addReBateProduct marketingActivityId={}",marketingActivityId);
        if (StringUtils.isEmpty(marketingActivityId)){
            return ResultVO.error("活动Id为空");
        }
        List<ActivityProductReq> activityProductReqs = actReBateProductReq.getActivityProductReqs();
        activityProductManager.deleteActivityProduct(marketingActivityId);
        actActivityProductRuleManager.deleteActivityProductRule(marketingActivityId);
        List<ActivityProduct> activityProducts = Lists.newArrayList();
        List<ActActivityProductRule> actActivityProductRules = Lists.newArrayList();
        if (!CollectionUtils.isEmpty(activityProductReqs)){
            for (ActivityProductReq activityProductReq : activityProductReqs) {
                ActivityProduct activityProduct = new ActivityProduct();
                BeanUtils.copyProperties(activityProductReq, activityProduct);
                activityProduct.setIsDeleted(PromoConst.IsDelete.IS_DELETE_CD_0.getCode());
                activityProduct.setMarketingActivityId(actReBateProductReq.getMarketingActivityId());
                activityProduct.setCreator(actReBateProductReq.getUserId());
                activityProducts.add(activityProduct);
                //添加活动产品规则信息
                ActActivityProductRule actActivityProductRule = new ActActivityProductRule();
                //BeanUtils.copyProperties(actReBateProductReq.getActActivityProductRuleDTO(), actActivityProductRule);
                actActivityProductRule.setCreator(actReBateProductReq.getUserId());
                actActivityProductRule.setActProdRelId(marketingActivityId);
                actActivityProductRule.setGmtCreate(new Date());
                actActivityProductRule.setGmtModified(new Date());
                actActivityProductRule.setModifier(actReBateProductReq.getUserId());
                actActivityProductRule.setActProdRelId(actReBateProductReq.getMarketingActivityId());
                actActivityProductRule.setProductId(activityProductReq.getProductId());
                actActivityProductRule.setIsDeleted(PromoConst.IsDelete.IS_DELETE_CD_0.getCode());
                //达量
                actActivityProductRule.setRuleAmount(String.valueOf(activityProductReq.getReachAmount()));
                //返利金额
                actActivityProductRule.setPrice(String.valueOf(activityProductReq.getRebatePrice()));
                actActivityProductRules.add(actActivityProductRule);
            }
            log.info("ActivityProductServiceImpl.addReBateProduct  activityProducts={}",JSON.toJSON(activityProducts));
            log.info("ActivityProductServiceImpl.addReBateProduct  actActivityProductRules={}",JSON.toJSON(actActivityProductRules));
            activityProductManager.saveBatch(activityProducts);
            actActivityProductRuleManager.saveBatch(actActivityProductRules);
        }
        //返利 新增活动规则
        List<ActivityRuleDTO> activityRuleDTOList = actReBateProductReq.getActivityRuleDTOList();
        List<ActivityRule> activityRuleList = Lists.newArrayList();
        activityRuleManager.deleteActivityRule(marketingActivityId);
        if (!CollectionUtils.isEmpty(activityRuleDTOList)) {
            for (ActivityRuleDTO activityRuleDTO : activityRuleDTOList) {
                ActivityRule activityRule = new ActivityRule();
                BeanUtils.copyProperties(activityRuleDTO, activityRule);
                activityRule.setMarketingActivityId(marketingActivityId);
                activityRule.setIsDeleted(PromoConst.IsDelete.IS_DELETE_CD_0.getCode());
                activityRuleList.add(activityRule);
            }
            // 添加活动规则
            activityRuleManager.addActivityRule(activityRuleList);
        }
        //启动返利活动审核流程
        MarketingActivity marketingActivity = marketingActivityManager.queryMarketingActivity(marketingActivityId);
        AuitMarketingActivityReq auitMarketingActivityReq = new AuitMarketingActivityReq();
        BeanUtils.copyProperties(actReBateProductReq, auitMarketingActivityReq);
        auitMarketingActivityReq.setId(actReBateProductReq.getMarketingActivityId());
        auitMarketingActivityReq.setName(marketingActivity.getName());
        marketingActivityService.auitMarketingActivity(auitMarketingActivityReq);
        return ResultVO.success();
    }

    @Override
    public ResultVO<Page<ReBateActivityListResp>> listReBateActivity(ReBateActivityListReq req) {
        log.info("ActivityProductServiceImpl.listReBateActivity req={}", JSON.toJSONString(req));
        return ResultVO.success(actActivityProductRuleManager.listMarketingActivity(req));
    }

}