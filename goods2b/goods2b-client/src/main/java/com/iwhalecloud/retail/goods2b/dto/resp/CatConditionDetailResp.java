package com.iwhalecloud.retail.goods2b.dto.resp;

import com.iwhalecloud.retail.goods2b.dto.TypeDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author wenlong.zhong
 * @date 2019/7/11
 */
@Data
@ApiModel(value = "商品分类条件详情, relType=1的会带出 type详情")
public class CatConditionDetailResp implements Serializable {

    private static final long serialVersionUID = 5999483192120555715L;


    @ApiModelProperty(value = "类型详情，relType=1 时才有值")
    private TypeDTO typeDetail;

    /**
     * 主键ID
     */
    @ApiModelProperty(value = "主键ID")
    private String id;

    /**
     * 产品类别ID
     */
    @ApiModelProperty(value = "产品类别ID(prod_cat表主键)")
    private String catId;

    /**
     * 商品类型关联的筛选条件类型 1. 产品类型 2. 产品属性 3. 品牌 4. 营销活动类型 5. 产品标签
     */
    @ApiModelProperty(value = "商品类型关联的筛选条件类型 1. 产品类型 2. 产品属性 3. 品牌 4. 营销活动类型 5. 产品标签")
    private String relType;

    /**
     *   商品类型关联类型为以下类型时，记录关联的对象ID 1. 产品类型 时 记录产品类型ID 2. 产品属性 时 记录产品属性ID 3. 品牌 时 记录品牌ID 4. 营销活动类型 时 记录营销活动类型ID 5. 产品标签 时 记录产品标签ID
     */
    @ApiModelProperty(value = "  商品类型关联类型为以下类型时，记录关联的对象ID 1. 产品类型 时 记录产品类型ID 2. 产品属性 时 记录产品属性ID 3. 品牌 时 记录品牌ID 4. 营销活动类型 时 记录营销活动类型ID 5. 产品标签 时 记录产品标签ID")
    private String relObjId;

    /**
     * 商品类型关联类型 产品属性时记录属性的值
     */
    @ApiModelProperty(value = "商品类型关联类型 产品属性时记录属性的值")
    private String relObjValue;

    /**
     * 排序
     */
    @ApiModelProperty(value = "排序")
    private Long orderBy;
}
