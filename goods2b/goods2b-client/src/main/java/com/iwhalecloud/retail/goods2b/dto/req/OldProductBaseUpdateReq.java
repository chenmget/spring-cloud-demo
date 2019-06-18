package com.iwhalecloud.retail.goods2b.dto.req;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.iwhalecloud.retail.dto.AbstractRequest;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2019/6/18.
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class OldProductBaseUpdateReq extends AbstractRequest implements Serializable {

    private static final long serialVersionUID = 1L;


    /**
     * 产品信息
     */
    @ApiModelProperty(value = "产品信息")
    private List<ProductUpdateReq> productUpdateReqs;

    @ApiModelProperty(value = "产品名称")
    private String productName;

    @ApiModelProperty(value = "卖点")
    private String sallingPoint;


}
