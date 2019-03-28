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
public class MemberGroupQueryMemberReq extends PageVO {

    private static final long serialVersionUID = 9188246897554401789L;

    /**
     * 会员群ID
     */
    @ApiModelProperty(value = "会员群ID")
    private String groupId;

}
