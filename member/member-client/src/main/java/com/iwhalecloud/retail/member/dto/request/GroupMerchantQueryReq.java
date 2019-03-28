package com.iwhalecloud.retail.member.dto.request;

import com.iwhalecloud.retail.dto.AbstractRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author: wang.jiaxin
 * @Date: 2019年03月05日
 * @Description:
 **/
@Data
@ApiModel(value = "对应模型mem_group, 对应实体Group类")
public class GroupMerchantQueryReq extends PageVO {

    private static final long serialVersionUID = -5663663951988637665L;

    /**
     * 商家ID
     */
    @ApiModelProperty(value = "商家ID")
    private java.lang.String merchId;

    /**
     * 会员群ID
     */
    @ApiModelProperty(value = "会员群ID")
    private java.lang.String groupId;

    /**
     * 是否有效 1有效  0无效
     */
    @ApiModelProperty(value = "是否有效 1有效  0无效")
    private java.lang.String status;

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
    private java.lang.String updateStaff;
}
