package com.iwhalecloud.retail.goods2b.dto.resp;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author wenlong.zhong
 * @date 2019/7/4
 */
@Data
@ApiModel(value = "查询ProdTagRel类集合 （带出标签信息）")
public class TagRelDetailListResp implements Serializable {

    @ApiModelProperty(value = "relId")
    private String relId;

    @ApiModelProperty(value = "tagId")
    private String tagId;

    @ApiModelProperty(value = "productBaseId")
    private String productBaseId;

    @ApiModelProperty(value = "productId")
    private String productId;

    /**  下面到是 prod_tag  表字段 ***/

    @ApiModelProperty(value = "tagName")
    private String tagName;
}
