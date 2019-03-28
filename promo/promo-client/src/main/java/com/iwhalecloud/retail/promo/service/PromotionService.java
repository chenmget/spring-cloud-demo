package com.iwhalecloud.retail.promo.service;


import com.iwhalecloud.retail.promo.dto.PromotionDTO;

import java.util.List;

public interface PromotionService {

    /**
     * 根据商品ID 获取商品对应的活动卡券列表（promotionType为20的券类型）
     * @param goodsId
     * @return
     */
//    ResultVO<PromotionDTO> listPromotionByGoodsId(String goodsId);

    /**
     * 订单优惠券列表查询（包含当前订单可用的、不可用的券）
     * @param req
     * @return
     */
//    ResultVO<OrderPromotionListResp> orderPromotionList(OrderPromotionListReq req);

    /**
     * 新增活动优惠信息
     * @param promotionDTO
     * @return
     */
    Integer addActPromotion(PromotionDTO promotionDTO);

    /**
     * 查询活动的优惠券
     * @param marketingActivityId
     * @param promotionType
     * @return
     */
    List<PromotionDTO> queryActPromotion(String marketingActivityId, java.lang.String promotionType);

    /**
     * 删除活动优惠
     * @param marketingActivityId
     * @param mktResId
     * @return
     */
    Integer deleteActPromotion(String marketingActivityId,String mktResId);
}