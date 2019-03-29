package com.iwhalecloud.retail.promo.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.promo.dto.req.ActivityProductReq;
import com.iwhalecloud.retail.promo.entity.ActActivityProductRule;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Class: ActActivityProductRuleMapper
 * @author autoCreate
 */
@Mapper
public interface ActActivityProductRuleMapper extends BaseMapper<ActActivityProductRule>{
    /**
     * 删除返利活动规则产品
     * @param activityProductReq
     * @return
     */
    ResultVO deleteReBateProductRuleActivity(ActivityProductReq activityProductReq);
}