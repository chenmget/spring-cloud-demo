package com.iwhalecloud.retail.member.dto.response;

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
@ApiModel(value = "对应模型mem_group_merchant, 对应实体GroupMerchant类")
public class GroupMerchantQueryResp implements Serializable {

    private static final long serialVersionUID = 7004044004109171576L;

    //属性 begin
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
     * 更新时间
     */
    @ApiModelProperty(value = "更新时间")
    private java.util.Date updateDate;

    /**
     * 更新人
     */
    @ApiModelProperty(value = "更新人")
    private java.lang.String updateStaff;
}
