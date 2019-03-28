package com.iwhalecloud.retail.goods2b.dto.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Author My
 * @Date 2018/10/10
 **/
@Data
@ApiModel(value = "添加评论对象")
public class CommentsRequestDTO implements Serializable {
    @ApiModelProperty(value = "评论对象")
    private List<AddCommentsReqDTO> comments;

    @ApiModelProperty(value = "用户ID")
    private String memberId;

    private String action = "1";
}
