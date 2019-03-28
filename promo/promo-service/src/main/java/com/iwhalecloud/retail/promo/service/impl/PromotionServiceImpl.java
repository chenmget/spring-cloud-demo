package com.iwhalecloud.retail.promo.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.google.common.collect.Lists;
import com.iwhalecloud.retail.promo.dto.PromotionDTO;
import com.iwhalecloud.retail.promo.entity.Promotion;
import com.iwhalecloud.retail.promo.manager.PromotionManager;
import com.iwhalecloud.retail.promo.service.PromotionService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;


@Service
public class PromotionServiceImpl implements PromotionService {

    @Autowired
    private PromotionManager promotionManager;

//    @Reference
//    private CouponInstService couponInstService;

//    /**
//     * 订单优惠券列表查询（包含当前订单可用的、不可用的券）
//     * @param req
//     * @return
//     */
//    @Override
//    public ResultVO orderPromotionList(OrderPromotionListReq req) {
//        OrderPromotionListResp resp = new OrderPromotionListResp<QueryCouponInstRespDTO>();
//
//        // 获取所有的优惠券
//        QueryCouponInstPageReq queryCouponInstPageReq = new QueryCouponInstPageReq();
//        queryCouponInstPageReq.setCustNum(req.getUserId());
//        queryCouponInstPageReq.setStatusCd(RightsStatusConsts.RIGHTS_STATUS_UNUSED); // 未使用状态的
//        queryCouponInstPageReq.setPageNo(1);
//        queryCouponInstPageReq.setPageSize(100); // 设大一点
//        Page<QueryCouponInstRespDTO> page = couponInstService.queryCouponinst(queryCouponInstPageReq);
//
//        // 1、营销活动中过滤优惠券
//
//        // 2、权益中心中过滤优惠券
//
//        resp.setEnabledPromotions(page.getRecords());
//        return ResultVO.success(page.getRecords());
//    }

    @Override
    public Integer addActPromotion(PromotionDTO promotionDTO) {
        Promotion promotion = new Promotion();
        BeanUtils.copyProperties(promotionDTO,promotion);
        promotion.setGmtCreate(new Date());
        return promotionManager.addActPromotion(promotion);
    }

    @Override
    public Integer deleteActPromotion(String marketingActivityId, String mktResId) {
        return promotionManager.deleteActPromotion(marketingActivityId,mktResId);
    }

    @Override
    public List<PromotionDTO> queryActPromotion(String marketingActivityId, String promotionType) {
        List<Promotion> promotionList = promotionManager.queryActPromotion(marketingActivityId,promotionType);
        List<PromotionDTO> promotionDTOList = Lists.newArrayList();
        for(Promotion promotion : promotionList){
            PromotionDTO promotionDTO = new PromotionDTO();
            BeanUtils.copyProperties(promotion,promotionDTO);
            promotionDTOList.add(promotionDTO);
        }
        return promotionDTOList;
    }
}