package com.iwhalecloud.retail.goods2b.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * CatCondition
 *
 * @author generator
 * @version 1.0
 * @since 1.0
 */
@Data
@TableName("prod_cat_condition")
@ApiModel(value = "对应模型prod_cat_condition, 对应实体CatCondition类")
@KeySequence(value = "seq_par_merchant_rules_id", clazz = String.class)
public class CatCondition implements Serializable {
    /**
     * 表名常量
     */
    public static final String TNAME = "prod_cat_condition";
    private static final long serialVersionUID = 1L;


    //属性 begin
    /**
     * 主键ID
     */
    @TableId
    @ApiModelProperty(value = "主键ID")
    private String id;

    /**
     * 产品类别ID(prod_cat表主键)
     */
    @ApiModelProperty(value = "产品类别ID(prod_cat表主键)")
    private String catId;

    /**
     * 商品类型关联的筛选条件类型 1. 产品类型 2. 产品属性 3. 品牌 4. 营销活动类型 5. 产品标签
     */
    @ApiModelProperty(value = "商品类型关联的筛选条件类型 1. 产品类型 2. 产品属性 3. 品牌 4. 营销活动类型 5. 产品标签")
    private String relType;

    /**
     * 商品类型关联类型为以下类型时，记录关联的对象ID 1. 产品类型 时 记录产品类型ID 2. 产品属性 时 记录产品属性ID 3. 品牌 时 记录品牌ID 4. 营销活动类型 时 记录营销活动类型ID 5. 产品标签 时 记录产品标签ID
     */
    @ApiModelProperty(value = "  商品类型关联类型为以下类型时，记录关联的对象ID 1. 产品类型 时 记录产品类型ID 2. 产品属性 时 记录产品属性ID 3. 品牌 时 记录品牌ID 4. 营销活动类型 时 记录营销活动类型ID 5. 产品标签 时 记录产品标签ID")
    private String relObjId;

    /**
     * 商品类型关联类型 产品属性时记录属性的值
     */
    @ApiModelProperty(value = "商品类型关联类型 产品属性时记录属性的值")
    private String relObjValue;

    /**
     * 排序
     */
    @ApiModelProperty(value = "排序")
    private Long order;

    /**
     * 创建人
     */
    @ApiModelProperty(value = "创建人")
    private String createStaff;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    private java.util.Date createDate;

    /**
     * 修改人
     */
    @ApiModelProperty(value = "修改人")
    private String updateStaff;

    /**
     * 修改时间
     */
    @ApiModelProperty(value = "修改时间")
    private java.util.Date updateDate;


    //属性 end

    /**
     * 字段名称枚举.
     */
    public enum FieldNames {
        /**
         * 主键ID.
         */
        id("id", "ID"),

        /**
         * 类型ID.
         */
        catId("catId", "CAT_ID"),

        /**
         * 商品类型关联的筛选条件类型 1. 产品类型 2. 产品属性 3. 品牌 4. 营销活动类型 5. 产品标签.
         */
        relType("relType", "REL_TYPE"),

        /**
         * 商品类型关联类型为以下类型时，记录关联的对象ID 1. 产品类型 时 记录产品类型ID 2. 产品属性 时 记录产品属性ID 3. 品牌 时 记录品牌ID 4. 营销活动类型 时 记录营销活动类型ID 5. 产品标签 时 记录产品标签ID.
         */
        relObjId("relObjId", "REL_OBJ_ID"),

        /**
         * 商品类型关联类型 产品属性时记录属性的值.
         */
        relObjValue("relObjValue", "REL_OBJ_VALUE"),

        /**
         * 排序.
         */
        order("order", "ORDER"),

        /**
         * 创建人.
         */
        createStaff("createStaff", "CREATE_STAFF"),

        /**
         * 创建时间.
         */
        createDate("createDate", "CREATE_DATE"),

        /**
         * 修改人.
         */
        updateStaff("updateStaff", "UPDATE_STAFF"),

        /**
         * 修改时间.
         */
        updateDate("updateDate", "UPDATE_DATE");

        private String fieldName;
        private String tableFieldName;

        FieldNames(String fieldName, String tableFieldName) {
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
