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
public class MemberGroupQueryGroupReq extends PageVO {

    private static final long serialVersionUID = 4316731660809502278L;

    /**
     * 会员ID
     */
    @ApiModelProperty(value = "会员ID")
    private String memId;

}
