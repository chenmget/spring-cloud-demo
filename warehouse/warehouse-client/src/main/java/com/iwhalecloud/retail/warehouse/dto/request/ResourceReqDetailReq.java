package com.iwhalecloud.retail.warehouse.dto.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import java.io.Serializable;

/**
 * @Author My
 * @Date 2019/04/18
 **/
@Data
public class ResourceReqDetailReq implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "记录营销资源申请单标识")
    @NotBlank(message = "申请单ID不能为空")
    private String mktResReqId;

    @ApiModelProperty(value = "串码")
    private String mktResInstNbr;

    @ApiModelProperty(value = "是否抽检")
    private String isInspection;

}
