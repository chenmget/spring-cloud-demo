package com.iwhalecloud.retail.goods2b.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName("PROD_ATTR_SPEC_GROUP")
@KeySequence(value="seq_prod_attr_spec_group_id",clazz = String.class)
@ApiModel(value = "对应模型PROD_ATTR_SPEC_GROUP, 对应实体AttrSpecGroup类")
public class AttrSpecGroup implements Serializable {
    /**表名常量*/
    public static final String TNAME = "PROD_ATTR_SPEC_GROUP";

    @TableId
    @ApiModelProperty(value = "typeId")
    private String typeId;

    @ApiModelProperty(value = "attrGroupName")
    private String attrGroupName;

    @ApiModelProperty(value = "attrGroupId")
    private String attrGroupId;

    @ApiModelProperty(value = "statusCd")
    private String statusCd;

    @ApiModelProperty(value = "remark")
    private String remark;

    public enum FieldNames{

        typeId("typeId","TYPE_ID"),
        attrGroupName("attrGroupName","ATTR_GROUP_NAME"),
        attrGroupId("attrGroupId","ATTR_GROUP_ID"),
        statusCd("statusCd","STATUS_CD"),
        remark("remark","REMARK");

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
