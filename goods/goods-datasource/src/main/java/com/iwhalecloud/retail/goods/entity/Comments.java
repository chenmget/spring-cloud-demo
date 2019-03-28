package com.iwhalecloud.retail.goods.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author My
 * @Date 2018/11/27
 **/
@Data
@ApiModel(value = "对应模型pord_comments, 对应实体ord_comments类")
@TableName("prod_comments")
public class Comments implements Serializable {

    /**表名常量*/
    public static final String TNAME = "prod_comments";
    private static final long serialVersionUID = 1L;


    //属性 begin
    /**
     * commentId
     */
    @TableId(type = IdType.ID_WORKER_STR)
    @ApiModelProperty(value = "commentId")
    private java.lang.String commentId;

    /**
     * forCommentId
     */
    @ApiModelProperty(value = "forCommentId")
    private java.lang.String forCommentId;

    /**
     * 关联ID 商品ID
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
    @ApiModelProperty(value = "pIndex")
    private java.lang.Long pIndex;

    /**
     * disabled
     */
    @ApiModelProperty(value = "disabled")
    private java.lang.String disabled;

    /**
     * commentType
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


    //属性 end

    public static enum FieldNames{
        /** commentId */
        commentId,
        /** forCommentId */
        forCommentId,
        /** 关联ID */
        objectId,
        /** 消息类型 */
        objectType,
        /** authorId */
        authorId,
        /** author */
        author,
        /** levelname */
        levelname,
        /** contact */
        contact,
        /** memReadStatus */
        memReadStatus,
        /** 是否已读 */
        admReadStatus,
        /** time */
        time,
        /** lastreply */
        lastreply,
        /** replyName */
        replyName,
        /** title */
        title,
        /** commt */
        commt,
        /** ip */
        ip,
        /** 是否展示 */
        display,
        /** pindex */
        pIndex,
        /** disabled */
        disabled,
        /** commenttype */
        commentType,
        /** grade */
        grade,
        /** img */
        img,
        /** 订单id */
        orderId,
        /** quotas */
        quotas
    }
}
