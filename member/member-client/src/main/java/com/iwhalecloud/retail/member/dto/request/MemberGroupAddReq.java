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
public class MemberGroupAddReq extends PageVO {

    private static final long serialVersionUID = -7380097525604590470L;

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
     * 创建人
     */
    @ApiModelProperty(value = "创建人")
    private String createStaff;

}
