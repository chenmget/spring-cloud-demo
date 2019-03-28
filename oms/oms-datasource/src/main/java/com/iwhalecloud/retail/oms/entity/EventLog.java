package com.iwhalecloud.retail.oms.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@TableName("event")
public class EventLog implements Serializable {
    @TableId(type= IdType.ID_WORKER)
    private Long id;

    private Date gmtCreate;

    private Date gmtModified;

    private String creator;

    private String modifer;

    private Integer isDeleted;

    private String deviceNumber;

    private String eventCode;

    private String eventName;

    private Integer eventType;

    private String eventSource;

    private String preEventCode;

    private String eventExtend;

    private String menuUrl;

    private String isExpire;

    private String eventExtendExtra1;
}
