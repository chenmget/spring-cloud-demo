package com.iwhalecloud.retail.promo.dto.req;

import com.iwhalecloud.retail.dto.AbstractRequest;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author 吴良勇
 * @date 2019/3/12 20:06
 */
@Data
public class VerifyProductPurchasesLimitReq extends AbstractRequest implements Serializable {
    @NotEmpty
    @ApiModelProperty("活动ID")
    private String activityId;
    @NotEmpty
    @ApiModelProperty("产品ID")
    private String productId;
    @NotNull
    @ApiModelProperty("购买量")
    private Integer purchaseCount;
}
