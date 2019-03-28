package com.iwhalecloud.retail.oms.dto.resquest.cloud;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Auther: yangbl
 * @Date: 2018/11/1 16:24
 * @Description:
 */
@Data
@ApiModel(value = "云货架更新运营位")
public class CloudShelfAction implements Serializable {

    @ApiModelProperty(value = "动作add/delete")
    private String action;
    @ApiModelProperty(value = "id")
    private Long id; //id
    @ApiModelProperty(value = "货架类目id")
    private String shelfCategoryId; //货架类目id:推荐、套餐、手机、智能家居、配件

}
