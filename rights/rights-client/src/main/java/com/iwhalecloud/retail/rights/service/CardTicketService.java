package com.iwhalecloud.retail.rights.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.rights.dto.request.CouponNotUsedReq;
import com.iwhalecloud.retail.rights.dto.request.CouponUsedReq;
import com.iwhalecloud.retail.rights.dto.response.CouponNotUsedResp;
import com.iwhalecloud.retail.rights.dto.response.CouponUsedResp;

import java.util.List;

public interface CardTicketService {

    /**
     * 查询全部商家未使用的优惠券
     *
     * @param req
     * @return
     */
    public Page<CouponNotUsedResp> queryAllBusinessCouponNotUsed(CouponNotUsedReq req);

    /**
     * 查询全部商家已使用的优惠券
     *
     * @param req
     * @return
     */
    public Page<CouponUsedResp> queryAllBusinessCouponUsed(CouponUsedReq req);


    /**
     * 查询全部商家已使用的优惠券(不分页)
     *
     * @param req
     * @return
     */
    public List<CouponUsedResp> queryAllBusinessCouponUsedNotPage(CouponUsedReq req);
}
