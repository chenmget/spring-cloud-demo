package com.iwhalecloud.retail.goods2b.dto.req;

import com.iwhalecloud.retail.dto.AbstractRequest;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author My
 * @Date 2019/1/9
 **/
@Data
public class GoodsQueryReq extends AbstractRequest implements Serializable {


    @ApiModelProperty(value = "商品id")
    private String goodsId;

    @ApiModelProperty(value = "登录Id")
    private String userId;

    @ApiModelProperty(value = "是否登录")
    private Boolean isLogin;

    @ApiModelProperty(value = "产品id")
    private String productId;



}
