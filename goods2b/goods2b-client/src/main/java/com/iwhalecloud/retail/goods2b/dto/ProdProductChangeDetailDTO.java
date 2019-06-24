package com.iwhalecloud.retail.goods2b.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * Created by Administrator on 2019/5/16.
 */
@Data
@ApiModel(value = "对应模型PROD_PRODUCT_CHANGE_DETAIL, 对应实体prodProductChangeDetail类")
public class ProdProductChangeDetailDTO implements java.io.Serializable{
    private static final long serialVersionUID = 1L;

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

    @ApiModelProperty(value = "变更字段列表")
    private List<String> changeFields;
}
