package com.iwhalecloud.retail.system.dto.request;

import com.iwhalecloud.retail.dto.PageVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel(value = "公告通知分页列表请求对象")
public class NoticePageReq extends PageVO {

    /**
     * 通知类型 1：业务类 2：热点消息 3：通知公告
     */
    @ApiModelProperty(value = "通知类型 1：业务类 2：热点消息 3：通知公告")
    private List<String> noticeType;

    /**
     * 通知标题
     */

    @ApiModelProperty(value = "通知标题")
    private String noticeTitle;

    /**
     * 状态 	0：无效 1：有效 2：待审核 3：审核不通过
     */
    @ApiModelProperty(value = "状态 	0：无效 1：有效 2：待审核 3：审核不通过")
    private String status;

    /**
     * 发布类型  1：系统类 ，表示不需要指定用户 2：定向类，表示指定用户阅读。关联sys_notice_user表
     */
    @ApiModelProperty(value = "发布类型  1：系统类 ，表示不需要指定用户 2：定向类，表示指定用户阅读。关联sys_notice_user表")
    private List<String> publishType;

    /**
     * 通知id集合
     */
    @ApiModelProperty(value = "通知id集合")
    private List<String> noticeIdList;

    /**
     * 是否查询所有消息
     */
    @ApiModelProperty(value = "是否查询所有消息 是：1 否：2")
    private String qryAllNotice;

//
//    /**
//     * 用户ID
//     */
//    @ApiModelProperty(value = "用户ID")
//    private String userId;
//
//    /**
//     * 已读未读状态
//     */
//    @ApiModelProperty(value = "已读未读状态")
//    private String readStatus;


}
