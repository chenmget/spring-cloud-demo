package com.iwhalecloud.retail.member.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author My
 * @Date 2018/11/28
 **/
@Data
@TableName("mem_member_address")
@KeySequence(value = "seq_mem_member_address_id", clazz = String.class)
@ApiModel(value = "对应模型mem_member_address, 对应实体MemberAddress类")
public class MemberAddress implements Serializable {
    /**表名常量*/
    public static final String TNAME = "mem_member_address";
    private static final long serialVersionUID = 1L;

    //属性 begin
    /**
     * addrId
     */
    @TableId
    @ApiModelProperty(value = "addrId")
    private java.lang.String addrId;

    /**
     * 会员ID
     */
    @ApiModelProperty(value = "会员ID")
    private java.lang.String memberId;

    /**
     * country
     */
    @ApiModelProperty(value = "country")
    private java.lang.String country;

    /**
     * 省份ID
     */
    @ApiModelProperty(value = "省份ID")
    private java.lang.String provinceId;

    /**
     * 市县ID
     */
    @ApiModelProperty(value = "市县ID")
    private java.lang.String cityId;

    /**
     * 区域ID
     */
    @ApiModelProperty(value = "区域ID")
    private java.lang.String regionId;

    /**
     * 区域
     */
    @ApiModelProperty(value = "区域")
    private java.lang.String region;

    /**
     * 市县
     */
    @ApiModelProperty(value = "市县")
    private java.lang.String city;

    /**
     * 省份
     */
    @ApiModelProperty(value = "省份")
    private java.lang.String province;

    /**
     * 具体地址
     */
    @ApiModelProperty(value = "具体地址")
    private java.lang.String addr;


    @ApiModelProperty(value = "邮编")
    private String zip;

    @ApiModelProperty(value = "邮箱")
    private String email;


    @ApiModelProperty(value = "是否有效地址 1是  0否")
    private java.lang.String isEffect;

    /**
     * 是否默认地址 1是  0否
     */
    @ApiModelProperty(value = "是否默认地址 1是  0否")
    private java.lang.String isDefault;

    /**
     * 收货人名称
     */
    @ApiModelProperty(value = "收货人名称")
    private java.lang.String consigeeName;

    /**
     * 收货人联系方式
     */
    @ApiModelProperty(value = "收货人联系方式")
    private java.lang.String consigeeMobile;

    /**
     * 更新时间
     */
    @ApiModelProperty(value = "更新时间")
    private java.util.Date lastUpdate;


    //属性 end

    /** 字段名称枚举. */
    public enum FieldNames {
        /** addrId. */
        addrId("addrId","ADDR_ID"),

        /** 会员ID. */
        memberId("memberId","MEMBER_ID"),

        /** country. */
        country("country","COUNTRY"),

        /** 省份ID. */
        provinceId("provinceId","PROVINCE_ID"),

        /** 市县ID. */
        cityId("cityId","CITY_ID"),

        /** 区域ID. */
        regionId("regionId","REGION_ID"),

        /** 区域. */
        region("region","REGION"),

        /** 市县. */
        city("city","CITY"),

        /** 省份. */
        province("province","PROVINCE"),

        /** 具体地址. */
        addr("addr","ADDR"),

        /** 邮编. */
        zip("zip","ZIP"),

        /** 邮箱. */
        email("email","EMAIL"),

        /** 是否默认地址 1是  0否. */
        isEffect("isEffect","IS_EFFECT"),

        /** 是否默认地址 1是  0否. */
        isDefault("isDefault","IS_DEFAULT"),

        /** 收货人名称. */
        consigeeName("consigeeName","CONSIGEE_NAME"),

        /** 收货人联系方式. */
        consigeeMobile("consigeeMobile","CONSIGEE_MOBILE"),

        /** 更新时间. */
        lastUpdate("lastUpdate","LAST_UPDATE");

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
