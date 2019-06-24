package com.iwhalecloud.retail.rights.manager;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.rights.dto.request.QueryPreSubsidyReqDTO;
import com.iwhalecloud.retail.rights.dto.request.QueryProductCouponReq;
import com.iwhalecloud.retail.rights.dto.request.QueryRightsReqDTO;
import com.iwhalecloud.retail.rights.dto.request.UpdateMktResCouponReqDTO;
import com.iwhalecloud.retail.rights.dto.response.CouponSupplyRuleRespDTO;
import com.iwhalecloud.retail.rights.dto.response.MktResCouponRespDTO;
import com.iwhalecloud.retail.rights.dto.response.QueryMktResCouponRespDTO;
import com.iwhalecloud.retail.rights.dto.response.QueryRightsRespDTO;
import com.iwhalecloud.retail.rights.entity.MktResCoupon;
import com.iwhalecloud.retail.rights.mapper.MktResCouponMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;


@Component
public class MktResCouponManager{
    @Resource
    private MktResCouponMapper mktResCouponMapper;

    public Page<QueryRightsRespDTO> queryrights(QueryRightsReqDTO dto){
    	Page<QueryRightsReqDTO> page = new Page<QueryRightsReqDTO>(dto.getPageNo(), dto.getPageSize());
    	return mktResCouponMapper.queryrights(page,dto);
    }
    
    public QueryMktResCouponRespDTO queryMktResCoupon(String mktResId){
    	return mktResCouponMapper.queryMktResCoupon(mktResId);
    }

    public int addMktResCoupon(MktResCoupon mktResCoupon){
        return mktResCouponMapper.insert(mktResCoupon);
    }

    public int updateMktResCoupon(UpdateMktResCouponReqDTO updateMktResCouponReqDTO){
        MktResCoupon mktResCoupon = new MktResCoupon();
        BeanUtils.copyProperties(updateMktResCouponReqDTO,mktResCoupon);
        return mktResCouponMapper.updateById(mktResCoupon);
    }

    public int deleteMktResCoupon(String mktResId){
        return mktResCouponMapper.deleteById(mktResId);
    }

    /**
     * 根据优惠券id查询优惠券信息
     * @param mktResIds
     * @return
     */
    public List<MktResCoupon> queryMktResCouponByIds(List<String> mktResIds) {
        QueryWrapper queryWrapper = new QueryWrapper<>();
        queryWrapper.in("MKT_RES_ID",mktResIds);
        return mktResCouponMapper.selectList(queryWrapper);
    }

    public MktResCoupon queryMktResCouponById(String mktResId) {
        QueryWrapper queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("MKT_RES_ID",mktResId);
        queryWrapper.eq("STATUS_CD",1000);
        return mktResCouponMapper.selectOne(queryWrapper);
    }

    /**
     * 根据活动id和产品id查询优惠券信息
     * @param queryProductCouponReq
     * @return
     */
    public List<MktResCouponRespDTO> queryActivityCoupon(QueryProductCouponReq queryProductCouponReq){
        return mktResCouponMapper.queryActivityCoupon(queryProductCouponReq);
    }

    public List<MktResCoupon> queryCouponByActId(String marketingActivityId){
        QueryWrapper<MktResCoupon> queryWrapper = new QueryWrapper<MktResCoupon>();
        queryWrapper.eq(MktResCoupon.FieldNames.marketingActivityId.getTableFieldName(),marketingActivityId);
        return mktResCouponMapper.selectList(queryWrapper);
    }

    /**
     * 更新优惠券基础信息
     *
     * @param mktResCoupon
     * @return
     */
    public Integer updateMktResCouponById(MktResCoupon mktResCoupon) {
        mktResCoupon.setUpdateDate(new Date());
        return mktResCouponMapper.updateById(mktResCoupon);
    }

    /**
     * 更新活动的优惠券的券种类
     * @param marketingActivityId
     * @param couponKind
     * @return
     */
    public Integer updateActCouponType(String marketingActivityId,String couponKind){
        MktResCoupon mktResCoupon = new MktResCoupon();
        mktResCoupon.setCouponKind(couponKind);
        QueryWrapper<MktResCoupon> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(MktResCoupon.FieldNames.marketingActivityId.getTableFieldName(), marketingActivityId);
        return mktResCouponMapper.update(mktResCoupon,queryWrapper);
    }

    /**
     * 查询可混合使用的优惠券
     * @param queryPreSubsidyReqDTO
     * @return
     */
    public List<MktResCouponRespDTO> queryMixUseCoupon(QueryPreSubsidyReqDTO queryPreSubsidyReqDTO){
        return mktResCouponMapper.queryMixUseCoupon(queryPreSubsidyReqDTO);
    }

    /**
     * 查询可以推送优惠券
     * @param marketingActivityId
     * @return
     */
    public List<CouponSupplyRuleRespDTO> queryAutoPushCoupon(String marketingActivityId){
        return mktResCouponMapper.queryAutoPushCoupon(marketingActivityId);
    }
}
