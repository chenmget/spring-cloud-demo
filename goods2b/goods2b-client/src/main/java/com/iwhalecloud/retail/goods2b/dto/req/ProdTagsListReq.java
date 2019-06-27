package com.iwhalecloud.retail.goods2b.dto.req;

import com.iwhalecloud.retail.dto.AbstractRequest;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * @Author My
 * @Date 2018/11/29
 **/
public class ProdTagsListReq extends AbstractRequest implements Serializable {
    private static final long serialVersionUID = 1L;


    //属性 begin
    /**
     * tagId
     */
    @ApiModelProperty(value = "tagId")
    private String tagId;

    /**
     * tagType
     */
    @ApiModelProperty(value = "tagType")
    private String tagType;

    /**
     * tagName
     */
    @ApiModelProperty(value = "tagName")
    private String tagName;

    /**
     * createStaff
     */
    @ApiModelProperty(value = "createStaff")
    private String createStaff;

    /**
     * updateStaff
     */
    @ApiModelProperty(value = "updateStaff")
    private String updateStaff;

}
