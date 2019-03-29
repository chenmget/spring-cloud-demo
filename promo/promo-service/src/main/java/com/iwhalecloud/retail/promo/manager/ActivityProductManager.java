package com.iwhalecloud.retail.promo.manager;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.promo.common.PromoConst;
import com.iwhalecloud.retail.promo.dto.req.ActivityProductListReq;
import com.iwhalecloud.retail.promo.dto.req.ActivityProductReq;
import com.iwhalecloud.retail.promo.entity.ActivityProduct;
import com.iwhalecloud.retail.promo.mapper.ActivityProductMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Date;
import java.util.List;


@Component
@Slf4j
public class ActivityProductManager extends ServiceImpl<ActivityProductMapper,ActivityProduct> {
    @Resource
    private ActivityProductMapper activityProductMapper;

    /**
     * 批量添加参与活动产品
     * @param activityProductList 参与活动产品实体
     * @return
     */
    public void addActivityProductBatch(List<ActivityProduct> activityProductList) {
        if (!CollectionUtils.isEmpty(activityProductList)) {
            for (ActivityProduct activityProduct : activityProductList) {
                addOrUpdateActivityProduct(activityProduct);
            }
        }
    }

    /**
     * 添加参与活动产品
     * @param activityProduct 参与活动产品实体
     * @return
     */
    public void addActivityProduct(ActivityProduct activityProduct) {
        addOrUpdateActivityProduct(activityProduct);
    }

    /**
     * 添加或修改参与活动产品
     * @param activityProduct 参与活动产品实体
     * @return
     */
    private void addOrUpdateActivityProduct(ActivityProduct activityProduct) {
        if (StringUtils.isEmpty(activityProduct.getId())) {
            activityProduct.setGmtCreate(new Date());
            activityProduct.setGmtModified(new Date());
            activityProduct.setIsDeleted("0");
            activityProductMapper.insert(activityProduct);
        }
    }
    /**
     * 删除参与活动产品
     * @param marketingActivityId 参与活动产品实体
     * @return
     */
    public void deleteActivityProductById(String marketingActivityId) {

        activityProductMapper.deleteActivityProduct(marketingActivityId);

    }

    /**
     * 根据活动id查询参与活动产品
     * @param marketingActivityId 根据活动id查询参与活动产品
     * @return
     */
    public List<ActivityProduct> queryActivityProductByCondition(String marketingActivityId) {
        List<String> marketingActivityIds = Arrays.asList(marketingActivityId);
        return queryActivityProductByCondition(marketingActivityIds);
    }


    /**
     * 根据活动id查询参与活动产品
     * @param marketingActivityIds 活动ID集合
     * @return
     */
    public List<ActivityProduct> queryActivityProductByCondition(List<String> marketingActivityIds) {
        QueryWrapper<ActivityProduct> queryWrapper = new QueryWrapper<>();
        queryWrapper.in(ActivityProduct.FieldNames.marketingActivityId.getTableFieldName(), marketingActivityIds);
        queryWrapper.eq(ActivityProduct.FieldNames.isDeleted.getTableFieldName(),PromoConst.IsDelete.IS_DELETE_CD_0.getCode());
        return activityProductMapper.selectList(queryWrapper);
    }

    /**
     * 根据产品ID查询参与活动产品
     * @param productId 产品ID
     * @return
     */
    public List<ActivityProduct> queryActivityProductByProductId(String productId) {
        QueryWrapper<ActivityProduct> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(ActivityProduct.FieldNames.isDeleted.getTableFieldName(),PromoConst.UNDELETED);
        queryWrapper.eq(ActivityProduct.FieldNames.productId.getTableFieldName(), productId);
        return activityProductMapper.selectList(queryWrapper);
    }

    /**
     * 根据活动id查询参与活动产品
     * @param req 活动产品请求对象
     * @return
     */
    public List<ActivityProduct> queryActivityProductByCondition(ActivityProductListReq req) {
        QueryWrapper<ActivityProduct> queryWrapper = new QueryWrapper<>();

        if (!CollectionUtils.isEmpty(req.getMarketingActivityIds())) {
            queryWrapper.in(ActivityProduct.FieldNames.marketingActivityId.getTableFieldName(), req.getMarketingActivityIds());
        }

        if (!StringUtils.isEmpty(req.getProductId())) {
            queryWrapper.eq(ActivityProduct.FieldNames.productId.getTableFieldName(),req.getProductId());
        }
        queryWrapper.eq(ActivityProduct.FieldNames.isDeleted.getTableFieldName(),PromoConst.IsDelete.IS_DELETE_CD_0.getCode());
        return activityProductMapper.selectList(queryWrapper);
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

    /**
     * 查询活动产品的参与总数
     * @param marketingActivityId
     * @return
     */
    public Long queryActProductSum(String marketingActivityId){
        return activityProductMapper.queryActProductSum(marketingActivityId);
    }
    public Long queryActProductSumByProduct(String activityId,String productId){
        QueryWrapper<ActivityProduct> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(ActivityProduct.FieldNames.marketingActivityId.getTableFieldName(), activityId);
        queryWrapper.eq(ActivityProduct.FieldNames.productId.getTableFieldName(), productId);
        queryWrapper.eq(ActivityProduct.FieldNames.isDeleted.getTableFieldName(),PromoConst.IsDelete.IS_DELETE_CD_0.getCode());
        List<ActivityProduct> activityProductList = activityProductMapper.selectList(queryWrapper);
        if(activityProductList!=null&&!activityProductList.isEmpty()){
            ActivityProduct activityProduct = activityProductList.get(0);
            if(activityProduct!=null&&activityProduct.getNum()!=null){
                return activityProduct.getNum();
            }
        }

        return null;
    }
    /**
     * 根据活动id和产品Id查询参与活动产品
     * @param marketingActivityIds 活动ID集合 productId 产品Id
     * @return
     */
    public List<ActivityProduct> queryActivityProductBymktIdProdId(List<String> marketingActivityIds,String productId) {
        QueryWrapper<ActivityProduct> queryWrapper = new QueryWrapper<>();
        queryWrapper.in(ActivityProduct.FieldNames.marketingActivityId.getTableFieldName(), marketingActivityIds);
        queryWrapper.eq(ActivityProduct.FieldNames.productId.getTableFieldName(), productId);
        queryWrapper.eq(ActivityProduct.FieldNames.isDeleted.getTableFieldName(),PromoConst.IsDelete.IS_DELETE_CD_0.getCode());
        return activityProductMapper.selectList(queryWrapper);
    }

    /**
     * 保存更改后的产品信息
     * @param activityProductReq
     * @return
     */
    public ResultVO saveActProduct(ActivityProductReq activityProductReq){
        log.info("ActivityProductServiceImpl.saveActProduct activityProductReq={}", JSON.toJSON(activityProductReq));
        if (null == activityProductReq){
            return  ResultVO.error("ActivityProductServiceImpl.saveActProduct activityProductReq is null");
        }
        return activityProductMapper.saveActProduct(activityProductReq);
    }

    /**
     * 删除返利产品
     * @param activityProductReq
     * @return
     */
    public ResultVO deleteReBateProductActivity(ActivityProductReq activityProductReq) {
        log.info("ActivityProductServiceImpl.saveActProduct activityProductReq={}",JSON.toJSON(activityProductReq));
        if (StringUtils.isEmpty(activityProductReq.getMarketingActivityId()) ||
                StringUtils.isEmpty(activityProductReq.getProductId())){
            return  ResultVO.error("ActivityProductServiceImpl.deleteReBateProductActivity activityProductReq is null");
        }
        activityProductReq.setGmtModified(new Date());
        return activityProductMapper.deleteReBateProductActivity(activityProductReq);
    }


}
