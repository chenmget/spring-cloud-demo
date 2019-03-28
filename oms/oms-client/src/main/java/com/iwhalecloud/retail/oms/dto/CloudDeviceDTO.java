package com.iwhalecloud.retail.oms.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.io.Serializable;
import java.util.Date;

/**
 * @Auther: lin.wh
 * @Date: 2018/10/17 15:57
 * @Description: 云货架终端设备实体类
 */

@Data
@ApiModel(value = "对应模型cloud_device, 对应实体CloudDeviceDTO类")
public class CloudDeviceDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id")
    private java.lang.Long id; //id

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

    @ApiModelProperty(value = "设备编码")
    private String num; //设备编码:全局唯一

    @ApiModelProperty(value = "绑定的云货架编码")
    private String cloudShelfNumber; //绑定的云货架编码

    @ApiModelProperty(value = "设备状态")
    private int status; //设备状态:未激活、启用、停用

    @ApiModelProperty(value = "设备名称")
    private String deviceName; //设备名称

    @ApiModelProperty(value = "设备类型")
    private String deviceType; //设备类型

    @ApiModelProperty(value = "在线状态")
    private String onlineType; //在线状态：0首次连接、10在线、20离线、30故障、40未知状态

    @ApiModelProperty(value = "累计工作时长")
    private String totallyWorkTime; //累计工作时长：单位秒，从log表中汇总

    @ApiModelProperty(value = "所属厅店")
    private String adscriptionShop; //所属厅店

    @ApiModelProperty(value = "所属厅店名称")
    private String adscriptionShopName; //所属厅店名称

    @ApiModelProperty(value = "所属城市")
    private String adscriptionCity; //所属城市

    @ApiModelProperty(value = "所属城市名称")
    private String adscriptionCityName; //所属城市名称

    @ApiModelProperty(value = "是否允许删除")
    private int canDelete; //是否允许删除：0否、1是

    @ApiModelProperty(value = "所属城区")
    private String adscriptionCityArea; //所属城区

    @ApiModelProperty(value = "设备描述")
    private String deviceDesc; //设备描述

}

