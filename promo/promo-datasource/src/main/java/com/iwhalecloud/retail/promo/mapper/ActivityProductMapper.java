package com.iwhalecloud.retail.promo.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.promo.dto.req.ActivityProductReq;
import com.iwhalecloud.retail.promo.entity.ActivityProduct;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Class: ActivityProductMapper
 * @author autoCreate
 */
@Mapper
public interface ActivityProductMapper extends BaseMapper<ActivityProduct>{

    /**
     * 批量新增参与活动产品
     * @param activityProducts
     * @return
     */
//    public Integer batchInsert(List<ActivityProduct> activityProducts);

    /**
     * 根据活动Id删除活动产品
     * @param marketingActivityId
     */
    void deleteActivityProduct(String marketingActivityId);

    /**
     * 查询活动产品的参入总数
     * @param marketingActivityId
     * @return
     */
    Long queryActProductSum(String marketingActivityId);
    /**
     * 保存更改后的产品信息
     * @param activityProductReq
     * @return
     */
    ResultVO saveActProduct(ActivityProductReq activityProductReq);

    /**
     * 删除返利产品
     * @param activityProductReq
     * @return
     */
    ResultVO deleteReBateProductActivity(ActivityProductReq activityProductReq);

}