package com.iwhalecloud.retail.goods2b.dto.req;

import com.iwhalecloud.retail.dto.AbstractRequest;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * Created by he.sw on 2018/12/24.
 */
@Data
public class ProductGetIdReq extends AbstractRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "productName")
    private  String productName; // 产品名称

    @ApiModelProperty(value = "sn")
    private  String sn;// 产品25位编码

    @ApiModelProperty(value = "unitType")
    private String unitType;//产品型号

    @ApiModelProperty(value = "color")
    private String color; //颜色
    @ApiModelProperty(value = "memory")
    private  String memory;//内存

}
