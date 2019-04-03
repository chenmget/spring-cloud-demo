package com.iwhalecloud.retail.promo.dto.req;

import com.iwhalecloud.retail.dto.AbstractRequest;
import lombok.Data;

import java.io.Serializable;

/**
 * @author 吴良勇
 * @date 2019/3/25 18:02
 */
@Data
public class QueryAccountBalanceReq extends AbstractRequest implements Serializable {
    private String custId;
    private String acctId;
    private String balanceTypeId;
}
