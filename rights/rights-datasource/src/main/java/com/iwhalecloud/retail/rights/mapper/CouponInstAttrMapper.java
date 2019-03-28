package com.iwhalecloud.retail.rights.mapper;


import org.apache.ibatis.annotations.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.iwhalecloud.retail.rights.dto.response.CouponInstAttrRespDTO;
import com.iwhalecloud.retail.rights.entity.CouponInstAttr;

/**
 * @Class: CouponInstAttrMapper
 * @author autoCreate
 */
@Mapper
public interface CouponInstAttrMapper extends BaseMapper<CouponInstAttr>{

	/**
	 * 查询优惠券实例属性信息
	 * @param mktResId
	 * @return
	 */
	public CouponInstAttrRespDTO queryCouponInstAttrByMktResId(String mktResId);
}