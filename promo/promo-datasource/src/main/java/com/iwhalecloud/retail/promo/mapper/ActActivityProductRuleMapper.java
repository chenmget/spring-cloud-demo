package com.iwhalecloud.retail.promo.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.promo.dto.req.ActivityProductReq;
import com.iwhalecloud.retail.promo.dto.req.ReBateActivityListReq;
import com.iwhalecloud.retail.promo.dto.resp.ReBateActivityListResp;
import com.iwhalecloud.retail.promo.entity.ActActivityProductRule;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

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

    /**
     * 查询活动列表(分页)
     * @param page
     * @param req
     * @return
     */
    Page<ReBateActivityListResp> listReBateActivity(Page<ReBateActivityListResp> page, @Param("req") ReBateActivityListReq req);
}