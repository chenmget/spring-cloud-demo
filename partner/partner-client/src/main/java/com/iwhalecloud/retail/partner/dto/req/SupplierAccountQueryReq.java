package com.iwhalecloud.retail.partner.dto.req;

import com.iwhalecloud.retail.dto.PageVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author Wu.LiangHang
 * @date 2018/11/15 20:23
 */
@Data
@ApiModel(value = "供应商账户查询")
public class SupplierAccountQueryReq extends PageVO {
    @ApiModelProperty(value = "供应商ID")
    private String supplierId;
}
