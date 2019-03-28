package com.iwhalecloud.retail.order.dto.resquest;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Author My
 * @Date 2018/11/26
 **/
@Data
public class DeleteCartReqDTO implements Serializable {

    @ApiModelProperty(value = "是否清空已选购物车")
    private boolean clean;

    @ApiModelProperty(value = "是否清空购物车")
    private boolean cleanAll = false;

    @ApiModelProperty(value = "购物车编号")
    private List<String> cartIds;

    @ApiModelProperty(value = "会员ID")
    private String memberId;

    @ApiModelProperty(value = "判断是否清空")
    private String isClean;

}
