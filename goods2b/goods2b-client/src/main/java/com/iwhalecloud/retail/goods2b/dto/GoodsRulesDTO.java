package com.iwhalecloud.retail.goods2b.dto;

import com.iwhalecloud.retail.dto.AbstractRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel
public class GoodsRulesDTO extends AbstractRequest implements Serializable {

    private String productBaseId;

    /**
     * 分货种类，1；机型，2：规格，默认按规格
     */
    private String assignType;

    @ApiModelProperty(value = "关联ID")
    private String goodsRuleId;

    @ApiModelProperty(value = "商品ID")
    private String goodsId;

    @ApiModelProperty(value = "产品ID")
    private String productId;

    /**
     * productCode,,,实际为sn
     */
    @ApiModelProperty(value = "产品编码")
    private String productCode;

    @ApiModelProperty(value = "产品名称")
    private String productName;

    @ApiModelProperty(value = "分货数量")
    private Long marketNum;

    @ApiModelProperty(value = "零售商ID")
    private String targetId;

    @ApiModelProperty(value = "目标对象编码")
    private String targetCode;

    @ApiModelProperty(value = "目标对象名称")
    private String targetName;

    @ApiModelProperty(value = "目标对象类型,1-经营主体，2-零售商")
    private String targetType;

    @ApiModelProperty(value = "提货数量")
    private Long purchasedNum;

}
