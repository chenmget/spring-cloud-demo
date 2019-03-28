package com.iwhalecloud.retail.rights.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.rights.dto.request.CommonQueryByMktResIdReqDTO;
import com.iwhalecloud.retail.rights.dto.request.SaveCouponDiscountRuleReqDTO;
import com.iwhalecloud.retail.rights.dto.request.UpdateCouponDiscountRuleReqDTO;
import com.iwhalecloud.retail.rights.dto.response.CouponDiscountRuleRespDTO;

/**
 * 权益抵扣规则
 */
public interface CouponDiscountRuleService {

    /**
     * 权益抵扣规则查询
     * @param dto
     * @return
     */
    public Page<CouponDiscountRuleRespDTO> queryCouponDiscountRule(CommonQueryByMktResIdReqDTO dto);
   
    /**
     * 权益抵扣规则修改
     * @param dto
     * @return
     */
    public ResultVO updateCouponDiscountRule(UpdateCouponDiscountRuleReqDTO dto);
    
    /**
     * 权益抵扣规则保存
     * @param dto
     * @return
     */
    public ResultVO saveCouponDiscountRule(SaveCouponDiscountRuleReqDTO dto) ;
}
