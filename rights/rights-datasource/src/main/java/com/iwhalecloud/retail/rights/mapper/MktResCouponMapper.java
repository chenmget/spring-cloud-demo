package com.iwhalecloud.retail.rights.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.rights.dto.request.QueryPreSubsidyReqDTO;
import com.iwhalecloud.retail.rights.dto.request.QueryProductCouponReq;
import com.iwhalecloud.retail.rights.dto.request.QueryRightsReqDTO;
import com.iwhalecloud.retail.rights.dto.response.CouponSupplyRuleRespDTO;
import com.iwhalecloud.retail.rights.dto.response.MktResCouponRespDTO;
import com.iwhalecloud.retail.rights.dto.response.QueryMktResCouponRespDTO;
import com.iwhalecloud.retail.rights.dto.response.QueryRightsRespDTO;
import com.iwhalecloud.retail.rights.entity.MktResCoupon;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Class: MktResCouponMapper
 * @author autoCreate
 */
@Mapper
public interface MktResCouponMapper extends BaseMapper<MktResCoupon>{

	/**
	 * 权益查询
	 * @param page
	 * @param request
	 * @return
	 */
	public Page<QueryRightsRespDTO> queryrights(Page<QueryRightsReqDTO> page, @Param("req") QueryRightsReqDTO request);

	/**
	 * 查询单个权益
	 * @param mktResId
	 * @return
	 */
	public QueryMktResCouponRespDTO queryMktResCoupon(String mktResId);

	/**
	 * 查询活动产品的优惠券
	 * @param queryProductCouponReq
	 * @return
	 */
	List<MktResCouponRespDTO>  queryActivityCoupon(QueryProductCouponReq queryProductCouponReq);

	/**
	 * 查询可混合使用的优惠券
	 * @param queryPreSubsidyReqDTO
	 * @return
	 */
	List<MktResCouponRespDTO> queryMixUseCoupon(QueryPreSubsidyReqDTO queryPreSubsidyReqDTO);

	/**
	 * 查询主动推送的优惠券
	 * @param marketingActivityId
	 * @return
	 */
	List<CouponSupplyRuleRespDTO> queryAutoPushCoupon(String marketingActivityId);

}