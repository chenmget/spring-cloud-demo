package com.iwhalecloud.retail.partner.dto.req;

import com.iwhalecloud.retail.dto.PageVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("根据条件查找 厂家类型的商家信息 分页列表")
public class FactoryMerchantPageReq extends PageVO {

    //（par_merchant表字段）商家名称、商家渠道视图编码
    //（sys_user表字段）系统账号

    @ApiModelProperty(value = "商家编码")
    private String merchantCode;

    @ApiModelProperty(value = "商家名称")
    private String merchantName;


/**  下面的是 非 par_merchant 表字段  **/

    @ApiModelProperty(value = "商家对应系统账号")
    private String loginName;
}
