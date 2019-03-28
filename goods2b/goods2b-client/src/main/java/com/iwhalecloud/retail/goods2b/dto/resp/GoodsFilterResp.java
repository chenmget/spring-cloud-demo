package com.iwhalecloud.retail.goods2b.dto.resp;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author mzl
 * @date 2018/12/27
 */
@Data
public class GoodsFilterResp implements Serializable {

    private static final long serialVersionUID = 2766395184196851641L;

    @ApiModelProperty(value = "属性ID")
    private String attrId;

    @ApiModelProperty(value = "展示在前端页面的属性名称")
    private String cname;

    @ApiModelProperty(value = "展示顺序")
    private int order;

    @ApiModelProperty(value = "属性类型")
    private List<String> valueList;
}
