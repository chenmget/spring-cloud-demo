package com.iwhalecloud.retail.partner.dto.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author zhou.zc
 * @date 2019年03月28日
 * @Description:根据lan和city查询商家信息
 */
@Data
public class MerchantListLanCityReq implements Serializable {

    private static final long serialVersionUID = -5398664071000146043L;

    @ApiModelProperty(value = "本地网集合")
    private List<String> lanList;

    @ApiModelProperty(value = "地市集合")
    private List<String> cityList;
}
