package com.iwhalecloud.retail.order2b.service;

import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.order2b.dto.response.PromotionResp;
import com.iwhalecloud.retail.order2b.dto.resquest.promo.PromotionListReq;

import java.util.List;

/**
 * @Author he.sw
 * @Date 2019/07/08
 **/
public interface PromotionService {
    /**
     * 订单优惠列表查询
     * @param req
     */
    ResultVO<List<PromotionResp>> selectPromotion(PromotionListReq req);

}
