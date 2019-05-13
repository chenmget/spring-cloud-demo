package com.iwhalecloud.retail.warehouse.dto.request;

import com.iwhalecloud.retail.dto.PageVO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

/**
 * @Author My
 * @Date 2019/04/18
 **/
@Data
public class ResourceReqDetailPageReq extends PageVO {

    @ApiModelProperty(value = "记录营销资源申请单标识")
    @NotBlank(message = "申请单ID不能为空")
    private String mktResReqId;

    @ApiModelProperty(value = "串码")
    private String mktResInstNbr;

    @ApiModelProperty(value = "是否抽检")
    private String isInspection;

}
