package com.iwhalecloud.retail.goods2b.dto.req;

import com.iwhalecloud.retail.dto.AbstractRequest;
import com.iwhalecloud.retail.goods2b.dto.InfoDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2019/2/27.
 */
@Data
@ApiModel(value = "添加商品推荐信息")
public class ComplexInfoReq extends AbstractRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "aGoodsId")
    private String aGoodsId;

    @ApiModelProperty(value = "zGoodsId")
    private List<InfoDTO> infoDTOList;
}
