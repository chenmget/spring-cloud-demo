package com.iwhalecloud.retail.rights.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.iwhalecloud.retail.rights.entity.CouponInstUseRec;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Class: CouponInstUseRecMapper
 * @author autoCreate
 */
@Mapper
public interface CouponInstUseRecMapper extends BaseMapper<CouponInstUseRec>{

	/**
	 * 保存
	 * @param t
	 * @return
	 */
	public Integer insertCouponInstUseRec(CouponInstUseRec t);
}