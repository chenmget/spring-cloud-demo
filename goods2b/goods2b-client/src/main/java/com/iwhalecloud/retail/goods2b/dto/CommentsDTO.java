package com.iwhalecloud.retail.goods2b.dto;

import com.iwhalecloud.retail.goods2b.dto.req.AddCommentsReqDTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Author My
 * @Date 2018/11/12
 **/
@Data
public class CommentsDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     *评论等级
     */
    private String grade;

    @ApiModelProperty(value = "会员ID")
    private String memberId;

    @ApiModelProperty(value = "评论信息")
    private List<AddCommentsReqDTO> comment;

    @ApiModelProperty(value = "类型：1商品 2订单 默认为1")
    private String action = "1";
}
