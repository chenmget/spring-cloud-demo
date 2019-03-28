package com.iwhalecloud.retail.goods2b.entity;

import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * ProdType
 * @author generator
 * @version 1.0
 * @since 1.0
 */
@Data
@TableName("prod_type")
@KeySequence(value="seq_prod_type_id",clazz = String.class)
@ApiModel(value = "对应模型prod_type, 对应实体ProdType类")
public class Type implements Serializable {
    /**表名常量*/
    public static final String TNAME = "prod_type";
    private static final long serialVersionUID = 1L;


    //属性 begin
    /**
     * 类型ID
     */
    @TableId
    @ApiModelProperty(value = "类型ID")
    private java.lang.String typeId;

    /**
     * 类型名称
     */
    @ApiModelProperty(value = "类型名称")
    private java.lang.String typeName;

    /**
     * 排序
     */
    @ApiModelProperty(value = "排序")
    private java.lang.Long typeOrder;

    /**
     * is_deleted
     */
    @ApiModelProperty(value = "is_deleted")
    private java.lang.String isDeleted;

    /**
     * 创建人
     */
    @ApiModelProperty(value = "创建人")
    private java.lang.String createStaff;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    private java.util.Date createDate;

    /**
     * 修改人
     */
    @ApiModelProperty(value = "修改人")
    private java.lang.String updateStaff;

    /**
     * 修改时间
     */
    @ApiModelProperty(value = "修改时间")
    private java.util.Date updateDate;

    /**
     * sourceFrom
     */
    @ApiModelProperty(value = "sourceFrom")
    private java.lang.String sourceFrom;


    //属性 end

    /** 字段名称枚举. */
    public enum FieldNames {
        /** 类型ID. */
        typeId("typeId","TYPE_ID"),

        /** 类型名称. */
        typeName("typeName","TYPE_NAME"),

        /** 排序. */
        typeOrder("typeOrder","TYPE_ORDER"),

        /** is_deleted. */
        isDeleted("isDeleted","IS_DELETED"),

        /** 创建人. */
        createStaff("createStaff","CREATE_STAFF"),

        /** 创建时间. */
        createDate("createDate","CREATE_DATE"),

        /** 修改人. */
        updateStaff("updateStaff","UPDATE_STAFF"),

        /** 修改时间. */
        updateDate("updateDate","UPDATE_DATE"),

        /** sourceFrom. */
        sourceFrom("sourceFrom","SOURCE_FROM");

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
