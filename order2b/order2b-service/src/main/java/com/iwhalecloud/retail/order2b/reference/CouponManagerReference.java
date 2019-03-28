package com.iwhalecloud.retail.order2b.reference;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.order2b.consts.OmsCommonConsts;
import com.iwhalecloud.retail.order2b.dto.base.CommonResultResp;
import com.iwhalecloud.retail.order2b.dto.model.order.CouponInsDTO;
import com.iwhalecloud.retail.order2b.dto.resquest.order.PreCreateOrderReq;
import com.iwhalecloud.retail.order2b.entity.OrderItem;
import com.iwhalecloud.retail.order2b.entity.Promotion;
import com.iwhalecloud.retail.order2b.model.BuilderOrderModel;
import com.iwhalecloud.retail.order2b.model.CreateOrderLogModel;
import com.iwhalecloud.retail.rights.dto.request.*;
import com.iwhalecloud.retail.rights.dto.response.CheckRightsResponseDTO;
import com.iwhalecloud.retail.rights.service.CouponInstService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
public class CouponManagerReference {

    @Reference
    private CouponInstService couponInstService;


    /**
     * 权益核销校验
     *
     * @return
     */
    public CommonResultResp orderCouponAmount(PreCreateOrderReq request, List<CouponInsDTO> couponIns, BuilderOrderModel model) {

        CommonResultResp<List<Promotion>> resp = new CommonResultResp<>();

        OrderUseCouponReq couponReq = new OrderUseCouponReq();
        couponReq.setMemberId(request.getMemberId());
        couponReq.setMerchantId(request.getMerchantId());
        couponReq.setOrderItems(new ArrayList<>(model.getOrderItem().size()));
        for (OrderItem item : model.getOrderItem()) {
            CouponOrderItemDTO useCouponDTO = new CouponOrderItemDTO();
            useCouponDTO.setOrderItemId(item.getItemId());
            useCouponDTO.setGoodsId(item.getGoodsId());
            useCouponDTO.setProductId(item.getProductId());
            useCouponDTO.setNum(item.getNum());
            useCouponDTO.setPrice(item.getPrice());
            couponReq.getOrderItems().add(useCouponDTO);
        }

        List<ProductOrderItemDTO> productOrderItemDTOS = new ArrayList<>();
        for (CouponInsDTO dto : couponIns) {
            ProductOrderItemDTO itemDTO = new ProductOrderItemDTO();
            itemDTO.setCouponInstId(dto.getCouponCode());
            itemDTO.setProductId(dto.getProductId());
            productOrderItemDTOS.add(itemDTO);
        }
        couponReq.setProductOrderItemDTOS(productOrderItemDTOS);

        ResultVO<OrderUseCouponResp> resultVO = couponInstService.OrderUseCoupon(couponReq);
        log.info("gs_10010_orderCouponAmount,req{},resp{}", JSON.toJSONString(couponReq), JSON.toJSONString(resultVO));
        if (!resultVO.isSuccess()) {
            resp.setResultCode(OmsCommonConsts.RESULE_CODE_FAIL);
            resp.setResultMsg("使用优惠券失败，" + resultVO.getResultMsg());
            return resp;
        }

        if (resultVO.getResultData() == null || CollectionUtils.isEmpty(resultVO.getResultData().getCouponDTOList())) {
            resp.setResultCode(OmsCommonConsts.RESULE_CODE_FAIL);
            resp.setResultMsg("返回优惠券列表为空");
            return resp;
        }

        List<OrderUseCouponDTO> couponDTOS = resultVO.getResultData().getCouponDTOList();
        List<Promotion> list = new ArrayList<>();
        for (OrderUseCouponDTO couponItem : couponDTOS) {
            Promotion promotion = new Promotion();
            promotion.setCreateDate(String.valueOf(new Timestamp(System.currentTimeMillis())));
            if(couponItem.getDiscount()==null){
                continue;
            }
            promotion.setDiscount(String.valueOf(couponItem.getDiscount()));
            promotion.setGoodsId(couponItem.getGoodsId());
            promotion.setOrderItemId(couponItem.getOrderItemId());
            promotion.setProductId(couponItem.getProductId());
            promotion.setPromotionInstId(couponItem.getCouponInstId());
            promotion.setPromotionName(couponItem.getMktResName());
            promotion.setPromotionId(couponItem.getMktResId()); //优惠标识
            list.add(promotion);
        }
        resp.setResultCode(OmsCommonConsts.RESULE_CODE_SUCCESS);
        model.setPromotionList(list);
        return resp;
    }


    /**
     * 权益核销
     */
    public CommonResultResp userights(CreateOrderLogModel requestDTO, String status, String orderId) {
        CommonResultResp resp = new CommonResultResp();
        UserightsRequestDTO userightsRequestDTO = new UserightsRequestDTO();
        userightsRequestDTO.setCustNum(requestDTO.getUserCode());
        StringBuilder sb = new StringBuilder();
        for (CouponInsDTO coupon : requestDTO.getCouponInsList()) {
            sb.append(coupon.getCouponCode()).append(",");
        }
        String conIns = "";
        if (sb.toString().contains(",")) {
            conIns = sb.substring(0, sb.toString().length() - 1);
        }
        userightsRequestDTO.setCouponInstId(conIns);
        userightsRequestDTO.setCreateStaff(requestDTO.getUserId());
        // RightsStatusConsts.RIGHTS_STATUS_UNUSED  待使用
        // RightsStatusConsts.RIGHTS_STATUS_USED  已使用
        userightsRequestDTO.setStatusCd(status);
        userightsRequestDTO.setOrderId(orderId);
        ResultVO resultVO = couponInstService.userights(userightsRequestDTO);
        log.info("gs_10010_userights,request={},resultVO={}",
                JSON.toJSONString(userightsRequestDTO), JSON.toJSONString(resultVO));
        BeanUtils.copyProperties(resultVO, resp);
        return resp;
    }

    public static void main(String[] args) {
        List<String> str = new ArrayList<>();
        str.add("1");
        str.add("2");
        StringBuilder sb = new StringBuilder();

        for (String coupon : str) {
            sb.append(coupon).append(",");
        }
        String a = "";
        if (sb.toString().contains(",")) {
            a = sb.substring(0, sb.toString().length() - 1);
        }
        System.out.println(a);
    }

}
