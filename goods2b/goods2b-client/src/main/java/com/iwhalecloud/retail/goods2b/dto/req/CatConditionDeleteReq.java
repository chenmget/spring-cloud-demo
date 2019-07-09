package com.iwhalecloud.retail.goods2b.dto.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

import java.io.Serializable;

/**
 * @author wenlong.zhong
 * @date 2019/7/9
 */
@Data
@ApiModel(value = "商品类型条件 删除请求对象")
public class CatConditionDeleteReq implements Serializable {

    private static final long serialVersionUID = 8413022557752823702L;

    @ApiModelProperty(value = "主键ID")
    private String id;

    @ApiModelProperty(value = "产品类别ID(prod_cat表主键)")
    private String catId;

}
