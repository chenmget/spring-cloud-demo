package com.iwhalecloud.retail.rights.service;

import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.rights.dto.request.*;
import com.iwhalecloud.retail.rights.dto.response.MktResCouponRespDTO;
import com.iwhalecloud.retail.rights.dto.response.PreSubsidyProductPromResqDTO;
import com.iwhalecloud.retail.rights.dto.response.QueryPreSubsidyCouponResqDTO;

import java.util.List;

/**
 * @author zhou.zc
 * @date 2019年02月21日
 * @Description:前置补贴优惠券
 */
public interface PreSubsidyCouponService {
    /**
     * 配置前置补贴优惠券
     * @param req
     * @return
     */
    ResultVO addPreSubsidyCoupon(AddPreSubsidyCouponReqDTO req);

    /**
     * 更新置补贴优惠券
     * @param req
     * @return
     */
    ResultVO updatePreSubsidyCoupon(UpdatePreSubsidyCouponReqDTO req);

    /**
     * 查询前置补贴的优惠券
     * @param queryPreSubsidyReqDTO
     * @return
     */
    ResultVO<List<QueryPreSubsidyCouponResqDTO>> queryPreSubsidyCoupon(QueryPreSubsidyReqDTO queryPreSubsidyReqDTO);

    /**
     * 删除
     * @param queryPreSubsidyReqDTO
     * @return
     */
    ResultVO deletePreSubsidyCoupon(QueryPreSubsidyReqDTO queryPreSubsidyReqDTO);

    /**
     * 添加前置补贴活动产品
     * @param addPromotionProductReqDTO
     * @return
     */
    ResultVO addPreSubsidyProduct(AddPromotionProductReqDTO addPromotionProductReqDTO);

    /**
     * 查询前置补贴活动产品
     * @param queryPreSubsidyReqDTO
     * @return
     */
    ResultVO<List<PreSubsidyProductPromResqDTO>> queryPreSubsidyProduct(QueryPreSubsidyReqDTO queryPreSubsidyReqDTO);


    /**
     * 查询可以混用的优惠券
     *
     * @param queryPreSubsidyReqDTO
     * @return
     */
    ResultVO<List<MktResCouponRespDTO>> queryMixUseCoupon(QueryPreSubsidyReqDTO queryPreSubsidyReqDTO);

    /**
     * 更新活动的优惠券的类型
     *
     * @param queryPreSubsidyReqDTO
     * @return
     */
    ResultVO updateActCouponType(QueryPreSubsidyReqDTO queryPreSubsidyReqDTO);

    /**
     * 更新优惠券的时间
     * @param marketingActivityId
     * @return
     */
    ResultVO updateCouponDate( String marketingActivityId);
}
