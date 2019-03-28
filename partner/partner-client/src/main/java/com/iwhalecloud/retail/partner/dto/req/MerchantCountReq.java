package com.iwhalecloud.retail.partner.dto.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author wenlong.zhong
 * @date 2019/3/27
 */
@Data
@ApiModel("根据条件查找 商家 条数")
public class MerchantCountReq implements Serializable {

    private static final long serialVersionUID = 4889258044628065390L;

    @ApiModelProperty(value = "商家状态")
    private String status;

    @ApiModelProperty(value = "商家类型集合")
    private List<String> merchantTypeList;


}
