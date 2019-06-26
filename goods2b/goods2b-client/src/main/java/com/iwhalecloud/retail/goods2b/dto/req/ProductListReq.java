package com.iwhalecloud.retail.goods2b.dto.req;

import com.google.common.collect.Lists;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 产品列表 查询入参
 * @author wenlong.zhong
 * @date 2019/6/26
 */
@Data
public class ProductListReq implements Serializable {
    private static final long serialVersionUID = 7116768778614832022L;

    /**
     * 状态:01 待提交，02审核中，03 已挂网，04 已退市
     */
    @ApiModelProperty(value = "状态:1 待提交，2审核中，3 已挂网，4 已退市")
    private String status;

    /**
     * isDeleted
     */
    @ApiModelProperty(value = " 是否被删除: 0:否   1：是")
    private String isDeleted;


    @ApiModelProperty(value = "attrValue10")
    private String attrValue10;


}
