package com.iwhalecloud.retail.goods2b.dto.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 根据活动查询型号
 * @author z
 * @date 2019/3/5
 */
@Data
public class ProductBaseActivityListReq extends ProductBaseListReq {

    private static final long serialVersionUID = 1L;

    /**
     * 活动ID集合
     */
    @ApiModelProperty(value = "活动ID集合")
    private List<String> activityIds;

    /**
     * 商家id用于权限查询
     */
    @ApiModelProperty(value = "商家id")
    private String merchantId;
}
