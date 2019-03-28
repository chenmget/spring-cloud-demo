package com.iwhalecloud.retail.partner.dto.req;

import com.iwhalecloud.retail.dto.PageVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel("厂商信息分页查询请求对象")
@Data
public class ManufacturerPageReq extends PageVO {

    @ApiModelProperty(value = "厂商名称")
    private String manufacturerName;

    @ApiModelProperty(value = "厂商编码")
    private String manufacturerCode;
}