package com.iwhalecloud.retail.goods.dto.req;

import com.iwhalecloud.retail.goods.dto.PageVO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author My
 * @Date 2018/11/27
 **/
@Data
public class ListCommentsReqDTO extends PageVO {

    @ApiModelProperty(value = "商品ID或订单ID")
    private String goodsId;

    @ApiModelProperty(value = "commentType")
    private String commentType;
}
