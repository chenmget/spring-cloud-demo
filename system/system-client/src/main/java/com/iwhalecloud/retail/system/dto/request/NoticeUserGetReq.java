package com.iwhalecloud.retail.system.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(value = "通知用户关联 列表查询")
public class NoticeUserGetReq implements Serializable {

    private static final long serialVersionUID = -4466102548745550475L;

    /**
     * ID
     */
    @ApiModelProperty(value = "ID")
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