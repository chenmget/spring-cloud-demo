package com.iwhalecloud.retail.web.controller.b2b.order.dto.response;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.order2b.consts.OrderManagerConsts;
import com.iwhalecloud.retail.order2b.dto.model.order.OrderItemDTO;
import com.iwhalecloud.retail.order2b.dto.model.order.PromotionDTO;
import com.iwhalecloud.retail.order2b.dto.response.OrderSelectDetailResp;
import com.iwhalecloud.retail.order2b.dto.response.OrderSelectResp;
import org.springframework.beans.BeanUtils;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class OrderSelectSwap {

    public static final OrderSelectSwapResp swapOrderSelectResp(OrderSelectResp resp) {
        OrderSelectSwapResp swapResp = null;
        if (resp != null) {
            swapResp = new OrderSelectSwapResp();
            List<OrderItemSwapResp> orderItems = new ArrayList<OrderItemSwapResp>();
            //属性复制
            BeanUtils.copyProperties(resp, swapResp);
            //订单项
            List<OrderItemDTO> itemList = resp.getOrderItems();
            //营销活动
            List<PromotionDTO> promotionList = resp.getPromotionList();

            swapResp.setOrderItems(swapOrderItemsList(itemList, promotionList));
        }
        return swapResp;
    }

    public static final ResultVO<IPage<OrderSelectSwapResp>> swapResultOrderSelectRespList(ResultVO<IPage<OrderSelectResp>> respResultVO) {
        ResultVO<IPage<OrderSelectSwapResp>> result = new ResultVO<IPage<OrderSelectSwapResp>>();
        //属性复制
        BeanUtils.copyProperties(respResultVO, result);

        IPage<OrderSelectResp> page = respResultVO.getResultData();
        List<OrderSelectResp> list = page.getRecords();
        List<OrderSelectSwapResp> swapList = new ArrayList<OrderSelectSwapResp>();
        if (list != null && !list.isEmpty()) {
            for (OrderSelectResp orderSelectResp : list) {
                OrderSelectSwapResp swapResp = swapOrderSelectResp(orderSelectResp);
                swapList.add(swapResp);
            }
        }
        IPage<OrderSelectSwapResp> reusltPage = result.getResultData();
        reusltPage.setRecords(swapList);
        return result;

    }

    /**
     * 将ResultVO<OrderSelectDetailResp>转ResultVO<OrderSelectDetailSwapResp>
     * @param respResultVO
     */
    public static final ResultVO<OrderSelectDetailSwapResp> swapResultOrderSelectDetailResp(ResultVO<OrderSelectDetailResp> respResultVO) {
        ResultVO<OrderSelectDetailSwapResp> result = new  ResultVO<OrderSelectDetailSwapResp>();
        //属性复制
        BeanUtils.copyProperties(respResultVO, result);
        OrderSelectDetailResp orderSelectDetailResp = (OrderSelectDetailResp) respResultVO.getResultData();
        result.setResultData(swapOrderSelectDetailResp(orderSelectDetailResp));
        return result;

    }

    /**
     * 将OrderSelectDetailResp 转成OrderSelectDetailSwapResp
     * @param resp
     * @return
     */
    public static final OrderSelectDetailSwapResp swapOrderSelectDetailResp(OrderSelectDetailResp resp) {

        OrderSelectDetailSwapResp swapResp = null;
        if (resp != null) {
            swapResp = new OrderSelectDetailSwapResp();
            List<OrderItemSwapResp> orderItems = new ArrayList<OrderItemSwapResp>();
            //属性复制
            BeanUtils.copyProperties(resp, swapResp);
            //订单项
            List<OrderItemDTO> itemList = resp.getOrderItems();
            //营销活动
            List<PromotionDTO> promotionList = resp.getPromotionList();

            swapResp.setOrderItems(swapOrderItemsList(itemList, promotionList));
        }
        return swapResp;

    }

    private static final List<OrderItemSwapResp> swapOrderItemsList(List<OrderItemDTO> itemList, List<PromotionDTO> promotionList) {
        List<OrderItemSwapResp> orderItems = new ArrayList<OrderItemSwapResp>();

        if (itemList != null && !itemList.isEmpty()) {
            Map<String, List<PromotionDTO>> map = new HashMap<String, List<PromotionDTO>>();
            if (promotionList != null && !promotionList.isEmpty()) {
                for (PromotionDTO promotionDTO : promotionList) {
                    String key = promotionDTO.getOrderId() + "_" + promotionDTO.getOrderItemId();
                    if (!map.containsKey(key)) {
                        map.put(key, new ArrayList<PromotionDTO>());
                    }
                    map.get(key).add(promotionDTO);
                }
            }
            for (OrderItemDTO item : itemList) {
                OrderItemSwapResp itemSwap = new OrderItemSwapResp();
                BeanUtils.copyProperties(item, itemSwap);
                String key = item.getOrderId() + "_" + item.getItemId();
                List<PromotionDTO> promotionDTOList = map.get(key);

                /**
                 * 前置补贴活动，不能退货
                 */
                itemSwap.setIsRetrun("0");
                if(!CollectionUtils.isEmpty(promotionDTOList)){
                    for (PromotionDTO dto: promotionDTOList){
                        if(OrderManagerConsts.ACTIVITY_TYPE_1002.equals(dto.getMktActType())){
                            itemSwap.setIsRetrun("1");
                            break;
                        }
                    }
                }
                itemSwap.setPromotionList(promotionDTOList);
                orderItems.add(itemSwap);
            }
        }
        return orderItems;


    }
}
