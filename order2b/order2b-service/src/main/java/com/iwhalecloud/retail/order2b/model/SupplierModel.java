package com.iwhalecloud.retail.order2b.model;

import com.iwhalecloud.retail.partner.dto.MerchantDTO;
import lombok.Data;

import java.io.Serializable;

@Data
public class SupplierModel extends MerchantDTO implements Serializable {

    private String supplierId;

    /**
     * 1 厂商    2 地包商    3 省包商   4 零售商
     */
    private java.lang.String merchantType;
}
