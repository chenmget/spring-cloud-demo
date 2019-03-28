package com.iwhalecloud.retail.goods2b.dto.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author My
 * @Date 2018/12/1
 **/
@Data
public class CommentAddVoReq implements Serializable {
    @ApiModelProperty(value = "用户ID")
    private String memberId;

    @ApiModelProperty(value = "评论信息")
    private AddCommentsReqDTO comment;

    @ApiModelProperty(value = "评论信息")
    private String action = "1";
}
