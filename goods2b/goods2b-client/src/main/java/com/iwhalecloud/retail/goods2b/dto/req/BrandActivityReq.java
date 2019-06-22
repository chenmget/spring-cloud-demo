package com.iwhalecloud.retail.goods2b.dto.req;

import com.iwhalecloud.retail.dto.PageVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2019/3/6.
 */
@Data
@ApiModel(value = "B2B品牌查询商品请求参数")
public class BrandActivityReq extends PageVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "brandId")
    private String brandId;

    @ApiModelProperty(value = "regionId")
    private String regionId;

    @ApiModelProperty(value = "lanId")
    private String lanId;

    @ApiModelProperty(value = "supplierId")
    private String supplierId;

    @ApiModelProperty(value = "productIdList")
    private List<String> productIdList;
}
