package com.iwhalecloud.retail.order2b.dto.resquest.promo;

import com.iwhalecloud.retail.dto.AbstractRequest;
import com.iwhalecloud.retail.order2b.dto.base.PageVO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 * @author lhr 2019-03-30 10:10:30
 */
@Data
public class ReBateOrderInDetailReq extends PageVO implements Serializable {

    private static final long serialVersionUID = -8960398445068683824L;
    @NotNull(message = "订单ID不能为空")
    @ApiModelProperty(value = "订单")
    private String orderId;
    @NotNull(message = "订单项ID不能为空")
    @ApiModelProperty(value = "订单项Id")
    private String itemId;

}
