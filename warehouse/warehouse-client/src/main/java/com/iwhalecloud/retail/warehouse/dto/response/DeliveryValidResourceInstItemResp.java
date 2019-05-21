package com.iwhalecloud.retail.warehouse.dto.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @author 吴良勇
 * @date 2019/3/8 21:07
 */
@Data
@ApiModel(value = "串码校验返回")
public class DeliveryValidResourceInstItemResp implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * 产品键、串码集合值
     */
    @ApiModelProperty(value = "产品键、串码集合值")
    private Map<String, List<String>> productIdAndNbrList;

    /**
     * 不存在串码集合
     */
    @ApiModelProperty(value = "不存在串码集合")
    private List<String> notExistsNbrList;

    /**
     * 状态不正确串码集合
     */
    @ApiModelProperty(value = "状态不正确串码集合")
    private List<String> wrongStatusNbrList;

    /**
     * 串码产品id和校验产品id不匹配的串码
     */
    @ApiModelProperty(value = "串码产品id和校验产品id不匹配的串码")
    private List<String> notMatchProductIdNbrList;

}
