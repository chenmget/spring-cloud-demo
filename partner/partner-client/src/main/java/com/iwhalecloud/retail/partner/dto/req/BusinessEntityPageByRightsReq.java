package com.iwhalecloud.retail.partner.dto.req;

import com.iwhalecloud.retail.dto.PageVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * BusinessEntityPageByRightsReq
 * @author hsw
 * @version 1.0
 * @since 1.0
 * @date  2019/03/13
 */
@ApiModel("经营主体分页查询请求对象")
@Data
public class BusinessEntityPageByRightsReq extends PageVO {

    @ApiModelProperty(value = "经营主体名称, 模糊查询")
    private String businessEntityName;

    @ApiModelProperty(value = "经营主体编码，模糊查询")
    private String businessEntityCode;

    @ApiModelProperty(value = "经营主体状态  1:有效   0:无效")
    private String status;

    @ApiModelProperty(value = "商家主键集合")
    private List<String> merchantIdList;

    @ApiModelProperty(value = "商家主键,管理员调用的时候传，如果没传查看所有")
    private String merchantId;

    @ApiModelProperty(value = "商家名称")
    private String merchantName;

    @ApiModelProperty(value = "销售点编码")
    private String shopCode;

    @ApiModelProperty(value = "渠道大类")
    private String channelType;

    @ApiModelProperty(value = "标签")
    private String tagId;
}
