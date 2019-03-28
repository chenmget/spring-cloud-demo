package com.iwhalecloud.retail.partner.dto.req;

import com.iwhalecloud.retail.dto.PageVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel("经营主体分页查询请求对象")
@Data
public class BusinessEntityPageReq extends PageVO {

    @ApiModelProperty(value = "经营主体名称, 模糊查询")
    private String businessEntityName;

    @ApiModelProperty(value = "经营主体编码，模糊查询")
    private String businessEntityCode;

    @ApiModelProperty(value = "经营主体状态  1:有效   0:无效")
    private String status;

    @ApiModelProperty(value = "本地网")
    private String lanId;

    @ApiModelProperty(value = "所属区域")
    private String regionId;

}
