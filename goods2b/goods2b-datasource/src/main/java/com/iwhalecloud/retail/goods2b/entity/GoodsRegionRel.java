package com.iwhalecloud.retail.goods2b.entity;

import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * GoodsRegionRel
 *
 * @author generator
 * @version 1.0
 * @since 1.0
 */
@Data
@TableName("prod_goods_region_rel")
@ApiModel(value = "对应模型prod_goods_region_rel, 对应实体GoodsRegionRel类")
@KeySequence(value = "seq_prod_goods_region_rel_id", clazz = String.class)
public class GoodsRegionRel implements Serializable {
    /**
     * 表名常量
     */
    public static final String TNAME = "prod_goods_region_rel";
    private static final long serialVersionUID = 1L;


    //属性 begin
    /**
     * relId
     */
    @TableId
    @ApiModelProperty(value = "relId")
    private String relId;

    /**
     * goodsId
     */
    @ApiModelProperty(value = "goodsId")
    private String goodsId;

    /**
     * regionId
     */
    @ApiModelProperty(value = "regionId")
    private String regionId;

    /**
     * regionName
     */
    @ApiModelProperty(value = "regionName")
    private String regionName;

    /**
     * lanId
     */
    @ApiModelProperty(value = "lanId")
    private String lanId;

    /**
     * 组织ID, sys_commom_org表主键
     */
    @ApiModelProperty(value = "组织ID, sys_commom_org表主键")
    private java.lang.String orgId;

    /**
     * 组织名称
     */
    @ApiModelProperty(value = "组织名称")
    private java.lang.String orgName;


    //属性 end

    /**
     * 字段名称枚举.
     */
    public enum FieldNames {
        /**
         * relId.
         */
        relId("relId", "REL_ID"),

        /**
         * goodsId.
         */
        goodsId("goodsId", "GOODS_ID"),

        /**
         * regionId.
         */
        regionId("regionId", "REGION_ID"),

        /**
         * regionName.
         */
        regionName("regionName", "REGION_NAME"),

        /**
         * 组织ID, sys_commom_org表主键.
         */
        orgId("orgId", "ORG_ID"),

        /**
         * 组织名称.
         */
        orgName("orgName", "ORG_NAME");

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
