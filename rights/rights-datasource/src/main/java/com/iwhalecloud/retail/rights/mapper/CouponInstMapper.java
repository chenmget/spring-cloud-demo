package com.iwhalecloud.retail.rights.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.rights.dto.request.GetUnuseCouponInstRequestDTO;
import com.iwhalecloud.retail.rights.dto.request.InputRightsRequestDTO;
import com.iwhalecloud.retail.rights.dto.request.QueryCouponInstPageReq;
import com.iwhalecloud.retail.rights.dto.request.QueryCouponInstReqDTO;
import com.iwhalecloud.retail.rights.dto.response.CheckCouponInstRespDTO;
import com.iwhalecloud.retail.rights.dto.response.CouponInstDetailDTO;
import com.iwhalecloud.retail.rights.dto.response.QueryCouponInstRespDTO;
import com.iwhalecloud.retail.rights.entity.CouponInst;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Class: CouponInstMapper
 * @author autoCreate
 */
@Mapper
public interface CouponInstMapper extends BaseMapper<CouponInst>{
	
	/**
	 * 权益入库
	 * @param listRightsRequestDTO
	 * @return
	 */
	public Long inputRights(InputRightsRequestDTO listRightsRequestDTO);

	/**
	 * 根据编号查询权益实例是否存在
	 * @param couponInstNbr
	 * @return
	 */
	public CheckCouponInstRespDTO checkByInstNbr(String couponInstNbr);
	
	/**
	 * 批量入库
	 * @param data
	 * @return
	 */
	public Integer insertBatchCouponInst(List<CouponInst> data);
	
	/**
	 * 优惠券标识查询未分配实例
	 * @param dto
	 * @return
	 */
	public List<CouponInst> queryByMktResId(GetUnuseCouponInstRequestDTO dto);
	
	/**
	 * 更新权益实例
	 * @param t
	 * @return
	 */
	public Integer updateCouponInst(CouponInst t);
	
	/**
	 * 权益实例查询
	 * @param page
	 * @param request
	 * @return
	 */
	public Page<QueryCouponInstRespDTO> queryCouponinst(Page<QueryCouponInstPageReq> page, @Param("req")QueryCouponInstPageReq request);
	
	/**
	 * 查询最大的权益编码
	 * @return
	 */
	public Long queryBiggestInstNbr();

	/**
	 * 查询用户领取有效优惠券
	 * @param request
	 * @return
	 */
	public List<QueryCouponInstRespDTO> queryEffInstByCustNum(QueryCouponInstReqDTO request);

	/**
	 * 查询优惠券实例  带出优惠券的其他具体信息（名称、描述、抵扣规则等等）
	 * @param request
	 * @return
	 */
	public List<CouponInstDetailDTO> listCouponInstDetail(QueryCouponInstReqDTO request);

	/**
	 * 权益实例批量入库
	 * @param couponInsts
	 * @return
	 */
	Integer insertBatchCouponInstNew(List<CouponInst> couponInsts);
}