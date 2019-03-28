package com.iwhalecloud.retail.rights.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.rights.dto.request.QueryCouponByIdReq;
import com.iwhalecloud.retail.rights.dto.request.QueryCouponByProductAndActivityIdReq;
import com.iwhalecloud.retail.rights.dto.request.QueryRightsReqDTO;
import com.iwhalecloud.retail.rights.dto.response.CouponRuleAndTypeQueryResp;
import com.iwhalecloud.retail.rights.dto.response.MktResCouponRespDTO;
import com.iwhalecloud.retail.rights.dto.response.QueryRightsRespDTO;

import java.util.List;

/**
 * 权益管理
 */
public interface MktResCouponService {

    /**
     * 权益查询
     * @param dto
     * @return
     */
    public Page<QueryRightsRespDTO> queryRights(QueryRightsReqDTO dto) ;


    CouponRuleAndTypeQueryResp queryCouponRuleAndTypeById(QueryCouponByIdReq req);

    /**
     * 根据产品和活动查询优惠券
     * @param req
     * @return
     */
    ResultVO<List<MktResCouponRespDTO>> queryCouponByProductAndActivityId(QueryCouponByProductAndActivityIdReq req);
   
}
