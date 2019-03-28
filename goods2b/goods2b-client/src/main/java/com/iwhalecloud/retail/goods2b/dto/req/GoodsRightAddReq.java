package com.iwhalecloud.retail.goods2b.dto.req;

import com.iwhalecloud.retail.dto.AbstractRequest;
import com.iwhalecloud.retail.goods2b.dto.RightDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@ApiModel(value = "新增 商品-权益关联 请求")
public class GoodsRightAddReq extends AbstractRequest implements Serializable {
    private static final long serialVersionUID = -7476462792528926579L;


    /**
     * 商品ID：goodsId
     */
    @ApiModelProperty(value = "goodsId")
    private java.lang.String goodsId;

    /**
     * 权益列表：rightList
     */
    @ApiModelProperty(value = "rightsId")
    private List<RightDTO> rightsList;


    /**
     * 权益状态：status  1000：有效   1100：无效
     */
//    @ApiModelProperty(value = "status")
//    private java.lang.String status;

}
