package com.iwhalecloud.retail.order2b.model;

import com.iwhalecloud.retail.order2b.dto.resquest.order.ReceiveGoodsReq;
import lombok.Data;

import java.util.List;

@Data
public class HHReceiveGoodsModel {

    //正常收货
    private ReceiveGoodsReq receiveGoodsReq;

    //换货收货
    private List<ReceiveGoodsReq> hhReceiveReq;
}
