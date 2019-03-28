package com.iwhalecloud.retail.web.controller.b2b.rights.service;

import com.iwhalecloud.retail.web.controller.b2b.order.dto.ExcelTitleName;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lin.wenhui@iwhalecloud.com
 * @date 2019/2/26 23:49
 * @description
 */
public class CouponExportUtil {
    public static List<ExcelTitleName> getCoupon() {
        List<ExcelTitleName> couponMap = new ArrayList<>();
        couponMap.add(new ExcelTitleName("couponResource", "优惠券来源"));
        couponMap.add(new ExcelTitleName("couponNo", "优惠券编号"));
        couponMap.add(new ExcelTitleName("couponName", "优惠券名称"));
        couponMap.add(new ExcelTitleName("couponType", "优惠券类型"));
        couponMap.add(new ExcelTitleName("expiryDate", "有效期"));
        couponMap.add(new ExcelTitleName("amount", "面额"));
        couponMap.add(new ExcelTitleName("supplier", "使用供应商"));
        couponMap.add(new ExcelTitleName("productName", "产品名称"));
        couponMap.add(new ExcelTitleName("orderNo", "订单编号"));
        couponMap.add(new ExcelTitleName("orderState", "订单状态"));
        couponMap.add(new ExcelTitleName("dealTime", "交易时间"));
        return couponMap;
    }
}

