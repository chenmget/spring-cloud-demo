package com.iwhalecloud.retail.goods2b.dto;

import com.iwhalecloud.retail.dto.AbstractRequest;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * Created by Administrator on 2019/2/27.
 */
@Data
public class ComplexInfoDTO extends AbstractRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "complexInfoId")
    private String complexInfoId;

    @ApiModelProperty(value = "aGoodsId")
    private String aGoodsId;

    @ApiModelProperty(value = "zGoodsId")
    private String zGoodsId;

    @ApiModelProperty(value = "complexInfo")
    private String complexInfo;

}
