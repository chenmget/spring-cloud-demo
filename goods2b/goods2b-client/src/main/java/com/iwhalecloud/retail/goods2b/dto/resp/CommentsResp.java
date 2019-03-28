package com.iwhalecloud.retail.goods2b.dto.resp;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author My
 * @Date 2018/12/26
 **/
@Data
public class CommentsResp implements Serializable {
    /**
     *评论人
     */
    @ApiModelProperty(value = "评论人")
    private java.lang.String author;
    /**
     * 评论时间
     */
    @ApiModelProperty(value = "评论时间")
    private java.util.Date time;
    /**
     * 评论内容
     */
    @ApiModelProperty(value = "评论内容")
    private java.lang.String quotas;
    /**
     * 评论图片
     */
    @ApiModelProperty(value = "评论图片")
    private java.lang.String img;
}
