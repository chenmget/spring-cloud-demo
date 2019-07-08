package com.iwhalecloud.retail.web.controller.b2b.order;

import com.alibaba.dubbo.config.annotation.Reference;
import com.iwhalecloud.retail.dto.ResultCodeEnum;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.order2b.dto.resquest.order.ReceiveGoodsReq;
import com.iwhalecloud.retail.order2b.dto.resquest.order.SendGoodsRequest;
import com.iwhalecloud.retail.order2b.service.OrderDRGoodsOpenService;
import com.iwhalecloud.retail.web.annotation.UserLoginToken;
import com.iwhalecloud.retail.web.controller.b2b.order.dto.DeliverGoodsReq;
import com.iwhalecloud.retail.web.controller.b2b.order.service.DeliveryGoodsResNberExcel;
import com.iwhalecloud.retail.web.interceptor.UserContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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
        SendGoodsRequest sendGoodsRequest = new SendGoodsRequest();
        BeanUtils.copyProperties(request,sendGoodsRequest);
        sendGoodsRequest.setUserId(UserContext.getUserId());
        sendGoodsRequest.setUserCode(UserContext.getUser().getRelCode());
        sendGoodsRequest.setMerchantId(UserContext.getMerchantId());
        try {
            if(request.getResNbrFile()!=null && !StringUtils.isEmpty(request.getResNbrList())){
                resultVO.setResultCode(ResultCodeEnum.ERROR.getCode());
                resultVO.setResultMsg("输入串码和导入串码不能同时上传");
                return resultVO;
            }
            if(request.getResNbrFile()==null && StringUtils.isEmpty(request.getResNbrList())){
                resultVO.setResultCode(ResultCodeEnum.ERROR.getCode());
                resultVO.setResultMsg("串码不能为空");
                return resultVO;
            }
            List<String> mktResInstNbrList = null;
            if(!StringUtils.isEmpty(request.getResNbrList())){
                mktResInstNbrList = request.getResNbrList();
            }else{
                mktResInstNbrList = deliveryGoodsResNberExcel.readExcel(request.getResNbrFile());
            }
            Integer shipNum = mktResInstNbrList.size();
            Integer maxNum = 1001;
            if(shipNum > maxNum){
                resultVO.setResultCode(ResultCodeEnum.ERROR.getCode());
                resultVO.setResultMsg("串码过多，不能超过1000个");
                return resultVO;
            }
        } catch (Exception e) {
            resultVO.setResultCode(ResultCodeEnum.ERROR.getCode());
            resultVO.setResultMsg("解析数据失败：error="+e.getMessage());
            return resultVO;
        }
        sendGoodsRequest.setOrderApplyId(request.getOrderApplyId());
        sendGoodsRequest.setType(request.getType());
        sendGoodsRequest.setGetCode(request.getGetGoodsCode());
        ResultVO resultVOTemp = orderDRGoodsOpenService.deliverGoods(sendGoodsRequest);
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
