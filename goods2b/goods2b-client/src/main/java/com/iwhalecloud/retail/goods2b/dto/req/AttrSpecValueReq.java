package com.iwhalecloud.retail.goods2b.dto.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author mzl
 * @date 2018/12/29
 */
@Data
public class AttrSpecValueReq implements Serializable {

    private static final long serialVersionUID = -7543679086400605823L;


    /**
     * 属性ID
     */
    @ApiModelProperty(value = "attrId")
    private String attrId;

    /**
     * 字段名称
     */
    @ApiModelProperty(value = "filedName")
    private String filedName;

    /**
     * 属性类型
     */
    @ApiModelProperty(value = "attrType")
    private String attrType;

    /**
     * 属性规格值列表
     */
    @ApiModelProperty(value = "属性规格值列表")
    private List<String> valuesList;
}
