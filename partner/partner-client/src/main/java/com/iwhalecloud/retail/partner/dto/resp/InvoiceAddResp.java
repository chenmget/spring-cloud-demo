package com.iwhalecloud.retail.partner.dto.resp;


import com.iwhalecloud.retail.dto.PageVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author li.xinhang
 * @date 2019/2/23
 */

@Data
@ApiModel(value = "增加商家发票返回参数")
public class InvoiceAddResp implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 发票信息ID
     */
    @ApiModelProperty(value = "发票信息ID")
    private String invoiceId;
}
