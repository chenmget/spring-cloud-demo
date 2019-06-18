package com.iwhalecloud.retail.order2b.entity;

import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @auther lin.wenhui@iwhalecloud.com
 * @date 2019/4/25 11:30
 * @description 采购申请单项实体类
 */

@Data
@ApiModel(value = "对应模型PUR_APPLY_ITEM, 对应实体PurApplyItem类")
@TableName("PUR_APPLY_ITEM")
@KeySequence(value = "seq_pur_apply_item_id", clazz = String.class)
public class PurApplyItem implements Serializable {

    private static final long serialVersionUID = 3194378593483000251L;

    /**
     * 申请单项ID
     */
    @ApiModelProperty(value = "applyItemId")
    private String applyItemId;

    /**
     * 申请单ID
     */
    @ApiModelProperty(value = "applyId")
    private String applyId;

    /**
     * 产品ID
     */
    @ApiModelProperty(value = "productId")
    private String productId;

    /**
     * 采购数量
     */
    @ApiModelProperty(value = "purNum")
    private String purNum;

    /**
     * 采购价格
     */
    @ApiModelProperty(value = "purPrice")
    private String purPrice;

    /**
     * 采购类型
     */
    @ApiModelProperty(value = "purType")
    private String purType;

    /**
     * 初始采购类型
     */
    @ApiModelProperty(value = "oringinPurType")
    private String oringinPurType;

    /**
     * 状态
     */
    @ApiModelProperty(value = "statusCd")
    private String statusCd;

    /**
     * 创建人
     */
    @ApiModelProperty(value = "createStaff")
    private String createStaff;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "createDate")
    private String createDate;

    /**
     * 修改人
     */
    @ApiModelProperty(value = "updateStaff")
    private String updateStaff;

    /**
     * 修改时间
     */
    @ApiModelProperty(value = "updateDate")
    private String updateDate;

    /**
     * 状态时间
     */
    @ApiModelProperty(value = "statusDate")
    private String statusDate;

}

