package com.iwhalecloud.retail.oms.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Auther: lin.wh
 * @Date: 2018/10/29 17:13
 * @Description:
 */
@Data
@ApiModel(value = "对应模型t_catalog, 对应实体CatalogDTO类")
@TableName("t_catalog")
public class Catalog implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.ID_WORKER_STR)
    @ApiModelProperty(value = "目录id")
    private Integer cataid; //目录id

    @ApiModelProperty(value = "目录名称")
    private String name; //目录名称

    @ApiModelProperty(value = "上级目录")
    private Integer parentCataId; //上级目录

    @ApiModelProperty(value = "目录层级")
    private Integer catalogLevel; //目录层级

    @ApiModelProperty(value = "操作人")
    private String oprId; //操作人

    @ApiModelProperty(value = "更新时间")
    private String updDate; //更新时间

    @ApiModelProperty(value = "是否可删除 0不可删除 1可删除")
    private Integer canDel; //是否可删除	0不可删除 1可删除

    //属性 end

    public static enum FieldNames {
        cataid,
        name,
        parentCataId,
        catalogLevel,
        oprid,
        upddate,
        canDel
    }
}

