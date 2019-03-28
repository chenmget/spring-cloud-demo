package com.iwhalecloud.retail.system.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "通知用户关联更新，对应模型sys_notice_user, 对应实体NoticeUser类")
public class NoticeUserUpdateReq implements java.io.Serializable {
    private static final long serialVersionUID = 5325220367733728294L;

    //属性 begin
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

    /**
     * 用户名称
     */
    @ApiModelProperty(value = "用户名称")
    private String userName;

    /**
     * 状态 	0：无效 1：有效 2：待审核 3：审核不通过
     */
    @ApiModelProperty(value = "状态 	0：无效 1：有效 2：待审核 3：审核不通过")
    private String status;

    /**
     * 已读时间
     */
    @ApiModelProperty(value = "已读时间")
    private java.util.Date readTime;


}