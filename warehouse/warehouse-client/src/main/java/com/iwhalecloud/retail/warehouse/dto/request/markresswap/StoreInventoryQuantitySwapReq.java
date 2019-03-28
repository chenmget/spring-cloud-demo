package com.iwhalecloud.retail.warehouse.dto.request.markresswap;

import com.iwhalecloud.retail.warehouse.dto.request.markres.base.AbstractMarkResPageRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

import java.io.Serializable;

/**
 * @author 吴良勇
 * @date 2019/3/6 19:35
 */
@Data
@ApiModel("查询零售商仓库终端的库存数量按条件请求参数")
public class StoreInventoryQuantitySwapReq  extends AbstractMarkResPageRequest implements Serializable {

    @NotEmpty(message = "仓库ID不能为空")
    @ApiModelProperty(value = "仓库ID")
    private String storeId;

    @ApiModelProperty(value = "机型")
    private String mktResId;

    @ApiModelProperty(value = "状态")
    private String state;

}
