package com.iwhalecloud.retail.rights.mapper;


import com.iwhalecloud.retail.rights.dto.request.QueryProductCouponReq;
import com.iwhalecloud.retail.rights.dto.request.UpdateMktResCouponReqDTO;
import com.iwhalecloud.retail.rights.dto.response.MktResCouponRespDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.rights.dto.request.QueryRightsReqDTO;
import com.iwhalecloud.retail.rights.dto.response.QueryRightsRespDTO;
import com.iwhalecloud.retail.rights.dto.response.QueryMktResCouponRespDTO;
import com.iwhalecloud.retail.rights.entity.MktResCoupon;

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
}