package com.iwhalecloud.retail.partner.dto.resp;

import com.iwhalecloud.retail.partner.dto.req.SupplierReq;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(value = "供应商注册返回")
public class SupplierResistResp implements Serializable {

    private SupplierReq req;
    private String messg;
    private String desc;
}
