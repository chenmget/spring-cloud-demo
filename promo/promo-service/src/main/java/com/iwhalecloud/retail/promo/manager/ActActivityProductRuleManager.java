package com.iwhalecloud.retail.promo.manager;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.promo.common.PromoConst;
import com.iwhalecloud.retail.promo.dto.ActActivityProductRuleDTO;
import com.iwhalecloud.retail.promo.dto.req.ActivityProductReq;
import com.iwhalecloud.retail.promo.dto.req.ReBateActivityListReq;
import com.iwhalecloud.retail.promo.dto.resp.ReBateActivityListResp;
import com.iwhalecloud.retail.promo.entity.ActActivityProductRule;
import com.iwhalecloud.retail.promo.entity.ActivityProduct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import javax.annotation.Resource;
import com.iwhalecloud.retail.promo.mapper.ActActivityProductRuleMapper;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;


@Component
@Slf4j
public class ActActivityProductRuleManager extends ServiceImpl<ActActivityProductRuleMapper, ActActivityProductRule>{
    @Resource
    private ActActivityProductRuleMapper actActivityProductRuleMapper;

    /**
     * 根据营销活动ID删除参与活动规则产品
     * @param marketingActivityId
     * @return
     */
    public Integer deleteActivityProductRule(String marketingActivityId) {
        QueryWrapper<ActActivityProductRule> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(ActActivityProductRule.FieldNames.actProdRelId.getTableFieldName(), marketingActivityId);
        queryWrapper.eq(ActActivityProductRule.FieldNames.isDeleted.getTableFieldName(), PromoConst.IsDelete.IS_DELETE_CD_0.getCode());
        return actActivityProductRuleMapper.delete(queryWrapper);
    }
    /**
     * 删除返利活动规则产品
     * @param activityProductReq
     * @return
     */
    public ResultVO deleteReBateProductRuleActivity(ActivityProductReq activityProductReq) {
        log.info("ActivityProductServiceImpl.saveActProduct activityProductReq={}", JSON.toJSON(activityProductReq));
        if (StringUtils.isEmpty(activityProductReq.getMarketingActivityId()) ||
                StringUtils.isEmpty(activityProductReq.getProductId())){
            return  ResultVO.error("ActivityProductServiceImpl.deleteReBateProductActivity activityProductReq is null");
        }
        activityProductReq.setGmtModified(new Date());
        return actActivityProductRuleMapper.deleteReBateProductRuleActivity(activityProductReq);
    }
    public List<ActActivityProductRule> queryActActivityProductRuleDTO(String marketingId,String prodId){
        QueryWrapper<ActActivityProductRule> queryWrapper = new QueryWrapper<>();

        if (org.apache.commons.lang3.StringUtils.isNotEmpty(marketingId)) {
            queryWrapper.in(ActActivityProductRule.FieldNames.actProdRelId.getTableFieldName(), marketingId);
        }

        if (org.apache.commons.lang3.StringUtils.isNotEmpty(prodId)) {
            queryWrapper.eq(ActActivityProductRule.FieldNames.productId.getTableFieldName(),prodId);
        }
        queryWrapper.eq(ActActivityProductRule.FieldNames.isDeleted.getTableFieldName(),PromoConst.IsDelete.IS_DELETE_CD_0.getCode());
        return actActivityProductRuleMapper.selectList(queryWrapper);
    }
    /**
     * 查询返利活动列表
     * @param req 查询返利活动列表
     * @return
     */
    public Page<ReBateActivityListResp> listMarketingActivity(ReBateActivityListReq req) {
        Page<ReBateActivityListResp> page = new Page<>(req.getPageNo(), req.getPageSize());
        Page<ReBateActivityListResp> reBatePage = actActivityProductRuleMapper.listReBateActivity(page, req);
        log.info("ActActivityProductRuleManager.listMarketingActivity reBatePage{}",reBatePage);
        return reBatePage;
    }
}
