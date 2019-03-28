package com.iwhalecloud.retail.goods2b.dto.req;

import com.iwhalecloud.retail.dto.AbstractRequest;
import com.iwhalecloud.retail.goods2b.dto.BuyCountCheckDTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2019/3/2.
 */
@Data
public class GoodsProductRelEditReq extends AbstractRequest implements Serializable {
    private static final long serialVersionUID = 1L;
    @ApiModelProperty
    private String productId;

    @ApiModelProperty
    private String goodsId;

    @ApiModelProperty
    private Boolean isHaveStock;

    @ApiModelProperty
    List<BuyCountCheckDTO> buyCountCheckDTOList;

    @ApiModelProperty
    private String marketingActivityId;

    /**
     * 预售标识(1=预售，0否)
     */
    @ApiModelProperty
    private String isAdvanceSale;

    /**
     * 商家ID
     */
    @ApiModelProperty(value = "商家ID")
    private String merchantId;

    /**
     * 买家ID
     */
    @ApiModelProperty(value = "买家ID")
    private String userId;
}
