package com.iwhalecloud.retail.goods2b.dto.req;

import com.iwhalecloud.retail.dto.AbstractRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Author My
 * @Date 2018/11/8
 **/
@Data
@ApiModel(value = "商品组关联表")
public class GoodsGroupUpdateReq extends AbstractRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "商品组ID")
    private String groupId;

    @ApiModelProperty(value = "商品组ID")
    private List<String> goodsIds;
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
    /**
     * 描述
     */
    @ApiModelProperty(value = "描述")
    private String groupDesc;
}
