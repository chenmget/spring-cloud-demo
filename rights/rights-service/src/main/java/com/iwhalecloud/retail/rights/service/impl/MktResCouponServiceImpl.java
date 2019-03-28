package com.iwhalecloud.retail.rights.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.rights.common.RightsConst;
import com.iwhalecloud.retail.rights.consts.RightsStatusConsts;
import com.iwhalecloud.retail.rights.dto.request.QueryCouponByIdReq;
import com.iwhalecloud.retail.rights.dto.request.QueryCouponByProductAndActivityIdReq;
import com.iwhalecloud.retail.rights.dto.request.QueryProductCouponReq;
import com.iwhalecloud.retail.rights.dto.request.QueryRightsReqDTO;
import com.iwhalecloud.retail.rights.dto.response.CouponRuleAndTypeQueryResp;
import com.iwhalecloud.retail.rights.dto.response.MktResCouponRespDTO;
import com.iwhalecloud.retail.rights.dto.response.QueryMktResCouponRespDTO;
import com.iwhalecloud.retail.rights.dto.response.QueryRightsRespDTO;
import com.iwhalecloud.retail.rights.entity.CouponDiscountRule;
import com.iwhalecloud.retail.rights.manager.CouponInstManager;
import com.iwhalecloud.retail.rights.manager.MktResCouponManager;
import com.iwhalecloud.retail.rights.manager.MktResCouponManagerRule;
import com.iwhalecloud.retail.rights.service.MktResCouponService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component("mktResCouponService")
@Service
public class MktResCouponServiceImpl implements MktResCouponService {
	
    @Autowired
    private MktResCouponManager mktResCouponManager;

    @Autowired
	private MktResCouponManagerRule mktResCouponManagerRule;

    @Autowired
	private CouponInstManager couponInstManager;

	/**
     * 权益查询
     * @param dto
     * @return
     */
	@Override
	public Page<QueryRightsRespDTO> queryRights(
			QueryRightsReqDTO dto) {
		return mktResCouponManager.queryrights(dto);
	}

	@Override
	public CouponRuleAndTypeQueryResp queryCouponRuleAndTypeById(QueryCouponByIdReq req) {
		CouponDiscountRule couponDiscountRules = mktResCouponManagerRule.queryCouponlistById(req.getMktResId());
		CouponRuleAndTypeQueryResp couponRuleAndTypeQueryResp = new CouponRuleAndTypeQueryResp();
		QueryMktResCouponRespDTO queryMktResCouponRespDTO = mktResCouponManager.queryMktResCoupon(String.valueOf(req.getMktResId()));
		if(queryMktResCouponRespDTO != null){
			couponRuleAndTypeQueryResp.setMktResTypeName(queryMktResCouponRespDTO.getCouponType());
			couponRuleAndTypeQueryResp.setMktResId(couponDiscountRules.getMktResId());
			couponRuleAndTypeQueryResp.setDiscountValue(couponDiscountRules.getDiscountValue());
			couponRuleAndTypeQueryResp.setRuleAmount(couponDiscountRules.getRuleAmount());
			return couponRuleAndTypeQueryResp;
		}
		return null;
	}

	@Override
	public ResultVO<List<MktResCouponRespDTO>> queryCouponByProductAndActivityId(QueryCouponByProductAndActivityIdReq req) {
		QueryProductCouponReq queryProductCouponReq = new QueryProductCouponReq();
//		queryProductCouponReq.setProductId(req.getProductId());
//		queryProductCouponReq.setMarketingActivityId(req.getMktActivityId());
//		queryProductCouponReq.setStatusCd(RightsStatusConsts.RIGHTS_STATUS_EXPIRE);
//		queryProductCouponReq.setObjType(RightsConst.CouponApplyObjType.PRODUCT.getType());



		List<MktResCouponRespDTO> mktResRegionRespDTOS = mktResCouponManager.queryActivityCoupon(queryProductCouponReq);
		return ResultVO.success(mktResRegionRespDTOS);
	}
}