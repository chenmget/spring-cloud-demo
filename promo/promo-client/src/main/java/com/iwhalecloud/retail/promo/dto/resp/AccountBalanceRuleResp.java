package com.iwhalecloud.retail.promo.dto.resp;

import lombok.Data;

import java.io.Serializable;

/**
 * @author 吴良勇
 * @date 2019/4/2 18:51
 */
@Data
public class AccountBalanceRuleResp implements Serializable {

    private String actId;
    private String objType;
    private String objId;
    private String balanceTypeId;
    private String balanceTypeName;
}
