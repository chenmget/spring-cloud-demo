package com.iwhalecloud.retail.order2b.entity;

import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @auther lin.wenhui@iwhalecloud.com
 * @date 2019/4/24 20:03
 * @description 采购申请单项明细实体类
 */

@Data
@ApiModel(value = "对应模型PUR_APPLY_ITEM_DETAIL, 对应实体PurApplyItemDetail类")
@TableName("PUR_APPLY_ITEM_DETAIL")
@KeySequence(value = "SEQ_PUR_APPLY_ITEM_DETAIL_ID", clazz = String.class)
public class PurApplyItemDetail implements Serializable {

    private static final long serialVersionUID = 6937002796719524334L;

    public static final String TNAME = "PUR_APPLY_ITEM_DETAIL";

    /**
     * 明细ID
     */
    @ApiModelProperty(value = "itemDetailId")
    @TableId
    private String itemDetailId;

    /**
     * 申请单项ID
     */
    @ApiModelProperty("applyItemId")
    private String applyItemId;

    /**
     * 申请单ID
     */
    @ApiModelProperty("applyId")
    private String applyId;

    /**
     * 产品ID
     */
    @ApiModelProperty("productId")
    private String productId;

    /**
     * 串码
     */
    @ApiModelProperty("mktResInstNbr")
    private String mktResInstNbr;

    /**
     * 发货批次
     */
    @ApiModelProperty("batchId")
    private String batchId;

    /**
     * 状态
     */
    @ApiModelProperty("statusCd")
    private Double statusCd;

    /**
     * 创建人
     */
    @ApiModelProperty("createStaff")
    private String createStaff;

    /**
     * 创建时间
     */
    @ApiModelProperty("createDate")
    private Date createDate;

    /**
     * 修改人
     */
    @ApiModelProperty("updateStaff")
    private String updateStaff;

    /**
     * 修改时间
     */
    @ApiModelProperty("updateDate")
    private Date updateDate;

    /**
     * 状态时间
     */
    @ApiModelProperty("statusDate")
    private Date statusDate;

}

