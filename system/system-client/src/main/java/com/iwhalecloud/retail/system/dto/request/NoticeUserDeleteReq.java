package com.iwhalecloud.retail.system.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;


@Data
@ApiModel(value = "通知用户关联 列表查询")
public class NoticeUserDeleteReq implements Serializable {

    private static final long serialVersionUID = 1982914815743563559L;

    /**
     * 通知用户关联ID
     */
    @ApiModelProperty(value = "通知用户关联ID")
    private String id;

    /**
     * 公告ID
     */
    @ApiModelProperty(value = "公告ID")
    private String noticeId;

    /**
     * 用户ID
     */
    @ApiModelProperty(value = "用户ID")
    private String userId;


}