package com.iwhalecloud.retail.goods2b.dto.req;

import com.iwhalecloud.retail.dto.AbstractRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author My
 * @Date 2018/11/7
 **/
@Data
@ApiModel(value = "商品组")
public class GoodGroupQueryReq extends AbstractRequest implements Serializable {
    /**
     * 商品组名称
     */
    @ApiModelProperty(value = "商品组名称")
    private String groupName;
    /**
     * 商品组编码
     */
    @ApiModelProperty(value = "商品组编码")
    private String groupCode;

    @ApiModelProperty(value = "页码")
    private Integer pageNo = 1;

    @ApiModelProperty(value = "每页数量")
    private Integer pageSize = 10;

}
