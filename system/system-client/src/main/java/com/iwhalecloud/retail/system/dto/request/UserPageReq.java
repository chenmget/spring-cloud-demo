package com.iwhalecloud.retail.system.dto.request;

import com.iwhalecloud.retail.dto.PageVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel("系统用户分页请求参数")
public class UserPageReq extends PageVO {
    /**
     * 账号
     */
    @ApiModelProperty(value = "账号")
    private String loginName;

    /**
     * 昵称
     */
    @ApiModelProperty(value = "昵称")
    private String userName;


    /**
     * 账户类型
     */
    @ApiModelProperty(value = "账户类型")
    private Integer userFounder;

    /**
     * 组织ID
     */
    @ApiModelProperty(value = "组织ID")
    private String orgId;

    /**
     * 组织名称
     */
    @ApiModelProperty(value = "组织名称")
    private String orgName;

    /**
     * relCode是否 为空
     */
    @ApiModelProperty(value = "rel_code字段是否 为空")
    private Boolean isRelCodeNull;

    /**
     * 状态
     */
//    @ApiModelProperty(value = "状态")
//    private String status;

    /**
     * 状态
     */
    @ApiModelProperty(value = "状态")
    private List<Integer> statusList;

    /**
     * 不包括的用户ID集合
     */
//    @ApiModelProperty(value = "不包括的用户ID集合")
//    private List<String> notInUserIdList;

}
