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
public class GroupMerchantUpdateReq extends AbstractRequest implements Serializable {

    private static final long serialVersionUID = -197499970505303086L;

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
     * 更新人
     */
    @ApiModelProperty(value = "更新人")
    private java.lang.String updateStaff;
}
