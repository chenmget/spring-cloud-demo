package com.iwhalecloud.retail.goods.dto;

import lombok.Data;

/**
 * @Author My
 * @Date 2018/11/12
 **/
@Data
public class CommentsPageListDTO extends PageVO{

    /**
     * 好评率
     */
    private String goodCommentRate;
    public String error_code = "-1";
    public String error_msg;

}
