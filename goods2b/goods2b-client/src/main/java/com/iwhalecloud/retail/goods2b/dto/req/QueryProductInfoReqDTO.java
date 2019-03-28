package com.iwhalecloud.retail.goods2b.dto.req;

import com.iwhalecloud.retail.dto.AbstractRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author zhou.zc
 * @date 2019年02月24日
 * @Description:
 */
@Data
@ApiModel(value = "根据产品表id查询产品信息")
public class QueryProductInfoReqDTO extends AbstractRequest implements Serializable {

    private static final long serialVersionUID = -6287855163345600083L;

    /**
     * 产品表主键
     */
    @ApiModelProperty(value = "产品表主键")
    private String productId;

}
