package com.iwhalecloud.retail.goods2b.dto.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author wenlong.zhong
 * @date 2019/6/3
 */
@Data
@ApiModel(value = "商品类型条件 新建请求对象")
public class CatConditionListReq implements Serializable {
    private static final long serialVersionUID = -8932336306321239450L;

    @ApiModelProperty(value = "产品类别ID(prod_cat表主键)")
    private String catId;

    @ApiModelProperty(value = "商品类型关联的筛选条件类型 1. 产品类型 2. 产品属性 3. 品牌 4. 营销活动类型 5. 产品标签")
    private String relType;

    @ApiModelProperty(value = "  商品类型关联类型为以下类型时，记录关联的对象ID 1. 产品类型 时 记录产品类型ID 2. 产品属性 时 记录产品属性ID 3. 品牌 时 记录品牌ID 4. 营销活动类型 时 记录营销活动类型ID 5. 产品标签 时 记录产品标签ID")
    private String relObjId;

    @ApiModelProperty(value = "商品类型关联类型 产品属性时记录属性的值")
    private String relObjValue;

}
