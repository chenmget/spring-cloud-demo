package com.iwhalecloud.retail.goods2b.dto.req;

import com.iwhalecloud.retail.goods2b.dto.PageVO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * Created by he.sw on 2018/12/24.
 */
@Data
public class ProductBaseListReq extends PageVO{

    private static final long serialVersionUID = 1L;

    /**
     * 产品类别
     */
    @ApiModelProperty(value = "产品类别")
    private String catId;
    /**
     * 类别名称
     */
    @ApiModelProperty(value = "类别名称")
    private String catName;
    /**
     * 品牌id
     */
    @ApiModelProperty(value = "品牌id")
    private String brandId;

    /**
     * 产品类型
     */
    @ApiModelProperty(value = "产品类型")
    private String typeId;

    /**
     * 产品名称
     */
    @ApiModelProperty(value = "产品名称")
    private String productName;

    /**
     * 产品型号
     */
    @ApiModelProperty(value = "产品型号")
    private String unitType;

    /**
     * 产品编码
     */
    @ApiModelProperty(value = "产品编码")
    private String productCode;
    /**
     * 25位产品编码
     */
    @ApiModelProperty(value = "25位产品编码")
    private String sn;

    /**
     * 产品基本信息ID列表
     */
    @ApiModelProperty(value = "产品基本信息ID列表")
    private List<String> productBaseIdList;

    /**
     * 品牌id集合
     */
    @ApiModelProperty(value = "品牌id集合")
    private List<String> brandIdList;

    /**
     * 产品状态
     */
    @ApiModelProperty(value = "产品状态")
    private String status;

    /**
     * 商家id用于权限查询
     */
    @ApiModelProperty(value = "商家id")
    private String merchantId;
    /**
     * 创建人
     */
    @ApiModelProperty(value = "创建人")
    private String createStaff;
    /**
     * 归属厂商
     */
    @ApiModelProperty(value = "归属厂商")
    private String manufacturerId;

    /**
     * 产品id集合
     */
    @ApiModelProperty(value = "产品id集合")
    private List<String> productIdList;

    /**
     * 价格档位
     */
    @ApiModelProperty(value = "价格档位")
    private String priceLevel;
}
