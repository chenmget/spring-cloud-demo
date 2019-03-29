package com.iwhalecloud.retail.promo.dto.req;

import com.iwhalecloud.retail.dto.AbstractRequest;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 * @author 吴良勇
 * @date 2019/3/25 16:29
 * 返利计算请求
 */
@Data
public class AccountBalanceCalculationReq extends AbstractRequest implements Serializable {
    @NotEmpty(message = "商家标识不能为空")
    @ApiModelProperty(value = "商家标识")
    private String custId;
    @NotEmpty(message = "来源类型不能为空")
    @ApiModelProperty(value = "来源类型:3001返利款")
    private String balanceSourceType;
    @NotNull(message = "订单项不能为空")
    @ApiModelProperty(value = "订单项")
    private List<AccountBalanceCalculationOrderItemReq> orderItemList;
    @NotNull(message = "当前操作人ID不能为空")
    @ApiModelProperty(value = "当前操作人ID")
    private String userId;
}
