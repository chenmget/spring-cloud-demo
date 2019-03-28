package com.iwhalecloud.retail.goods2b.dto.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author: wang.jiaxin
 * @Date: 2019年02月27日
 * @Description:
 **/
@Data
public class GoodsQueryByTagsReq implements Serializable {

    //属性 begin
    /**
     * tagId
     */
    @ApiModelProperty(value = "tagId")
    private String tagId;

}
