package com.iwhalecloud.retail.promo.dto.resp;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 参与活动产品返回体
 *
 * @author xu.qinyuan@ztesoft.com
 * @date 2019年02月20日
 */
@Data
@ApiModel(value = "添加参与活动产品返回结果")
public class ActivityProductResp implements Serializable{

    private static final long serialVersionUID = 5371450874265256639L;

    /**
     * 参与活动产品Id
     */
    @ApiModelProperty(value = "参与活动产品Id")
    private String id;
}
