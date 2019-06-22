package com.iwhalecloud.retail.rights.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.iwhalecloud.retail.rights.dto.request.QueryCouponInstProvRecNumReqDTO;
import com.iwhalecloud.retail.rights.entity.CouponInstProvRec;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @Class: CouponInstProvRecMapper
 * @author autoCreate
 */
@Mapper
public interface CouponInstProvRecMapper extends BaseMapper<CouponInstProvRec>{

	/**
	 * 查询周期内已领取权益实体数量
	 * @param dto
	 * @return
	 */
	public Long queryCouponInstProvRecNum(QueryCouponInstProvRecNumReqDTO dto);
	
	/**
	 * 保存
	 * @param t
	 * @return
	 */
	public Integer insertCouponInstProvRec(CouponInstProvRec t);

	/**
	 * 批量录入数据
	 * @param couponInstProvRecs
	 * @return
	 */
	Integer insertBatchCouponInstProvRec(List<CouponInstProvRec> couponInstProvRecs);
}