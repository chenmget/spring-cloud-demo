package com.iwhalecloud.retail.web.controller.b2b.order.dto;

import com.iwhalecloud.retail.oms.dto.response.FileManagerRespDTO;
import lombok.Data;

import java.io.Serializable;

@Data
public class DeliverGoodsItemDTO implements Serializable {

    private String itemId;
    private String goodsId;

    private String productId;

    private String  resNbrList;

    private FileManagerRespDTO resNbrFile;
}
