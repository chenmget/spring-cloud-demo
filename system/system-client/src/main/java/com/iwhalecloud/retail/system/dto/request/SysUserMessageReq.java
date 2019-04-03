package com.iwhalecloud.retail.system.dto.request;

import com.iwhalecloud.retail.system.dto.AbstractPageReq;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author li.yulong
 * @date 2019/3/28 11:03
 */
@Data
@ApiModel("根据userId，查询用户告警消息详情入参")
public class SysUserMessageReq extends AbstractPageReq implements Serializable {

    private String userId;

    private Date beginTime;

    private Date endTime;

    private String messageTitle;

}
