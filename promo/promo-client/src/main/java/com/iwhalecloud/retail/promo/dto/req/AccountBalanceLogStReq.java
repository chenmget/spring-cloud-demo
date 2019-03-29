package com.iwhalecloud.retail.promo.dto.req;

import com.iwhalecloud.retail.dto.AbstractRequest;
import lombok.Data;

import java.io.Serializable;

/**
 * @author 吴良勇
 * @date 2019/3/26 16:44
 */
@Data
public class AccountBalanceLogStReq extends AbstractRequest implements Serializable {
    private String acctId;

}
