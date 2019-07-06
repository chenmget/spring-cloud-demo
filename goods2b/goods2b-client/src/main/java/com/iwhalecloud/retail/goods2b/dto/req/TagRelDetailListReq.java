package com.iwhalecloud.retail.goods2b.dto.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author wenlong.zhong
 * @date 2019/7/4
 */
@Data
@ApiModel(value = "查询ProdTagRel类 详情请求参数（带出标签信息）")
public class TagRelDetailListReq implements Serializable {

    private static final long serialVersionUID = 3473189349059560608L;
    /**
     * productBaseId
     */
    @ApiModelProperty(value = "productBaseId")
    private String productBaseId;

    /**
     * productId
     */
    @ApiModelProperty(value = "productId")
    private String productId;

}
