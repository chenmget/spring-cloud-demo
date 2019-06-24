package com.iwhalecloud.retail.promo.dto.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 * @author 吴良勇
 * @date 2019/3/27 19:28
 * 初始化创建账本的请求参数
 */
@Data
public class InitCreateAccountBalanceReq  implements Serializable {
    @NotEmpty(message = "创建人不能为空")
    @ApiModelProperty(value = "创建人")
    private String createStaff;

    @ApiModelProperty(value = "账户ID")
    @NotNull(message = "账户ID不能为空")
    private String acctId;


    @ApiModelProperty(value = "商家Id")
    @NotNull(message = "商家ID不能为空")
    private String custId;

    @NotNull(message = "订单项信息不能为空")
    @ApiModelProperty(value = "订单项信息")
    private List<AccountBalanceCalculationOrderItemReq> orderItemReqList;
    @ApiModelProperty(value = "账户余额类型")
    private String balanceTypeId;
}
