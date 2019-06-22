package com.iwhalecloud.retail.rights.manager;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.iwhalecloud.retail.rights.dto.request.QueryCouponInstProvRecNumReqDTO;
import com.iwhalecloud.retail.rights.entity.CouponInstProvRec;
import com.iwhalecloud.retail.rights.mapper.CouponInstProvRecMapper;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;


@Component
public class CouponInstProvRecManager extends ServiceImpl<CouponInstProvRecMapper,CouponInstProvRec>{
    @Resource
    private CouponInstProvRecMapper couponInstProvRecMapper;
    
    /**
	 * 查询周期内已领取权益实体数量
	 * @param dto
	 * @return
	 */
    public Long queryCouponInstProvRecNum(QueryCouponInstProvRecNumReqDTO dto){
    	return couponInstProvRecMapper.queryCouponInstProvRecNum(dto);
    }
    
    /**
	 * 保存
	 * @param t
	 * @return
	 */
	public Integer insertCouponInstProvRec(CouponInstProvRec t){
		return couponInstProvRecMapper.insert(t);
	}

	/**
	 * 批量入库领取记录
	 * @param couponInstProvRecs
	 * @return
	 */
	public Integer insertBatchCouponInstProvRec(List<CouponInstProvRec> couponInstProvRecs){
		return couponInstProvRecMapper.insertBatchCouponInstProvRec(couponInstProvRecs);
	}
}
