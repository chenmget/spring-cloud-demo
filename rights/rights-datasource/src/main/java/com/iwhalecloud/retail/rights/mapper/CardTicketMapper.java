package com.iwhalecloud.retail.rights.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.rights.dto.request.CouponNotUsedReq;
import com.iwhalecloud.retail.rights.dto.request.CouponUsedReq;
import com.iwhalecloud.retail.rights.dto.response.CouponNotUsedResp;
import com.iwhalecloud.retail.rights.dto.response.CouponUsedResp;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CardTicketMapper {

    /**
     * 查询全部商家未使用的优惠券
     *
     * @param page
     * @param req
     * @return
     */
    Page<CouponNotUsedResp> queryAllBusinessCouponNotUsed(Page<CouponNotUsedReq> page, @Param("req") CouponNotUsedReq req);

    /**
     * 查询全部商家已使用的优惠券
     *
     * @param page
     * @param req
     * @return
     */
    Page<CouponUsedResp> queryAllBusinessCouponUsed(Page<CouponUsedResp> page, @Param("req") CouponUsedReq req);

    /**
     * 查询全部商家已使用的优惠券(不分页)
     *
     * @param req
     * @return
     */
    List<CouponUsedResp> queryAllBusinessCouponUsedNotPage(@Param("req") CouponUsedReq req);
}
