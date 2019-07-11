package com.iwhalecloud.retail.promo.manager;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.promo.common.PromoConst;
import com.iwhalecloud.retail.promo.dto.req.ActivityProductListReq;
import com.iwhalecloud.retail.promo.dto.req.ActivityProductReq;
import com.iwhalecloud.retail.promo.entity.ActivityProduct;
import com.iwhalecloud.retail.promo.mapper.ActivityProductMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Date;
import java.util.List;


@Component
public class ActivityProductManager extends ServiceImpl<ActivityProductMapper,ActivityProduct> {
    @Resource
    private ActivityProductMapper activityProductMapper;

    /**
     * 查询参与活动产品
     * @param req 活动产品请求对象
     * @return
     */
    public List<ActivityProduct> queryActivityProductByCondition(ActivityProductListReq req) {
        QueryWrapper<ActivityProduct> queryWrapper = new QueryWrapper<>();
        if (!CollectionUtils.isEmpty(req.getMarketingActivityIds())) {
            queryWrapper.in(ActivityProduct.FieldNames.marketingActivityId.getTableFieldName(), req.getMarketingActivityIds());
        }
        if (!StringUtils.isEmpty(req.getMarketingActivityId())) {
            queryWrapper.eq(ActivityProduct.FieldNames.marketingActivityId.getTableFieldName(),req.getMarketingActivityId());
        }
        if (!StringUtils.isEmpty(req.getProductId())) {
            queryWrapper.eq(ActivityProduct.FieldNames.productId.getTableFieldName(),req.getProductId());
        }
        queryWrapper.eq(ActivityProduct.FieldNames.isDeleted.getTableFieldName(),PromoConst.IsDelete.IS_DELETE_CD_0.getCode());
        queryWrapper.eq(ActivityProduct.FieldNames.status.getTableFieldName(),PromoConst.Status.Audited.getCode());
        return activityProductMapper.selectList(queryWrapper);
    }

    /**
     * 查询参与活动产品--重载1
     * @param marketingActivityId
     * @return
     */
    public List<ActivityProduct> queryActivityProductByCondition(String marketingActivityId) {
        //根据活动id查找活动有效产品
        if(StringUtils.isEmpty(marketingActivityId)){
            return null;
        }
        ActivityProductListReq req = new ActivityProductListReq();
        req.setMarketingActivityId(marketingActivityId);
        return this.queryActivityProductByCondition(req);
    }

    /**
     * 查询参与活动产品--重载2
     * @param marketingActivityIds
     * @return
     */
    public List<ActivityProduct> queryActivityProductByCondition(List<String> marketingActivityIds) {
        //根据活动id查找活动有效产品
        if(CollectionUtils.isEmpty(marketingActivityIds)){
            return null;
        }
        ActivityProductListReq req = new ActivityProductListReq();
        req.setMarketingActivityIds(marketingActivityIds);
        return this.queryActivityProductByCondition(req);
    }

    /**
     * 查询参与活动产品--重载3
     * @param marketingActivityIds
     * @return
     */
    public List<ActivityProduct> queryActivityProductByCondition(List<String> marketingActivityIds,String productId) {
        //根据活动id查找活动有效产品
        if(CollectionUtils.isEmpty(marketingActivityIds)&&StringUtils.isEmpty(productId)){
            return null;
        }
        ActivityProductListReq req = new ActivityProductListReq();
        req.setMarketingActivityIds(marketingActivityIds);
        req.setProductId(productId);
        return this.queryActivityProductByCondition(req);
    }

    /**
     * 查找活动商品的数量属性
     * @param activityId
     * @param productId
     * @return
     */
    public Long queryActProductSumByProduct(String activityId,String productId){
        ActivityProductListReq req = new ActivityProductListReq();
        req.setMarketingActivityId(activityId);
        req.setProductId(productId);
        List<ActivityProduct> activityProductList = queryActivityProductByCondition(req);
        if(!CollectionUtils.isEmpty(activityProductList)){
            ActivityProduct activityProduct = activityProductList.get(0);
            if(activityProduct!=null&&activityProduct.getNum()!=null){
                return activityProduct.getNum();
            }
        }
        return null;
    }

    /**
     * 新增参与活动产品
     * @param activityProduct
     * @return
     */
    public Integer insertProductActivity(ActivityProduct activityProduct){
        return activityProductMapper.insert(activityProduct);
    }

    /**
     * 根据营销活动ID删除参与活动产品
     * @param marketingActivityId
     * @return
     */
    public Integer deleteActivityProduct(String marketingActivityId) {
        QueryWrapper<ActivityProduct> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(ActivityProduct.FieldNames.marketingActivityId.getTableFieldName(), marketingActivityId);
        queryWrapper.eq(ActivityProduct.FieldNames.isDeleted.getTableFieldName(),PromoConst.IsDelete.IS_DELETE_CD_0.getCode());
        return activityProductMapper.delete(queryWrapper);
    }

}
