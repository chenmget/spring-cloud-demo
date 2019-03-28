package com.iwhalecloud.retail.partner.dto.req;

import com.iwhalecloud.retail.dto.PageVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 店员列表查询
 * @author zwl
 * @date 2018.11.01
 */

@Data
@ApiModel(value = "内容列表分页查询请求参数对象")
public class PartnerStaffPageReq extends PageVO {

    @ApiModelProperty(value = "厅店ID")
    private String partnerShopId;

    @ApiModelProperty(value = "店名")
    private String shopName;

    @ApiModelProperty(value = "店员姓名")
    private String staffName;

    @ApiModelProperty(value = "店员工号")
    private String staffCode;

}
