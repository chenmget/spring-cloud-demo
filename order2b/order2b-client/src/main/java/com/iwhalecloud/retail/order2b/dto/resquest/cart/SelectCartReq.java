package com.iwhalecloud.retail.order2b.dto.resquest.cart;

import com.iwhalecloud.retail.order2b.dto.base.MRequest;
import lombok.Data;

import java.io.Serializable;

@Data
public class SelectCartReq extends MRequest implements Serializable {

    private String supplierId;
}
