package com.iwhalecloud.retail.goods2b.dto.req;

import com.iwhalecloud.retail.goods2b.dto.PageVO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Created by he.sw on 2018/12/24.
 */
@Data
public class ProductDetailGetReq extends PageVO {

    private static final long serialVersionUID = 1L;
    @ApiModelProperty(value = "productBaseId")
    private String productBaseId;
    /**
     * 产品类别
     */
    @ApiModelProperty(value = "产品类别")
    private String catId;

    /**
     * 产品分类
     */
    @ApiModelProperty(value = "产品类型")
    private String typeId;
    /**
     * 品牌
     */
    @ApiModelProperty(value = "品牌")
    private String brandId;

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
     * 型号名称
     */
    @ApiModelProperty(value = "型号名称")
    private String unitTypeName;


    /**
     * 产品编码
     */
    @ApiModelProperty(value = "产品编码")
    private String productCode;

    /**
     * 产品状态
     */
    @ApiModelProperty(value = "产品状态")
    private String status;

    /**
     * isDeleted
     */
    @ApiModelProperty(value = "isDeleted")
    private String isDeleted;

}
