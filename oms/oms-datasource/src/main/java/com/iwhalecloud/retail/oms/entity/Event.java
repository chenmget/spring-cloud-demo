package com.iwhalecloud.retail.oms.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @Auther: lin.wh
 * @Date: 2018/11/6 09:38
 * @Description:
 */

@Data
@ApiModel(value = "对应模型event, 对应实体Event类")
@TableName("event")
public class Event implements Serializable {
    /**
     * 表名常量
     */
    public static final String TNAME = "event";
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id")
    @TableId(type = IdType.ID_WORKER)
    private Long id; //id

    @ApiModelProperty(value = "创建时间")
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
    private Date gmtCreate; //创建时间

    @ApiModelProperty(value = "修改时间")
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
    private Date gmtModified; //修改时间

    @ApiModelProperty(value = "创建人")
    private String creator; //创建人

    @ApiModelProperty(value = "修改人")
    private String modifier; //修改人

    @ApiModelProperty(value = "是否删除")
    private int isDeleted; //是否删除：0未删、1删除

    @ApiModelProperty(value = "设备编号")
    private String deviceNumber; //设备编号：可使用MAC地址

    @ApiModelProperty(value = "事件编号")
    private String eventCode; //事件编号

    @ApiModelProperty(value = "事件名称")
    private String eventName; //事件名称

    @ApiModelProperty(value = "事件类型")
    private int eventType; //事件类型：0点击操作、1滑屏操作

    @ApiModelProperty(value = "事件来源")
    private String eventSource; //事件来源：0云货架，1小程序

    @ApiModelProperty(value = "上一个事件id")
    private String preEventCode; //上一个事件id

    @ApiModelProperty(value = "事件扩展属性")
    private String eventExtend; //事件扩展属性

    @ApiModelProperty(value = "功能菜单地址")
    private String menuUrl; //功能菜单地址

    @ApiModelProperty(value = "是否超出思考时间")
    private String isExpire; //是否超出思考时间：是否删除：0未超时、1超出

    @ApiModelProperty(value = "事件扩展属性扩充1")
    private String eventExtendExtra1; //事件扩展属性扩充1，存放商品名称

    @ApiModelProperty(value = "事件扩展属性扩充2")
    private String eventExtendExtra2; //事件扩展属性扩充2，存放商品评价星级

    @ApiModelProperty(value = "设备链接")
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
    private Date firstConnectTime; //操作开始时间

    @ApiModelProperty(value = "内容ID")
    private String contentId; //内容ID

    @ApiModelProperty(value = "分销商ID")
    private String partnerId;// 分销商
}

