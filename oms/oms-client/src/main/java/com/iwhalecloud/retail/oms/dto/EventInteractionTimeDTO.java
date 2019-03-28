package com.iwhalecloud.retail.oms.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @Auther: lin.wh
 * @Date: 2018/11/5 20:31
 * @Description: 事件采集日志
 */

@Data
@ApiModel(value = "对应模型event_interaction_time, 对应实体EventInteractionTimeDTO类")
public class EventInteractionTimeDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id")
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
    private int isDeleted; //是否删除：0未删、1删除

    @ApiModelProperty(value = "设备编号")
    private String deviceNumber; //设备编号:可使用MAC地址

    @ApiModelProperty(value = "所属厅店ID")
    private String adscriptionShopId; //所属厅店ID

    @ApiModelProperty(value = "所属厅店名称")
    private String adscriptionShopName;

    @ApiModelProperty(value = "所属城市")
    private String adscriptionCity; //所属城市

    @ApiModelProperty(value = "所属城区")
    private String adscriptionCityArea; //所属城区

    @ApiModelProperty(value = "交互时长")
    private long interactionTime; //交互时长：单位秒
}

