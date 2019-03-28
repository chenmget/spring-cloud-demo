package com.iwhalecloud.retail.oms.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @Auther: lin.wh
 * @Date: 2018/10/29 20:21
 * @Description:
 */

@Data
@TableName("cloud_device")
public class CloudDevice implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.ID_WORKER)
    private Long id; //id

    private Date gmtCreate; //创建时间

    private Date gmtModified; //修改时间

    private String creator; //创建人

    private String modifier; //修改人

    private int isDeleted; //是否删除：0未删、1删除

    private String num; //设备编码:全局唯一

    private String cloudShelfNumber; //绑定的云货架编码

    private int status; //设备状态:未激活、启用、停用

    private String deviceName; //设备名称

    private String deviceType; //设备类型

    private String onlineType; //在线状态：0首次连接、10在线、20离线、30故障、40未知状态

    private String totallyWorkTime; //累计工作时长：单位秒，从log表中汇总

    private String adscriptionShop; //所属厅店

    private String adscriptionCity; //所属城市

    private int canDelete; //是否允许删除：0否、1是

    private String adscriptionCityArea; //所属城区

    private String deviceDesc; //设备描述
}

