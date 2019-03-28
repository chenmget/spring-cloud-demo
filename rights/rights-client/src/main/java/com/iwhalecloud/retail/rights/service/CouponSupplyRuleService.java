package com.iwhalecloud.retail.rights.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.rights.dto.request.CommonQueryByMktResIdReqDTO;
import com.iwhalecloud.retail.rights.dto.request.SaveCouponSupplyRuleReqDTO;
import com.iwhalecloud.retail.rights.dto.request.UpdateCouponSupplyRuleReqDTO;
import com.iwhalecloud.retail.rights.dto.response.CouponSupplyRuleRespDTO;

/**
 * 权益抵扣规则
 */
public interface CouponSupplyRuleService {

    /**
     * 权益抵扣规则查询
     * @param dto
     * @return
     */
    public Page<CouponSupplyRuleRespDTO> queryCouponSupplyRule(CommonQueryByMktResIdReqDTO dto);
   
    /**
     * 权益抵扣规则修改
     * @param dto
     * @return
     */
    public ResultVO updateCouponSupplyRule(UpdateCouponSupplyRuleReqDTO dto);
    
    /**
     * 权益抵扣规则保存
     * @param dto
     * @return
     */
    public ResultVO saveCouponSupplyRule(SaveCouponSupplyRuleReqDTO dto) ;
}
