package com.iwhalecloud.retail.goods.dto.resp;

import com.iwhalecloud.retail.goods.dto.ProdSpecificationDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author mzl
 * @date 2018/11/30
 */
@Data
@ApiModel(value = "商品规格列表")
public class ProdGoodsSpecListResp implements Serializable {

    private static final long serialVersionUID = -1754479798505194115L;

    @ApiModelProperty(value = "规格列表")
    private List<ProdSpecificationDTO> specificationList;
}
