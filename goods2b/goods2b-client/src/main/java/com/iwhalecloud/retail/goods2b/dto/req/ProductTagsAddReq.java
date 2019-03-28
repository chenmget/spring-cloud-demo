package com.iwhalecloud.retail.goods2b.dto.req;

import com.iwhalecloud.retail.dto.AbstractRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author: wang.jiaxin
 * @Date: 2019年02月27日
 * @Description:
 **/
@Data
@ApiModel(value = "新增产品标签入参")
public class ProductTagsAddReq extends AbstractRequest implements java.io.Serializable {

    //属性 begin
    /**
     * productId
     */
    @ApiModelProperty(value = "productId")
    private String productId;

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
