package com.iwhalecloud.retail.goods2b.dto.req;

import com.iwhalecloud.retail.dto.AbstractRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Author My
 * @Date 2018/11/6
 **/
@Data
@ApiModel(value = "商品组")
public class GoodsGroupAddReq extends AbstractRequest implements Serializable {

    /**
     * 商品组名称
     */
    @ApiModelProperty(value = "商品组名称")
    private String groupName;
    /**
     *商品id
     */
    @ApiModelProperty(value = "商品id")
    private List<String> goodsIds;
    /**
     * 商品组编码
     */
    @ApiModelProperty(value = "商品组编码")
    private String groupCode;
    /**
     * 描述
     */
    @ApiModelProperty(value = "描述")
    private String groupDesc;
    /**
     * 平台
     */
    @ApiModelProperty(value = "平台")
    private String sourceFrom;

}
