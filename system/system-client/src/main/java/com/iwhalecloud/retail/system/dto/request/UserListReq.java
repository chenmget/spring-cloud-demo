package com.iwhalecloud.retail.system.dto.request;

import com.iwhalecloud.retail.system.dto.UserDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
@ApiModel("根据条件查找用户列表")
public class UserListReq implements Serializable {
    private static final long serialVersionUID = -7933902841280774674L;

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
     * 状态
     */
//    @ApiModelProperty(value = "状态")
//    private List<Integer> statusList;

    @ApiModelProperty(value = "userFounder集合")
    private List<Integer> userFounderList;

    /**
     * 用户ID集合
     */
    @ApiModelProperty(value = "用户ID集合")
    private List<String> userIdList;


    /**
     * 状态
     */
    @ApiModelProperty(value = "状态")
    private Integer statusCd;

    /**
     * 店员ID
     */
    @ApiModelProperty(value = "关联店员的 staffId")
    private String relNo;

    /**
     * 关联ID
     */
    @ApiModelProperty(value = "关联商家ID集合")
    private String relCode;

    /**
     * 关联商家ID集合
     */
    @ApiModelProperty(value = "关联商家ID集合")
    private List<String> relCodeList;

    /**
     *角色ID
     */
    @ApiModelProperty(value = "角色ID")
    private String roleId;

    /**
     * 本地网地市
     */
    @ApiModelProperty(value = "本地网地市")
    private String lanId;

}
