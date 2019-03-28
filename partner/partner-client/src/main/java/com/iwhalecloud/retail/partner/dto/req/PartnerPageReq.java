package com.iwhalecloud.retail.partner.dto.req;

import com.iwhalecloud.retail.dto.PageVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 代理商分页查询请求参数.
 *
 * @author Ji.kai
 * @date 2018/11/17 15:27
 */
@ApiModel(value = "代理商分页查询请求参数")
@Data
public class PartnerPageReq extends PageVO {

    //属性 begin

    /**
     * 分销商名称
     */
    @ApiModelProperty(value = "分销商名称")
    private java.lang.String partnerName;

    /**
     * 状态 0申请、1正常、2冻结、3注销,-1审核不通过
     */
    @ApiModelProperty(value = "状态 0申请、1正常、2冻结、3注销,-1审核不通过")
    private List<String> states;

}
