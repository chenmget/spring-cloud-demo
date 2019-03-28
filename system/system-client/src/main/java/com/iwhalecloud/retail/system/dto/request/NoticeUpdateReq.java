package com.iwhalecloud.retail.system.dto.request;

import com.iwhalecloud.retail.system.dto.FileDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

import java.io.Serializable;
import java.util.List;

@Data
@ApiModel(value = "更新公告通知请求对象")
public class NoticeUpdateReq implements Serializable {
    private static final long serialVersionUID = -5990511796413602849L;

    //属性 begin
    /**
     * 通知ID
     */
    @ApiModelProperty(value = "通知ID")
    @NotEmpty(message = "通知ID不能为空")
    private String noticeId;

    /**
     * 用户ID集合，发布定向类通知时使用
     */
    @ApiModelProperty(value = "用户ID集合，发布定向类通知时使用")
    private List<String> userIdList;

    /**
     * 通知类型 1：业务类 2：热点消息 3：通知公告
     */
    @ApiModelProperty(value = "通知类型 1：业务类 2：热点消息 3：通知公告")
    private String noticeType;

    /**
     * 通知标题
     */
    @ApiModelProperty(value = "通知标题")
    private String noticeTitle;

    /**
     * 通知内容
     */
    @ApiModelProperty(value = "通知内容")
    private String noticeContent;

    /**
     * 发布类型  1：系统类 ，表示不需要指定用户 2：定向类，表示指定用户阅读。关联sys_notice_user表
     */
    @ApiModelProperty(value = "发布类型  1：系统类 ，表示不需要指定用户 2：定向类，表示指定用户阅读。关联sys_notice_user表")
    private String publishType;


    /**
     * 修改时间
     */
//    @ApiModelProperty(value = "修改时间")
//    private java.util.Date updateTime;

    /**
     * 修改用户ID
     */
    @ApiModelProperty(value = "修改用户ID")
    private String updateUserId;

    /**
     * 状态 	0：无效 1：有效 2：待审核 3：审核不通过
     */
    @ApiModelProperty(value = "状态 	0：无效 1：有效 2：待审核 3：审核不通过")
    private String status;

    /**
     * 公告发布开始时间
     */
    @ApiModelProperty(value = "公告发布开始时间")
    private java.util.Date beginTime;

    /**
     * 公告发布结束时间
     */
    @ApiModelProperty(value = "公告发布结束时间")
    private java.util.Date endTime;

    /**
     * 附件路径
     */
    @ApiModelProperty(value = "附件内容")
    private List<FileDTO> file;

    /**
     * 附件路径
     */
    @ApiModelProperty(value = "附件路径")
    private String fileUrl;

    /**
     * 无用time字段
     */
    @ApiModelProperty(value = "无用time字段")
    private java.util.Date time;
}