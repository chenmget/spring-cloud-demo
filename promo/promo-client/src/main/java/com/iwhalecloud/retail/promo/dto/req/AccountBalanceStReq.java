package com.iwhalecloud.retail.promo.dto.req;

import com.iwhalecloud.retail.dto.AbstractRequest;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

import java.io.Serializable;

/**
 * @author 吴良勇
 * @date 2019/3/26 11:53
 * 账本统计请求参数
 */
@Data
public class AccountBalanceStReq extends AbstractRequest implements Serializable {

    private static final long serialVersionUID = 1L;
    @ApiModelProperty(value = "账户类型")
    private String acctType;
    @NotEmpty(message = "账户ID不能为空")
    @ApiModelProperty(value = "账户ID")
    private String acctId;
}
