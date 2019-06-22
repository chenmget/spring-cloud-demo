package com.iwhalecloud.retail.partner.dto.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author Wu.LiangHang
 * @date 2018/11/14 15:48
 */
@Data
@ApiModel(value = "供应商账户新增")
public class SupplierAccountAddReq implements java.io.Serializable{
    //属性 begin
    /**
     * ACCOUNT_ID
     */
//    @ApiModelProperty(value = "ACCOUNT_ID")
//    private java.lang.String accountId;

    /**
     * SUPPLIER_ID
     */
    @ApiModelProperty(value = "SUPPLIER_ID")
    private java.lang.String supplierId;

    /**
     * ACCOUNT_TYPE
     */
    @ApiModelProperty(value = "ACCOUNT_TYPE")
    private java.lang.String accountType;

    /**
     * ACCOUNT
     */
    @ApiModelProperty(value = "ACCOUNT")
    private java.lang.String account;

    /**
     * IS_DEFAULT
     */
    @ApiModelProperty(value = "IS_DEFAULT")
    private java.lang.String isDefault;

    /**
     * 状态
     */
    @ApiModelProperty(value = "状态")
    private java.lang.String state;

}
