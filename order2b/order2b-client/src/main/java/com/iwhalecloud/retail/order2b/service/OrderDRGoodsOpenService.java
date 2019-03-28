package com.iwhalecloud.retail.order2b.service;

import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.order2b.dto.resquest.order.ReceiveGoodsReq;
import com.iwhalecloud.retail.order2b.dto.resquest.order.SendGoodsRequest;


public interface OrderDRGoodsOpenService {


    ResultVO deliverGoods(SendGoodsRequest request);


    ResultVO receiveGoods(ReceiveGoodsReq request);
}
