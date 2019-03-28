package com.iwhalecloud.retail.oms.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @Auther: lin.wh
 * @Date: 2018/10/30 17:31
 * @Description:
 */

@Data
@ApiModel(value = "对应模型shelf_templates, 对应实体ShelfTemplatesDTO类")
@TableName("shelf_templates")
public class ShelfTemplates implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id")
    @TableId(type = IdType.ID_WORKER)
    private Long id; //id

    @ApiModelProperty(value = "创建时间")
    private Date gmtCreate; //创建时间

    @ApiModelProperty(value = "修改时间")
    private Date gmtModified; //修改时间

    @ApiModelProperty(value = "创建人")
    private String creator; //创建人

    @ApiModelProperty(value = "修改人")
    private String modifier; //修改人

    @ApiModelProperty(value = "是否删除")
    private Integer isDeleted; //是否删除：0未删、1删除

    @ApiModelProperty(value = "货架默认模板编号")
    private String tempNumber; //货架模板编号

    @ApiModelProperty(value = "货架默认模板名称")
    private String shelfTemplatesName; //货架默认模板名称

    @ApiModelProperty(value = "货架默认模板描述")
    private String shelfTemplatesDesc; //货架默认模板描述

    @ApiModelProperty(value = "货架背景图片地址")
    private String backgroudImgUrl; //货架背景图片地址

    @ApiModelProperty(value = "货架默认模板风格：1000（默认风格）、2000（苹果风格）")
    private Integer shelfTemplatesStyle;// 货架默认模板风格：1000（默认风格）、2000（苹果风格）

    @ApiModelProperty(value = "货架封面图url")
    private String coverUrl;// 货架封面图url

    /** 字段名称枚举. */
    public enum FieldNames {
        /** ID. */
        id("id","ID"),

        /** 创建时间. */
        gmtCreate("gmtCreate","GMT_CREATE"),

        /** 修改时间. */
        gmtModified("gmtModified","GMT_MODIFIED"),

        /** 创建人. */
        creator("creator","CREATOR"),

        /** 修改人. */
        modifier("modifier","MODIFIER"),

        /** 是否删除：0未删、1删除. */
        isDeleted("isDeleted","IS_DELETED"),

        /** 货架默认模板编号. */
        tempNumber("tempNumber","TEMP_NUMBER"),

        /** 货架默认模板名称. */
        shelfTemplatesName("shelfTemplatesName","SHELF_TEMPLATES_NAME"),

        /** 货架默认模板描述. */
        shelfTemplatesDesc("shelfTemplatesDesc","SHELF_TEMPLATES_DESC"),

        /** 货架背景图片地址. */
        backgroudImgUrl("backgroudImgUrl","BACKGROUND_IMG_URL"),

        /** 货架默认模板风格：1000（默认风格）、2000（苹果风格）*/
        shelfTemplatesStyle("shelfTemplatesStyle","SHELF_TEMPLATES_STYLE"),

        /** 货架封面图url */
        coverUrl("coverUrl","COVER_URL");

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

