package com.iwhalecloud.retail.promo.manager;

import com.iwhalecloud.retail.promo.common.PromoConst;
import com.iwhalecloud.retail.promo.entity.Promotion;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.iwhalecloud.retail.promo.mapper.PromotionMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;



@Component
public class PromotionManager {
    @Resource
    private PromotionMapper promotionMapper;

    /**
     * 添加优惠信息
     * @param promotionList 优惠信息
     * @return
     */
    public void addPromotion(List<Promotion> promotionList) {
        if (!CollectionUtils.isEmpty(promotionList)) {
            for (Promotion promotion : promotionList) {
                if (StringUtils.isEmpty(promotion.getId())) {
                    Date date = new Date();
                    promotion.setGmtCreate(date);
                    promotion.setGmtModified(date);
                    promotion.setIsDeleted("0");
                    promotionMapper.insert(promotion);
                }
            }
        }
    }
    /**
     * 删除优惠信息
     * @param marketingActivityId 优惠信息
     * @return
     */
    public void deletePromotion(String marketingActivityId) {

        promotionMapper.deletePromotion(marketingActivityId);

    }
    /**
     * 根据活动id查询优惠信息
     * @param marketingActivityId 根据活动id查询优惠信息
     * @return
     */
    public List<Promotion> queryPromotionByCondition(String marketingActivityId) {
        QueryWrapper<Promotion> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(Promotion.FieldNames.isDeleted.getTableFieldName(),PromoConst.IsDelete.IS_DELETE_CD_0.getCode());
        queryWrapper.eq(Promotion.FieldNames.marketingActivityId.getTableFieldName(), marketingActivityId);
        return promotionMapper.selectList(queryWrapper);
    }


    /**
     * 根据活动编码和优惠类型查询活动优惠
     * @param marketingActivityIds 活动编码
     * @param promotionType 优惠类型
     * @return 活动优惠
     */
    public List<Promotion> getPromotion(List<String> marketingActivityIds, java.lang.String promotionType) {
        QueryWrapper queryWrapper = new QueryWrapper<>();
        queryWrapper.in(Promotion.FieldNames.marketingActivityId.getTableFieldName(),marketingActivityIds);
        queryWrapper.eq(Promotion.FieldNames.promotionType.getTableFieldName(),promotionType);
        queryWrapper.eq(Promotion.FieldNames.isDeleted.getTableFieldName(), PromoConst.IsDelete.IS_DELETE_CD_0.getCode());
        queryWrapper.apply(Promotion.FieldNames.promotionEffectTime.getTableFieldName() + " <= now()");
        queryWrapper.apply(Promotion.FieldNames.promotionOverdueTime.getTableFieldName() + " >= now()");
        return promotionMapper.selectList(queryWrapper);
    }

    public Promotion getPromotoObj(String mktResId, String promotionType){
        QueryWrapper queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(Promotion.FieldNames.mktResId.getTableFieldName(), mktResId);
        queryWrapper.eq(Promotion.FieldNames.promotionType.getTableFieldName(),promotionType);
        queryWrapper.eq(Promotion.FieldNames.isDeleted.getTableFieldName(), PromoConst.IsDelete.IS_DELETE_CD_0.getCode());
        queryWrapper.apply(Promotion.FieldNames.promotionEffectTime.getTableFieldName() + " <= now()");
        queryWrapper.apply(Promotion.FieldNames.promotionOverdueTime.getTableFieldName() + " >= now()");
        return promotionMapper.selectOne(queryWrapper);
    }

    /**
     * 新增活动优惠信息
     * @param promotion
     * @return
     */
    public Integer addActPromotion(Promotion promotion){
        return promotionMapper.insert(promotion);
    }


    /**
     * 根据优惠ID集合查询 优惠信息集合
     * @param mktResIdList
     * @return 优惠信息集合
     */
    public List<Promotion> getPromotionsByMktResIds(List<String> mktResIdList) {
        QueryWrapper queryWrapper = new QueryWrapper<>();
        queryWrapper.in(Promotion.FieldNames.mktResId.getTableFieldName(), mktResIdList);
        return promotionMapper.selectList(queryWrapper);
    }

    public Integer deleteActPromotion(String marketingActivityId,String mktResId){
        Promotion promotion = new Promotion();
        promotion.setIsDeleted(PromoConst.IsDelete.IS_DELETE_CD_1.getCode());
        QueryWrapper<Promotion> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(Promotion.FieldNames.mktResId.getTableFieldName(), mktResId);
        queryWrapper.eq(Promotion.FieldNames.marketingActivityId.getTableFieldName(), marketingActivityId);
        return promotionMapper.update(promotion,queryWrapper);
    }

    /**
     *查询活动配置的优惠券
     * @param marketingActivityId
     * @param promotionType
     * @return
     */
    public List<Promotion> queryActPromotion(String marketingActivityId, java.lang.String promotionType) {
        QueryWrapper queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(Promotion.FieldNames.marketingActivityId.getTableFieldName(),marketingActivityId);
        queryWrapper.eq(Promotion.FieldNames.promotionType.getTableFieldName(),promotionType);
        queryWrapper.eq(Promotion.FieldNames.isDeleted.getTableFieldName(),PromoConst.IsDelete.IS_DELETE_CD_0.getCode());
        return promotionMapper.selectList(queryWrapper);
    }
}
