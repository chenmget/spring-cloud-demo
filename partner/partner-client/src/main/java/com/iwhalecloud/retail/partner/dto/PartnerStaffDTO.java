package com.iwhalecloud.retail.partner.dto;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(value = "对应模型par_partner_staff,对应实体PartnerStaff类")
public class PartnerStaffDTO implements Serializable {
    private static final long serialVersionUID = -1855298856877785836L;
    /**
     * 员工ID，主键
     */
    @ApiModelProperty(value = "staff_id")
    private java.lang.String staffId;

    // 该字段不在 ES_PARTNER_STAFF表
    @ApiModelProperty(value = "店员归属的店名, 该字段在 PAR_PARTNER_SHOP表")
    private java.lang.String shopName;

    //属性 begin
    /**
     * phoneNo
     */
    @ApiModelProperty(value = "phoneNo")
    private java.lang.String phoneNo;

    /**
     * staffName
     */
    @ApiModelProperty(value = "staffName")
    private java.lang.String staffName;

    /**
     * 性别 :  男,  女
     */
    @ApiModelProperty(value = "性别 :  男,  女")
    private java.lang.String sex;

    /**
     * 工号编码--取sys_user的login_name
     */
    @ApiModelProperty(value = "工号编码--取sys_user的login_name")
    private java.lang.String staffCode;

    /**
     * 职位，3--店长   6--店员
     */
    @ApiModelProperty(value = "职位，3--店长   6--店员")
    private java.lang.String position;

    /**
     * 出生日期
     */
    @ApiModelProperty(value = "出生日期")
    private java.util.Date birthday;

    /**
     * 婚姻状态: 已婚 、 未婚
     */
    @ApiModelProperty(value = "婚姻状态: 已婚 、 未婚")
    private java.lang.String maritalStatus;

    /**
     * 民族
     */
    @ApiModelProperty(value = "民族")
    private java.lang.String nation;

    /**
     * 政治面貌
     */
    @ApiModelProperty(value = "政治面貌")
    private java.lang.String politiesStatus;

    /**
     * 籍贯
     */
    @ApiModelProperty(value = "籍贯")
    private java.lang.String nativePlace;

    /**
     * 证件类型
     */
    @ApiModelProperty(value = "证件类型")
    private java.lang.String certificateType;

    /**
     * 证件号码
     */
    @ApiModelProperty(value = "证件号码")
    private java.lang.String certificateNo;

    /**
     * 邮箱地址
     */
    @ApiModelProperty(value = "邮箱地址")
    private java.lang.String email;

    /**
     * 家庭地址
     */
    @ApiModelProperty(value = "家庭地址")
    private java.lang.String homeAddress;

    /**
     * 紧急联系人
     */
    @ApiModelProperty(value = "紧急联系人")
    private java.lang.String linkMan;

    /**
     * 紧急联系人的电话
     */
    @ApiModelProperty(value = "紧急联系人的电话")
    private java.lang.String linkPhone;

    /**
     * 和紧急联系人的关系
     */
    @ApiModelProperty(value = "和紧急联系人的关系")
    private java.lang.String linkRelationship;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    private java.util.Date createDate;

    /**
     * 员工图片地址
     */
    @ApiModelProperty(value = "员工图片地址")
    private java.lang.String staffDetailImage;

    /**
     * 厅店ID
     */
    @ApiModelProperty(value = "厅店ID")
    private java.lang.String partnerShopId;

}
