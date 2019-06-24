package com.iwhalecloud.retail.order2b.dto.model.order;

import lombok.Data;

import java.io.Serializable;
import java.util.List;


@Data
public class SendGoodsItemDTO implements Serializable {

    private String itemId;

    private String goodsId;

    private String productId;

    private Integer deliveryNum;

    /**
     * 串码id
     */
    private List<String> resNbrList;
}
