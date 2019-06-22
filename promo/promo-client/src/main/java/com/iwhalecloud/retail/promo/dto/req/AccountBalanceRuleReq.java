package com.iwhalecloud.retail.promo.dto.req;

import com.iwhalecloud.retail.dto.AbstractRequest;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author 吴良勇
 * @date 2019/4/2 18:51
 */
@Data
public class AccountBalanceRuleReq  extends AbstractRequest implements Serializable {

    private String actId;
    private String ruleType;
    private String objId;
    private String balanceTypeId;

    private List<String> actIdList;

    private List<String> objIdList;
}
