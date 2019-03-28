package com.iwhalecloud.retail.partner.dto.resp;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author Wu.LiangHang
 * @date 2018/11/14 16:16
 */
@Data
@ApiModel(value = "供应商账户新增返回参数类")
public class SupplierAccountAddResp implements java.io.Serializable{
    /**
     * ACCOUNT_ID
     */
    @TableId(type = IdType.ID_WORKER_STR)
    @ApiModelProperty(value = "ACCOUNT_ID")
    private java.lang.String accountId;

    /**
     * SUPPLIER_ID
     */
    @ApiModelProperty(value = "SUPPLIER_ID")
    private java.lang.String supplierId;

}
