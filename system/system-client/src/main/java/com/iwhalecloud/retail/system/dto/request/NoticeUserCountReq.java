package com.iwhalecloud.retail.system.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(value = "通知用户关联 计数查询，对应模型sys_notice_user, 对应实体NoticeUser类")
public class NoticeUserCountReq implements Serializable {

    private static final long serialVersionUID = -5902987218666757278L;

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
