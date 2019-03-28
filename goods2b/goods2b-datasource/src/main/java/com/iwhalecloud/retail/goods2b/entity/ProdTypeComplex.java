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
@TableName("prod_type_complex")
@KeySequence(value="seq_prod_type_complex_id",clazz = String.class)
@ApiModel(value = "对应模型prod_type_complex, 对应实体ProdTypeComplex类")
public class ProdTypeComplex implements Serializable {
    /**表名常量*/
    public static final String TNAME = "prod_type_complex";
    private static final long serialVersionUID = 1L;


    //属性 begin
    /**
     * ID
     */
    @TableId
    @ApiModelProperty(value = "ID")
    private java.lang.String Id;

    /**
     * 类型ID
     */

    @ApiModelProperty(value = "类型ID")
    private java.lang.String typeId;

    /**
     *品牌ID
     */
    @ApiModelProperty(value = "品牌ID")
    private java.lang.String brandId;

    /**
     * 排序
     */
    @ApiModelProperty(value = "排序")
    private java.lang.Long complexOrder;


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

    //属性 end

    /** 字段名称枚举. */
    public enum FieldNames {

        /** ID. */
        Id("Id","ID"),

        /** 类型ID. */
        typeId("typeId","TYPE_ID"),

        /** 品牌ID. */
        brandId("brandId","BRAND_ID"),

        /** 排序. */
        complexOrder("complexOrder","COMPLEX_ORDER"),

        /** 创建人. */
        createStaff("createStaff","CREATE_STAFF"),

        /** 创建时间. */
        createDate("createDate","CREATE_DATE");


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
