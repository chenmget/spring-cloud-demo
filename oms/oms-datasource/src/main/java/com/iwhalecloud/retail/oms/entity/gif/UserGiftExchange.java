package com.iwhalecloud.retail.oms.entity.gif;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author he.sw
 * @date 2018/10/16
 * @Description: 用户奖品兑换表
 */
@Data
@TableName("user_gift_exchange")
public class UserGiftExchange implements Serializable {

    private static final long serialVersionUID = -3903559677538999971L;

    @TableId(type = IdType.ID_WORKER)
    @ApiModelProperty(value = "兑奖id")
    private Long exchangeId;
    
    @ApiModelProperty(value = "用户id")
    private Long userId;
    
    @ApiModelProperty(value = "奖品id")
    private Long giftId;
    
    @ApiModelProperty(value = "兑换时间")
    private Date createDate;
    
    @ApiModelProperty(value = "奖品名称")
    private String giftName;
    
    @ApiModelProperty(value = "兑换奖品所需积分")
    private Integer needPointAmount;
    
    @ApiModelProperty(value = "奖品类型")
    private Integer giftType;
    
    @ApiModelProperty(value = "兑换奖品数量")
    private Integer gainAmount;
    
    @ApiModelProperty(value = "用户当前总积分")
    private Integer totalPoint;


}
