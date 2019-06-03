package com.iwhalecloud.retail.goods2b.dto.req;

import com.iwhalecloud.retail.goods2b.dto.PageVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@ApiModel(value = "对应模型prod_goods, 对应实体ProdGoods类")
public class GoodsPageReq extends PageVO {

    @ApiModelProperty(value = "分类ID")
    private String goodsCatId;

    @ApiModelProperty(value = "商品状态，0：下架，1：上架")
    private Integer marketEnable;

    @ApiModelProperty(value = "营销活动ID列表")
    private List<String> actId;

    @ApiModelProperty(value = "营销活动名称")
    private String actName;

    @ApiModelProperty(value = "是否为分货商品，0：否，1：是")
    private Integer isAllot;

    @ApiModelProperty(value = "商品名称")
    private String goodsName;

    @ApiModelProperty(value = "地市ID或编码（暂不可用）")
    private String regionCity;

    /*@ApiModelProperty(value = "供应商名称")
    private String supplierName;*/

    @ApiModelProperty(value = "供应商ID列表(仅供管理员选择用)")
    private List<String> supplierIds;

    @ApiModelProperty(value = "起始时间")
    private Date beginTime;

    @ApiModelProperty(value = "终止时间")
    private Date endTime;

    @ApiModelProperty(value = "审批状态")
    private String auditState;

    @ApiModelProperty(value = "起始时间")
    private Date beginUpdateTime;

    @ApiModelProperty(value = "终止时间")
    private Date endUpdateTime;

    @ApiModelProperty(value = "产品型号")
    private String unitType;

    @ApiModelProperty(value = "产品ID列表")
    private List<String> brandIdList;

    @ApiModelProperty(value = "供货价起始价格")
    private Long deliveryPriceStart;

    @ApiModelProperty(value = "供货价结束价格")
    private Long deliveryPriceEnd;

    @ApiModelProperty(value = "来源平台")
    private String sourceFrom;

    @ApiModelProperty(value = "产品ID")
    private String productBaseId;

    @ApiModelProperty(value = "产品编码")
    private String sn;

    /**
     * 支付方式
     */
    @ApiModelProperty(value = "支付方式")
    private String payments;
    @ApiModelProperty(value = "供应商ID")
    private String supplierId;

    @ApiModelProperty(value = "供应商名称")
    private String supplierName;

    @ApiModelProperty(value = "供应商类型")
    private String merchantType;

    @ApiModelProperty(value = "类型Id")
    private String typeId;

    /**
     * 价格档位
     */
    @ApiModelProperty(value = "价格档位")
    private String priceLevel;
}
