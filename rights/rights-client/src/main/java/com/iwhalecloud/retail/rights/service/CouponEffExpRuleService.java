package com.iwhalecloud.retail.rights.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.rights.dto.request.CommonQueryByMktResIdReqDTO;
import com.iwhalecloud.retail.rights.dto.request.SaveCouponEffExpRuleReqDTO;
import com.iwhalecloud.retail.rights.dto.request.UpdateCouponEffExpRuleReqDTO;
import com.iwhalecloud.retail.rights.dto.response.CouponEffExpRuleRespDTO;

/**
 * 权益生效规则
 */
public interface CouponEffExpRuleService {

    /**
     * 权益生效规则查询
     * @param dto
     * @return
     */
    public Page<CouponEffExpRuleRespDTO> queryCouponEffExpRule(CommonQueryByMktResIdReqDTO dto);
   
    /**
     * 权益生效规则修改
     * @param dto
     * @return
     */
    public ResultVO updateCouponEffExpRule(UpdateCouponEffExpRuleReqDTO dto);
    
    /**
     * 权益生效规则新增
     * @param dto
     * @return
     */
    public ResultVO saveCouponEffExpRule(SaveCouponEffExpRuleReqDTO dto) ;
}
