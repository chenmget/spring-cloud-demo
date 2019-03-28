package com.iwhalecloud.retail.system.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

import java.io.Serializable;

@Data
@ApiModel(value = "编辑系统用户 信息  对应模型sys_user, 对应实体User类")
public class UserEditReq implements Serializable {
    private static final long serialVersionUID = 3775158193731080449L;

    //属性 begin
    @NotEmpty(message = "用户id不能为空")
    @ApiModelProperty(value = "用户id")
    private String userId;

    @NotEmpty(message = "用户账号不能为空")
    @ApiModelProperty(value = "登陆用户名")
    private String loginName;

    /**
     * 登陆密码
     */
    @NotEmpty(message = "用户密码不能为空")
    @ApiModelProperty(value = "登陆密码")
    private String loginPwd;

    /**
     * 状态   1有效、 0 失效  2：其他状态
     */
    @ApiModelProperty(value = "状态   1有效、 0 失效  2：其他状态")
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
    @ApiModelProperty(value = "6为代销商 1超级管理员  0电信员工")
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
     * 当前登陆时间
     */
//    @ApiModelProperty(value = "当前登陆时间")
//    private java.util.Date curLoginTime;

    /**
     * 上次登陆时间
     */
//    @ApiModelProperty(value = "上次登陆时间")
//    private java.util.Date lastLoginTime;

    /**
     * 登陆失败的次数
     */
//    @ApiModelProperty(value = "登陆失败的次数")
//    private java.lang.Integer failLoginCnt;

    /**
     * 登陆成功的次数
     */
//    @ApiModelProperty(value = "登陆成功的次数")
//    private java.lang.Integer successLoginCnt;

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
//    @ApiModelProperty(value = "创建人")
//    private java.lang.String createStaff;

    /**
     * 创建时间
     */
//    @ApiModelProperty(value = "创建时间")
//    private java.util.Date createDate;

    /**
     * 修改人
     */
    @ApiModelProperty(value = "修改人")
    private String updateStaff;

    /**
     * 修改时间
     */
//    @ApiModelProperty(value = "修改时间")
//    private java.util.Date updateDate;

}
