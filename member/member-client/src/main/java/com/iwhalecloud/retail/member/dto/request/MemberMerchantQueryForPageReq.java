package com.iwhalecloud.retail.member.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


/**
 * @Author: wang.jiaxin
 * @Date: 2019年03月05日
 * @Description:
 **/
@Data
@ApiModel(value = "对应模型mem_group, 对应实体Group类")
public class MemberMerchantQueryForPageReq extends PageVO {

    private static final long serialVersionUID = 6212513103147242135L;

    /**
     * 商家ID
     */
    @ApiModelProperty(value = "商家ID")
    private String merchId;

    /**
     * 会员ID
     */
    @ApiModelProperty(value = "会员ID")
    private String memId;

    /**
     * 会员等级ID
     */
    @ApiModelProperty(value = "会员等级ID")
    private Integer lvId;

    /**
     * 会员状态  1有效  0无效
     */
    @ApiModelProperty(value = "会员状态  1有效  0无效")
    private String status;

    /**
     * 更新开始时间
     */
    @ApiModelProperty(value = "更新开始时间")
    private java.util.Date updateStartDate;

    /**
     * 更新结束时间
     */
    @ApiModelProperty(value = "更新结束时间")
    private java.util.Date updateEndDate;

    /**
     * 更新人
     */
    @ApiModelProperty(value = "更新人")
    private String updateStaff;

}
