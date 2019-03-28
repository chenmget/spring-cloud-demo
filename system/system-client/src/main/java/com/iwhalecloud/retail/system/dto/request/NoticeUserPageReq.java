package com.iwhalecloud.retail.system.dto.request;

import com.iwhalecloud.retail.dto.PageVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

@Data
@ApiModel(value = "通知用户关联 分页查询，对应模型sys_notice_user, 对应实体NoticeUser类")
public class NoticeUserPageReq extends PageVO {

    /**
     * 公告ID
     */
    @ApiModelProperty(value = "公告ID")
    @NotEmpty(message = "通知ID不能为空")
    private String noticeId;

    /**
     * 用户ID
     */
//    @ApiModelProperty(value = "用户ID")
//    private String userId;

    /**
     * 状态 	0：未读 1：已读
     */
//    @ApiModelProperty(value = "状态 	0：未读 1：已读")
//    private String status;


}