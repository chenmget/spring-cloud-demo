package com.iwhalecloud.retail.goods2b.dto.resp;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author My
 * @Date 2018/12/26
 **/
@Data
public class GoodActRelResp implements Serializable {
    /**
     * 营销活动ID
     */
    @ApiModelProperty(value = "营销活动ID")
    private String actId;

    /**
     * 营销活动名称
     */
    @ApiModelProperty(value = "营销活动名称")
    private String actName;
}
