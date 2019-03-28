package com.iwhalecloud.retail.rights.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.rights.dto.request.ChangeCouponApplyObjectReqDTO;
import com.iwhalecloud.retail.rights.dto.request.CommonQueryByMktResIdReqDTO;
import com.iwhalecloud.retail.rights.dto.request.QueryCouponByProductAndActivityIdReq;
import com.iwhalecloud.retail.rights.dto.response.CouponApplyObjectRespDTO;

import java.util.List;

/**
 * 权益适用地区
 */
public interface CouponApplyObjectService {

    /**
     * 权益适用地区查询
     * @param dto
     * @return
     */
    public Page<CouponApplyObjectRespDTO> queryCouponApplyObject(CommonQueryByMktResIdReqDTO dto);
   
    /**
     * 权益适用地区变更
     * @param dto
     * @return
     */
    public ResultVO changeCouponApplyObject(ChangeCouponApplyObjectReqDTO dto);

    /**
     * 根据产品筛选优惠券
     * @param req
     * @return
     */
    ResultVO<List<CouponApplyObjectRespDTO>> queryCouponApplyObjectByCondition(QueryCouponByProductAndActivityIdReq req);
}
