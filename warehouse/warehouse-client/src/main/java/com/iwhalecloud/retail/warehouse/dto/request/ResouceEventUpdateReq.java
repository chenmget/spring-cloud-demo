package com.iwhalecloud.retail.warehouse.dto.request;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author My
 * @Date 2019/1/12
 **/
@Data
public class ResouceEventUpdateReq implements Serializable {

    /**
     * 记录状态。LOVB=RES-C-0010
     */
    @ApiModelProperty(value = "记录状态。LOVB=RES-C-0010")
    private String statusCd;

    /**
     * 营销资源库存变动事件标识
     */
    @ApiModelProperty(value = "营销资源库存变动事件标识")
    private java.lang.String mktResEventId;

}
