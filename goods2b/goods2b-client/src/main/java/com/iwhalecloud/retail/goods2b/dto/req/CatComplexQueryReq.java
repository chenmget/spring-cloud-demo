package com.iwhalecloud.retail.goods2b.dto.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Author: wang.jiaxin
 * @Date: 2019年02月27日
 * @Description:
 **/
@Data
public class CatComplexQueryReq extends AbstractPageReq implements Serializable {
    private static final long serialVersionUID = -1524264697344418417L;
    //属性 begin
    /**
     * catId
     */
    @ApiModelProperty(value = "catId")
    private String catId;

    /**
     * 该分类和分类下的所有子分类
     */
    private List<String> catIdList;

    /**
     * targetType
     */
    @ApiModelProperty(value = "targetType")
    private String targetType;

    /**
     * 是否登陆
     */
    @ApiModelProperty(value = "是否登陆")
    private Boolean isLogin;
}
