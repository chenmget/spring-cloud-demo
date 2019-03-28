package com.iwhalecloud.retail.promo.dto.resp;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author zhou.zc
 * @date 2019年02月27日
 * @Description:
 */
@Data
@ApiModel(value = "预售补贴产品配置查询")
public class PreSaleProductRespDTO implements Serializable {

    private static final long serialVersionUID = -9142849891637087201L;

    /**
     * 预售活动产品
     */
    @ApiModelProperty(value = "预售活动产品")
    List<PreSubsidyProductRespDTO> preSubsidyProductResqDTOList;
}
