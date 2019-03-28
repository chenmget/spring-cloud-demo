package com.iwhalecloud.retail.web.controller.system.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

import java.io.Serializable;
import java.util.List;

@Data
public class AddUserReq implements Serializable {
    private static final long serialVersionUID = 8663110644827091891L;

    //属性 begin

    @ApiModelProperty(value = "角色列表ID集合")
    private List<String> roleIdList;

    /**
     * 登陆用户名
     */
    @NotEmpty(message = "用户账号不能为空")
    @ApiModelProperty(value = "登陆用户名")
    private String loginName;

    /**
     * 登陆密码
     */
    @NotEmpty(message = "用户密码不能为空")
    @ApiModelProperty(value = "登陆密码")
    private String loginPwd;

    @ApiModelProperty(value = "状态   1有效、 0 禁用（失效）  2：删除")
    private Integer statusCd;

    /**
     * 用户昵称
     */
    @ApiModelProperty(value = "用户昵称")
    private String userName;

    /**
     * 备注
     */
    @ApiModelProperty(value = "备注")
    private String remark;

    /**
     * 6为代销商  1超级管理员  0电信员工
     */
    @ApiModelProperty(value = " 1超级管理员 3：代理商 4：分销商 6店员 ")
    private Integer userFounder;

    /**
     * 关联类型
     */
    @ApiModelProperty(value = "关联类型")
    private String relType;

    /**
     * 关联员工ID
     */
    @ApiModelProperty(value = "关联员工ID")
    private String relNo;

    /**
     * 用户归属本地网
     */
    @ApiModelProperty(value = "用户归属本地网")
    private String lanId;

    /**
     * 关联代理商ID  或 供应商ID
     */
    @ApiModelProperty(value = "关联代理商ID  或 供应商ID")
    private String relCode;

    /**
     * 用户电话号码
     */
    @ApiModelProperty(value = "用户电话号码")
    private String phoneNo;

    /**
     * 关联组织 id
     */
    @ApiModelProperty(value = "关联组织 id")
    private String orgId;

    /**
     * 邮箱账号
     */
    @ApiModelProperty(value = "邮箱账号")
    private String email;

    /**
     * 区域ID
     */
    @ApiModelProperty(value = "区域ID")
    private String regionId;

    /**
     * 创建人
     */
    @ApiModelProperty(value = "创建人")
    private String createStaff;

}
