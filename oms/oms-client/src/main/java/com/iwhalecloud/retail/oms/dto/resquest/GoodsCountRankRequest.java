package com.iwhalecloud.retail.oms.dto.resquest;

import com.iwhalecloud.retail.oms.dto.PageVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
@ApiModel(value = "对应搜索GoodsCountRank模型, 对应实体GoodsCountRankRequest类")
public class GoodsCountRankRequest extends PageVO {
    @ApiModelProperty(value = "adscriptionCity")
    private String adscriptionCity;

    @ApiModelProperty(value = "eventCode")
    private String eventCode;

    @ApiModelProperty(value = "beginTime")
    private Date beginTime;

    @ApiModelProperty(value = "endTime")
    private Date endTime;


}
