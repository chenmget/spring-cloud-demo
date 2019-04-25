package com.iwhalecloud.retail.goods2b.dto.resp;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author My
 * @Date 2018/12/28
 **/
@Data
public class ProductForResourceResp implements Serializable {


    /**
     * 产品编码
     */
    @ApiModelProperty(value = "产品编码")
    private String sn;
    /**
     * 是否固网产品 1是；0否
     */
    @ApiModelProperty(value = "是否固网产品 1是；0否")
    private Double isFixedLine;

}
