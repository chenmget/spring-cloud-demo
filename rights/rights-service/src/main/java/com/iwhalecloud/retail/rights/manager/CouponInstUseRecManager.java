package com.iwhalecloud.retail.rights.manager;

import com.iwhalecloud.retail.rights.entity.CouponInstUseRec;
import com.iwhalecloud.retail.rights.mapper.CouponInstUseRecMapper;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;


@Component
public class CouponInstUseRecManager{
    @Resource
    private CouponInstUseRecMapper couponInstUseRecMapper;
    
    
    
    /**
	 * 保存
	 * @param t
	 * @return
	 */
	public Integer insertCouponInstProvRec(CouponInstUseRec t){
		return couponInstUseRecMapper.insert(t);
	}
}
