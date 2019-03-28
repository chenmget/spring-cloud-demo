package com.iwhalecloud.retail.rights.model;

import com.iwhalecloud.retail.rights.entity.CouponDiscountRule;
import lombok.Data;

/**
 * 优惠券基本信息
 *
 * @author xu.qinyuan@ztesoft.com
 * @date 2019年02月26日
 */
@Data
public class MktResCouponModel {

    /**
     * 优惠券标识
     */
    private String mktResId;

    /**
     * 优惠券实例Id
     */
    private String couponInstId;

    /**
     * 优惠券名称
     */
    private String mktResName;

    /**
     * 优惠券编码
     */
    private String mktResNbr;

    /**
     * 抵扣规则
     */
    private CouponDiscountRule discountRule;

}
