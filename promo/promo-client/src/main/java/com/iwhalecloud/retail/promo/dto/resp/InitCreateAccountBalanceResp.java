package com.iwhalecloud.retail.promo.dto.resp;

import com.iwhalecloud.retail.promo.dto.AccountBalanceDTO;
import com.iwhalecloud.retail.promo.dto.AccountBalanceRuleDTO;
import com.iwhalecloud.retail.promo.dto.AccountBalanceTypeDTO;
import lombok.Data;

import java.io.Serializable;

/**
 * @author 吴良勇
 * @date 2019/4/2 17:08
 */
@Data
public class InitCreateAccountBalanceResp implements Serializable {

    private AccountBalanceDTO accountBalance;
//    private AccountBalanceTypeDTO accountBalanceType;
//    private AccountBalanceRuleDTO accountBalanceRule;


}
