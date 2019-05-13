package com.iwhalecloud.retail.goods2b.dto.req;

import com.iwhalecloud.retail.goods2b.dto.PageVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel(value = "对应模型prod_product, 对应实体ProdProduct类")
public class ProductsPageReq extends PageVO {

    /**
     * 产品编码
     */
    @ApiModelProperty(value = "产品编码")
    private String sn;

    /**
     * 产品名称
     */
    @ApiModelProperty(value = "产品名称")
    private String unitName;

    /**
     * 基本表产品名称
     */
    @ApiModelProperty(value = "基本表产品名称")
    private String productName;

    /**
     * 类别名称
     */
    @ApiModelProperty(value = "类别名称")
    private String catName;


    /**
     * 状态:01 待提交，02审核中，03 已挂网，04 已退市
     */
    @ApiModelProperty(value = "状态:01 待提交，02审核中，03 已挂网，04 已退市")
    private List<String> statusList;

    /**
     * 查询表PROD_CAT使用
     */
    @ApiModelProperty(value = "产品类别")
    private String catId;

    /**
     * 查询表PROD_TYPE使用
     */
    @ApiModelProperty(value = "产品类型")
    private String typeId;

    /**
     * 查询表PROD_PRODUCT_BASE使用
     */
    @ApiModelProperty(value = "产品型号")
    private String unitType;
    /**
     * isDeleted
     */
    @ApiModelProperty(value = "isDeleted")
    private String isDeleted;

    /**
     * 归属厂家
     */
    @ApiModelProperty(value = "归属厂家")
    private String manufacturerId;

    /**
     * 产品id集合
     */
    @ApiModelProperty(value = "产品id集合")
    private List<String> productIdList;
    /**
     * 品牌id集合
     */
    @ApiModelProperty(value = "品牌id集合")
    private List<String> brandIdList;
    /**
     * 产品基本信息id集合
     */
    @ApiModelProperty(value = "产品基本信息id集合")
    private List<String> productBaseIdList;
    /**
     * 创建人
     */
    @ApiModelProperty(value = "创建人")
    private String createStaff;

    @ApiModelProperty(value = "销售价下限")
    private String costLower;

    @ApiModelProperty(value = "销售价上限")
    private String costUpper;

    @ApiModelProperty(value = "审核状态")
    private String auditState;

    @ApiModelProperty(value = "采购类型")
    private String purchaseType;
}
