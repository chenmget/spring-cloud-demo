package com.iwhalecloud.retail.rights.manager;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.iwhalecloud.retail.rights.consts.RightsStatusConsts;
import com.iwhalecloud.retail.rights.dto.request.GetUnuseCouponInstRequestDTO;
import com.iwhalecloud.retail.rights.dto.request.QueryCouponInstPageReq;
import com.iwhalecloud.retail.rights.dto.request.QueryCouponInstReqDTO;
import com.iwhalecloud.retail.rights.dto.response.CheckCouponInstRespDTO;
import com.iwhalecloud.retail.rights.dto.response.CouponInstDetailDTO;
import com.iwhalecloud.retail.rights.dto.response.QueryCouponInstRespDTO;
import com.iwhalecloud.retail.rights.entity.CouponInst;
import com.iwhalecloud.retail.rights.mapper.CouponInstMapper;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;


@Component
public class CouponInstManager extends ServiceImpl<CouponInstMapper,CouponInst>{
    @Resource
    private CouponInstMapper couponInstMapper;
    
    /**
	 * 权益入库
	 */
    public Integer insertCouponInst(CouponInst t){
    	return couponInstMapper.insert(t);
    }
    
    /**
	 * 根据编号查询权益实例是否存在
	 */
    public CheckCouponInstRespDTO checkByInstNbr(String couponInstNbr){
    	return couponInstMapper.checkByInstNbr(couponInstNbr);
    }
    
    /**
	 * 权益批量入库
	 */
    public Integer insertBatchCouponInst(List<CouponInst> data){
    	return couponInstMapper.insertBatchCouponInst(data);
    }
    
    /**
	 * 优惠券标识查询未分配实例
	 */
    public List<CouponInst> queryByMktResId(GetUnuseCouponInstRequestDTO dto){
    	return couponInstMapper.queryByMktResId(dto);
    }
    
    /**
	 * 更新权益实例
	 * @param t
	 * @return
	 */
	public Integer updateCouponInst(CouponInst t){
		return couponInstMapper.updateCouponInst(t);
	}
	
	/**
	 * 通过主键获取权益实例
	 * @param couponInstId
	 * @return
	 */
	public CouponInst selectById(String couponInstId,String custAcctId){
		QueryWrapper<CouponInst> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq(CouponInst.FieldNames.custAcctId.getTableFieldName(),custAcctId);
		queryWrapper.eq(CouponInst.FieldNames.couponInstId.getTableFieldName(),couponInstId);
		return couponInstMapper.selectOne(queryWrapper);
	}
	
	/**
	 * 权益实例查询
	 * @param req
	 * @return
	 */
	public Page<QueryCouponInstRespDTO> queryCouponinst(QueryCouponInstPageReq req){
		Page<QueryCouponInstPageReq> page = new Page<QueryCouponInstPageReq>(req.getPageNo(), req.getPageSize());
		return couponInstMapper.queryCouponinst(page, req);
	}
	
	/**
	 * 查询最大的权益编码
	 * @return
	 */
	public Long queryBiggestInstNbr(){
		return couponInstMapper.queryBiggestInstNbr();
	}
	
	/**
	 * 查询用户领取有效优惠券
	 * @param request
	 * @return
	 */
	public List<QueryCouponInstRespDTO> queryEffInstByCustNum(QueryCouponInstReqDTO request){
		return couponInstMapper.queryEffInstByCustNum(request);
	}

	/**
	 * 查询用户领取有效优惠券
	 * @param request
	 * @return
	 */
	public List<CouponInstDetailDTO> listCouponInstDetail(QueryCouponInstReqDTO request){
		return couponInstMapper.listCouponInstDetail(request);
	}

	public List<CouponInst> queryResIdByInstIds(List<String> instIds) {
		QueryWrapper queryWrapper = new QueryWrapper<>();
		queryWrapper.in("COUPON_INST_ID",instIds);
		queryWrapper.eq("STATUS_CD",RightsStatusConsts.RIGHTS_STATUS_UNUSED);
		List<CouponInst> couponInsts = couponInstMapper.selectList(queryWrapper);
		return couponInsts;
	}

	public Integer queryCouponInstNum(String mktResId){
		QueryWrapper queryWrapper = new QueryWrapper<>();
		queryWrapper.eq(CouponInst.FieldNames.mktResId.getTableFieldName(),mktResId);
		queryWrapper.eq(CouponInst.FieldNames.statusCd.getTableFieldName(), RightsStatusConsts.COUPON_INST_STATUS_NOUSED);
		queryWrapper.apply(CouponInst.FieldNames.effDate.getTableFieldName() + " <= now()");
		queryWrapper.apply(CouponInst.FieldNames.expDate.getTableFieldName() + " >= now()");
		return couponInstMapper.selectCount(queryWrapper);
	}

	/**
	 * 已经领取的优惠券数量
	 * @param mktResId
	 * @return
	 */
	public Integer queryAreadyReceiveNum(String mktResId){
		QueryWrapper<CouponInst> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq(CouponInst.FieldNames.mktResId.getTableFieldName(),mktResId);
		return couponInstMapper.selectCount(queryWrapper);
	}

	/**
	 * 用户已经领取的优惠券数量
	 * @param mktResId
	 * @return
	 */
	public Integer queryCustAreadyReceiveNum(String mktResId,String custAcctId){
		QueryWrapper<CouponInst> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq(CouponInst.FieldNames.mktResId.getTableFieldName(),mktResId);
		queryWrapper.eq(CouponInst.FieldNames.custAcctId.getTableFieldName(),custAcctId);
		return couponInstMapper.selectCount(queryWrapper);
	}

	/**
	 * 权益实例批量入库（新）
	 * @param couponInsts
	 * @return
	 */
	public Integer insertBatchCouponInstNew(List<CouponInst> couponInsts){
		return couponInstMapper.insertBatchCouponInstNew(couponInsts);
	}
}
