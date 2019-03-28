package com.iwhalecloud.retail.order.dbservice;

import com.iwhalecloud.retail.order.dto.base.CommonResultResp;
import com.iwhalecloud.retail.order.dto.resquest.BuilderOrderRequest;
import com.iwhalecloud.retail.order.model.BuilderOrderDTO;

import java.util.List;

public interface BuilderOrderExpandService {

    /**
     * 等值减免
     * 优惠券使用(方案一)：按百分比减免
     */
    CommonResultResp<List<BuilderOrderDTO>> useCouponByEquivalence(List<BuilderOrderDTO> orderList,BuilderOrderRequest requestDTO);

    /**
     * 等值减免(回滚)
     */
    CommonResultResp RollbackCouponByEquivalence(String orderiD, BuilderOrderRequest requestDTO);

    /**
     * 更新库存
     */
    CommonResultResp updateGoodsNum(List<String> orders, BuilderOrderRequest requestDTO);
}
