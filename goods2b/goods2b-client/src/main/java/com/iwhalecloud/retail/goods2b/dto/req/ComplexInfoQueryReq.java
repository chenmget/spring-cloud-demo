package com.iwhalecloud.retail.goods2b.dto.req;

import com.iwhalecloud.retail.dto.AbstractRequest;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2019/3/2.
 */
@Data
public class ComplexInfoQueryReq extends AbstractRequest implements Serializable {
    private static final long serialVersionUID = 1L;
    @ApiModelProperty(value = "goodsIdList")
    private List<String> goodsIdList;
}
