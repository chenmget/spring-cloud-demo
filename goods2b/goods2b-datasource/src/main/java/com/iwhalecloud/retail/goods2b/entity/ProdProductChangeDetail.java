package com.iwhalecloud.retail.goods2b.entity;

import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * Created by Administrator on 2019/5/14.
 */
@Data
@ApiModel(value = "对应模型prod_product_change_detail, 对应实体ProdProductChangeDetail类")
@KeySequence(value="seq_product_change_detail_id",clazz = String.class)
public class ProdProductChangeDetail implements Serializable {
    /**表名常量*/
    public static final String TNAME = "prod_product_change_detail";
    private static final long serialVersionUID = 1L;

    /**
     * 关联ID
     */
    @TableId
    @ApiModelProperty(value = "变更明细流水")
    private String changeDetailId;

    @ApiModelProperty(value = "变更流水")
    private String changeId;

    @ApiModelProperty(value = "操作类型")
    private String operType;

    @ApiModelProperty(value = "版本号")
    private Long verNum;

    @ApiModelProperty(value = "表名")
    private String tableName;

    @ApiModelProperty(value = "变更字段")
    private String changeField;

    @ApiModelProperty(value = "变更字段名")
    private String changeFieldName;

    @ApiModelProperty(value = "原始值")
    private String oldValue;

    @ApiModelProperty(value = "变更值")
    private String newValue;

    @ApiModelProperty(value = "业务ID")
    private String keyValue;

    @ApiModelProperty(value = "创建时间")
    private java.util.Date createDate;

    @ApiModelProperty(value = "创建人")
    private String createStaff;

    public static enum FieldNames{
        /** 变更明细流水 */
        changeDetailId,
        /** 变更流水 */
        changeId,
        /** 操作类型 */
        operType,
        /** 版本号 */
        verNum,
        /** 表名 */
        tableName,
        /** 变更字段 */
        changeField,
        /** 变更字段名 */
        changeFieldName,
        /** 原始值 */
        oldValue,
        /** 变更值 */
        newValue,
        /** 业务ID */
        keyValue,
        /** 创建时间 */
        createDate,
        /** 创建人 */
        createStaff
    }
}
