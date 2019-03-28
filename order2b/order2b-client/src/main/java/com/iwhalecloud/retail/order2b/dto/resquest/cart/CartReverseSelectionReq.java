package com.iwhalecloud.retail.order2b.dto.resquest.cart;

import com.iwhalecloud.retail.order2b.dto.base.OrderRequest;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author My
 * @Date 2018/11/29
 **/
@Data
public class CartReverseSelectionReq extends OrderRequest implements Serializable {

    @ApiModelProperty(value = "会员ID")
    private String userId;

    @ApiModelProperty(value = "分销商ID")
    private String supplierId;
    @ApiModelProperty(value = "反选状态")
    private String checkedFlag;


    private Integer checkedStatus;

}
