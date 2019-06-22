package com.iwhalecloud.retail.partner.dto.req;

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
@ApiModel(value = "查询商家发票列表")
public class InvoicePageReq extends PageVO implements Serializable {
    
    private static final long serialVersionUID = 1L;
    

    /**
     * 商家ID
     */
    @ApiModelProperty(value = "商家ID")
    private String merchantId;


    /**
     * 发票类型
     */
    @ApiModelProperty(value = "发票类型：OTC-0006； 100\t普通发票 110\t普通增值税发票 120\t专用增值税发票 130\t电子发票 200\t收据")
    private String invoiceType;
    
    
}
