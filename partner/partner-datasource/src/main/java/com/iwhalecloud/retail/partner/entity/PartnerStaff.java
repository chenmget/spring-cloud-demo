package com.iwhalecloud.retail.partner.entity;

import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * PartnerStaff
 * @author generator
 * @version 1.0
 * @since 1.0
 */
@Data
@TableName("par_partner_staff")
@ApiModel(value = "对应模型par_partner_staff, 对应实体PartnerStaff类")
@KeySequence(value="seq_par_partner_staff_id",clazz = String.class)

public class PartnerStaff implements Serializable {
    /**表名常量*/
    public static final String TNAME = "par_partner_staff";
    private static final long serialVersionUID = 1L;

    //属性 begin
    /**
     * staffId
     */
    @TableId
    @ApiModelProperty(value = "staffId")
    private java.lang.String staffId;

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


    //属性 end

    /** 字段名称枚举. */
    public enum FieldNames {
        /** phoneNo. */
        phoneNo("phoneNo","PHONE_NO"),

        /** staffName. */
        staffName("staffName","STAFF_NAME"),

        /** 性别 :  男,  女. */
        sex("sex","SEX"),

        /** 工号编码--取sys_user的login_name. */
        staffCode("staffCode","STAFF_CODE"),

        /** staffId. */
        staffId("staffId","STAFF_ID"),

        /** 职位，3--店长   6--店员. */
        position("position","POSITION"),

        /** 出生日期. */
        birthday("birthday","BIRTHDAY"),

        /** 婚姻状态: 已婚 、 未婚. */
        maritalStatus("maritalStatus","MARITAL_STATUS"),

        /** 民族. */
        nation("nation","NATION"),

        /** 政治面貌. */
        politiesStatus("politiesStatus","POLITIES_STATUS"),

        /** 籍贯. */
        nativePlace("nativePlace","NATIVE_PLACE"),

        /** 证件类型. */
        certificateType("certificateType","CERTIFICATE_TYPE"),

        /** 证件号码. */
        certificateNo("certificateNo","CERTIFICATE_NO"),

        /** 邮箱地址. */
        email("email","EMAIL"),

        /** 家庭地址. */
        homeAddress("homeAddress","HOME_ADDRESS"),

        /** 紧急联系人. */
        linkMan("linkMan","LINK_MAN"),

        /** 紧急联系人的电话. */
        linkPhone("linkPhone","LINK_PHONE"),

        /** 和紧急联系人的关系. */
        linkRelationship("linkRelationship","LINK_RELATIONSHIP"),

        /** 创建时间. */
        createDate("createDate","CREATE_DATE"),

        /** 员工图片地址. */
        staffDetailImage("staffDetailImage","STAFF_DETAIL_IMAGE"),

        /** 厅店ID. */
        partnerShopId("partnerShopId","PARTNER_SHOP_ID");

        private String fieldName;
        private String tableFieldName;
        FieldNames(String fieldName, String tableFieldName){
            this.fieldName = fieldName;
            this.tableFieldName = tableFieldName;
        }

        public String getFieldName() {
            return fieldName;
        }

        public String getTableFieldName() {
            return tableFieldName;
        }
    }

}
