package com.iwhalecloud.retail.partner.entity;

import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 供应商表
 * @author generator
 * @version 1.0
 * @since 1.0
 */
@Data
@TableName("PAR_SUPPLIER")
@KeySequence(value="seq_par_supplier_id",clazz = String.class)
@ApiModel(value = "对应模型PAR_SUPPLIER, 对应实体Supplier类")
public class Supplier implements Serializable {
    /**表名常量*/
    public static final String TNAME = "PAR_SUPPLIER";
    private static final long serialVersionUID = 1L;


    //属性 begin
    /**
     * 供应商ID
     */
    @TableId
    @ApiModelProperty(value = "供应商ID")
    private java.lang.String supplierId;

    /**
     * 供应商编码
     */
    @ApiModelProperty(value = "供应商编码")
    private java.lang.String supplierCode;

    /**
     * 供应商名称
     */
    @ApiModelProperty(value = "供应商名称")
    private java.lang.String supplierName;

    /**
     * 联系人
     */
    @ApiModelProperty(value = "联系人")
    private java.lang.String linkMan;

    /**
     * 联系人电话
     */
    @ApiModelProperty(value = "联系人电话")
    private java.lang.String phoneNo;

    /**
     * 供应商类型:1省包、2地包
     */
    @ApiModelProperty(value = "供应商类型:1省包、2地包")
    private java.lang.String supplierType;

    /**
     * 状态: 1有效、0失效
     */
    @ApiModelProperty(value = "状态: 1有效、0失效")
    private java.lang.String supplierState;

    /**
     * 员工id
     */
    @ApiModelProperty(value = "员工id")
    private java.lang.String userId;

    /**
     * DELETE_FLAG
     */
    @ApiModelProperty(value = "DELETEFLAG")
    private java.lang.Long deleteFlag;


    //属性 end

    /** 字段名称枚举. */
    public enum FieldNames {
        /**
         * 供应商ID.
         */
        supplierId("supplierId", "SUPPLIER_ID"),

        /**
         * 供应商编码.
         */
        supplierCode("supplierCode", "SUPPLIER_CODE"),

        /**
         * 供应商名称.
         */
        supplierName("supplierName", "SUPPLIER_NAME"),

        /**
         * 联系人.
         */
        linkman("linkman", "LINKMAN"),

        /**
         * 联系人电话.
         */
        phoneNo("phoneNo", "PHONE_NO"),

        /**
         * 供应商类型:1省包、2地包.
         */
        supplierType("supplierType", "SUPPLIER_TYPE"),

        /**
         * 状态:1000 有效、1100失效.
         */
        supplierState("supplierState", "SUPPLIER_STATE"),

        /**
         * 员工id.
         */
        userId("userId", "USER_ID"),

        /**
         * DELETEFLAG.
         */
        deleteFlag("deleteFlag", "DELETE_FLAG");

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
