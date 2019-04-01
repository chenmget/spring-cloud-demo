package com.iwhalecloud.retail.partner.dto.req;

import lombok.Data;

import java.util.List;

/**
 * @author 吴良勇
 * @date 2019/4/1 11:37
 */
@Data
public class QueryInvoiceByMerchantIdsReq  implements java.io.Serializable{

    private List<String> merchantIdList;

    private String invoiceType;

}
