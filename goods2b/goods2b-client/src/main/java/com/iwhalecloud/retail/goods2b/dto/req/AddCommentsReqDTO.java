package com.iwhalecloud.retail.goods2b.dto.req;

import com.iwhalecloud.retail.dto.AbstractRequest;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author My
 * @Date 2018/11/28
 **/
@Data
public class AddCommentsReqDTO extends AbstractRequest implements Serializable {
    /**
     * forCommentId
     */
    @ApiModelProperty(value = "forCommentId")
    private java.lang.String forCommentId;

    /**
     * 关联ID
     */
    @ApiModelProperty(value = "关联ID")
    private java.lang.String objectId;

    /**
     * 消息类型
     */
    @ApiModelProperty(value = "消息类型")
    private java.lang.String objectType;

    /**
     * authorId
     */
    @ApiModelProperty(value = "authorId")
    private java.lang.String authorId;

    /**
     * author
     */
    @ApiModelProperty(value = "author")
    private java.lang.String author;

    /**
     * levelname
     */
    @ApiModelProperty(value = "levelname")
    private java.lang.String levelname;

    /**
     * contact
     */
    @ApiModelProperty(value = "contact")
    private java.lang.String contact;

    /**
     * memReadStatus
     */
    @ApiModelProperty(value = "memReadStatus")
    private java.lang.String memReadStatus;

    /**
     * 是否已读
     */
    @ApiModelProperty(value = "是否已读")
    private java.lang.String admReadStatus;

    /**
     * time
     */
    @ApiModelProperty(value = "time")
    private java.util.Date time;

    /**
     * lastreply
     */
    @ApiModelProperty(value = "lastreply")
    private java.lang.String lastreply;

    /**
     * replyName
     */
    @ApiModelProperty(value = "replyName")
    private java.lang.String replyName;

    /**
     * title
     */
    @ApiModelProperty(value = "title")
    private java.lang.String title;

    /**
     * commt
     */
    @ApiModelProperty(value = "commt")
    private java.lang.String commt;

    /**
     * ip
     */
    @ApiModelProperty(value = "ip")
    private java.lang.String ip;

    /**
     * 是否展示
     */
    @ApiModelProperty(value = "是否展示")
    private java.lang.String display;

    /**
     * pindex
     */
    @ApiModelProperty(value = "pindex")
    private java.lang.Long pindex;

    /**
     * disabled
     */
    @ApiModelProperty(value = "disabled")
    private java.lang.String disabled;

    /**
     * commenttype
     */
    @ApiModelProperty(value = "commentType")
    private java.lang.String commentType;

    /**
     * grade
     */
    @ApiModelProperty(value = "grade")
    private java.lang.Long grade;

    /**
     * img
     */
    @ApiModelProperty(value = "img")
    private java.lang.String img;

    /**
     * 订单id
     */
    @ApiModelProperty(value = "订单id")
    private java.lang.String orderId;

    /**
     * quotas
     */
    @ApiModelProperty(value = "quotas")
    private java.lang.String quotas;
    @ApiModelProperty(value = "类型：1商品 2订单 默认为1")
    private String action = "1";

    private String memberId;
}
