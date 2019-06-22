package com.iwhalecloud.retail.system.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(value = "通知用户关联 列表查询")
public class NoticeUserListReq implements Serializable {

    private static final long serialVersionUID = -6038628918050437849L;
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

    /**
     * 状态 	0：未读 1：已读
     */
    @ApiModelProperty(value = "状态 	0：未读 1：已读")
    private String status;

}
