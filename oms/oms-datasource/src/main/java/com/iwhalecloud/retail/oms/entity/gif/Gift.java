package com.iwhalecloud.retail.oms.entity.gif;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

/**
 * @author hesw
 * @date 2018/10/26
 * @Description: 奖品表
 */

@Data
@TableName("gift")
@ApiModel(value = "对应模型gift")
public class Gift implements Serializable {

    private static final long serialVersionUID = -3903559677538999971L;
    
    @TableId(type = IdType.ID_WORKER)
    @ApiModelProperty(value = "奖品id")
    private Long giftId;
    
    @ApiModelProperty(value = "奖品名称")
    private String giftName;
    
    @ApiModelProperty(value = "奖品类型")
    private Integer giftType;
    
    @ApiModelProperty(value = "奖品价值")
    private Integer giftValue;
    
    @ApiModelProperty(value = "奖品获取方式")
    private Integer gainType;
    
    @ApiModelProperty(value = "奖品所需积分")
    private Integer needPointAmount;
    
    @ApiModelProperty(value = "奖品数量")
    private Integer giftAmount;
    
    @ApiModelProperty(value = "奖品剩余数量")
    private Integer giftLeftAmount;
    
    @ApiModelProperty(value = "奖品已兑换数量")
    private Integer giftExAmount;
    
    @ApiModelProperty(value = "每次可兑换数量")
    private Integer gainAmount;
    
    @ApiModelProperty(value = "最多可兑换数量")
    private Integer totalGainAmount;
    
    @ApiModelProperty(value = "是否下架，1：是 0：否")
    private Integer isDown;
    
    @ApiModelProperty(value = "最近下架时间")
    private Date downDate;
    
    @ApiModelProperty(value = "最近上架时间")
    private Date upDate;
    
    @ApiModelProperty(value = "有效期开始时间")
    private Date effDate;
    
    @ApiModelProperty(value = "有效期截止时间")
    private Date expDate;
    
    @ApiModelProperty(value = "是否删除")
    private Integer isDel;
    
    @ApiModelProperty(value = "创建日期")
    private Date createDate;
    
    @ApiModelProperty(value = "创建用户")
    private Integer createUser;


}
