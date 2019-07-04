package com.iwhalecloud.retail.system.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

import java.io.Serializable;

/**
 * AdminUser
 * @author zwl
 * @version 1.0
 * @since 1.0
 */
@Data
@ApiModel(value = "新增系统用户 信息  对应模型sys_user, 对应实体User类")
public class UserAddReq implements Serializable {
    //属性 begin
    /**
     * 登陆用户名
     */

    @NotEmpty(message = "用户账号不能为空")
    @ApiModelProperty(value = "登陆用户名")
    private java.lang.String loginName;

    /**
     * 登陆密码
     */
    @NotEmpty(message = "用户密码不能为空")
    @ApiModelProperty(value = "登陆密码")
    private java.lang.String loginPwd;

    /**
     * 状态   1有效、 0 失效  2：其他状态
     */
    @ApiModelProperty(value = "状态   1有效、 0 失效  2：其他状态")
    private java.lang.Integer statusCd;

    /**
     * 用户昵称
     */
    @ApiModelProperty(value = "用户昵称")
    private java.lang.String userName;

    /**
     * 备注
     */
    @ApiModelProperty(value = "备注")
    private java.lang.String remark;

    /**
     * 1超级管理员 2普通管理员  3零售商(门店、店中商)  4省包供应商  5地包供应商
     * 6 代理商店员  7经营主体  8厂商
     */
    @ApiModelProperty(value = "1超级管理员 2普通管理员  3零售商(门店、店中商)  4省包供应商  5地包供应商  " +
            " 6 代理商店员  7经营主体  8厂商 ")
    private java.lang.Integer userFounder;

    /**
     * 关联类型
     */
    @ApiModelProperty(value = "关联类型")
    private java.lang.String relType;

    /**
     * 关联员工ID
     */
    @ApiModelProperty(value = "关联员工ID")
    private java.lang.String relNo;

    /**
     * 用户归属本地网
     */
    @ApiModelProperty(value = "用户归属本地网")
    private java.lang.String lanId;

    /**
     * 关联代理商ID  或 供应商ID
     */
    @ApiModelProperty(value = "关联代理商ID  或 供应商ID")
    private java.lang.String relCode;

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
    private java.lang.String phoneNo;

    /**
     * 关联组织 id
     */
    @ApiModelProperty(value = "关联组织 id")
    private java.lang.String orgId;

    /**
     * 邮箱账号
     */
    @ApiModelProperty(value = "邮箱账号")
    private java.lang.String email;

    /**
     * 区域ID
     */
    @ApiModelProperty(value = "区域ID")
    private java.lang.String regionId;

    /**
     * 创建人
     */
    @ApiModelProperty(value = "创建人")
    private java.lang.String createStaff;

    /**
     * 创建时间
     */
//    @ApiModelProperty(value = "创建时间")
//    private java.util.Date createDate;

    /**
     * 修改人
     */
//    @ApiModelProperty(value = "修改人")
//    private java.lang.String updateStaff;

    /**
     * 修改时间
     */
//    @ApiModelProperty(value = "修改时间")
//    private java.util.Date updateDate;

    @ApiModelProperty(value = "岗位ID")
    private Long sysPostId;

    @ApiModelProperty(value = "岗位名称")
    private String sysPostName;

    @ApiModelProperty(value = "用户来源")
    private java.lang.Integer userSource;

    @ApiModelProperty(value = "用户Id")
    private String userId;
}

