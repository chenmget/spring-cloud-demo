package com.iwhalecloud.retail.goods2b.dto.req;

import com.iwhalecloud.retail.dto.AbstractRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author My
 * @Date 2018/11/6
 **/
@Data
@ApiModel(value = "商品组关联表")
public class GoodsGroupRelAddReq extends AbstractRequest implements Serializable {
    /**
     *商品id
     */
    @ApiModelProperty(value = "商品id")
    private String goodsId;
    /**
     *商品组id
     */
    @ApiModelProperty(value = "商品组id")
    private String groupId;
    /**
     *平台
     */
    @ApiModelProperty(value = "平台")
    private String sourceFrom;
}
