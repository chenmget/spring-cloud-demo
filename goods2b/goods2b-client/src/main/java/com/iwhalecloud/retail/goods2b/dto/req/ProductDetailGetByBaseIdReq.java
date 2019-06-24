package com.iwhalecloud.retail.goods2b.dto.req;

import com.iwhalecloud.retail.dto.AbstractRequest;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * Created by he.sw on 2018/12/24.
 */
@Data
public class ProductDetailGetByBaseIdReq extends AbstractRequest implements Serializable {

    private static final long serialVersionUID = -339631735078982115L;

    @ApiModelProperty(value = "productBaseId")
    private String productBaseId;

}
