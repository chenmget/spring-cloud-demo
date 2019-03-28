package com.iwhalecloud.retail.oms.entity.gif;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

/**
 * @author hesw
 * @date 2018/10/16
 * @Description: 用户积分流水表
 */
@Data
@TableName("user_point_record")
public class UserPointRecord implements Serializable {

    private static final long serialVersionUID = -3903559677538999971L;

    @TableId(type = IdType.ID_WORKER)
    @ApiModelProperty(value = "积分流水id")
    private Long recordId;
    
    @ApiModelProperty(value = "用户id")
    private Long userId;
    
    @ApiModelProperty(value = "改变积分数")
    private Integer changePoint;
    
    @ApiModelProperty(value = "类型")
    private Integer srcType;
    
    @ApiModelProperty(value = "备注")
    private String remark;
    
    @ApiModelProperty(value = "创建时间")
    private Date createDate;
    
    @ApiModelProperty(value = "用户当前总积分")
    private Integer totalPoint;


}
