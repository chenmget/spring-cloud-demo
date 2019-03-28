package com.iwhalecloud.retail.web.controller.b2b.order;

import com.alibaba.dubbo.config.annotation.Reference;
import com.iwhalecloud.retail.dto.ResultCodeEnum;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.oms.OmsCommonConsts;
import com.iwhalecloud.retail.order2b.dto.model.order.SendGoodsItemDTO;
import com.iwhalecloud.retail.order2b.dto.resquest.order.ReceiveGoodsReq;
import com.iwhalecloud.retail.order2b.dto.resquest.order.SendGoodsRequest;
import com.iwhalecloud.retail.order2b.service.OrderDRGoodsOpenService;
import com.iwhalecloud.retail.warehouse.dto.response.ValidResourceInstResp;
import com.iwhalecloud.retail.web.annotation.UserLoginToken;
import com.iwhalecloud.retail.web.controller.b2b.order.dto.DeliverGoodsItemDTO;
import com.iwhalecloud.retail.web.controller.b2b.order.dto.DeliverGoodsReq;
import com.iwhalecloud.retail.web.controller.b2b.order.service.DeliveryGoodsResNberExcel;
import com.iwhalecloud.retail.web.interceptor.UserContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/b2b/order/drgoods")
@Slf4j
public class OrderDRGoodsB2BController {
    /**
     * 收货，发货
     */

    @Reference
    private OrderDRGoodsOpenService orderDRGoodsOpenService;

    @Autowired
    private DeliveryGoodsResNberExcel deliveryGoodsResNberExcel;



    /**
     * 发货
     */
    @RequestMapping(value="/deliverGoods",method = RequestMethod.POST)
    @UserLoginToken
    public ResultVO deliverGoods(@RequestBody DeliverGoodsReq request){
        ResultVO resultVO=new ResultVO();
        SendGoodsRequest sendGoodsRequest=new SendGoodsRequest();
        BeanUtils.copyProperties(request,sendGoodsRequest);
        sendGoodsRequest.setUserId(UserContext.getUserId());
        sendGoodsRequest.setUserCode(UserContext.getUser().getRelCode());

        if(CollectionUtils.isEmpty(request.getGoodsList())){
            resultVO.setResultCode(OmsCommonConsts.RESULE_CODE_FAIL);
            resultVO.setResultMsg("订单项不能为空");
            return resultVO;
        }

        try {
            Integer shipNum=0;
            List<SendGoodsItemDTO> itemDTOList=new ArrayList<>(request.getGoodsList().size());
            for (DeliverGoodsItemDTO item:request.getGoodsList()) {
                SendGoodsItemDTO sendGoodsItemDTO=new SendGoodsItemDTO();
                BeanUtils.copyProperties(item,sendGoodsItemDTO);

                if(item.getResNbrFile()!=null && !StringUtils.isEmpty(item.getResNbrList())){
                    resultVO.setResultCode(OmsCommonConsts.RESULE_CODE_FAIL);
                    resultVO.setResultMsg("输入串码和导入串码不能同时");
                    return resultVO;
                }

                if(item.getResNbrFile()==null && StringUtils.isEmpty(item.getResNbrList())){
                    resultVO.setResultCode(OmsCommonConsts.RESULE_CODE_FAIL);
                    resultVO.setResultMsg("串码不能为空");
                    return resultVO;
                }
                List<String> strings=new ArrayList<>();
                if(!StringUtils.isEmpty(item.getResNbrList())){
                    strings=Arrays.asList(item.getResNbrList().split("\n"));
                }else{
                    strings.addAll(deliveryGoodsResNberExcel.readExcel(item.getResNbrFile()));
                }

                shipNum+=strings.size();
                sendGoodsItemDTO.setResNbrList(strings);
                itemDTOList.add(sendGoodsItemDTO);
            }
            if(shipNum>1001){
                resultVO.setResultCode(OmsCommonConsts.RESULE_CODE_FAIL);
                resultVO.setResultMsg("串码过多，不能超过1000个");
                return resultVO;
            }
            sendGoodsRequest.setShipNum(shipNum);
            sendGoodsRequest.setGoodsItemDTOList(itemDTOList);
        } catch (Exception e) {
            e.printStackTrace();
            resultVO.setResultCode(OmsCommonConsts.RESULE_CODE_FAIL);
            resultVO.setResultMsg("解析数据失败：error="+e.getMessage());

            return resultVO;
        }
        sendGoodsRequest.setOrderApplyId(request.getOrderApplyId());
        sendGoodsRequest.setType(request.getType());
        sendGoodsRequest.setGetCode(request.getGetGoodsCode());
        ResultVO resultVOTemp = orderDRGoodsOpenService.deliverGoods(sendGoodsRequest);

        if(!resultVOTemp.isSuccess()){
            Object data = resultVOTemp.getResultData();
            //如果是串码校验失败的，前端无法获取getResultData的数据,这里返回处理成功，但是校验结果为失败
            if(data!=null&& data instanceof ValidResourceInstResp){
                resultVOTemp.setResultCode(ResultCodeEnum.SUCCESS.getCode());
                return resultVOTemp;
            }
        }


        return resultVOTemp;
    }

    /**
     * 确认收货
     */
    @RequestMapping(value="/receiveGoods",method = RequestMethod.POST)
    @UserLoginToken
    public ResultVO receiveGoods(@RequestBody ReceiveGoodsReq request){
        request.setUserId(UserContext.getUserId());
        request.setUserCode(UserContext.getUser().getRelCode());
        return orderDRGoodsOpenService.receiveGoods(request);
    }

}
