package com.iwhalecloud.retail.goods2b.dto.req;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.iwhalecloud.retail.dto.AbstractRequest;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * Created by he.sw on 2018/12/24.
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProdProductBaseSoftDelReq extends AbstractRequest implements Serializable {

    private static final long serialVersionUID = 6485624111213931640L;

    @ApiModelProperty(value = "productBaseId")
    private String productBaseId;


}
