package com.iwhalecloud.retail.order2b.dto.model.order;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2019/3/29.
 */
@Data
public class SettleRecordOrderDTO implements Serializable {

    private String resNbr;

    private String supplierId;

    private String supplierCode;

    private String merchantId;

    private String merchantCode;

    private Double price;

    private Double couponPrice;

    private Date deliveryTime;

    private Integer lanId;

    private Date orderCreateTime;

    private String OrderId;

    private String supplierAccountId;

    private String merchantAccountId;

    private List<String> ids;
}
