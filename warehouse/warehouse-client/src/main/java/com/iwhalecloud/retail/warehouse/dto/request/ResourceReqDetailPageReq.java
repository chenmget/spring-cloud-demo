package com.iwhalecloud.retail.warehouse.dto.request;

import com.iwhalecloud.retail.dto.PageVO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author My
 * @Date 2019/04/18
 **/
@Data
public class ResourceReqDetailPageReq extends PageVO {

    @ApiModelProperty(value = "记录营销资源申请单标识")
    private String mktResReqId;

    @ApiModelProperty(value = "串码")
    private String mktResInstNbr;

}
