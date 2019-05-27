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
@ApiModel(value = "对应模型prod_product_change, 对应实体ProdProductChange类")
@KeySequence(value="seq_product_change_id",clazz = String.class)
public class ProdProductChange  implements Serializable {

    /**表名常量*/
    public static final String TNAME = "prod_product_change";
    private static final long serialVersionUID = 1L;

    /**
     * 关联ID
     */
    @TableId
    @ApiModelProperty(value = "变更流水")
    private String changeId;

    @ApiModelProperty(value = "版本号")
    private Long verNum;

    @ApiModelProperty(value = "产品基本信息ID")
    private String productBaseId;

    @ApiModelProperty(value = "审核状态")
    private String auditState;

    @ApiModelProperty(value = "创建时间")
    private java.util.Date createDate;

    @ApiModelProperty(value = "创建人")
    private String createStaff;

    public static enum FieldNames{
        /** 变更流水 */
        changeId,
        /** 版本号 */
        verNum,
        /** 产品基本信息ID */
        productBaseId,
        /** 审核状态 */
        auditState,
        /** 创建时间 */
        createDate,
        /** 创建人 */
        createStaff
    }
}
