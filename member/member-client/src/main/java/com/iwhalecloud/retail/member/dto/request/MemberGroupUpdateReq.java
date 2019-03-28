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
@ApiModel(value = "对应模型mem_member_group, 对应实体MemberGroup类")
public class MemberGroupUpdateReq extends AbstractRequest implements Serializable {

    private static final long serialVersionUID = -5134810315618834393L;

    /**
     * 会员群ID
     */
    @ApiModelProperty(value = "会员群ID")
    private String groupId;

    /**
     * 会员ID
     */
    @ApiModelProperty(value = "会员ID")
    private String memId;

    /**
     * 群成员状态 0有效，1失效
     */
    @ApiModelProperty(value = "群成员状态 1有效  0无效")
    private String status;

    /**
     * 更新人
     */
    @ApiModelProperty(value = "更新人")
    private String updateStaff;

}
